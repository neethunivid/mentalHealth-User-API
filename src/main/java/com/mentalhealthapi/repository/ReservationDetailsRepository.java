package com.mentalhealthapi.repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mentalhealthapi.model.ReservationDetails;

public interface ReservationDetailsRepository extends JpaRepository<ReservationDetails, Integer> {

	ReservationDetails findByReservationId(int reservationId); 

	
	@Query("SELECT rs FROM ReservationSlot rs " +
		       "WHERE (rs.reservationStatus IN ('TEMPORARY', 'RESERVED', 'CANCELLED')) " +
		       "AND (:fromDate IS NULL OR :toDate IS NULL OR rs.date BETWEEN :fromDate AND :toDate)")
		List<Object> findReservationSlotsByDate(
		        @Param("fromDate") LocalDate fromDate,
		        @Param("toDate") LocalDate toDate, Pageable pageable);
	
	@Query("SELECT rs FROM ReservationSlot rs " +
		       "JOIN rs.region r " + // Join with the region entity
		       "WHERE (rs.reservationStatus IN ('TEMPORARY', 'RESERVED', 'CANCELLED')) " +
		       "AND (:regionId IS NULL OR r.id = :regionId) ")
		List<Object> findReservationSlotsByRegion(
		        @Param("regionId") Integer regionId,Pageable pageable);
	
 	@Query("SELECT rs FROM ReservationSlot rs " +
		       "JOIN rs.region r " + // Join with the region entity
		       "WHERE (rs.reservationStatus IN ('TEMPORARY', 'RESERVED', 'CANCELED')) " +
		       "AND (:regionId IS NULL OR r.id = :regionId) " +
		       "AND (:fromDate IS NULL OR :toDate IS NULL OR rs.date BETWEEN :fromDate AND :toDate)")
		List<Object> findReservationSlots(
		        @Param("regionId") Integer regionId,
				@Param("fromDate") LocalDate fromDate,
					@Param("toDate") LocalDate toDate,
					Pageable pageable);
 	@Query("SELECT COUNT(rs) FROM ReservationSlot rs " +
 	       "WHERE (rs.reservationStatus IN ('TEMPORARY', 'RESERVED', 'CANCELLED')) " +
 	       "AND (:fromDate IS NULL OR :toDate IS NULL OR rs.date BETWEEN :fromDate AND :toDate)")
 	Long countReservationSlotsByDate(
 	        @Param("fromDate") LocalDate fromDate,
 	        @Param("toDate") LocalDate toDate);
 	@Query("SELECT COUNT(rs) FROM ReservationSlot rs " +
 	       "JOIN rs.region r " +
 	       "WHERE (rs.reservationStatus IN ('TEMPORARY', 'RESERVED', 'CANCELLED')) " +
 	       "AND (:regionId IS NULL OR r.id = :regionId)")
 	Long countReservationSlotsByRegion(
 	        @Param("regionId") Integer regionId);
 	
 	@Query("SELECT COUNT(rs) FROM ReservationSlot rs " +
 	       "JOIN rs.region r " +
 	       "WHERE (rs.reservationStatus IN ('TEMPORARY', 'RESERVED', 'CANCELLED')) " +
 	       "AND (:regionId IS NULL OR r.id = :regionId) " +
 	       "AND (:fromDate IS NULL OR :toDate IS NULL OR rs.date BETWEEN :fromDate AND :toDate)")
 	Long countReservationSlots(
 	        @Param("regionId") Integer regionId,
 	        @Param("fromDate") LocalDate fromDate,
 	        @Param("toDate") LocalDate toDate);

	@Query("SELECT rs FROM ReservationSlot rs " +
		       "JOIN rs.region r " + // Join with the region entity
		       "WHERE (rs.reservationStatus IN ('TEMPORARY', 'RESERVED', 'CANCELLED'))")
		List<Object> findReservationBatchWise(Pageable pageable);
	@Query("SELECT count(rs) FROM ReservationSlot rs " +
		       "JOIN rs.region r " + // Join with the region entity
		       "WHERE (rs.reservationStatus IN ('TEMPORARY', 'RESERVED', 'CANCELLED'))")
		Long countReservationBatchWise();
	@Query("SELECT rs FROM ReservationSlot rs " +
		       "WHERE (rs.reservationStatus IN ('TEMPORARY', 'RESERVED', 'CANCELLED')) " +
		       "AND (:fromDate IS NULL OR :toDate IS NULL OR rs.date BETWEEN :fromDate AND :toDate)")
		List<Object> findReservationSlotsByDate(
		        @Param("fromDate") LocalDate fromDate,
		        @Param("toDate") LocalDate toDate);
	
	@Query("SELECT rs FROM ReservationSlot rs " +
		       "JOIN rs.region r " + // Join with the region entity
		       "WHERE (rs.reservationStatus IN ('TEMPORARY', 'RESERVED', 'CANCELLED')) " +
		       "AND (:regionId IS NULL OR r.id = :regionId) ")
		List<Object> findReservationSlotsByRegion(
		        @Param("regionId") Integer regionId);
	
 	@Query("SELECT rs FROM ReservationSlot rs " +
		       "JOIN rs.region r " + // Join with the region entity
		       "WHERE (rs.reservationStatus IN ('TEMPORARY', 'RESERVED', 'CANCELED')) " +
		       "AND (:regionId IS NULL OR r.id = :regionId) " +
		       "AND (:fromDate IS NULL OR :toDate IS NULL OR rs.date BETWEEN :fromDate AND :toDate)")
		List<Object> findReservationSlots(
		        @Param("regionId") Integer regionId,
				@Param("fromDate") LocalDate fromDate,
					@Param("toDate") LocalDate toDate
					);

	@Query("SELECT rs FROM ReservationSlot rs " +
		       "JOIN rs.region r " + // Join with the region entity
		       "WHERE (rs.reservationStatus IN ('TEMPORARY', 'RESERVED', 'CANCELLED'))")
		List<Object> findReservationBatchWise();
	@Query("SELECT rs FROM ReservationSlot rs " +
		       "JOIN rs.region r " + // Join with the region entity
		       "WHERE  (:regionId IS NULL OR r.id = :regionId)  " +
		       "OR (:fromDate IS NULL OR :toDate IS NULL OR rs.date BETWEEN :fromDate AND :toDate)")
		List<Object> findAllReservations(
				 @Param("regionId") Integer regionId,
		        @Param("fromDate") LocalDate fromDate,
		        @Param("toDate") LocalDate toDate);
	@Query("SELECT rs FROM ReservationSlot rs " +
		       "WHERE (:fromDate IS NULL OR :toDate IS NULL OR rs.date BETWEEN :fromDate AND :toDate)")
		List<Object> findDateBetweenReservations(
		        @Param("fromDate") LocalDate fromDate,
		        @Param("toDate") LocalDate toDate);
	@Query("SELECT rs FROM ReservationSlot rs " +
		       "JOIN rs.region r " + // Join with the region entity
		       "WHERE (:regionId IS NULL OR r.id = :regionId)")
		List<Object> findRegionReservations(
		        @Param("regionId") Integer regionId);

}
