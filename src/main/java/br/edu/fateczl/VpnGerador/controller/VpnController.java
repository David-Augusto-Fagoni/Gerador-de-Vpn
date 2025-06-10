package br.edu.fateczl.VpnGerador.controller;

import java.io.File;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.fateczl.VpnGerador.model.Funcionario;
import br.edu.fateczl.VpnGerador.model.Login;
import br.edu.fateczl.VpnGerador.model.Vpn;
import br.edu.fateczl.VpnGerador.model.VpnId;
import br.edu.fateczl.VpnGerador.repository.IFuncionarioRepository;
import br.edu.fateczl.VpnGerador.repository.ILoginRepository;
import br.edu.fateczl.VpnGerador.repository.IVpnRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;



@Controller
public class VpnController {
	@Autowired
	private IFuncionarioRepository funcionarioRep;
	@Autowired
	private ILoginRepository loginRep;
	@Autowired
	private IVpnRepository vpnRep;
	@Autowired
	private IndexController indexC;

	// Remover VPN em List, Excluir do banco e arquivo {removerVPN.sh}
	// Procura de VPN por identificador
	// Download de VPN
	@RequestMapping(name = "vpn", value = "/vpn", method = RequestMethod.GET)
	public ModelAndView vpnGet(@RequestParam Map<String, String> params, ModelMap model,HttpServletRequest request) {
		switch (indexC.verificarLogin(request)) {
		case "" -> {return new ModelAndView("redirect:/index");}
	}
		List<Vpn> vpns = new ArrayList<>();
		if(model.get("vpns") == null) {
			vpns = listarVpns();
		}else {
			vpns =  (List<Vpn>) model.get("vpns");
		}
	    String erro = (String) model.get("erro");
	    model.addAttribute("erro", erro);
	    model.addAttribute("vpns", vpns);
		return new ModelAndView("vpn");
	}
	@RequestMapping(name = "vpn", value = "/vpn", method = RequestMethod.POST)
	public ModelAndView vpnPost(@RequestParam Map<String, String> params, ModelMap model,HttpServletRequest request) {
	switch (indexC.verificarLogin(request)) {
		case "" -> {return new ModelAndView("redirect:/index");}
	}
		HttpSession session = request.getSession(false);
		Login login = (Login) session.getAttribute("login");
		Vpn vpn = new Vpn();
		Funcionario funcionario = consultarFuncionario(login.getUsuario());
		vpn.setFuncionario(funcionario);
		vpn.setId(gerarId());
		VpnId vpnId = new VpnId();
		vpnId.setFuncionario(funcionario);
		vpnId.setId(vpn.getId());
		while (vpnExists(vpnId)) {
			vpn.setId(gerarId());
			vpnId.setId(vpn.getId());
		}
		vpn.setDt_criacao(LocalDateTime.now().toLocalDate());
		vpn.setDt_validade(LocalDateTime.now().plusDays(7).toLocalDate());
		ProcessBuilder pb = new ProcessBuilder("sudo","/home/userlinux/VPNscript.sh", login.getUsuario(), login.getSenha(), vpn.getId());
		try {
			pb.start();
			cadastrarVpn(vpn);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new ModelAndView("redirect:/vpn");
	}
	@RequestMapping(name = "vpn", value = "/removerVpn", method = RequestMethod.POST)
	public ModelAndView removerVpnPost(@RequestParam Map<String, String> params, ModelMap model,HttpServletRequest request,@RequestParam(value = "selecionados", required = false) String[] selecionados,RedirectAttributes redirectAttributes) {
		HttpSession session = request.getSession(false);
	    if (selecionados == null) {
	    	String erro = "Nenhuma VPN selecionada";
	    	redirectAttributes.addFlashAttribute("erro",erro);
	    	return new ModelAndView("redirect:/vpn");
	    }
		Login login = (Login) session.getAttribute("login");
		Funcionario funcionario = consultarFuncionario(login.getUsuario());
		VpnId vpnId = new VpnId();
		vpnId.setFuncionario(funcionario);
		for (String id : selecionados) {
			vpnId.setId(id);
				if (vpnExists(vpnId)) {
				ProcessBuilder pb = new ProcessBuilder("sudo","/home/userlinux/removerVPN.sh", login.getUsuario(), login.getSenha(),id);
				removeVpn(vpnId);
				try {
					pb.start();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return new ModelAndView("redirect:/vpn");

	}
	@RequestMapping(name = "vpn", value = "/procurarVpn", method = RequestMethod.POST)
	public ModelAndView procurarVpnPost(@RequestParam Map<String, String> allRequestParam,@RequestParam Map<String, String> params, ModelMap model,HttpServletRequest request,RedirectAttributes redirectAttributes) {
		String erro = "";
		String id = allRequestParam.get("nomeVpn");
		if (id == "") {
	    	return new ModelAndView("redirect:/vpn");
		}
		HttpSession session = request.getSession(false);
		Login login = (Login) session.getAttribute("login");
		Funcionario funcionario = consultarFuncionario(login.getUsuario());
		List<Vpn> vpns= procVpns(id,funcionario.getEmail());
		if (vpns.size() == 0) {
			erro = "Sem resultados";
			vpns = null;
		}
		redirectAttributes.addFlashAttribute("erro",erro);
		redirectAttributes.addFlashAttribute("vpns",vpns);
		return new ModelAndView("redirect:/vpn");

	}

	@GetMapping("/downloadVpn/{id}")
	public ResponseEntity<Resource> downloadVpn(@PathVariable String id,ModelMap model,HttpServletRequest request) {
		if (id != null && indexC.verificarLogin(request) != "") {
			HttpSession session = request.getSession(false);
			Login login = (Login) session.getAttribute("login");

			String zipPath = "/home/userlinux/" + login.getUsuario() + "/" + id + "/" + id + ".zip";
			File zipFile = new File(zipPath);
	        if (!zipFile.exists() || !zipFile.canRead()) {
	            return ResponseEntity.notFound().build();
	        }
	        Resource resource = new FileSystemResource(zipFile);
	        return ResponseEntity.ok()
	        		.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + id + ".zip\"")
	        		.contentLength(zipFile.length())
	        		.contentType(MediaType.APPLICATION_OCTET_STREAM)
	        		.body(resource);
		}
	    return ResponseEntity.notFound().build();
	}
	private void cadastrarVpn(Vpn vpn) {
		vpnRep.save(vpn);
	}

	private List<Vpn> listarVpns() {
		return vpnRep.findAll();
	}

	private List<Vpn> procVpns(String id, String email){
		return vpnRep.buscarVpn(id, email);
	}

	private void removeVpn(VpnId vpn) {
		vpnRep.deleteById(vpn);
	}
	private boolean vpnExists(VpnId vpn) {
		return vpnRep.existsById(vpn);
	}
	private Funcionario consultarFuncionario(String usuario) {
		String email = loginRep.fn_procUsuario(usuario);
		return funcionarioRep.findById(email).get();
	}
	private String gerarId() {
		String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < 7; i++) {
            int indice = random.nextInt(caracteres.length());
            sb.append(caracteres.charAt(indice));
		}
		return sb.toString();

	}
}
