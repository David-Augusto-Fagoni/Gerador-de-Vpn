package br.edu.fateczl.VpnGerador.controller;

import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MenuController {
	
	
	@RequestMapping(name = "menu", value = "/menu", method = RequestMethod.GET)
	public ModelAndView deptoGet(@RequestParam Map<String, String> params, ModelMap model) {
		
		return new ModelAndView("menu");
	}

	@Transactional
	@RequestMapping(name = "menu", value = "/menu", method = RequestMethod.POST)
	public ModelAndView deptoPost(@RequestParam Map<String, String> params, ModelMap model) {
		
		return new ModelAndView("menu");
	}

}
