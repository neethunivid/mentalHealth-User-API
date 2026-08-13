package com.mentalhealthapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mentalhealthapi.model.ChecksheetTemplate;
import com.mentalhealthapi.model.ChecksheetType;
import com.mentalhealthapi.model.DiagnoseCheck;
import com.mentalhealthapi.model.RespPerson;



public interface CheckSheetTypeRepository extends JpaRepository<ChecksheetType,Integer> {

}