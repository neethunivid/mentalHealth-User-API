package com.mentalhealthapi.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mentalhealthapi.model.MailmagazineUser;


public interface  MailmagazineUserRepository extends JpaRepository< MailmagazineUser,Integer> {
	MailmagazineUser findByName(String name);
	MailmagazineUser findByEmailOrEmailMob(String emlPc, String emlMob);
	MailmagazineUser findFirstByEmail(String email);
    MailmagazineUser findFirstByEmailMob(String emailMob);
	

	MailmagazineUser findFirstByEmailOrEmailMob(String emlPc, String emlMob);
	MailmagazineUser findByMember(Integer member);
	MailmagazineUser findByEmail(String email);
    MailmagazineUser findByEmailMob(String emailMob);
	
	@Query(value = "SELECT u.id, u.date, u.email, u.name,m.memberid " +
            "FROM mailmagazine_user u JOIN member m ON u.member=m.id " +            
            "WHERE u.status = '1' AND m.status = '1' AND u.member > 0 AND u.email <> '' " +
            "ORDER BY m.id DESC", nativeQuery = true)   
            List<Object[]> getAllBbsList();	
            
	  @Query(value = "SELECT u.id, u.date, u.email, u.name, m.memberid " +
	            "FROM mailmagazine_user u JOIN member m ON u.member = m.id " +
	            "WHERE u.status = '1' AND u.member > 0 AND u.email <> '' " +
	            "ORDER BY m.id DESC",
	            countQuery = "SELECT COUNT(u.id) " +
	            "FROM mailmagazine_user u JOIN member m ON u.member = m.id " +
	            "WHERE u.status = '1' AND u.member > 0 AND u.email <> ''", nativeQuery = true)
	    Page<Object[]> getAllBbsListWithCount(Pageable pageable);
	    
    @Query(value = "SELECT u.id, u.date, u.email_mob, u.name,m.memberid " +
           "FROM mailmagazine_user u JOIN member m ON u.member=m.id " +            
           "WHERE u.status = '1' AND m.status = '1' AND u.member > 0 AND u.email_mob <> '' " +
           "ORDER BY m.id DESC", nativeQuery = true)   
	List<Object[]> getAllBbsMobList();
	
	 @Query(value = "SELECT u.id, u.date, u.email, u.name,null " +
	           "FROM mailmagazine_user u " +            
	           "WHERE u.status = '1' AND u.member = 0 AND u.email <> '' " +
	           "ORDER BY id DESC", nativeQuery = true)   
	List<Object[]> getAllGeneralList();
	
	 @Query(value = "SELECT u.id, u.date, u.email_mob, u.name,null " +
	           "FROM mailmagazine_user u " +            
	           "WHERE u.status = '1' AND u.member = 0 AND u.email_mob <> '' " +
	           "ORDER BY id DESC", nativeQuery = true)   
	List<Object[]> getAllGeneralMobList();
	

	 @Query(value = "SELECT u.id, u.date, u.email_mob, u.name, m.memberid " +
	            "FROM mailmagazine_user u JOIN member m ON u.member = m.id " +
	            "WHERE u.status = '1' AND u.member > 0 AND u.email_mob <> '' " +
	            "ORDER BY m.id DESC",
	            countQuery = "SELECT COUNT(u.id) " +
	                    "FROM mailmagazine_user u JOIN member m ON u.member = m.id " +
	                    "WHERE u.status = '1' AND u.member > 0 AND u.email_mob <> ''", nativeQuery = true)
	 Page<Object[]> getAllBbsMobListWithCount(Pageable pageable);
	    
    @Query(value = "SELECT u.id, u.date, u.email, u.name, null " +
            "FROM mailmagazine_user u " +
            "WHERE u.status = '1' AND u.member = 0 AND u.email <> '' " +
            "ORDER BY u.id DESC",
            countQuery = "SELECT COUNT(u.id) " +
                    "FROM mailmagazine_user u " +
                    "WHERE u.status = '1' AND u.member = 0 AND u.email <> ''", nativeQuery = true)
    Page<Object[]> getAllGeneralListWithCount(Pageable pageable);
    
    
	   @Query(value = "SELECT u.id, u.date, u.email_mob, u.name, null " +
	            "FROM mailmagazine_user u " +
	            "WHERE u.status = '1' AND u.member = 0 AND u.email_mob <> '' " +
	            "ORDER BY u.id DESC",
	            countQuery = "SELECT COUNT(u.id) " +
	                    "FROM mailmagazine_user u " +
	                    "WHERE u.status = '1' AND u.member = 0 AND u.email_mob <> ''", nativeQuery = true)
	    Page<Object[]> getAllGeneralMobListWithCount(Pageable pageable);

	
}