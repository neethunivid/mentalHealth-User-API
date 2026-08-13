package com.mentalhealthapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mentalhealthapi.model.NgWords;



@Repository
public interface NgWordsRepository extends JpaRepository<NgWords, Integer> {
	long countByTextContaining(String keyword);

}
