package com.szakdoga.controller;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.szakdoga.entity.Koltseg;
import com.szakdoga.entity.Munka;
import com.szakdoga.entity.Projekt;
import com.szakdoga.entity.Ugyfel;
import com.szakdoga.entity.UgyfelekProjektek;
import com.szakdoga.entity.User;
import com.szakdoga.entity.UsersProjektek;
import com.szakdoga.serviceimp.ProjektServiceImplementation;
import com.szakdoga.serviceimp.UgyfelServiceImplementation;
import com.szakdoga.serviceimp.UgyfelekProjektekServiceImplementation;
import com.szakdoga.serviceimp.UserServiceImplementation;
import com.szakdoga.serviceimp.UsersProjektekImplementation;
import com.szakdoga.serviceimp.EmailServiceImplementation;
import com.szakdoga.serviceimp.KerelemServiceImplementation;
import com.szakdoga.serviceimp.KoltsegServiceImplementation;
import com.szakdoga.serviceimp.MunkaServiceImplementation;

@RestController
public class ApiController {

	private final static String NULLPOINTERTEXT = "Az érték nem lehet null", SUCCESSTEXT = "success", UNSUCCESSTEXT = "unsuccess", ISMERETLENTEXT = "ismeretlen";
	
	private UgyfelekProjektekServiceImplementation UgyProImp;
	private ProjektServiceImplementation ProImp;
	private UgyfelServiceImplementation UgyImp;
	private UserServiceImplementation UserImp;
	private UsersProjektekImplementation UserProImp;
	private KoltsegServiceImplementation KoltsegImp;
	private MunkaServiceImplementation MunkaImp;
	private KerelemServiceImplementation KerelemImp;
	
	
	/************************
	 * 	  IMPLEMENTÁCIÓK	*
	 ************************/
	@Autowired
	public void setImps(
			UgyfelekProjektekServiceImplementation ugyProImp, 
			ProjektServiceImplementation proImp, 
			UgyfelServiceImplementation ugyImp,
			UserServiceImplementation userImp,
			UsersProjektekImplementation userProImp,
			KoltsegServiceImplementation koltsegImp,
			MunkaServiceImplementation munkaImp,
			KerelemServiceImplementation kerelemImp
			) {
		UgyProImp = ugyProImp;
		ProImp = proImp;
		UgyImp = ugyImp;
		UserImp = userImp;
		UserProImp = userProImp;
		KoltsegImp = koltsegImp;
		MunkaImp = munkaImp;
		KerelemImp = kerelemImp;
	}

	
	/************************
	 *       KÖTÉSEK    	*
	 ************************/
	@PostMapping("/admin/kotes/projektek")
	public @ResponseBody ResponseEntity<ArrayList<Projekt>> KotesProjektekLekerdezes(@RequestParam Map<String, String> allRequestDatas) throws Exception {

			if(allRequestDatas.get("id") == null) {
				throw new NullPointerException(NULLPOINTERTEXT);
			}
		
			ArrayList<Projekt> mindenProjekt = ProImp.findAll();
			
			if(allRequestDatas.get("tipus").equalsIgnoreCase("dolgozo")) { //DOLGOZÓHOZ PROJEKT KÖTÉS
				
		        Set<UsersProjektek> kotesek =  UserProImp.getAllProjektByUserId(Long.parseLong(allRequestDatas.get("id")));
				
		        for(UsersProjektek up : kotesek) {
			        for(int i = 0; i < mindenProjekt.size(); i++) {
				        	if(up.getProjekt().getId().toString().equalsIgnoreCase(mindenProjekt.get(i).getId().toString())) {
				        		mindenProjekt.remove(i);
				        	}
			        }
		        }
		   
		        
			}else if(allRequestDatas.get("tipus").equalsIgnoreCase("ugyfel")) { //ÜGYFÉLHEZ PROJEKT KÖTÉS
				
				Set<UgyfelekProjektek> kotesek =  UgyProImp.getAllProjektByUgyfelId(Long.parseLong(allRequestDatas.get("id")));
		        
		        for(UgyfelekProjektek up : kotesek) {
			        for(int i = 0; i < mindenProjekt.size(); i++) {
				        	if(up.getProjekt().getId().toString().equalsIgnoreCase(mindenProjekt.get(i).getId().toString())) {
				        		mindenProjekt.remove(i);
				        	}
			        }
		        }  
		        
			}
	        
	        return ResponseEntity.ok(mindenProjekt);
	        
	}
	
	
	/************************
	 * 	    AKTIVITÁSOK 	*
	 ************************/
	@PostMapping("/admin/aktivitas")
	public @ResponseBody ResponseEntity<String> Aktivitas(@RequestParam Map<String, String> allRequestDatas) throws Exception{
		
		if(allRequestDatas == null || allRequestDatas.size() == 0) {
			throw new NullPointerException(NULLPOINTERTEXT);
		}
		
		if(allRequestDatas.get("tipus").equalsIgnoreCase("ugyfel")) { //ÜGYFÉL AKTIVITÁS
			if(UgyImp.existsById(Long.parseLong(allRequestDatas.get("id")))) {
				if(UgyImp.aktivitas(allRequestDatas)) {
					return ResponseEntity.ok(SUCCESSTEXT);
				}else {
					return ResponseEntity.badRequest().body(UNSUCCESSTEXT);
				}
			}else {
				return ResponseEntity.badRequest().body("nincs_ugyfel");
			}
			
		}else if(allRequestDatas.get("tipus").equalsIgnoreCase("dolgozo")) { //DOLGOZÓ AKTIVITÁS
			if(UserImp.existsById(Long.parseLong(allRequestDatas.get("id")))) {
				if(UserImp.aktivitas(allRequestDatas)) {
					return ResponseEntity.ok(SUCCESSTEXT);
				}else {
					return ResponseEntity.badRequest().body(UNSUCCESSTEXT);
				}
			}else {
				return ResponseEntity.badRequest().body("nincs_user");
			}
			
		}else {
			return ResponseEntity.badRequest().body(ISMERETLENTEXT);
		}
		
	}
	
	
	/************************
	 * 	ÜGYFÉL LEKÉRDEZÉS	*
	 ************************/
	@GetMapping("/admin/ugyfel/{id}")
	public @ResponseBody ResponseEntity<Ugyfel> UgyfelLekerdezes(@PathVariable(value="id") Long id) throws Exception{
		
		if(id == null) {
			throw new NullPointerException(NULLPOINTERTEXT);
		}
			Ugyfel ugyfel = UgyImp.findById(id);
			return ResponseEntity.ok(ugyfel);	
	}
	
	
	/************************
	 * 	DOLGOZÓ LEKÉRDEZÉS	*
	 ************************/
	@GetMapping("/admin/dolgozo/{id}")
	public @ResponseBody ResponseEntity<User> DolgozoLekerdezes(@PathVariable(value="id") Long id) throws Exception{
		
		if(id == null) {
			throw new NullPointerException(NULLPOINTERTEXT);
		}
		
		User user = UserImp.findById(id);
		
		return ResponseEntity.ok(user);
		
	}
	
