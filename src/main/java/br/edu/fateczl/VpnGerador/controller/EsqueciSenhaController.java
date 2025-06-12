package br.edu.fateczl.VpnGerador.controller;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.fateczl.VpnGerador.model.Login;
import br.edu.fateczl.VpnGerador.model.TokenRedefinicao;
import br.edu.fateczl.VpnGerador.repository.ILoginRepository;
import br.edu.fateczl.VpnGerador.repository.ITokenRedefinicaoRepository;
import br.edu.fateczl.VpnGerador.service.EmailService;
import br.edu.fateczl.VpnGerador.service.LoginAttemptService;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class EsqueciSenhaController {
	@Autowired
	private EmailService emailService;
	@Autowired
	private ILoginRepository loginRep;
	@Autowired
	private ITokenRedefinicaoRepository tokenRep;
	@Autowired
	private LoginAttemptService loginAttemptService;
	
	@RequestMapping(name = "esqueciSenha", value = "/esqueciSenha", method = RequestMethod.GET)
	public ModelAndView esqueciSenhaGet(@RequestParam Map<String, String> params, ModelMap model,HttpServletRequest request,RedirectAttributes redirectAttributes) {
		String ip = getClientIP(request);
	    if (loginAttemptService.isBlocked(ip)) {
	        redirectAttributes.addFlashAttribute("erro", "Este IP foi bloqueado, Entre em contato com o administrador da rede.");
	        return new ModelAndView("redirect:/index");
	    }
		return new ModelAndView("esqueciSenha");
	}

	@RequestMapping(name = "esqueciSenha", value = "/esqueciSenha", method = RequestMethod.POST)
	public ModelAndView esqueciSenhaPost(@RequestParam Map<String, String> params, ModelMap model) {
		String email = params.get("emailFuncionario").trim();
		if(loginRep.existsById(email)) {
			tokenRep.sp_del_token(email);
			String token = UUID.randomUUID().toString();
			TokenRedefinicao tr = new TokenRedefinicao();
			tr.setToken(token);
			tr.setLogin(loginRep.findById(email).get());
			tr.setExpiracao(LocalDateTime.now().plusHours(1));
			tokenRep.save(tr);
			//----- O IP a baixo deve ser substituido pelo ip publico do gateway -----
			String linkRedefinicao = "http://192.168.1.12:8080/VPN/redefinirSenha?token=" + token;
	        String corpo = "Olá,\n\nClique no link abaixo para redefinir sua senha:\n" + linkRedefinicao +
                    "\n\nSe você não solicitou isso, apenas ignore.";
	        emailService.enviarEmail(email, "Redefinição de Senha: "+token, corpo);
	        model.addAttribute("mensagem", "Email de redefinição enviado com sucesso!");
		}
		model.addAttribute("erro", "Um e-mail com um link foi enviado para sua caixa de entrada");
		return new ModelAndView("esqueciSenha");
	}

	@RequestMapping(name = "redefinirSenha", value = "/redefinirSenha", method = RequestMethod.GET)
	public ModelAndView redefinirSenhaGet(@RequestParam("token") String token, ModelMap model,HttpServletRequest request,RedirectAttributes redirectAttributes) {
		String ip = getClientIP(request);
	    if (loginAttemptService.isBlocked(ip)) {
	        redirectAttributes.addFlashAttribute("erro", "Este IP foi bloqueado, Entre em contato com o administrador da rede.");
	        return new ModelAndView("redirect:/index");
	    }
		TokenRedefinicao tokenEntity = tokenRep.findById(token.trim()).orElse(null);
	    if (tokenEntity == null || tokenEntity.getExpiracao().isBefore(LocalDateTime.now())) {
	        redirectAttributes.addFlashAttribute("erro","Token inválido ou expirado.");
	        return new ModelAndView("redirect:/index");
	    }
		return new ModelAndView("redefinirSenha");
	}
	@RequestMapping(name = "redefinirSenha", value = "/redefinirSenha", method = RequestMethod.POST)
	public ModelAndView redefinirSenhaPost(@RequestParam Map<String, String> allRequestParam,@RequestParam("token") String token, ModelMap model) {
		TokenRedefinicao tokenEntity = tokenRep.findById(token).orElse(null);
		String senhaNova = allRequestParam.get("novaSenha").trim();
		String senhaNovaC = allRequestParam.get("confirmarSenha").trim();
		String erro = "";
		if(validar(senhaNova,senhaNovaC) && tokenEntity != null) {
				Login login = tokenEntity.getLogin();
				login.setSenha(senhaNovaC);
				tokenRep.sp_del_token(login.getEmail());
				erro = "Sucesso, agora você pode realizar a autenticação";
		} else {
			
			erro = "A senha informada não corresponde com a confirmação de senha";
		}

		model.addAttribute("novaSenha", senhaNova);
		model.addAttribute("confirmarSenha", senhaNovaC);
		model.addAttribute("erro", erro);
		return new ModelAndView("redefinirSenha");
	}
	public Boolean validar(String senhaNova, String senhaNovaC) {
		if(senhaNova == "" || senhaNova == null || senhaNovaC == "" || senhaNovaC == null) {return false;}
		if(senhaNova.length() < 8) {return false;}
		if(!senhaNova.matches(".*[a-zA-Z].*")) {return false;}
		if(!senhaNova.matches(".*[0-9].*")) {return false;}
		if(!senhaNova.matches(".*[!@#$%&*()_\\-+=].*")) {return false;}
		if(!senhaNova.contentEquals(senhaNovaC)) {return false;}
		return true;
	}
	private String getClientIP(HttpServletRequest request) {
	    String xfHeader = request.getHeader("X-Forwarded-For");
	    if (xfHeader == null) {
	        return request.getRemoteAddr(); // IP direto se não houver proxy
	    }
	    return xfHeader.split(",")[0]; // Primeiro IP da cadeia
	}
}
