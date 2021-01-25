package com.szakdoga.serviceimp;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.szakdoga.entity.Munka;
import com.szakdoga.entity.Projekt;
import com.szakdoga.entity.User;
import com.szakdoga.entity.UsersProjektek;
import com.szakdoga.repository.MunkaRepository;
import com.szakdoga.repository.ProjektRepository;
import com.szakdoga.repository.UserRepository;
import com.szakdoga.repository.UsersProjektekRepository;
import com.szakdoga.service.MunkaService;

@Service
public class MunkaServiceImplementation implements MunkaService{

	private MunkaRepository MunkaRepo;
	private UsersProjektekRepository UserProRepo;
	
	@Autowired
	public void setRepos(MunkaRepository munkaRepo, UsersProjektekRepository userProRepo) {
		MunkaRepo = munkaRepo;
		UserProRepo = userProRepo;
	}
	
	@Override
	public boolean save(Map<String, String> allRequestDatas) {
		
		Munka munka = new Munka();
		UsersProjektek userpro = UserProRepo.findByUserIdAndProjektId(Long.parseLong(allRequestDatas.get("dolgozo")), Long.parseLong(allRequestDatas.get("projekt")));
		
		munka.setUserpro(userpro);
		munka.setRaforditas(Float.parseFloat(allRequestDatas.get("raforditas")));
		munka.setMunkaleiras(allRequestDatas.get("leiras"));
		munka.setTipus(Integer.parseInt(allRequestDatas.get("muntipus")));
		munka.setCreated_at(new Timestamp(new Date().getTime()));
		munka.setDatum(allRequestDatas.get("datum"));
		
		if(MunkaRepo.save(munka) != null) {
			return true;
		}else {
			return false;
		}
	}
	
	@Override
	public ArrayList<Munka> findAll() {
		return MunkaRepo.findAll();
	}

	@Override
	public Munka findMunkaById(Long id) {
		return MunkaRepo.findMunkaById(id);
	}

	@Override
	public boolean existsById(Long id) {
		return MunkaRepo.existsById(id);
	}

	@Override
	public void deleteById(Long id) {
		MunkaRepo.deleteRow(id);
	}

	@Override
	public boolean update(Map<String, String> allRequestDatas) {
		Munka munka = MunkaRepo.findMunkaById(Long.parseLong(allRequestDatas.get("id")));
		UsersProjektek userpro = UserProRepo.findByUserIdAndProjektId(Long.parseLong(allRequestDatas.get("user")), Long.parseLong(allRequestDatas.get("projekt")));
		
		munka.setDatum(allRequestDatas.get("datum"));
		munka.setMunkaleiras(allRequestDatas.get("leiras"));
		munka.setRaforditas(Float.parseFloat(allRequestDatas.get("raforditas")));
		munka.setTipus(Integer.parseInt(allRequestDatas.get("muntipus")));
		munka.setCreated_at(munka.getCreated_at());
		munka.setUpdated_at(new Timestamp(new Date().getTime()));
		munka.setUserpro(userpro);
			
		if(MunkaRepo.save(munka) != null) {
			return true;
		}else {
			return false;
		}
	}

	@Override
	public boolean findByKotesId(Long id) {
		if(MunkaRepo.findByKotesId(id).size() > 0) {
			return true;
		}else {
			return false;
		}
	}
	
}
