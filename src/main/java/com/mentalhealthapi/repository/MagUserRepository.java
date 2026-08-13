package com.mentalhealthapi.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mentalhealthapi.model.MagUser;
import com.mentalhealthapi.model.MailMagazineBody;
import com.mentalhealthapi.model.MailMagazineEmail;




public interface MagUserRepository extends JpaRepository<MagUser,Integer> {
	
	 List<MagUser> findByStatusAndMailmagazine(Integer status, int mailmagazineId);
	 void deleteByMailmagazine(int mailmagazineId);
	 MagUser findByMailmagazineAndMailmagazineUser(Integer magId, Integer userId);
	 List<MagUser> findByMailmagazine(Integer mailmagazine);

	 @Query("SELECT COUNT(mu) FROM MagUser mu WHERE mu.mailmagazine = :mailmagazine")
	 long countByMailmagazine(@Param("mailmagazine") Integer mailmagazine);

}
