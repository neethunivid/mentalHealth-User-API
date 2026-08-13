package com.mentalhealthapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mentalhealthapi.model.Config;

import jakarta.transaction.Transactional;


@Repository
public interface ConfigRepository extends JpaRepository<Config, String> {
    Optional<Config> findByKey(String key);

	Optional<Config> findById(Integer id);
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE config c SET c.value = :value WHERE c.id = :id", nativeQuery = true)
	void updateConfigValue(@Param("id") Integer id, @Param("value") String value);
	

	@Transactional
	@Modifying
	@Query("UPDATE Config c SET c.notes = :notes, c.value = :value WHERE c.id = :id")
	void updateConfigByKey(@Param("id") Integer id, @Param("notes") String notes, @Param("value") String value);

}
