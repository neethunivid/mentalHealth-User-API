package com.mentalhealthapi.repository;


import org.springframework.data.domain.Pageable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mentalhealthapi.dto.CanceledReservtnResponse;
import com.mentalhealthapi.model.Remarks;
import com.mentalhealthapi.model.ReservationDetails;
import com.mentalhealthapi.model.ReservationRegion;
import com.mentalhealthapi.model.ReservationSlot;

@Repository
public interface ReservationCounsellingSlotRepo extends JpaRepository<ReservationSlot, Integer> {
	int countByDateAndTimeStartAndRegionId(LocalDate date, String timeStart, int regionId);
	// List<ReservationSlot> findByReservationStatusAndRegionIdAndDateBetween(String reservationStatus, int regionId,LocalDate fromDate, LocalDate toDate);
	  
//	@Query("SELECT rs FROM ReservationSlot rs " +
//	           "WHERE (:reservationStatus IS NULL OR rs.reservationStatus = :reservationStatus) " +
//	           "AND (:regionId IS NULL OR :regionId!=0 OR rs.region.id = :regionId) " +
//	           "AND (:fromDate IS NULL OR :toDate IS NULL OR rs.date BETWEEN :fromDate AND :toDate)"
//	           + "")
//	    List<Object> searchReservationSlots(
//	            @Param("reservationStatus") String reservationStatus,
//	            @Param("regionId") Integer regionId,
//	            @Param("fromDate") LocalDate fromDate,
//	            @Param("toDate") LocalDate toDate);
//	

	@Query("SELECT rs FROM ReservationSlot rs " +
		       "WHERE (:reservationStatus IS NULL OR rs.reservationStatus = :reservationStatus) " +
		       "AND (:regionId IS NULL OR :regionId != 0 OR rs.region.id = :regionId) " +
		       "AND (:fromDate IS NULL OR rs.date >= :fromDate) " +
		       "AND (:toDate IS NULL OR rs.date <= :toDate)")
		List<Object> searchReservationSlots(
		        @Param("reservationStatus") String reservationStatus,
		        @Param("regionId") Integer regionId,
		        @Param("fromDate") LocalDate fromDate,
		        @Param("toDate") LocalDate toDate);

//    @Query(value = "SELECT s.date as date,"
//    		+ "    s.time_start as timeStart,   s.time_end as timeEnd,  p.name as name,  s.id as id,   s.reservation_status as reservationStatus,"
//    		+ "    s.reservation_id as reservationId,"
//    		+ "    d.person_name as personName, d.surname, d.gender,  d.job, d.postalCode,"
//    		+ "    d.pref, d.town,  d.address, d.telephone,   d.email, r.name AS region " +
//            "FROM reservation_slot s " + 
//            "LEFT JOIN resp_person p ON s.resp_person = p.id " +
//            "LEFT JOIN reservation_details d ON s.reservation_id = d.reservation_id " +
//            "LEFT JOIN reservation_region r ON s.region_id = r.id " +
//            "WHERE s.reservation_status = 'CANCELLED' " +
//            "ORDER BY s.date DESC, s.time_start ASC " +
//            "LIMIT :start, :rpp", nativeQuery = true)
//    	List<CanceledReservtnResponse> findCanceledReservations(@Param("start") int start, @Param("rpp") int rpp);
//  	
//    
	    @Query(value = "SELECT s.date as date, s.time_start as timeStart, s.time_end as timeEnd,s.age,s.type_of_counselling, p.name as name, s.id as id, s.reservation_status as reservationStatus, s.reservation_id as reservationId, d.person_name as personName, d.surname, d.gender, d.job, d.postalCode, d.pref, d.town, d.address, d.telephone, d.email, r.name AS region " +
            "FROM reservation_slot s " + 
            "LEFT JOIN resp_person p ON s.resp_person = p.id " +
            "LEFT JOIN reservation_details d ON s.reservation_id = d.reservation_id " +
            "LEFT JOIN reservation_region r ON s.region_id = r.id " +
            "WHERE s.reservation_status = 'CANCELLED' " +
            "ORDER BY s.date DESC, s.time_start ASC " +
            "LIMIT :start, :rpp", nativeQuery = true)
		List<Object[]> findCanceledReservations(@Param("start") int start, @Param("rpp") int rpp);
		
		@Query(value = "SELECT COUNT(*) FROM reservation_slot s WHERE s.reservation_status = 'CANCELLED'", nativeQuery = true)
		long countCanceledReservations();
		    

    	@Query(nativeQuery = true, value =
                "SELECT s.date, s.time_start, s.time_end,s.age,s.type_of_counselling, p.name, s.id, s.reservation_status, " +
                "s.reservation_id, d.person_name, d.surname, d.gender, d.job, d.postalcode, " +
                "d.pref, d.town, d.address, d.telephone, d.email, s.region_id, r.name as region " +
                "FROM reservation_slot s " +
                "LEFT JOIN resp_person p ON s.resp_person = p.id " +
                "LEFT JOIN reservation_details d ON s.reservation_id = d.reservation_id " +
                "LEFT JOIN reservation_region r ON s.region_id = r.id " +
                "WHERE s.status = 1 " +
                "ORDER BY s.date DESC, s.time_start ASC " +
                "LIMIT :start, :rpp")
        List<Object[]> findReservationSlots(Integer start, Integer rpp);
        
        List<ReservationSlot> findByRegionIdAndReservationStatusInOrderByDateDescTimeStartAsc(
                int regionId, List<String> reservationStatus);


        @Query("SELECT s FROM ReservationSlot s WHERE s.reservationStatus = 'TEMPORARY'"
        		+ " AND s.status = 1 ORDER BY s.date DESC, s.timeStart ASC")
        Page<Object[]> findTemporarySlots(Pageable pageable);

        @Query("SELECT count(s) FROM ReservationSlot s WHERE s.reservationStatus = 'TEMPORARY'"
                + " AND s.status = 1")
        Long countTemporarySlots();
        
        @Query(value = "SELECT s.date, s.time_start, s.time_end, s.age, s.type_of_counselling, p.name, s.id, s.reservation_status, s.reservation_id, d.person_name, d.surname, d.gender, d.job, d.postalcode, d.pref, d.town, d.address, d.telephone, d.email, s.region_id, r.name AS region " +
                "FROM reservation_slot s " +
                "LEFT JOIN resp_person p ON s.resp_person = p.id " +
                "LEFT JOIN reservation_details d ON s.reservation_id = d.reservation_id " +
                "LEFT JOIN reservation_region r ON s.region_id = r.id " +
                "WHERE CONCAT(s.date, ' ', s.time_start) >= current_timestamp() " +
                "AND s.status = 1 " +
                "AND s.reservation_status IN ('UNRESERVED', 'RESERVED', 'TEMPORARY') " +
                "AND s.type_of_counselling = :typeOfCounselling " +  
                "ORDER BY s.date, s.time_start ASC", nativeQuery = true)
        List<Object[]> getCustomData(@Param("typeOfCounselling") String typeOfCounselling);



}