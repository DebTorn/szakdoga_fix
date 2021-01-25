package com.szakdoga.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.szakdoga.entity.Munka;

public interface MunkaRepository extends CrudRepository<Munka,Long>{
	
	public ArrayList<Munka> findAll();
	
	public Munka findMunkaById(Long id);
	
	@Modifying
	@Transactional
	@Query(value = "DELETE FROM munkak WHERE id = :id", nativeQuery=true)
	public void deleteRow(@Param("id") Long id);
	
	@Query(value = "SELECT * FROM munkak WHERE kotes_id = :id", nativeQuery=true)
	public ArrayList<Munka> findByKotesId(@Param("id") Long id);
}
