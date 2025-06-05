package br.edu.fateczl.VpnGerador.controller;

import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class EsqueciSenhaController {
	@RequestMapping(name = "esqueciSenha", value = "/esqueciSenha", method = RequestMethod.GET)
	public ModelAndView deptoGet(@RequestParam Map<String, String> params, ModelMap model) {
			
		return new ModelAndView("esqueciSenha");
	}
	

}
