package br.edu.fateczl.VpnGerador.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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

import br.edu.fateczl.VpnGerador.model.Login;
import br.edu.fateczl.VpnGerador.model.Vpn;
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
	// Adicionar VPN, script
	// Remover VPN em List, Excluir do banco e arquivo
	// Procura de VPN por identificador
	// Download de VPN
	@RequestMapping(name = "vpn", value = "/vpn", method = RequestMethod.GET)
	public ModelAndView vpnGet(@RequestParam Map<String, String> params, ModelMap model,HttpServletRequest request) {
		switch (indexC.verificarLogin(request)) {
		case "" -> {return new ModelAndView("redirect:/index");}
	}
	    List<Vpn> vpns = listarFuncionario();
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
		String email = loginRep.fn_procUsuario(login.getUsuario());
		vpn.setFuncionario(funcionarioRep.findById(email).get());
		vpn.setId(gerarId());
		
		ProcessBuilder pb = new ProcessBuilder("sudo","/home/userlinux/VPNscript.sh", login.getUsuario(), login.getSenha(), vpn.getId());
		try {
			Process process = pb.start();

			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String linha;
			while ((linha = reader.readLine()) != null) {
			    System.out.println(linha);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		cadastrarVpn(vpn);
		return new ModelAndView("vpn");
	}

	private void cadastrarVpn(Vpn vpn) {
		vpnRep.save(vpn);
	}

	private List<Vpn> listarFuncionario() {
		return vpnRep.findAll();
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
