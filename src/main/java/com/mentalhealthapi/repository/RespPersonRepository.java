package com.mentalhealthapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mentalhealthapi.model.RespPerson;



public interface RespPersonRepository extends JpaRepository<RespPerson,Integer> {

	List<RespPerson> findByRegionIdAndStatus(int region_id,int status); 
	
	@Query("select p from RespPerson p where p.status = 1")
	public List<RespPerson> findActiveList();
}