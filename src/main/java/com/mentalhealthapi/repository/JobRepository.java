package com.mentalhealthapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mentalhealthapi.model.Job;


public interface JobRepository extends JpaRepository<Job,Integer> { 

}
