package com.szakdoga.service;

import com.szakdoga.entity.Kerelem;

public interface KerelemService {

	public boolean save(Kerelem kerelem);
	
	public boolean findByTokenAndEmailDolgozo(String token);
	
	public String findByTokenAndEmail(String token, String tipus);
	
	public boolean existsByTokenAndEmailAndTipus(String token, String email, String tipus);
	
	public void deleteByToken(String token);
	
}
