package com.mentalhealthapi.repository;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mentalhealthapi.dto.RemarkResponseDTO;
import com.mentalhealthapi.dto.RemarkWithMemberDetailsDTO;
import com.mentalhealthapi.dto.TreeDTO;
import com.mentalhealthapi.model.Member;
import com.mentalhealthapi.model.Remarks;

@Repository
public interface RemarksRepository extends JpaRepository<Remarks, Integer> {
	@Query("SELECT r FROM Remarks r WHERE r.member = :memberId ORDER BY r.date ASC")
    List<Remarks> findAllByMemberOrderByDateAsc(@Param("memberId") Integer memberId);

	@Query("SELECT COUNT(r.id) FROM Remarks r,Member m WHERE r.status = 0 AND r.normal=1 AND r.normal <> '' AND m.id=r.member ")
    int countNormalByStatus();
	@Query("SELECT COUNT(r.id) FROM Remarks r,Member m WHERE r.status = 0 AND r.anxiety=1 AND r.anxiety <> '' AND m.id=r.member")
    int countAnxietyByStatus();
    
  
    @Query("SELECT COUNT(r.id) FROM Remarks r,Member m WHERE r.status = 0 AND r.blackmail=1 AND r.blackmail <> '' AND m.id=r.member")
    int countBlackmailByStatus();
    
    @Query("SELECT COUNT(r.id) FROM Remarks r,Member m WHERE r.status = 0  AND r.other=1 AND r.other <> '' AND m.id=r.member")
    int countOtherByStatus();
    
    @Query("SELECT COUNT(r.id) FROM Remarks r,Member m WHERE r.status = 0 AND r.growingup=1 AND r.growingup <> '' AND m.id=r.member ")
    int countGrowingupByStatus();
    
    @Query("SELECT COUNT(r.id) FROM Remarks r,Member m WHERE r.status = 0 AND r.addition1 <> '' AND m.id=r.member")
    int countAddition1ByStatus();
    
    @Query("SELECT COUNT(r.id) FROM Remarks r,Member m WHERE r.status = 0 AND r.addition2 <> '' AND m.id=r.member")
    int countAddition2ByStatus();
    
 
    @Query(value = "SELECT r.id, r.remark_no, m.name, m.email_pc, r.title, r.text, r.remarks, r.date,m.memberid " +
            "FROM remarks r " +
            "JOIN member m ON r.member = m.memberno " +
            "WHERE CASE WHEN :type = 'normal' THEN r.normal = 1 " +
            "           WHEN :type = 'anxiety' THEN r.anxiety = 1 " +
            "           WHEN :type = 'blackmail' THEN r.blackmail = 1 " +
            "           WHEN :type = 'other' THEN r.other = 1 " +
            "           WHEN :type = 'growingup' THEN r.growingup = 1 " +
            "           WHEN :type = 'addition1' THEN r.addition1!='' " +
            "           WHEN :type = 'addition2' THEN r.addition2!='' " +
            "           ELSE FALSE " +
            "      END " +
            "AND r.status = '0' " +
            "ORDER BY r.id ASC", nativeQuery = true)
    List<Object[]> findTypeRemarks(@Param("type") String type);   
   
    
    @Query(value = "SELECT r.remark_no,r.date,m.name,m.memberid, m.email_pc, r.title, r.text " +
            "FROM remarks r " +
            "JOIN member m ON r.member = m.memberno " +
            "WHERE CASE WHEN :type = 'normal' THEN r.normal = 1 " +
            "           WHEN :type = 'anxiety' THEN r.anxiety = 1 " +
            "           WHEN :type = 'blackmail' THEN r.blackmail = 1 " +
            "           WHEN :type = 'other' THEN r.other = 1 " +
            "           WHEN :type = 'growingup' THEN r.growingup = 1 " +
            "           WHEN :type = 'addition1' THEN r.addition1 !='' " +
            "           WHEN :type = 'addition2' THEN r.addition2 !='' " +
            "           ELSE FALSE " +
            "      END " +
            "AND r.status = '1' " +
            "ORDER BY r.id DESC", nativeQuery = true)
    List<Object[]> findTypeRemarksDwnld(@Param("type") String type);
    
