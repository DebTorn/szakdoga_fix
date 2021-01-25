package com.szakdoga.repository;

import java.util.ArrayList;
import java.util.Set;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.szakdoga.entity.UgyfelekProjektek;

public interface UgyfelekProjektekRepository extends CrudRepository<UgyfelekProjektek, Long> {

	public boolean existsByUgyfelIdAndProjektId(Long ugyid, Long proid);
	
	public ArrayList<UgyfelekProjektek> findAll();
	
	public Set<UgyfelekProjektek> findByUgyfelId(Long id);
	
	public UgyfelekProjektek save(UgyfelekProjektek ugypro);
	
	public UgyfelekProjektek findUgyfelekProjektekById(Long id);
	
	@Modifying
	@Transactional
	@Query(value = "DELETE FROM ugyfelek_projektek WHERE id = :id", nativeQuery=true)
	public void deleteRow(@Param("id") Long id);
	
}
