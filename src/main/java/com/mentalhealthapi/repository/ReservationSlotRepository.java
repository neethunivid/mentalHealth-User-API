package com.mentalhealthapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mentalhealthapi.model.ReservationSlot;
@Repository
public interface ReservationSlotRepository extends JpaRepository<ReservationSlot, Integer> {
	
}