    @Query(value = "SELECT r.remark_no,r.date,m.name,m.memberid, m.email_pc, r.title, r.text" +
            "FROM remarks r " +
            "JOIN member m ON r.member = m.memberno " +
            "WHERE  r.status = '1' " +
            "ORDER BY r.id DESC", nativeQuery = true)
    List<Object[]> findAllRemarksDwnld();
    
    @Query("SELECT r.remarkNo, r.date, m.name, m.memberid, m.email_pc, r.title, r.text FROM Remarks r JOIN Member m ON m.id = r.member " +
    	       "WHERE r.status = 1 " +
    	       "AND (:remarkId IS NULL OR r.remarkNo = :remarkId) " +
    	       "AND (:memberId IS NULL OR m.memberid = :memberId) " +
      	       "AND (:title IS NULL OR r.title LIKE %:title%) " +
    	       "AND (:fromDate IS NULL OR r.date >= :fromDate) " +
    	       "AND (:toDate IS NULL OR r.date <= :toDate)")
    	List<Object[]> searchRemarksDnwld(@Param("remarkId") String remarkId,
    	                                   @Param("memberId") String memberId,
    	                                   @Param("title") String title,
    	                                   @Param("fromDate") LocalDateTime fromDate,
    	                                   @Param("toDate") LocalDateTime toDate);

    	
     	 @Query(value = "SELECT r.remark_no,r.date,m.name,m.memberid, m.email_pc, r.title, r.text, r.remarks " +
                 "FROM remarks r " +
                 "JOIN member m ON r.member = m.memberno " +
                 "WHERE  r.status = '1' AND r.remark_no LIKE %:remarkId% OR r.id=:remarkId " +
                 "ORDER BY r.id DESC", nativeQuery = true)
         List<Object[]> searchRemarksDnwldRmk(@Param("remarkId") String remarkId);
         
         @Query(value = "SELECT r.remark_no,r.date,m.name,m.memberid, m.email_pc, r.title, r.text, r.remarks " +
                 "FROM remarks r " +
                 "JOIN member m ON r.member = m.memberno " +
                 "WHERE  r.status = '1' And  m.memberid = :memberId " +
                 "ORDER BY r.id DESC", nativeQuery = true)
         List<Object[]> searchRemarksDnwldMem(@Param("memberId") String memberId);
         
         @Query(value = "SELECT r.remark_no,r.date,m.name,m.memberid, m.email_pc, r.title, r.text, r.remarks " +
                 "FROM remarks r " +
                 "JOIN member m ON r.member = m.memberno " +
                 "WHERE  r.status = '1' And r.remark_no LIKE %:title%  OR r.title LIKE %:title% " +
                 "ORDER BY r.id DESC", nativeQuery = true)
         List<Object[]> searchRemarksDnwldTitle(@Param("title") String title);
         
         @Query(value = "SELECT r.remark_no, r.date, m.name, m.memberid, m.email_pc, r.title, r.text, r.remarks " +
                 "FROM remarks r " +
                 "JOIN member m ON r.member = m.memberno " +
                 "WHERE r.status = '1' " +
                 "AND (:fromDate IS NULL OR r.date >= :fromDate) " +
                 "AND (:toDate IS NULL OR r.date <= :toDate) " +
                 "ORDER BY r.id DESC", nativeQuery = true)
          List<Object[]> searchRemarksDnwldDate(@Param("fromDate") LocalDateTime fromDate,
                                       @Param("toDate") LocalDateTime toDate);
         
   
       
          @Query(value = "SELECT r.id, r.remark_no, r.remarks as parent_remark_id, r.title, r.text, r.remarks, r.date " +
        	         "FROM remarks r " +
        	         "WHERE CASE WHEN :type = 'normal' THEN r.normal = 1 " +
                     "           WHEN :type = 'anxiety' THEN r.anxiety = 1 " +
                     "           WHEN :type = 'blackmail' THEN r.blackmail = 1 " +
                     "           WHEN :type = 'other' THEN r.other = 1 " +
                     "           WHEN :type = 'growingup' THEN r.growingup = 1 " +                     
                     "           ELSE FALSE " +
                     "      END " +
                     "AND r.status = '1' " +
        	         "ORDER BY CASE WHEN r.remarks = 0 THEN r.id ELSE r.remarks END ASC, r.id ASC", nativeQuery = true)
        	List<Object[]> findRemarkHierarchyTreeByType(@Param("type") String type);

