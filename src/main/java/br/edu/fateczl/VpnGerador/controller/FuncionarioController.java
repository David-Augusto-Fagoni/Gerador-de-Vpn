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
public class FuncionarioController {
	@Autowired
	private IFuncionarioRepository funcionarioRep;
	@RequestMapping(name = "funcionario", value = "/funcionario", method = RequestMethod.GET)
	public ModelAndView clienteGet(@RequestParam Map<String, String> params, ModelMap model,HttpServletRequest request) {
	    String requestedWith = request.getHeader("X-Requested-With");
	    if (!"XMLHttpRequest".equals(requestedWith)) {
	        // Se não for AJAX, retorna erro ou redireciona
	        return new ModelAndView("redirect:/index"); // ou uma página de acesso negado
	    }
		List<Funcionario> funcionarios = listarFuncionario();

		model.addAttribute("funcionarios",funcionarios);
		model.addAttribute("erro","");
		return new ModelAndView("funcionario");
	}
	@RequestMapping(name = "funcionario", value = "/funcionario", method = RequestMethod.POST)
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
			cadastrarFuncionario(funcionario);
		}
		model.addAttribute("erro",erro);
		return new ModelAndView("redirect:/menu");
	}
	@RequestMapping(name = "funcionario", value = "/acaoFuncionario", method = RequestMethod.POST)
	public ModelAndView acaoPost(@RequestParam Map<String, String> allRequestParam,@RequestParam(value = "selecionados", required = false) String[] selecionados,ModelMap model) {
		String acao = allRequestParam.get("acao").trim();
		String erro = "";
	    System.out.println("Ação selecionada: " + acao);
	    List<Funcionario> funcionarios = new ArrayList<Funcionario>();
	    if (selecionados != null) {
	    	funcionarios = procurarFuncionarioById(selecionados);
	    } else {
	    	erro = "Nenhum funcionario selecionado";
	    	model.addAttribute("erro",erro);
	    	return new ModelAndView("redirect:/menu");
	    }
	    switch (acao.trim()) {
	    	case "elegerAdm" -> {
	    		for (Funcionario funcionario : funcionarios) {
	    			funcionario.setPermissao("Administrador");
	    			atualizarFuncionario(funcionario);
	    		}
	    	}
	    	case "revogarAdm" ->{
	    		for (Funcionario funcionario : funcionarios) {
	    			funcionario.setPermissao("Funcionario");
	    			atualizarFuncionario(funcionario);
	    		}
	    	}
	    	case "revogarAcess" ->{
	    		for (Funcionario funcionario : funcionarios) {
	    			funcionario.setAtivo(false);
	    		    System.out.println("Ação selecionada: " + acao);
	    			atualizarFuncionario(funcionario);
	    		}
	    	}
	    	case "ativarAcess" ->{
	    		for (Funcionario funcionario : funcionarios) {
	    			funcionario.setAtivo(true);
	    			atualizarFuncionario(funcionario);
	    		}
	    	}
	    	case "removerCad" ->{
	    		for (Funcionario funcionario : funcionarios) {
	    			deletarFuncionario(funcionario);
	    		}
	    	}
	    	default -> erro = "Comando não reconhecido";
	    }
	    model.addAttribute("erro",erro);
	    return new ModelAndView("redirect:/menu");
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
	private List<Funcionario> procurarFuncionarioById(String[] emails){
		List<Funcionario> funcionarios = new ArrayList<>();
		for (String email : emails) {
			Funcionario funcionario = new Funcionario();
			funcionario = consultarFuncionario(email);
			if (funcionario != null) {
				funcionarios.add(funcionario);
			}
		}
		return funcionarios;
	}
	private void cadastrarFuncionario(Funcionario funcionario) {
		funcionarioRep.save(funcionario);
	}
	private void atualizarFuncionario(Funcionario funcionario) {
		funcionarioRep.save(funcionario);
	}
	private void deletarFuncionario(Funcionario funcionario) {
		funcionarioRep.delete(funcionario);
	}
	private Funcionario consultarFuncionario(String email) {
		Optional<Funcionario> fun = funcionarioRep.findById(email);
		if (!fun.isEmpty()) {
			return fun.get();
		}
		return null;
	}
	private List<Funcionario> listarFuncionario() {
		return funcionarioRep.findAll();
	}
}