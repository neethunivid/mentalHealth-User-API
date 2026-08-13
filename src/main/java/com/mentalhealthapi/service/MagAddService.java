package com.mentalhealthapi.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mentalhealthapi.dto.MagaddUserDTO;
import com.mentalhealthapi.exceptions.ResourceNotFoundException;
import com.mentalhealthapi.model.MailmagazineUser;
import com.mentalhealthapi.model.Member;
import com.mentalhealthapi.repository.MailmagazineUserRepository;
import com.mentalhealthapi.repository.MemberRepository;

@Service
public class MagAddService {
	private final MailmagazineUserRepository mailmagazineUserRepository ;
	private final MemberRepository memberRepository;
	@Autowired
	public MagAddService(MailmagazineUserRepository mailmagazineUserRepository, MemberRepository memberRepository) {
		this.mailmagazineUserRepository = mailmagazineUserRepository;
		this.memberRepository =memberRepository;
	}

	public MailmagazineUser add(MagaddUserDTO request) {
		Integer memberId = 0; 
		if (request.getEmail() != null) {
			// Check if the email exists in the Member table get the memberId
			List<Member> existingMembers = memberRepository.findByEmailPcOrEmailMob(request.getEmail());
			if (!existingMembers.isEmpty()) {
				memberId = existingMembers.get(0).getId();
			}
		}
		MailmagazineUser mailMagazineUser = new MailmagazineUser();
        mailMagazineUser.setStatus(1);
        mailMagazineUser.setEmailMob(request.getEmailMob());
        mailMagazineUser.setEmail(request.getEmail());
        mailMagazineUser.setName(request.getName());
        mailMagazineUser.setDate(LocalDate.now());
        mailMagazineUser.setMember(memberId);
        return mailmagazineUserRepository.save(mailMagazineUser);
		
	}

	public String checkEmail(MagaddUserDTO request) {
		if (request.getEmail() != null) {
			MailmagazineUser emailResult = mailmagazineUserRepository.findByEmail(request.getEmail());
            if (emailResult != null) {
                return "Email exists in the database.";
            }
        }

        if (request.getEmailMob() != null) {
        	MailmagazineUser emailMobResult = mailmagazineUserRepository.findByEmailMob(request.getEmailMob());
            if (emailMobResult != null) {
                return "Email mobile exists in the database.";
            }
        }
		return null;
	}
	
	public Integer getMagId(MagaddUserDTO request) {
		if (request.getEmail() != null) {
			MailmagazineUser emailResult = mailmagazineUserRepository.findByEmail(request.getEmail());
            if (emailResult != null) {
                return emailResult.getId();
            }
        }

        if (request.getEmailMob() != null) {
        	MailmagazineUser emailMobResult = mailmagazineUserRepository.findByEmailMob(request.getEmailMob());
            if (emailMobResult != null) {
                return emailMobResult.getId();
            }
        }
		return 0;
	}

	public String delete(Integer id) {		
			try {
				MailmagazineUser existingMailUser = mailmagazineUserRepository.findById(id)
						.orElseThrow(() -> new ResourceNotFoundException("MailmagazineUser not found with id: " + id));
				Integer mem = existingMailUser.getMember();
				mailmagazineUserRepository.delete(existingMailUser);

				if (mem > 0) {
					Optional<Member> memberOptional = memberRepository.findById(mem);
					if (memberOptional.isPresent()) {
						Member member = memberOptional.get();
						member.setMailmagazine(2);
						memberRepository.save(member);
					}
				}

				return "MailmagazineUser deleted successfully";
			} catch (ResourceNotFoundException e) {
				return "MailmagazineUser not found with id: " + id;
			} catch (Exception e) {
				return "Failed to delete MailmagazineUser";
			}
		
		
	}

}