        	@Query(value = "SELECT r.id, r.remark_no, r.remarks AS parent_remark_id, r.title, r.text, r.remarks, r.date, r.member, m.name, " +
                    "r.remarks AS count " +
                    "FROM remarks r " +
                    "JOIN member m ON r.member = m.memberno " +
                    "WHERE CASE WHEN :type = 'normal' THEN r.normal = 1 " +
                    "           WHEN :type = 'anxiety' THEN r.anxiety = 1 " +
                    "           WHEN :type = 'blackmail' THEN r.blackmail = 1 " +
                    "           WHEN :type = 'other' THEN r.other = 1 " +
                    "           WHEN :type = 'growingup' THEN r.growingup = 1 " +
                    "           WHEN :type = 'addition1' THEN r.addition1 != '' " +
                    "           WHEN :type = 'addition2' THEN r.addition2 != '' " +
                    "           ELSE FALSE " +
                    "      END " +
                    "AND r.status = '1' AND r.remarks=0 " +
                    "ORDER BY r.id DESC",                   
                    nativeQuery = true)
     Page<Object[]> findRemarkHierarchyTreeListByType(@Param("type") String type, Pageable pageable);

     @Query(value = "SELECT count(r.id) as count " +            
             "FROM remarks r " +
             "JOIN member m ON r.member = m.memberno " +
             "WHERE CASE WHEN :type = 'normal' THEN r.normal = 1 " +
             "           WHEN :type = 'anxiety' THEN r.anxiety = 1 " +
             "           WHEN :type = 'blackmail' THEN r.blackmail = 1 " +
             "           WHEN :type = 'other' THEN r.other = 1 " +
             "           WHEN :type = 'growingup' THEN r.growingup = 1 " +
             "           WHEN :type = 'addition1' THEN r.addition1 != '' " +
             "           WHEN :type = 'addition2' THEN r.addition2 != '' " +
             "           ELSE FALSE " +
             "      END " +
             "AND r.status = '1' AND r.remarks=:parent " +
             "ORDER BY r.id DESC",                   
             nativeQuery = true)
     Integer findChildCount(@Param("type") String type,@Param("parent") Integer parent);
        	
        	
  	
        
        @Query(value = "SELECT r.id, r.remark_no,r.title,r.text, r.remarks, r.date " +
                "FROM remarks r " +
                "WHERE (:type IS NOT NULL) AND (r.remarks = :remarkId OR r.id = :remarkId) " +
                "ORDER BY CASE WHEN r.remarks = 0 THEN r.id ELSE r.remarks END ASC, r.id ASC", nativeQuery = true)
        List<Object[]> findRemarkHierarchyByTypeAndRemark(@Param("type") String type, @Param("remarkId") Integer remarkId);

      
        @Query(value = "SELECT r.*, m.name as name, m.email_pc as email_pc,m.memberid as memberid FROM remarks r JOIN member m ON " +
                "m.id = r.member AND r.status=1 " +
                "AND (:remarkId IS NULL OR r.id = :remarkId OR r.remark_no  LIKE %:remarkId%) " +
                "AND (:memberId IS NULL OR r.member = :memberId OR m.memberid = :memberId) " +
                "AND (:title IS NULL OR " +
                "   r.title LIKE %:title% OR " +
                "   r.remark_no LIKE %:title% OR " +
                "   CAST(r.id AS CHAR) LIKE %:title% " +
                ") " +
                "AND (:fromDate IS NULL OR r.date >= :fromDate) " +
                "AND (:toDate IS NULL OR r.date <= :toDate)", nativeQuery = true)
        List<Object[]> searchRemarks(@Param("remarkId") String remarkId,
                                     @Param("memberId") String memberId,
                                     @Param("title") String title,
                                     @Param("fromDate") Date fromDate,
                                     @Param("toDate") Date toDate);
        
