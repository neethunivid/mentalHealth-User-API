package com.mentalhealthapi.repository;

import com.mentalhealthapi.model.TelMainIssue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TelMainIssueRepository extends JpaRepository<TelMainIssue,Integer> {
}
