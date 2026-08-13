package com.mentalhealthapi.repository;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mentalhealthapi.dto.ResponseList;
import com.mentalhealthapi.model.MailMagJob;

public interface MailMagJobRepository extends JpaRepository<MailMagJob, Integer> {
	int countByTopic(String topic);
	Optional<MailMagJob> findById(Integer id);
	
	 @Query(value = "SELECT id,topic from mail_mag_job where type=:type and status=0 " +
            "ORDER BY id ASC", nativeQuery = true)
     List<Object[]> getTopic(@Param("type") String type);
     

	
}
