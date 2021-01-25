package com.szakdoga.service;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import com.szakdoga.entity.UgyfelekProjektek;

public interface UgyfelekProjektekService {

	public ArrayList<UgyfelekProjektek> getAllUgyfelKotes();
	
	public Set<UgyfelekProjektek> getAllProjektByUgyfelId(Long id);
	
	public boolean save(Map<String, String> ugypro);
	
	public UgyfelekProjektek findUgyfelekProjektekById(Long id);
	
	public void deleteById(Long id);
	
	public boolean existsById(Long id);
	
	public boolean existsByUgyfelIdAndProjektId(Long ugyid, Long proid);
}
