package com.mentalhealthapi.repository;

import com.mentalhealthapi.model.RouteToCounselling;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RouteToCounsellingRepository extends JpaRepository<RouteToCounselling,Integer> {
}
