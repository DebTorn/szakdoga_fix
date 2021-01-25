package com.szakdoga.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.szakdoga.entity.Kerelem;

public interface KerelemRepository extends CrudRepository<Kerelem, Long> {

	public Kerelem findByTokenAndEmail(String token, String email);
	
	public boolean existsByToken(String token);
	
	public Kerelem findEmailByTokenAndTipus(String token, String tipus);
	
	public Kerelem findByEmailAndTipus(String email, String tipus);
	
	@Transactional
	public void deleteByToken(String token);
	
	
}
