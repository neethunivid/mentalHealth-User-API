package com.mentalhealthapi.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mentalhealthapi.model.MailMagazineBody;
import com.mentalhealthapi.model.MailmagazineUser;




public interface MailmagazineBodyRepository extends JpaRepository<MailMagazineBody,Integer> {
	 

//	 List<MailMagazineBody> findByStatusAndTextNotNullAndSendStatusAndSendOutTimeBefore
//	 (int status, int sendStatus, LocalDateTime sendOutTime);
	 @Query("SELECT mmb FROM MailMagazineBody mmb WHERE  mmb.text IS NOT NULL AND mmb.sendStatus = 0 AND mmb.sendOutTime < ?1 AND (mmb.jobStatus = 'PENDING' OR mmb.jobStatus = 'PROCESSING')")
	    List<MailMagazineBody> findPendingOrProcessingNewslettersToSend(LocalDateTime currentDateTime);
	 @Query("SELECT m.mailMagJobId FROM MailMagazineBody m WHERE m.id = :id")
	    Integer findMailMagJobIdById(@Param("id") Integer id);
	 @Query(value = "SELECT * FROM mail_magazine_body WHERE  send_out_time >= DATE_SUB(NOW(), INTERVAL 1 WEEK) ORDER BY id DESC", nativeQuery = true)
	 List<MailMagazineBody> findAllToSend();

}