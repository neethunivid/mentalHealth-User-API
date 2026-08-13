package com.mentalhealthapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mentalhealthapi.model.Member;
import com.mentalhealthapi.model.RemarksInitial;


public interface  RemarksInitialRepository extends JpaRepository< RemarksInitial,Integer> {
	RemarksInitial findByTitle(String title);
	Optional<RemarksInitial> findById(Integer id);
	RemarksInitial save(Optional<RemarksInitial> rmkIni);
}