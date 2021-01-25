package com.szakdoga.controller;

import java.sql.Timestamp;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.RedirectView;

import com.szakdoga.entity.Kerelem;
import com.szakdoga.entity.User;
import com.szakdoga.entity.UserJelszo;
import com.szakdoga.serviceimp.KerelemServiceImplementation;
import com.szakdoga.serviceimp.KoltsegServiceImplementation;
import com.szakdoga.serviceimp.MunkaServiceImplementation;
import com.szakdoga.serviceimp.ProjektServiceImplementation;
import com.szakdoga.serviceimp.UgyfelServiceImplementation;
import com.szakdoga.serviceimp.UgyfelekProjektekServiceImplementation;
import com.szakdoga.serviceimp.UserServiceImplementation;
import com.szakdoga.serviceimp.UsersProjektekImplementation;

@Controller
public class AdminViewController {
	
	private UgyfelServiceImplementation UgyfelImp;
	private ProjektServiceImplementation ProjekImp;
	private UserServiceImplementation UserImp;
	private UsersProjektekImplementation UPImp;
	private UgyfelekProjektekServiceImplementation UgyPImp;
	private KoltsegServiceImplementation KoltsegImp;
	private MunkaServiceImplementation MunkaImp;
	private KerelemServiceImplementation KerelemImp;
	
	
	
	@Autowired
	public void setImps(
	UgyfelServiceImplementation ugyfelImp, 
	ProjektServiceImplementation proImp, 
	UserServiceImplementation userImp, 
	UsersProjektekImplementation uPImp, 
	UgyfelekProjektekServiceImplementation ugyPImp,
	KoltsegServiceImplementation koltsegImp,
	MunkaServiceImplementation munkaImp,
	KerelemServiceImplementation kerelemImp
	) {
		UgyfelImp = ugyfelImp;
		ProjekImp = proImp;
		UserImp = userImp;
		UPImp = uPImp;
		UgyPImp = ugyPImp;
		KoltsegImp = koltsegImp;
		MunkaImp = munkaImp;
		KerelemImp = kerelemImp;
	}
	
	@GetMapping("admin/ugyfel")
	public String Ugyfel(Model model) {
		model.addAttribute("ugyfelek", UgyfelImp.ugyfelek());
		model.addAttribute("ugykotes", UgyPImp.getAllUgyfelKotes());
		return "ugyfel";
	}
	
	@GetMapping("admin/dolgozo")
	public String Dolgozo(Model model) {
		model.addAttribute("dolgozok", UserImp.getAllUser());
		model.addAttribute("dolgkotes", UPImp.getAllKotes());
		return "dolgozo";
	}
	
	@GetMapping("admin/projekt")
	public String Projekt(Model model) {
		model.addAttribute("projektek", ProjekImp.findAll());
		model.addAttribute("koltsegek", KoltsegImp.findAll());
		return "projekt";
	}
	
	@GetMapping("admin/elszamolas")
	public String Elszamolas(Model model) {
		model.addAttribute("munkak", MunkaImp.findAll());
		model.addAttribute("projektek", ProjekImp.findAll());
		return "elszamolas";
	}
	
	@GetMapping("dolgozo/jelszo/{token}")
	public String JelszoVisszaallitas(@PathVariable("token") String token) {
		if(KerelemImp.findByTokenAndEmailDolgozo(token)) {
			return "jelszo";
		}else {
			return "profil";
		}
	}
	
	@GetMapping("/jelszo/{token}")
	public String JelszoVisszaallitasAdmin(@PathVariable("token") String token, Model model) {
		if(KerelemImp.findByTokenAndEmail(token, "jelszo") != null) {
			UserJelszo uJelszo = new UserJelszo();
			uJelszo.setEmail(KerelemImp.findByTokenAndEmail(token, "jelszo"));
			uJelszo.setToken(token);
			model.addAttribute("user", uJelszo);
			model.addAttribute("token", token);
			return "jelszo";
		}else {
			return null;
		}
	}
	
	@GetMapping("/visszaigazolas/{token}")
	public RedirectView EmailVisszaigazolas(@PathVariable("token") String token) {
		if(UserImp.findByToken(token) != null) {
			User user = UserImp.findByToken(token);
			user.setAktiv(1);
			user.setToken(null);
			if(UserImp.simaSave(user)) {
				return new RedirectView("visszaigazolas?sikeres=1");
			}else {
				return new RedirectView("visszaigazolas?sikeres=2");
			}
		}else {
			return null;
		}
	}
	
	@GetMapping("/elfelejtett")
	public String ElfelejtettJelszo(Model model) {
		Kerelem kerelem = new Kerelem();
		kerelem.setTipus("jelszo");
		model.addAttribute("kerelem", kerelem);
		return "elfelejtett";
	}
	
}
