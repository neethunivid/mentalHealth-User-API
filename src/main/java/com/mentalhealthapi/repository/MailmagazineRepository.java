package com.mentalhealthapi.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mentalhealthapi.model.Mailmagazine;
import com.mentalhealthapi.model.MailmagazineUser;



public interface MailmagazineRepository extends JpaRepository< Mailmagazine,Integer> {
	 Mailmagazine findByTitle(String title);
     void save(MailmagazineUser mailUser);
	 List<Mailmagazine> findByStatusAndTextNotNullAndSendStatusAndSendTimeBefore
	 (int status, int sendStatus, LocalDateTime sendOutTime);
	 
	 @Query(value = "SELECT * FROM mailmagazine WHERE non = :nonValue AND sendtime >= DATE_SUB(NOW(), INTERVAL 1 WEEK) ORDER BY id DESC", nativeQuery = true)
	 Page<Mailmagazine> findByNonAndSendtimeOrderByidDesc(@Param("nonValue") byte nonValue, Pageable pageable);
	 
	 @Query(value = "SELECT * FROM mailmagazine WHERE non = :nonValue ORDER BY id DESC", nativeQuery = true)
	 Page<Mailmagazine> findByNonOrderByidDesc(@Param("nonValue") byte nonValue,Pageable pageable);
	 
	 @Query(value = "SELECT COUNT(*) FROM mailmagazine WHERE non = :nonValue", nativeQuery = true)
	 Long countByNon(@Param("nonValue") byte nonValue);

	  List<Mailmagazine> findByStatusAndTextNotNullAndSendStatusAndSendTimeLessThanEqualOrderByDateAsc(int status,
	             int sendStatus,
	             LocalDateTime curDate);
	 

}