	/************************
	 * 	PROJEKT LEKÉRDEZÉS	*
	 ************************/
	@GetMapping("/admin/projekt/{id}")
	public @ResponseBody ResponseEntity<Projekt> ProjektLekerdezes(@PathVariable(value="id") Long id) throws Exception{
		
		if(id == null) {
			throw new NullPointerException(NULLPOINTERTEXT);
		}
		
		Projekt projekt = ProImp.findById(id);
		
		return ResponseEntity.ok(projekt);
		
	}
	
	/************************
	 * 	KÖLTSÉG LEKÉRDEZÉS	*
	 ************************/
	@GetMapping("/admin/koltseg/{id}")
	public @ResponseBody ResponseEntity<Koltseg> KoltsegLekerdezes(@PathVariable(value="id") Long id) throws Exception{
		
		if(id == null) {
			throw new NullPointerException(NULLPOINTERTEXT);
		}
		
		Koltseg koltseg = KoltsegImp.findKoltsegById(id);
		
		return ResponseEntity.ok(koltseg);
		
	}
	
	/************************
	 * 	 MUNKA LEKÉRDEZÉS	*
	 ************************/
	@GetMapping("/admin/munka/{id}")
	public @ResponseBody ResponseEntity<Munka> MunkaLekerdezes(@PathVariable(value="id") Long id) throws Exception{
		
		if(id == null) {
			throw new NullPointerException(NULLPOINTERTEXT);
		}
		
		Munka munka = MunkaImp.findMunkaById(id);
		
		return ResponseEntity.ok(munka);
		
	}
	
	
	/************************
	 * 	    MÓDOSÍTÁSOK		*
	 ************************/
	@PostMapping("/admin/modositas")
	public @ResponseBody ResponseEntity<String> Modositas(@RequestParam Map<String, String> allRequestDatas) throws Exception{
		
		if(allRequestDatas == null || allRequestDatas.size() == 0) {
			throw new Exception(NULLPOINTERTEXT);
		}
		
		if(allRequestDatas.get("tipus").equalsIgnoreCase("dolgozo")) { //DOLGOZÓ MÓDOSÍTÁS
			if(UserImp.update(allRequestDatas)) {
				return ResponseEntity.ok(SUCCESSTEXT);
			}else {
				return ResponseEntity.badRequest().body(UNSUCCESSTEXT);
			}
		}else if(allRequestDatas.get("tipus").equalsIgnoreCase("ugyfel")) { //ÜGYFÉL MÓDOSÍTÁS
			if(UgyImp.update(allRequestDatas)) {
				return ResponseEntity.ok(SUCCESSTEXT);
			}else {
				return ResponseEntity.badRequest().body(UNSUCCESSTEXT);
			}
		}else if(allRequestDatas.get("tipus").equalsIgnoreCase("projekt")) { //PROJEKT MÓDOSÍTÁS
			if(ProImp.update(allRequestDatas)) {
				return ResponseEntity.ok(SUCCESSTEXT);
			}else {
				return ResponseEntity.badRequest().body(UNSUCCESSTEXT);
			}
		}else if(allRequestDatas.get("tipus").equalsIgnoreCase("koltseg")) {
			if(KoltsegImp.update(allRequestDatas)) {
				return ResponseEntity.ok(SUCCESSTEXT);
			}else {
				return ResponseEntity.badRequest().body(UNSUCCESSTEXT);
			}
		}else if(allRequestDatas.get("tipus").equalsIgnoreCase("munka")) {
			String[] raforditas = allRequestDatas.get("raforditas").split("\\.");
			if(raforditas.length > 1) {
				int perc = Integer.parseInt(raforditas[1]);
				if(perc == 25 || perc == 5 || perc == 75) {		
					if(MunkaImp.update(allRequestDatas)) {
						return ResponseEntity.ok(SUCCESSTEXT);
					}else {
						return ResponseEntity.badRequest().body(UNSUCCESSTEXT);
					}
				}else {
					return ResponseEntity.badRequest().body("hibas_ido");
				}	
			}else if(raforditas.length == 1){
				if(MunkaImp.update(allRequestDatas)) {
					return ResponseEntity.ok(SUCCESSTEXT);
				}else {
					return ResponseEntity.badRequest().body(UNSUCCESSTEXT);
				}
			}else {
				return ResponseEntity.badRequest().body(UNSUCCESSTEXT);
			}
		}else {
				return ResponseEntity.badRequest().body(ISMERETLENTEXT);
		}
		
	}
	
	
	/************************
	 * 	  KÖTÉSEK TÖRLÉSE 	*
	 ************************/
	@PostMapping("/admin/kotes/torles")
	public @ResponseBody ResponseEntity<String> KotesTorles(@RequestParam Map<String, String> allRequestDatas) throws Exception{
		
		if(allRequestDatas == null || allRequestDatas.size() == 0) {
			throw new Exception(NULLPOINTERTEXT);
		}
		
		if(allRequestDatas.get("tipus").equalsIgnoreCase("ugyfel")) { //ÜGYFÉL KÖTÉS TÖRLÉS
			if(UgyProImp.existsById(Long.parseLong(allRequestDatas.get("id")))) {
				UgyProImp.deleteById(Long.parseLong(allRequestDatas.get("id")));
				if(UgyProImp.existsById(Long.parseLong(allRequestDatas.get("id")))) {
					return ResponseEntity.badRequest().body(UNSUCCESSTEXT);
				}else {
					return ResponseEntity.ok(SUCCESSTEXT);
				}
			}else {
				return ResponseEntity.badRequest().body("nincs_kotes");
			}
		}else if(allRequestDatas.get("tipus").equalsIgnoreCase("dolgozo")) { //DOLGOZÓ KÖTÉS TÖRLÉS
			if(MunkaImp.findByKotesId(Long.parseLong(allRequestDatas.get("id")))) {
				return ResponseEntity.badRequest().body("van_munka");
			}else {
				if(UserProImp.existsById(Long.parseLong(allRequestDatas.get("id")))) {
					UserProImp.deleteById(Long.parseLong(allRequestDatas.get("id")));
					if(UserProImp.existsById(Long.parseLong(allRequestDatas.get("id")))) {
						return ResponseEntity.badRequest().body(UNSUCCESSTEXT);
					}else {
						return ResponseEntity.ok(SUCCESSTEXT);
					}
				}else {
					return ResponseEntity.badRequest().body("nincs_kotes");
				}
			}
		}else{
			return ResponseEntity.badRequest().body(ISMERETLENTEXT);
		}
		
	}
	
	
	/************************
	 * 	 KÖTÉS FELTÖLTÉSE	*
	 ************************/
	@PostMapping("/admin/kotes/feltoltes")
	public @ResponseBody ResponseEntity<String> KotesFeltoltes(@RequestParam Map<String, String> allRequestDatas) throws Exception{
			if(allRequestDatas.get("tipus").equalsIgnoreCase("ugyfel")) { //ÜGYFÉL KÖTÉS FELTÖLTÉS
				if(UgyImp.existsById(Long.parseLong(allRequestDatas.get("ugyfel")))){
					if(ProImp.existsById(Long.parseLong(allRequestDatas.get("projekt")))) {
						if(UgyProImp.existsByUgyfelIdAndProjektId(Long.parseLong(allRequestDatas.get("ugyfel")), Long.parseLong(allRequestDatas.get("projekt")))) {
							return ResponseEntity.badRequest().body("van_kotes");
						}else {
							if(UgyProImp.save(allRequestDatas)) {
								return ResponseEntity.ok(SUCCESSTEXT);
							}else {
								return ResponseEntity.badRequest().body(UNSUCCESSTEXT);
							}
						}
					}else {
						return ResponseEntity.badRequest().body("nincs_projekt");
					}
				}else {
					return ResponseEntity.badRequest().body("nincs_ugyfel");
				}
			}else if(allRequestDatas.get("tipus").equalsIgnoreCase("dolgozo")) { //DOLGOZÓ KÖTÉS FELTÖLTÉS
				if(UserImp.existsById(Long.parseLong(allRequestDatas.get("dolgozo")))) {
					if(ProImp.existsById(Long.parseLong(allRequestDatas.get("projekt")))) {
						if(UserProImp.existsByUserIdAndProjektId(Long.parseLong(allRequestDatas.get("dolgozo")), Long.parseLong(allRequestDatas.get("projekt")))) {
							return ResponseEntity.badRequest().body("van_kotes");
						}else {
							if(UserProImp.save(allRequestDatas)) {
								return ResponseEntity.ok(SUCCESSTEXT);
							}else {
								return ResponseEntity.badRequest().body(UNSUCCESSTEXT);
							}
						}
					}else {
						return ResponseEntity.badRequest().body("nincs_projekt");
					}
				}else {
					return ResponseEntity.badRequest().body("nincs_dolgozo");
				}
			}else {
				return ResponseEntity.badRequest().body(ISMERETLENTEXT);
			}

	}
	
	
	/************************
	 * 	    FELTÖLTÉSEK 	*
	 ************************/
	@PostMapping("/admin/feltoltes")
	public @ResponseBody ResponseEntity<String> Feltoltes(@RequestParam Map<String, String> allRequestDatas) {
			if(allRequestDatas.get("tipus").equalsIgnoreCase("dolgozo")) { //DOLGOZÓ FELTÖLTÉS
				if(allRequestDatas.get("ujrajelszo").equalsIgnoreCase(allRequestDatas.get("jelszo"))) {
					if(UserImp.save(allRequestDatas)) {
						return ResponseEntity.ok(SUCCESSTEXT);
					}else {
						return ResponseEntity.badRequest().body(UNSUCCESSTEXT);
					}
				}else {
					return ResponseEntity.badRequest().body("nem_egyezik");
				}
			}else if(allRequestDatas.get("tipus").equalsIgnoreCase("ugyfel")) { //ÜGYFÉL FELTÖLTÉS
				if(UgyImp.existsByName(allRequestDatas.get("nev"))) {
					return ResponseEntity.badRequest().body("van_ugyfel");
				}else {
					if(UgyImp.save(allRequestDatas)) {
						return ResponseEntity.ok(SUCCESSTEXT);
					}else {
						return ResponseEntity.badRequest().body(UNSUCCESSTEXT);
					}
				}
			}else if(allRequestDatas.get("tipus").equalsIgnoreCase("projekt")) { //PROJEKT FELTÖLTÉS
				if(ProImp.existsByName(allRequestDatas.get("nev"))) {
					return ResponseEntity.badRequest().body("van_projekt");
				}else {
					if(ProImp.save(allRequestDatas)) {
						return ResponseEntity.ok(SUCCESSTEXT);
					}else {
						return ResponseEntity.badRequest().body(UNSUCCESSTEXT);
					}
				}
			}else if(allRequestDatas.get("tipus").equalsIgnoreCase("koltseg")) {
				if(KoltsegImp.save(allRequestDatas)) {
					return ResponseEntity.ok(SUCCESSTEXT);
				}else {
					return ResponseEntity.badRequest().body(UNSUCCESSTEXT);
				}
			}else if(allRequestDatas.get("tipus").equalsIgnoreCase("munka")) {
				String[] raforditas = allRequestDatas.get("raforditas").split("\\.");
				if(raforditas.length > 0) {
					int ora = Integer.parseInt(raforditas[0]);
					if(ora < 1 || ora > 23) {
						return ResponseEntity.badRequest().body("hibas_ora");
					}
					if(raforditas.length > 1) {
						int perc = Integer.parseInt(raforditas[1]);
						if(perc == 25 || perc == 5 || perc == 75) {
							if(MunkaImp.save(allRequestDatas)) {
								return ResponseEntity.ok(SUCCESSTEXT);
							}else {
								return ResponseEntity.badRequest().body(UNSUCCESSTEXT);
							}
						}else {
							return ResponseEntity.badRequest().body("hibas_perc");
						}
					}else if(raforditas.length == 1) {
							if(MunkaImp.save(allRequestDatas)) {
								return ResponseEntity.ok(SUCCESSTEXT);
							}else {
								return ResponseEntity.badRequest().body(UNSUCCESSTEXT);
							}
					}else {
						return ResponseEntity.badRequest().body("hibas_ido");
					}
				}else {
					return ResponseEntity.badRequest().body(UNSUCCESSTEXT);
				}
			}else{
				return ResponseEntity.badRequest().body(ISMERETLENTEXT);
			}
	}
	
