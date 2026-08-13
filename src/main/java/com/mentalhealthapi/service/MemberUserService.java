package com.mentalhealthapi.service;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.mentalhealthapi.dto.DiagnoseDataDTO;
import com.mentalhealthapi.dto.MemberDTO;
import com.mentalhealthapi.dto.MemberDeleteDTO;
import com.mentalhealthapi.model.Member;
import com.mentalhealthapi.model.MemberDeleted;
import com.mentalhealthapi.repository.DiagnoseTalkTypeRepository;
import com.mentalhealthapi.repository.MemberDeletedRepository;
import com.mentalhealthapi.repository.MemberRepository;

@Service
public class MemberUserService {

	private final MemberDeletedRepository memberDeletedRepository;
	private final MemberRepository memberRepository;
	
	MemberUserService(MemberRepository memberRepository,MemberDeletedRepository memberDeletedRepository){
		this.memberDeletedRepository=memberDeletedRepository;
		this.memberRepository=memberRepository;
	}
	
	public String deleteMember(MemberDeleteDTO request){
		
		Member m=memberRepository.findByMemberidAndPassword(request.getMemberId(), request.getPassword());
		
		if(m!=null)
		{
			if(m.getStatus()==3 || m.getStatus()==5)
			{
				return "もうすでに退会しています。";
			}else {
			//saving to member delete table
			MemberDeleted md=new MemberDeleted();
			md.setMember(m.getId());
			md.setMemberid(request.getMemberId());
			md.setMemberno(m.getMemberno());
			md.setSex(m.getSex());
			md.setAddress1(m.getAddress1());
			md.setPassword(m.getPassword());
			md.setName(m.getName());
			md.setNamek(m.getNamek());
			md.setAddress2(m.getAddress2());
			md.setDob(m.getDob());
			md.setReasonLeaving(request.getReasonLeaving());
			md.setStatus(1);
			md.setRemoveStatus(2);
			md.setStatus2(1);
			md.setDate(LocalDate.now());
			md.setAddress3(m.getAddress3());
			md.setEmailPc(m.getEmail_pc());
			md.setDateEffect(LocalDateTime.now());
			md.setTel(m.getTel());
			md.setWorkplace(m.getWorkplace());
			md.setEmailMob(m.getEmail_mob());
			md.setHowfound(m.getHowfound());
			md.setMob(m.getMob());
			md.setMailmagazine(m.getMailmagazine());
			md.setChangeDate(LocalDate.now());
			md.setRemarks_initial(m.getRemarks_initial());
			md.setChangeCause(m.getChangeCause());
			md.setJoinDate(m.getJoinDate());
			md.setEmailSmart(m.getEmail_smart());
			memberDeletedRepository.save(md);
			
			//Updating member table with status 3
			m.setStatus(3);
			m.setChangeDate(LocalDate.now());
			memberRepository.save(m); 
			}
		}
		else
		{
			return "非会員 !";
		}
		return "Deleted";
	}
	
}
