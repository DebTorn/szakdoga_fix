package com.szakdoga.service;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.data.repository.query.Param;

import com.szakdoga.entity.Munka;

public interface MunkaService {

	public boolean save(Map<String, String> allRequestDatas);
	
	public ArrayList<Munka> findAll();
	
	public Munka findMunkaById(Long id);
	
	public boolean existsById(Long id);
	
	public void deleteById(Long id);
	
	public boolean update(Map<String,String> allRequestDatas);
	
	public boolean findByKotesId(Long id);
	
}
