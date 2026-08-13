package com.mentalhealthapi.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mentalhealthapi.model.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Integer> {
//	@Query(value = "SELECT m.change_date,m.memberno, m.name, m.memberid, m.password,m.sex, m.dob, m.job,m.join_date, m.email_pc, md.reason_leaving "
//			+ "FROM member m JOIN member_deleted md ON m.id = md.id " + "WHERE m.status = 3 OR m.status = 5 "
//			+ "ORDER BY m.change_date DESC", nativeQuery = true)
//	List<Object[]> getMemberAndDeletedInfoByStatus();

	@Query(value = "SELECT m.change_date, m.memberno, m.name, m.memberid, m.password, m.sex, m.dob, j.name as job, m.join_date, m.email_pc, md.reason_leaving "
			+ "FROM member m "
			+ "JOIN member_deleted md ON m.id = md.member "
			+ "LEFT JOIN job j ON m.job = j.id where m.role='USER' " 			
			+ "ORDER BY m.change_date DESC", nativeQuery = true)
    List<Object[]> getMemberAndDeletedInfoByStatus();
	
	@Query("SELECT m, md FROM Member m JOIN MemberDeleted md ON m.memberno = md.memberno where m.role='USER' and m.memberno=md.member ORDER BY m.id ASC")
	Page<Object[]> getMemberAndDeletedInfo(Pageable pageable);
	
	@Query(value = "SELECT COUNT(m) FROM Member m JOIN MemberDeleted md ON m.memberno = md.memberno where m.role='USER' and m.memberno=md.member")
	Long getTotalMemberAndDeletedCount();

	
	@Query("SELECT m, r FROM Member m JOIN RemarksInitial r ON m.id = r.member where m.status=:status")
	List<Object[]> getMemberWithRemarks(@Param("status") int status);
	
	@Query("SELECT m, r FROM Member m JOIN RemarksInitial r ON m.id = r.member where m.id=:id")
	List<Object[]> getMemberDataWithRemarks(@Param("id") int id);
	
	
	@Query(value ="SELECT distinct m.memberno, m.name, m.name2, m.namek, m.namek2, m.memberid, m.password, " +
	        "CASE WHEN m.sex = 1 THEN '男性' ELSE '女性' END AS sex, " +
	        "m.dob, j.name, m.job_additional, p.name, m.address1, m.address2, m.address3, " +
	        "m.tel, m.mob, m.workplace, m.howfound, m.email_pc, m.email_mob,"
	        + "CASE WHEN m.mailmagazine = 1 THEN '購読' ELSE '中止' END AS magazine, " +
	        "m.remarks_initial, m.join_date, " +
	        "CASE WHEN m.status = 1 THEN '承認済み' " +
	        "     WHEN m.status = 2 THEN '利用中止' " +
	        "     WHEN m.status = 4 THEN '退会' " +
	        "     ELSE 'unknown' END AS status, " +
	        " m.change_date, m.change_cause, mr.title, mr.text " +
	        "FROM member m " +
	        "JOIN remarks_initial mr ON m.id = mr.member " +
	        "JOIN job j ON m.job = j.id " +
	        "JOIN prefecture p ON m.pref = p.id " +
	        "WHERE m.status != 3 AND m.status != 5 AND m.status = 1 AND m.role='USER' ", nativeQuery = true)
	List<Object[]> getMemberInfoByIdAndStatusNot3AndNot5(@Param("id") Integer id);


	  
//	@Query(value ="SELECT  m.memberno, m.name, m.name2, m.namek, m.namek2, m.memberid, m.password, m.sex, m.dob, m.job, m.job_additional, m.pref, m.address1, m.address2, m.address3, m.tel, m.mob, m.workplace, m.howfound, m.email_pc, m.email_mob, m.mailmagazine, m.remarks_initial, m.join_date, m.change_date, m.change_cause, m.status, m.date, mr.title, mr.text FROM member m JOIN remarks_initial mr ON m.id = mr.member WHERE m.status != 3 AND m.status != 5", nativeQuery = true)
//		List<Object[]> getMemberInfoByIdAndStatusNot3AndNot5(@Param("id") Integer id);

		@Query(value ="SELECT  m.memberno, m.name, m.name2, m.namek, m.namek2, m.memberid, m.password, "
				+ "CASE WHEN m.sex = 1 THEN '男性' ELSE '女性' END AS sex , m.dob, j.name,"
				+ " m.job_additional, p.name, m.address1, m.address2, m.address3, m.tel, m.mob, "
				+ "m.workplace, m.howfound, m.email_pc, m.email_mob, CASE WHEN m.mailmagazine = 1 THEN '購読' ELSE '中止' END AS magazine, m.remarks_initial, m.join_date,"
				+ "CASE WHEN m.status = 1 THEN '承認済み' WHEN m.status = 2 THEN '利用中止'"
				+ " WHEN m.status = 4 THEN '退会' ELSE 'unknown' END AS status, "
				+ "m.change_date, m.change_cause,mr.title, mr.text"
				+ " FROM member m JOIN remarks_initial mr "
				+ "ON m.id = mr.member JOIN job j ON m.job = j.id "
				+ "JOIN prefecture p ON m.pref = p.id WHERE m.status != 3 AND m.status != 5 AND m.status=1 "
				+ "AND m.role='USER' AND m.join_date BETWEEN :date1 AND :date2", nativeQuery = true)
			List<Object[]> getMemberInfoByDateRange(@Param("date1") LocalDate date1, @Param("date2") LocalDate date2);
	@Query("SELECT m.memberno, m.name FROM Member m")
	     List<Object[]> getMemberNoAndName();

	// Member findByMemberid(String memberid);
	Optional<Member> findByMemberid(String memberid);
	
	Optional<Member> findByMemberidAndStatus(String memberid, int status);
	
	Optional<Member> findByName(String username);

	@Query("SELECT m FROM Member m WHERE m.memberid = :memberid AND m.password = :password AND m.status = 1")
    Member findByMemberidAndPasswordAndStatus(@Param("memberid") String memberid, @Param("password") String password);
	Member findByMemberidAndPassword(String memberid, String password);

	boolean existsByMemberid(String memberid);
	
	 @Query("SELECT m FROM Member m WHERE m.email_pc = :email OR m.email_mob = :email")
	    List<Member> findByEmailPcOrEmailMob(@Param("email") String email);
	 @Query("SELECT m FROM Member m  where m.role='ADMIN'")
		Member getAdminMember();
		
	 @Query("SELECT m FROM Member m WHERE m.memberid = :memberId AND m.status = 1")
		Member findByMemberNo(@Param("memberId") String memberId);
}
