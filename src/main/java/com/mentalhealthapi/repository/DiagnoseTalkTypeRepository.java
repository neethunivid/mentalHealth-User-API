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
import com.mentalhealthapi.model.DiagnoseTalkType;

public interface DiagnoseTalkTypeRepository extends JpaRepository<DiagnoseTalkType, Integer> {
}