package com.mentalhealthapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mentalhealthapi.model.ChecksheetTemplate;
import com.mentalhealthapi.model.ChecksheetType;
import com.mentalhealthapi.model.DiagnoseCheck;
import com.mentalhealthapi.model.RespPerson;



public interface CheckSheetTemplateRepository extends JpaRepository<ChecksheetTemplate,Integer> {
	
	 List<ChecksheetTemplate> findByChecksheetTypeAndTemplateTypeAndIsDeleted(int checksheetType, String templateType, int isDeleted);
	
	    @Query("SELECT c FROM ChecksheetTemplate c WHERE c.checksheetType.id = :checksheetType AND c.templateType = :pcSpType AND c.isDeleted = 0")
	    List<ChecksheetTemplate> findTemplatesByType(@Param("checksheetType") int checksheetType, @Param("pcSpType") String pcSpType);
	
}