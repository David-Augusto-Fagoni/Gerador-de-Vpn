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

import br.edu.fateczl.VpnGerador.model.Login;
import br.edu.fateczl.VpnGerador.model.TokenRedefinicao;
import br.edu.fateczl.VpnGerador.repository.ILoginRepository;
import br.edu.fateczl.VpnGerador.repository.ITokenRedefinicaoRepository;
import br.edu.fateczl.VpnGerador.service.EmailService;

@Controller
public class EsqueciSenhaController {
	@Autowired
	private EmailService emailService;
	@Autowired
	private ILoginRepository loginRep;
	@Autowired
	private ITokenRedefinicaoRepository tokenRep;

	@RequestMapping(name = "esqueciSenha", value = "/esqueciSenha", method = RequestMethod.GET)
	public ModelAndView esqueciSenhaGet(@RequestParam Map<String, String> params, ModelMap model) {
		return new ModelAndView("esqueciSenha");
	}

	@RequestMapping(name = "esqueciSenha", value = "/esqueciSenha", method = RequestMethod.POST)
	public ModelAndView esqueciSenhaPost(@RequestParam Map<String, String> params, ModelMap model) {
		String email = "david.fagoni@gmail.com";
		if(loginRep.existsById(email+"@empresa.com.br")) {
			tokenRep.sp_del_token(email+"@empresa.com.br");
			String token = UUID.randomUUID().toString();
			TokenRedefinicao tr = new TokenRedefinicao();
			tr.setToken(token);
			tr.setLogin(loginRep.findById(email+"@empresa.com.br").get());
			tr.setExpiracao(LocalDateTime.now().plusHours(1));
			tokenRep.save(tr);
			String linkRedefinicao = "http://localhost:8080/VPN/redefinirSenha?token=" + token;
	        String corpo = "Olá,\n\nClique no link abaixo para redefinir sua senha:\n" + linkRedefinicao +
                    "\n\nSe você não solicitou isso, entre em contato com a administração.";
	        emailService.enviarEmail(email, "Redefinição de Senha", corpo);
	        model.addAttribute("mensagem", "Email de redefinição enviado com sucesso!");
		} else {
			model.addAttribute("erro", "Email não encontrado.");
		}
		return new ModelAndView("esqueciSenha");
	}

	@RequestMapping(name = "redefinirSenha", value = "/redefinirSenha", method = RequestMethod.GET)
	public ModelAndView redefinirSenhaGet(@RequestParam("token") String token, ModelMap model) {
		TokenRedefinicao tokenEntity = tokenRep.findById(token).orElse(null);
	    if (tokenEntity == null || tokenEntity.getExpiracao().isBefore(LocalDateTime.now())) {
	        model.addAttribute("erro", "Token inválido ou expirado.");
	        return new ModelAndView("erroToken", model);
	    }
		return new ModelAndView("redefinirSenha");
	}
	@RequestMapping(name = "redefinirSenha", value = "/redefinirSenha", method = RequestMethod.POST)
	public ModelAndView redefinirSenhaPost(@RequestParam Map<String, String> allRequestParam,@RequestParam("token") String token, ModelMap model) {
		TokenRedefinicao tokenEntity = tokenRep.findById(token).orElse(null);
		String senhaNova = allRequestParam.get("novaSenha").trim();
		String senhaNovaC = allRequestParam.get("confirmarSenha").trim();
		String erro = validar(senhaNova,senhaNovaC);
		if(erro == "") {
			Login login = tokenEntity.getLogin();
			login.setSenha(senhaNovaC);
			loginRep.save(login);
		}

		model.addAttribute("novaSenha", senhaNova);
		model.addAttribute("confirmarSenha", senhaNovaC);
		model.addAttribute("erro", erro);
		return new ModelAndView("redefinirSenha");
	}
	public String validar(String senhaNova, String senhaNovaC) {
		if(senhaNova == "" || senhaNova == null || senhaNovaC == "" || senhaNovaC == null) {return ("Todas as caixas devem ser preenchidas");}
		if(senhaNova.length() < 8) {return ("Senha deve ter ao menos 8 caracteres");}
		if(!senhaNova.matches(".*[a-zA-Z].*")) {return ("Senha deve ter ao 1 caractere de A-Z");}
		if(!senhaNova.matches(".*[0-9].*")) {return ("Senha deve ter ao 1 numero de 0-9");}
		if(!senhaNova.matches(".*[!@#$%&*()_\\-+=].*")) {return ("Senha deve ter ao 1 caractere especial, como: !@#$%&*()_\\-+=");}
		if(!senhaNova.contentEquals(senhaNovaC)) {return ("As senhas devem ser iguais");}
		return "";
	}
}
