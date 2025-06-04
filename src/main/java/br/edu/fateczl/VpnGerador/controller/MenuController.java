package br.edu.fateczl.VpnGerador.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.edu.fateczl.VpnGerador.model.Funcionario;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class MenuController {
	
	
	@RequestMapping(name = "menu", value = "/menu", method = RequestMethod.GET)
	public ModelAndView menuGet(@RequestParam Map<String, String> params, ModelMap model) {

		return new ModelAndView("menu");
	}

	@Transactional
	@RequestMapping(name = "menu", value = "/menu", method = RequestMethod.POST)
	public ModelAndView menuPost(@RequestParam Map<String, String> params, ModelMap model) {
		
		return new ModelAndView("menu");
	}

}
