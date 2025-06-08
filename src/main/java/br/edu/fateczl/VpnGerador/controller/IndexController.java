package br.edu.fateczl.VpnGerador.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.edu.fateczl.VpnGerador.model.Login;
import br.edu.fateczl.VpnGerador.repository.ILoginRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class IndexController {
	@Autowired
	private ILoginRepository loginRep;

	@RequestMapping(name = "index", value = "/index", method = RequestMethod.GET)
	public ModelAndView loginGet(HttpServletRequest request, ModelMap model) {
		 HttpSession session = request.getSession(false);
		 if (session != null) {
			 session.removeAttribute("login");
		 }
		 return new ModelAndView("index");
	}
	@RequestMapping(name = "index", value = "/index", method = RequestMethod.POST)
	public ModelAndView loginPost(@RequestParam Map<String, String> allRequestParam,HttpServletRequest request,ModelMap model) {
		String usuario = allRequestParam.get("usuarioFuncionario").trim();
		String senha = allRequestParam.get("senhaFuncionario").trim();
		String erro = validar(usuario, senha);
		Login login = new Login();
		login.setUsuario(usuario);
		login.setSenha(senha);
		if ( erro == "") {
			if (loginRep.fn_login(usuario,senha) != null) {
				HttpSession session = request.getSession();
				session.setAttribute("login", login);
				return new ModelAndView("redirect:/vpn");
			} else {
				erro = "Login invalido";
			}
		}
		model.addAttribute("erro",erro);
		return new ModelAndView("index");
	}

	public String verificarLogin (HttpServletRequest request) {
		 HttpSession session = request.getSession(false);
		 if (session == null) {
			 return "";
		 }
		 Login login = (Login) session.getAttribute("login");
		 if (login == null) {
			 return "";
		 }
		 String permissao = loginRep.fn_login(login.getUsuario(), login.getSenha());
		 if (permissao == null) {
			 return "";
		 }
		 return permissao;
	}
	
	private String validar (String usuario, String senha) {
		if(usuario == null || usuario == "") {return "Usuario deve ser preenchido";}
		if(senha == null || senha == "") {return "Senha deve ser preenchido";}
		if(usuario.length() > 80 || senha.length() > 30) {return "Login invalido";}
		return "";
	}
}
