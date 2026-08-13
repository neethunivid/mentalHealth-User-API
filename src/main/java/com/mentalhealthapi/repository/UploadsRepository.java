package com.mentalhealthapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mentalhealthapi.model.Uploads;

@Repository
public interface UploadsRepository extends JpaRepository<Uploads, Long> {
}