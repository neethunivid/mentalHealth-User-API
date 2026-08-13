package com.mentalhealthapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.mentalhealthapi.model.MemberDeleted;

public interface  MemberDeletedRepository extends JpaRepository< MemberDeleted,Integer> {
	MemberDeleted findByName(String name);

	MemberDeleted findByMember(Integer member);
}