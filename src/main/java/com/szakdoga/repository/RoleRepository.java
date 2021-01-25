package com.szakdoga.repository;

import org.springframework.data.repository.CrudRepository;

import com.szakdoga.entity.Role;

public interface RoleRepository extends CrudRepository<Role, Long> {

	Role findByName(String name);
	
}
