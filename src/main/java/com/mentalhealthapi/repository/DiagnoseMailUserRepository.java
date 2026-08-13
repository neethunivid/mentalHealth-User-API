package com.mentalhealthapi.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.mentalhealthapi.dto.DiagnosisMailingResponse;
import com.mentalhealthapi.model.DiagnoseMailMagazine;
import com.mentalhealthapi.model.DiagnoseMailUsers;



public interface DiagnoseMailUserRepository extends JpaRepository<DiagnoseMailUsers,Integer> {

	 List<DiagnoseMailUsers> findByDiagnoseMailMagazine(DiagnoseMailMagazine magazine);

	@Modifying
	@Query("DELETE FROM DiagnoseMailUsers u WHERE u.diagnoseMailMagazine = :magazine")
	void deleteByDiagnoseMailMagazine(DiagnoseMailMagazine magazine);

	 @Modifying
	 @Query("UPDATE DiagnoseMailUsers u SET u.sendStatus = :sendStatus WHERE u.diagnoseMailMagazine.id = :magazineId")
	 void updateSendStatusByMagazineId(int sendStatus, int magazineId);

}
