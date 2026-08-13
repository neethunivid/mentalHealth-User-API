package com.mentalhealthapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mentalhealthapi.model.GrantRequest2;

public interface GrantRequest2Repository extends JpaRepository<GrantRequest2, Long> {
	void deleteByGrantId(Long grantId);
	
}
