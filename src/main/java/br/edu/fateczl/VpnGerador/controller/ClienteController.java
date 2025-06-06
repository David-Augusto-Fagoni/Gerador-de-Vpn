package br.edu.fateczl.VpnGerador.controller;
 
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
 
import br.edu.fateczl.VpnGerador.model.Funcionario;
import br.edu.fateczl.VpnGerador.model.Login;
import br.edu.fateczl.VpnGerador.repository.IFuncionarioRepository;
import jakarta.servlet.http.HttpServletRequest;
 
@Controller
public class ClienteController {
	@Autowired
	private IFuncionarioRepository funcionarioRep;
	@RequestMapping(name = "cliente", value = "/cliente", method = RequestMethod.GET)
	public ModelAndView clienteGet(@RequestParam Map<String, String> params, ModelMap model,HttpServletRequest request) {
	    String requestedWith = request.getHeader("X-Requested-With");
 
	    if (!"XMLHttpRequest".equals(requestedWith)) {
	        // Se não for AJAX, retorna erro ou redireciona
	        return new ModelAndView("redirect:/index"); // ou uma página de acesso negado
	    }
		Funcionario funcionario1 = new Funcionario();
		funcionario1.setNome("Aavid Fagoni");
		funcionario1.setPermissao("Administrador");
 
		Funcionario funcionario2 = new Funcionario();
		funcionario2.setNome("1Favid Fagoni");
		funcionario2.setPermissao("Funcionario");
 
		Funcionario funcionario3 = new Funcionario();
		funcionario3.setNome("David Fagoni");
		funcionario3.setPermissao("Administrador");
 
		Funcionario funcionario4 = new Funcionario();
		funcionario4.setNome("Zavid Fagoni");
		funcionario4.setPermissao("Edministrador");
 
		List<Funcionario> funcionarios = new ArrayList<>();
		funcionarios.add(funcionario1);
		funcionarios.add(funcionario2);
		funcionarios.add(funcionario3);
		funcionarios.add(funcionario4);
 
		model.addAttribute("funcionarios",funcionarios);
		return new ModelAndView("cliente");
	}
	@RequestMapping(name = "cliente", value = "/cliente", method = RequestMethod.POST)
	public ModelAndView clientePost(@RequestParam Map<String, String> allRequestParam,ModelMap model) {
		String usuario = allRequestParam.get("usuarioFuncionario").trim();
		String nome = allRequestParam.get("nomeFuncionario").trim();
		String email = allRequestParam.get("emailFuncionario").trim();
		String administrador = allRequestParam.get("administradorCheck");
		String erro = validar(usuario,nome,email,administrador);
		if(erro == "") {
			if (administrador.contains("on")) {
				administrador = "Administrador";
			}else {
				administrador = "Funcionario";
			}
			Funcionario funcionario = new Funcionario(email+"@empresa.com.br", nome,administrador,true);
			Login login = new Login(usuario,"",funcionario);
			funcionario.setLogin(login);
			cadastrar(funcionario);
		}
		model.addAttribute("erro",erro);
		return new ModelAndView("menu");
	}
	private String validar(String usuario, String nome, String email, String administrador) {
		if(usuario == "" || usuario == null || nome =="" || nome ==null || email == "" || email == null || administrador == null) {
			return ("Todos os campos devem ser preenchidos");
		}
		if (email.length() > 80) {return ("Email deve ter menos de 80 caracteres");}
		Funcionario funcionario = consultarFuncionario(email);
		if (funcionario != null) {return ("Email ja cadastrado");}
		if(usuario.length()>70) {return ("Usuario deve ter menos de 70 caracteres");}
		//PRECISA CONSULTAR SE O USUARIO JA ESTA CADASTRADO
		if(nome.length()>200) {return ("Usuario deve ter menos de 200 caracteres");}
		return "";
	}
	
	private void cadastrar(Funcionario funcionario) {
		funcionarioRep.save(funcionario);
	}
	private Funcionario consultarFuncionario(String email) {
		Optional<Funcionario> fun = funcionarioRep.findById(email);
		if (!fun.isEmpty()) {
			return (Funcionario) fun.get();
		}
		return null;
	}
	private List<Funcionario> listarFuncionario() {
		return funcionarioRep.findAll();
	}
}