package com.mentalhealthapi.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mentalhealthapi.model.MailMagazineBody;
import com.mentalhealthapi.model.MailMagazineEmail;




public interface MailmagazineEmailRepository extends JpaRepository<MailMagazineEmail,Integer> {
	
	// List<MailMagazineEmail> findByMailMagJobAndStatus(int mailMagJobId, int status);
	    @Query("SELECT email FROM MailMagazineEmail email WHERE email.status = 0 AND email.mailMagJob=?1 AND email.sendStatus != 'COMPLETED'")
	    List<MailMagazineEmail> findPendingAndNotCompletedEmails(int mailMagJobId);
      
	    
	    @Query("SELECT m FROM MailMagazineEmail m WHERE m.mailMagJob = :id")
	    List<MailMagazineEmail> findMailMagEmailsByJobId(@Param("id") Integer id);
	    
}