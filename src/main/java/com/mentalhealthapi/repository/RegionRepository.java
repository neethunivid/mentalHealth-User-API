package com.mentalhealthapi.repository;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mentalhealthapi.model.Remarks;
import com.mentalhealthapi.model.ReservationDetails;
import com.mentalhealthapi.model.ReservationRegion;

@Repository
public interface RegionRepository extends JpaRepository<ReservationRegion, Integer> {
	ReservationRegion findById(int id);
}