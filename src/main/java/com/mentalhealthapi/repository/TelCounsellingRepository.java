package com.mentalhealthapi.repository;

import com.mentalhealthapi.model.TelConselling;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TelCounsellingRepository extends JpaRepository<TelConselling,Integer> {
	//gender Report
	@Query("SELECT FUNCTION('DATE_FORMAT', t.date, '%Y-%m') AS month, " +
			"t.gender AS genderType, " +
		    "t.userType AS userType, " +
		    "COUNT(t) AS count " +
		    "FROM TelConselling t " +
		    "WHERE t.date BETWEEN :startDate AND :endDate " +
		    "GROUP BY month, genderType, userType " +
		    "ORDER BY month")
		List<Object[]> customMonthlyReportQuery(LocalDate startDate, LocalDate endDate);


	@Query("SELECT FUNCTION('DATE_FORMAT', t.date, '%Y-%m-%d') AS day, " +
			"t.gender AS genderType, " +
			"t.userType AS userType, " +
			"COUNT(t) AS count " +
			"FROM TelConselling t " +
			"WHERE t.date BETWEEN :startDate AND :endDate " +
			"GROUP BY day, genderType, userType " +
			"ORDER BY day")
		List<Object[]> customDailyReportQuery(LocalDate startDate, LocalDate endDate);

	//Age Report
 	@Query("SELECT FUNCTION('DATE_FORMAT', t.date, '%Y-%m') AS month, " +
        	"t.age AS ageType, COUNT(t) AS count " +
        	"FROM TelConselling t " +
        	"WHERE t.date BETWEEN :startDate AND :endDate " +
        	"GROUP BY month, ageType " +
        	"ORDER BY month")
 	 List<Object[]> customMonthlyAgeReportQuery(LocalDate startDate, LocalDate endDate);

  	@Query("SELECT FUNCTION('DATE_FORMAT', t.date, '%Y-%m-%d') AS day, " +
         	"t.age AS ageType, COUNT(t) AS count " +
        	"FROM TelConselling t " +
        	"WHERE t.date BETWEEN :startDate AND :endDate " +
        	"GROUP BY day, ageType " +
         	"ORDER BY day")
  	List<Object[]> customDailyAgeReportQuery(LocalDate startDate, LocalDate endDate);

  	//Illness Report
  	@Query("SELECT FUNCTION('DATE_FORMAT', t.date, '%Y-%m') AS month, " +
  	        "t.illness1, t.illness2, t.illness3, t.illness4, t.illness5, t.illness6, t.illness7, t.illness8 " +
  	        "FROM TelConselling t " +
  	        "WHERE t.date BETWEEN :startDate AND :endDate " +
  	        "GROUP BY month, t.illness1, t.illness2, t.illness3, t.illness4, t.illness5, t.illness6, t.illness7, t.illness8 " +
  	        "ORDER BY month")
  	List<Object[]> customIllnessMonthlyReportQuery(LocalDate startDate, LocalDate endDate);

  	// Illness Report - Daily
  	@Query("SELECT FUNCTION('DATE_FORMAT', t.date, '%Y-%m-%d') AS day, " +
  	        "t.illness1, t.illness2, t.illness3, t.illness4, t.illness5, t.illness6, t.illness7, t.illness8 " +
  	        "FROM TelConselling t " +
  	        "WHERE t.date BETWEEN :startDate AND :endDate " +
  	        "GROUP BY day, t.illness1, t.illness2, t.illness3, t.illness4, t.illness5, t.illness6, t.illness7, t.illness8 " +
  	        "ORDER BY day")
  	List<Object[]> customIllnessDailyReportQuery(LocalDate startDate, LocalDate endDate);

  	// Counselling Report
  	@Query("SELECT FUNCTION('DATE_FORMAT', t.date, '%Y-%m') AS month, " +
  	        "t.advice1, t.advice2, t.advice3, t.advice4, t.advice5, t.advice6, t.advice7 " +
  	        "FROM TelConselling t " +
  	        "WHERE t.date BETWEEN :startDate AND :endDate " +
  	        "GROUP BY month, t.advice1, t.advice2, t.advice3, t.advice4, t.advice5, t.advice6, t.advice7 " +
  	        "ORDER BY month")
  	List<Object[]> counsellingMonthlyReportQuery(LocalDate startDate, LocalDate endDate);

  	@Query("SELECT FUNCTION('DATE_FORMAT', t.date, '%Y-%m-%d') AS day, " +
  	        "t.advice1, t.advice2, t.advice3, t.advice4, t.advice5, t.advice6, t.advice7 " +
  	        "FROM TelConselling t " +
  	        "WHERE t.date BETWEEN :startDate AND :endDate " +
  	        "GROUP BY day, t.advice1, t.advice2, t.advice3, t.advice4, t.advice5, t.advice6, t.advice7 " +
  	        "ORDER BY day")
  	List<Object[]> counsellingDailyReportQuery(LocalDate startDate, LocalDate endDate);


	@Query("SELECT t FROM TelConselling t WHERE t.status = 1")
	Page<TelConselling> findAllActive(PageRequest pageRequest);
}
