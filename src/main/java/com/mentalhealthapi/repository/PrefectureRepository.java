package com.mentalhealthapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mentalhealthapi.model.Prefecture;


@Repository
public interface PrefectureRepository extends JpaRepository<Prefecture, Integer> {
	Prefecture findById(int id);
}
