package com.szakdoga.service;

import java.util.ArrayList;
import java.util.Map;

import com.szakdoga.entity.Koltseg;

public interface KoltsegService {

	public boolean save(Map<String,String> koltseg);
	
	public ArrayList<Koltseg> findAll();
	
	public boolean update(Map<String, String> allRequestDatas);
	
	public Koltseg findKoltsegById(Long id);
	
	public void deleteById(Long id);
	
	public boolean existsById(Long id);
	
}
