package br.edu.fateczl.VpnGerador.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.fateczl.VpnGerador.model.Login;
import br.edu.fateczl.VpnGerador.repository.ILoginRepository;
import br.edu.fateczl.VpnGerador.service.LoginAttemptService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class IndexController {
	@Autowired
	private ILoginRepository loginRep;
	@Autowired
	private LoginAttemptService loginAttemptService;

	@RequestMapping(name = "index", value = "/index", method = RequestMethod.GET)
	public ModelAndView loginGet(HttpServletRequest request, ModelMap model) {
		 HttpSession session = request.getSession(false);
		 if (session != null) {
			 session.removeAttribute("login");
		 }
		 return new ModelAndView("index");
	}
	@RequestMapping(name = "index", value = "/index", method = RequestMethod.POST)
	public ModelAndView loginPost(@RequestParam Map<String, String> allRequestParam,HttpServletRequest request,ModelMap model,RedirectAttributes redirectAttributes) {
		String ip = getClientIP(request);
	    if (loginAttemptService.isBlocked(ip)) {
	        redirectAttributes.addFlashAttribute("erro", "Este IP foi bloqueado, Entre em contato com o administrador da rede.");
	        return new ModelAndView("redirect:/index");
	    }
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
				loginAttemptService.loginSucceeded(ip);
				return new ModelAndView("redirect:/vpn");
			} else {
				loginAttemptService.loginFailed(ip);
				erro = "Login invalido";
			}
		}else {
			loginAttemptService.loginFailed(ip);
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
	private String getClientIP(HttpServletRequest request) {
	    String xfHeader = request.getHeader("X-Forwarded-For");
	    if (xfHeader == null) {
	        return request.getRemoteAddr(); // IP direto se não houver proxy
	    }
	    return xfHeader.split(",")[0]; // Primeiro IP da cadeia
	}

}