        @Query(value = "SELECT r.remark_no, r.date, m.name, m.memberid, m.email_pc, r.title, r.text, r.remarks  FROM remarks r JOIN member m ON " +
                "m.id = r.member AND r.status=1 " +
                "AND (:remarkId IS NULL OR r.id = :remarkId OR r.remark_no  LIKE %:remarkId%) " +
                "AND (:memberId IS NULL OR r.member = :memberId OR m.memberid = :memberId) " +
                "AND (:title IS NULL OR " +
                "   r.title LIKE %:title% OR " +
                "   r.remark_no LIKE %:title% OR " +
                "   CAST(r.id AS CHAR) LIKE %:title% " +
                ") " +
                "AND (:fromDate IS NULL OR r.date >= :fromDate) " +
                "AND (:toDate IS NULL OR r.date <= :toDate)", nativeQuery = true)
        List<Object[]> searchRemarksDwnld(@Param("remarkId") String remarkId,
                                     @Param("memberId") String memberId,
                                     @Param("title") String title,
                                     @Param("fromDate") Date fromDate,
                                     @Param("toDate") Date toDate);
        
     


        @Query(value = "SELECT r.*, m.name as name, m.email_pc as email_pc,m.memberid as memberid FROM remarks r, member m WHERE m.id = r.member AND r.status=1 ", nativeQuery = true)
        List<Object[]> getAllRemarks();

        @Query("SELECT COUNT(r.id) FROM Remarks r JOIN Member m ON r.member=m.id Where r.status=1")
        Integer getTotalRemarkCount();
        
        @Query("SELECT r.id FROM Remarks r WHERE r.remarks = :parentId ORDER BY r.id")
        List<Integer> findIdsByRemarksOrderByid(@Param("parentId") Integer parentId);
        
        
        int countByRemarksAndStatus(int remarkId, int status);
      
        @Query(value = "SELECT r.*, m.name as memberName, m.sex as memberSex " +
                "FROM Remarks r JOIN Member m ON r.member = m.id " +
                "WHERE r.status = 1 AND r.remarks = 0 AND r.id = :rid AND " +
                "(:condition = 'normal' AND r.normal = 1 OR " +
                ":condition = 'anxiety' AND r.anxiety = 1 OR " +
                ":condition = 'blackmail' AND r.blackmail = 1 OR " +
                ":condition = 'other' AND r.other = 1 OR " +
                ":condition = 'growingup' AND r.growingup = 1) " +
                "ORDER BY r.date DESC", nativeQuery = true)
        	List<RemarkWithMemberDetailsDTO> findByConditionAndId(@Param("condition") String condition, @Param("rid") Integer rid);


         @Query("SELECT r FROM Remarks r WHERE r.remarks = :parentId AND r.status = 1 ORDER BY r.date ASC")
         List<Remarks> findChildRemarks(@Param("parentId") Integer parentId);
         
       //  List<RemarkResponseDTO> findByRemarksAndStatus(Integer integer, String status);
         
         @Query("SELECT r.id, r.remarkNo, r.title, r.text, r.date, r.status,r.member,r.remarks, m.name, m.sex " +
        	       "FROM Remarks r JOIN Member m ON r.member = m.id " +
        	       "WHERE r.remarks = :remarks AND r.status = :status")
        	List<Object[]> findByRemarksAndStatus(@Param("remarks") Integer remarks, @Param("status") String status);
         
         @Query(value = "SELECT COUNT(*) FROM remarks r " +
                 "JOIN member m ON r.member = m.memberno " +
                 "WHERE CASE WHEN :type = 'normal' THEN r.normal = 1 " +
                 "           WHEN :type = 'anxiety' THEN r.anxiety = 1 " +
                 "           WHEN :type = 'blackmail' THEN r.blackmail = 1 " +
                 "           WHEN :type = 'other' THEN r.other = 1 " +
                 "           WHEN :type = 'growingup' THEN r.growingup = 1 " +
                 "           WHEN :type = 'addition1' THEN r.addition1 != '' " +
                 "           WHEN :type = 'addition2' THEN r.addition2 != '' " +
                 "           ELSE FALSE " +
                 "      END " +
                 "AND r.status = '1' AND r.remarks=0", 
                 nativeQuery = true)
           Long countRemarkHierarchyTreeByType(@Param("type") String type);

}