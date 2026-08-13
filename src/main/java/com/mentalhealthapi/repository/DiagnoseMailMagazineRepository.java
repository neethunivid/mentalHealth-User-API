package com.mentalhealthapi.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mentalhealthapi.dto.DiagnosisMailingResponse;
import com.mentalhealthapi.model.DiagnoseMailMagazine;

public interface DiagnoseMailMagazineRepository extends JpaRepository<DiagnoseMailMagazine, Integer> {
//
//	@Query("SELECT DISTINCT a.id, b.title, "
//			+ "CASE WHEN a.mail_type = 1 THEN 'SP' ELSE 'PC' END, a.sentTime, a.sendStatus, dc.userEmail,dc.id as checkid,"
//			+ "b.id as typeId,a.magMailSubject as magMailSubject,a.content as content,a.title as title "
//			+ "FROM DiagnoseMailMagazine a, ChecksheetType b, DiagnoseMailUsers usr, DiagnoseCheck dc " + 
//	          "WHERE a.checkSheetType = b.id AND usr.diagnoseMailMagazine = a.id AND dc.id = usr.diagnoseCheck "
//			+ "ORDER BY a.id DESC")
//	Page<Object[]> findDiagnoseMailMagazineList(Pageable pageable);
	   @Query(value = "SELECT a.id, b.title, "
	            + "CASE WHEN a.mail_type = 1 THEN 'SP' ELSE 'PC' END, a.send_time, a.send_status, "
	            + "GROUP_CONCAT(dc.user_email SEPARATOR ', ') AS user_emails, GROUP_CONCAT(dc.id SEPARATOR ', ') AS checkid, "
	            + "b.id AS typeId, a.mag_mail_subject AS magMailSubject, a.content AS content, a.title AS title "
	            + "FROM diagnose_mail_magazine a "
	            + "INNER JOIN checksheet_type b ON a.check_sheet_type = b.id "
	            + "INNER JOIN diagnose_mail_users usr ON usr.diagnose_mail_magazine = a.id "
	            + "INNER JOIN diagnose_check dc ON dc.id = usr.diagnose_check "
	            + "GROUP BY a.id, b.title, a.mail_type, a.send_status, a.send_time "
	            + "ORDER BY a.id DESC ",
	            nativeQuery = true)
	    Page<Object[]> findDiagnoseMailMagazineList(Pageable pageable);
	@Query("SELECT count(a) FROM DiagnoseMailMagazine a, ChecksheetType b WHERE a.checkSheetType = b.id")
	int findCountDiagnoseMailMagazineList();

	@Query("SELECT COUNT(DISTINCT a.id) FROM DiagnoseMailMagazine a , ChecksheetType b WHERE a.checkSheetType = b.id")
	int countDistinctIds();
	 
	@Query("SELECT m.id, m.checkSheetType, m.mail_type, m.title, m.content FROM DiagnoseMailMagazine m "
			+ "WHERE m.sendStatus = 0 AND m.sentTime <= CURRENT_TIMESTAMP ORDER BY m.id")
	List<Object[]> findUnsentMagazines();

//	    @Query("SELECT mu.id AS user_id, d.id AS sheet_id, d.userName, d.userEmail " +
//	            "FROM DiagnoseMailUsers mu " +
//	            "LEFT JOIN mu.diagnoseCheck d " +
//	            "LEFT JOIN mu.diagnoseMailMagazine m " +
//	            "WHERE m.id = :magazineId " +
//	            "AND (mu.sendStatus != 1 OR mu.sendStatus IS NULL) " +
//	            "AND d.id > 0 " +
//	            "LIMIT 10")
//	     List<Object[]> findUsersForMagazine(long magazineId);
//	
	@Query("SELECT mu.id AS user_id, d.id AS sheet_id, d.userName, d.userEmail " + "FROM DiagnoseMailUsers mu "
			+ "LEFT JOIN mu.diagnoseCheck d " + "LEFT JOIN mu.diagnoseMailMagazine m " + "WHERE m.id = :magazineId "
			+ "AND (mu.sendStatus != 1 OR mu.sendStatus IS NULL) " + "AND d.id > 0")
	List<Object[]> getUsersWithLimitAndOffset(@Param("magazineId") Long magazineId);

	@Modifying
	@Query("UPDATE DiagnoseMailMagazine SET sentTime = CURRENT_TIMESTAMP, sendStatus = 1 WHERE id = :magId ")
	int updateDiagnoseMailMagStatus(@Param("magId") int magId);
	
	@Modifying
	@Query("UPDATE DiagnoseMailUsers SET sendTime=CURRENT_TIMESTAMP, sendStatus=1 WHERE diagnoseCheck.id IN :checkIds and diagnoseMailMagazine.id= :magId ")
	int updateDiagnoseMailMagUserStatus(@Param("magId") int magId,@Param("checkIds") List<Integer> checkIds);
	
	@Modifying
	@Query("UPDATE DiagnoseMailMagazine SET jobRemark=:jobRemark,jobStatus=:jobStatus WHERE id= :magId ")
	int updateDiagnoseMailMagJobStatus(@Param("magId") int magId,@Param("jobRemark") String jobRemark,@Param("jobStatus") String jobStatus);
	
	@Modifying
	@Query("UPDATE DiagnoseCheck SET mailSendDate=CURRENT_TIMESTAMP, mailSendStatus=1 WHERE id IN :checkIds ")
	int updateDiagnoseCheckStatus(@Param("checkIds") List<Integer> checkIds);
	

}
