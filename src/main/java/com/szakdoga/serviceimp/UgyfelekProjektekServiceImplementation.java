package com.szakdoga.serviceimp;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.szakdoga.entity.Projekt;
import com.szakdoga.entity.Ugyfel;
import com.szakdoga.entity.UgyfelekProjektek;
import com.szakdoga.repository.ProjektRepository;
import com.szakdoga.repository.UgyfelRepository;
import com.szakdoga.repository.UgyfelekProjektekRepository;
import com.szakdoga.service.UgyfelekProjektekService;

@Service
public class UgyfelekProjektekServiceImplementation implements UgyfelekProjektekService{

	private UgyfelekProjektekRepository UgyProRepo;
	private ProjektRepository ProRepo;
	private UgyfelRepository UgyRepo;
	
	@Autowired
	public void setRepos(UgyfelekProjektekRepository ugyProRepo, ProjektRepository proRepo, UgyfelRepository ugyRepo) {
		UgyProRepo = ugyProRepo;
		ProRepo = proRepo;
		UgyRepo = ugyRepo;
	}

	@Override
	public ArrayList<UgyfelekProjektek> getAllUgyfelKotes() {
		return UgyProRepo.findAll();
	}

	@Override
	public Set<UgyfelekProjektek> getAllProjektByUgyfelId(Long id) {
		return UgyProRepo.findByUgyfelId(id);
	}

	@Override
	public boolean save(Map<String, String> allRequestDatas) {
		
		Projekt projekt = ProRepo.findProjektById(Long.parseLong(allRequestDatas.get("projekt")));
		Ugyfel ugyfel = UgyRepo.findUgyfelById(Long.parseLong(allRequestDatas.get("ugyfel")));
		
		UgyfelekProjektek ugypro = new UgyfelekProjektek();
		
		ugypro.setOraber(Integer.parseInt(allRequestDatas.get("oraber")));
		ugypro.setProjekt(projekt);
		ugypro.setUgyfel(ugyfel);
		
		
		if(UgyProRepo.save(ugypro) == null) {
			return false;
		}else {
			return true;
		}
	}

	@Override
	public UgyfelekProjektek findUgyfelekProjektekById(Long id) {
		return UgyProRepo.findUgyfelekProjektekById(id);
	}

	@Override
	public void deleteById(Long id) {
		UgyProRepo.deleteRow(id);
	}

	@Override
	public boolean existsById(Long id) {
		return UgyProRepo.existsById(id);
	}

	@Override
	public boolean existsByUgyfelIdAndProjektId(Long ugyid, Long proid) {
		return UgyProRepo.existsByUgyfelIdAndProjektId(ugyid, proid);
	}

}
