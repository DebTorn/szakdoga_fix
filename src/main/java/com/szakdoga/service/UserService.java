package com.szakdoga.service;

import java.util.ArrayList;
import java.util.Map;

import com.szakdoga.entity.Ugyfel;
import com.szakdoga.entity.User;

public interface UserService {

	public User findByEmail(String email);
	
	public ArrayList<User> getAllUser();
	
	public boolean save(Map<String, String> user);
	
	public boolean aktivitas(Map<String, String> ugyfel);
	
	public User findById(Long id);
	
	public boolean update(Map<String,String> user);
	
	public boolean existsById(Long id);
	
	public boolean simaSave(User user);
	
	public User findByToken(String token);
	
}
