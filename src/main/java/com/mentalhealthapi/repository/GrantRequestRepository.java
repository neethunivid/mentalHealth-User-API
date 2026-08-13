package com.mentalhealthapi.repository;


import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.query.Param;


import com.mentalhealthapi.model.GrantRequest;



public interface GrantRequestRepository extends JpaRepository<GrantRequest, Long> {
	 @Query(value = "SELECT a.id, a.location1, a.prefectures1, a.municipality1, a.address1, a.building1, a.institution, a.dept, a.fullname, a.furigana, a.dob, a.age, a.home, a.prefectures2, a.municipality2, a.address2, a.building2, a.phone, a.email2, a.representative, a.rep_location, a.rep_prefectures, a.rep_municipality, a.rep_address, a.rep_building, a.rep_phone, a.rep_email, a.in_charge, a.recomd_pern_inst, a.recomd_pern_job, a.recomd_pern_fullname, a.recomd_pern_location,\r\n"
	            + " a.recomd_pern_prefectures4, a.recomd_pern_municipality, a.recomd_pern_address, a.recomd_pern_building, a.recomd_pern_phone, a.file1, a.biography, a.application, a.classification, a.new_cont, a.research_name, a.exp_equipment_costs, a.exp_consumables_costs, a.exp_travel_expenses, a.exp_reward_money, a.exp_other, a.exp_total, a.sub_equipment_costs, a.sub_consumables_costs, a.sub_travel_expenses, a.sub_reward_money, a.\r\n"
	            + " sub_other, a.sub_total, a.file2, a.Date_Created, a.country1, a.country2, a.country3, a.country4, b.details, b.purpose, b.target, b.contents, b.expected_results, b.approval, b.research_subjects, b.keywords, b.originality, b.achievements, b.org_principal, b.affiliation, b.age0, b.role0, b.coordinator1, b.affiliation1, b.age1, b.role1, b.coordinator2, b.affiliation2, b.age2, b.role2, b.coordinator3, b.affiliation3, b.age3, b.role3,\r\n"
	            + " b.coordinator4, b.affiliation4, b.age4, b.role4, b.coordinator5, b.affiliation5, b.age5, b.role5, b.research_contents, a.remark " +
	            "FROM grant_request a " + "LEFT JOIN grant_request2 b ON a.id = b.grant_id " +
	            "WHERE a.id IN (:grantIds)", nativeQuery = true)
	    List<Object[]> findCombinedDataByGrantIds(@Param("grantIds") List<Long> grantIds);
	    @Modifying
	    @Query("DELETE FROM GrantRequest WHERE id IN :grantIds")
	    void deleteAllByIds(@Param("grantIds") List<Long> grantIds);
	    
	    List<GrantRequest> findByDateCreatedGreaterThanEqual(LocalDate startOfYear);
	    List<GrantRequest> findByDateCreatedBetween(LocalDate startDate, LocalDate endDate);
	    List<GrantRequest> findByDateCreatedLessThanEqual(LocalDate startDate);
	}


