package br.edu.fateczl.VpnGerador.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
		vpn.setFuncionario(consultarFuncionario(login.getUsuario()));
		vpn.setId(gerarId());
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
	@RequestMapping(name = "vpn", value = "/downloadVpn", method = RequestMethod.POST)
	public ModelAndView downloadVpnPost(@RequestParam Map<String, String> params, ModelMap model,HttpServletRequest request,RedirectAttributes redirectAttributes) {
		System.out.println("download");
		return new ModelAndView("redirect:/vpn");
		
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
