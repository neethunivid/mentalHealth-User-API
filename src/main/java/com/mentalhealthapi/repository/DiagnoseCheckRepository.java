package com.mentalhealthapi.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mentalhealthapi.model.DiagnoseCheck;
import com.mentalhealthapi.model.RespPerson;



public interface DiagnoseCheckRepository extends JpaRepository<DiagnoseCheck,Integer> {
	 @Query("SELECT DISTINCT d.userEmail FROM DiagnoseCheck d WHERE d.id IN :ids")
	    List<String> findDistinctUserEmailsByIdIn(@Param("ids") List<Integer> ids);
	 
	 List<DiagnoseCheck> findByFinYear(int finYear);
	 
	 @Query("SELECT d FROM DiagnoseCheck d WHERE d.finYear = :year")
	    Page<DiagnoseCheck> findDiagnoseCheckByFinYear(@Param("year") int year, Pageable pageable);

	 @Query("SELECT DISTINCT dc.finYear FROM DiagnoseCheck dc ORDER BY dc.finYear")
	    List<String> findDistinctFinYearOrderByYear();
	 
	 @Query("SELECT count(d) FROM DiagnoseCheck d WHERE d.finYear=:year")
	    int countDiagnoseCheckByFinYear(@Param("year") int year);

	 @Query("SELECT d FROM DiagnoseCheck d WHERE isDeleted=1")
	    List<DiagnoseCheck> findDeletedMail(Pageable pageable);
	 
	 @Query("SELECT count(d) FROM DiagnoseCheck d WHERE isDeleted=1")
	    int findCountDeletedMail();
	 
	    @Modifying
	    @Query("UPDATE DiagnoseCheck dc SET dc.isDeleted=0 WHERE dc.id IN :idArray")
	    int updateIsDeletedForIds(@Param("idArray") List<Integer> idArray);
	    
	    @Query(value = "SELECT COUNT(id) AS cnt, DATE(received_date) AS dt, check_sheet_type " +
                "FROM diagnose_check " +
                "WHERE DATE(received_date) >= :start " +
                "  AND DATE(received_date) <= :end " +
                "  AND check_sheet_type > 0 " +
                "  AND is_deleted != 1 " +
                "GROUP BY DATE(received_date), check_sheet_type", nativeQuery = true)
         List<Object[]> getDiagnoseCheckCounts(@Param("start") String start, @Param("end") String end) ; 
         @Query(value = "SELECT DATE_FORMAT(received_date, '%Y/%m') AS term, check_sheet_type, COUNT(id) AS cnt " +
                 "FROM diagnose_check " +
                 "WHERE DATE(received_date) BETWEEN :startDate AND :endDate " +
                 "AND check_sheet_type > 0 " +
                 "AND is_deleted != 1 " +
                 "GROUP BY DATE_FORMAT(received_date, '%Y/%m'), check_sheet_type",
         nativeQuery = true)
         List<Object[]> customMonthlyReportQuery(@Param("startDate") LocalDate startDate,
                                          @Param("endDate") LocalDate endDate);
         
         

         @Query(value = "SELECT COUNT(tt.id) as cnt, DATE(dc.received_date) as dt, tt.talk_type check_sheet_type " +
                 "FROM diagnose_talk_type AS tt " +
                 "LEFT JOIN diagnose_check dc ON tt.diagnose_check = dc.id " +
                 "WHERE DATE(dc.received_date) >= :start " +
                 "AND dc.is_deleted != 1 " +
                 "AND DATE(dc.received_date) <= :end " +
                 "AND dc.check_sheet_type = :checksheetType " +
                 "GROUP BY DATE(dc.received_date), tt.talk_type", nativeQuery = true)
         List<Object[]> customQueryWithChecksheetType(
                 @Param("start") String start,
                 @Param("end") String end,
                 @Param("checksheetType") String checksheetType
         );

         @Query(value = "SELECT COUNT(tt.id) as cnt, DATE(dc.received_date) as dt, tt.talk_type check_sheet_type " +
                 "FROM diagnose_talk_type AS tt " +
                 "LEFT JOIN diagnose_check dc ON tt.diagnose_check = dc.id " +
                 "WHERE DATE(dc.received_date) >= :start " +
                 "AND dc.is_deleted != 1 " +
                 "AND DATE(dc.received_date) <= :end " +
                 "GROUP BY DATE(dc.received_date), tt.talk_type", nativeQuery = true)
         List<Object[]> customQueryWithoutChecksheetType(
                 @Param("start") String start,
                 @Param("end") String end
         );
         
         @Query(nativeQuery = true, value = 
        	        "SELECT COUNT(tt.id) AS cnt, DATE_FORMAT(dc.received_date, '%Y/%m') AS term, tt.talk_type AS check_sheet_type " +
        	        "FROM diagnose_talk_type AS tt " +
        	        "LEFT JOIN diagnose_check dc ON tt.diagnose_check = dc.id " +
        	        "WHERE DATE(dc.received_date) >= :start " +
        	        "AND dc.is_deleted != 1 " +
        	        "AND DATE(dc.received_date) <= :end " +
        	        "GROUP BY DATE_FORMAT(dc.received_date, '%Y/%m'), tt.talk_type")
        	    List<Object[]> getDiagnoseCheckCountsByDate(
        	        @Param("start") LocalDate start,
        	        @Param("end") LocalDate end
        	    );
                @Query(nativeQuery = true, value = 
            	        "SELECT COUNT(tt.id) AS cnt, DATE_FORMAT(dc.received_date, '%Y/%m') AS term, tt.talk_type AS check_sheet_type " +
            	        "FROM diagnose_talk_type AS tt " +
            	        "LEFT JOIN diagnose_check dc ON tt.diagnose_check = dc.id " +
            	        "WHERE DATE(dc.received_date) >= :start " +
            	        "AND dc.is_deleted != 1 " +
            	        "AND DATE(dc.received_date) <= :end " +
            	        "AND dc.check_sheet_type = :checksheetType " +
            	        "GROUP BY DATE_FORMAT(dc.received_date, '%Y/%m'), tt.talk_type")
            	    List<Object[]> getDiagnoseCheckCountsByDateAndType(
            	        @Param("start") LocalDate start,
            	        @Param("end") LocalDate end,
            	        @Param("checksheetType") String checksheetType
            	    );
}