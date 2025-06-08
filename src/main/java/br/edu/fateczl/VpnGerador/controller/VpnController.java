package br.edu.fateczl.VpnGerador.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.edu.fateczl.VpnGerador.model.Vpn;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class VpnController {
	@RequestMapping(name = "vpn", value = "/vpn", method = RequestMethod.GET)
	public ModelAndView vpnGet(@RequestParam Map<String, String> params, ModelMap model,HttpServletRequest request) {

	    Vpn vpn1 = new Vpn();
	    vpn1.setId("1234567890");
	    vpn1.setDt_criacao(LocalDate.parse("2018-12-15"));
	    vpn1.setDt_validade(LocalDate.parse("2018-12-15"));
	    Vpn vpn2 = new Vpn();
	    vpn2.setId("123456");
	    vpn2.setDt_criacao(LocalDate.parse("2018-12-15"));
	    vpn2.setDt_validade(LocalDate.parse("2018-12-15"));

	    List<Vpn> vpns = new ArrayList<>();
	    vpns.add(vpn1);
	    vpns.add(vpn2);
	    model.addAttribute("vpns", vpns);
		return new ModelAndView("vpn");
	}
}
