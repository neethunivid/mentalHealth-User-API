package com.mentalhealthapi.repository;


import java.awt.print.Pageable;
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
import com.mentalhealthapi.model.ReservationSlot;

@Repository
public interface ReservationCounsellingRepo extends JpaRepository<ReservationSlot, Integer> {
	int countByDateAndTimeStartAndRegionId(String date, String timeStart, int regionId);
	

}