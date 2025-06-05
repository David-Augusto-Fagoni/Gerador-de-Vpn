package br.edu.fateczl.VpnGerador.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.edu.fateczl.VpnGerador.model.Funcionario;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class ClienteController {

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
	public ModelAndView clientePost(@RequestParam Map<String, String> allRequestParam) {
		String usuario = allRequestParam.get("usuarioFuncionario").trim();
		String nome = allRequestParam.get("nomeFuncionario").trim();
		String email = allRequestParam.get("emailFuncionario").trim()+"@empresa.com.br";
		String administrador = allRequestParam.get("administradorCheck");
		return new ModelAndView("menu");
	}
}
