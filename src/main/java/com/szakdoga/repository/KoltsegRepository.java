package com.szakdoga.repository;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;

import com.szakdoga.entity.Koltseg;

public interface KoltsegRepository extends CrudRepository<Koltseg,Long> {

	public ArrayList<Koltseg> findAll();
	
	public Koltseg findKoltsegById(Long id);
	
}