	@PostMapping("/admin/torles")
	public @ResponseBody ResponseEntity<String> Torles(@RequestParam Map<String, String> allRequestDatas){
		if(allRequestDatas.get("tipus").equalsIgnoreCase("munka")) {
			if(MunkaImp.existsById(Long.parseLong(allRequestDatas.get("id")))) {
				MunkaImp.deleteById(Long.parseLong(allRequestDatas.get("id")));
				if(MunkaImp.existsById(Long.parseLong(allRequestDatas.get("id")))) {
					return ResponseEntity.badRequest().body(UNSUCCESSTEXT);
				}else {
					return ResponseEntity.ok(SUCCESSTEXT);
				}
			}else {
				return ResponseEntity.badRequest().body("nincs_munka");
			}
		}else {
			return ResponseEntity.badRequest().body(ISMERETLENTEXT);
		}
	}
	
	/************************
	 * 	  KÖLTSÉG TÖRLÉS 	*
	 ************************/
	@PostMapping("/admin/koltseg/torles")
	public @ResponseBody ResponseEntity<String> KoltsegTorles(@RequestParam Map<String, String> allRequestDatas){
		if(allRequestDatas.get("tipus").equalsIgnoreCase("projekt")) {
			if(KoltsegImp.existsById(Long.parseLong(allRequestDatas.get("id")))) {
				KoltsegImp.deleteById(Long.parseLong(allRequestDatas.get("id")));
				if(KoltsegImp.existsById(Long.parseLong(allRequestDatas.get("id")))) {
					return ResponseEntity.ok(UNSUCCESSTEXT);
				}else {
					return ResponseEntity.badRequest().body(SUCCESSTEXT);
				}
			}else {
				return ResponseEntity.badRequest().body("nincs_koltseg");
			}
		}else {
			return ResponseEntity.badRequest().body(ISMERETLENTEXT);
		}
	}
	
}
