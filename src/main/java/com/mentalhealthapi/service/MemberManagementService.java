package com.mentalhealthapi.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.Date;
import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.mentalhealthapi.dto.MemberRemarkDTO;
import com.mentalhealthapi.dto.MemberResponse;
import com.mentalhealthapi.dto.RemarkSearchDTO;
import com.mentalhealthapi.dto.RemarkSearchResponse;
import com.mentalhealthapi.dto.TreeDTO;
import com.mentalhealthapi.exceptions.ResourceNotFoundException;
import com.mentalhealthapi.helper.ApiListResponse;
import com.mentalhealthapi.model.Config;
import com.mentalhealthapi.model.NgWords;
import com.mentalhealthapi.model.Remarks;
import com.mentalhealthapi.repository.ConfigRepository;
import com.mentalhealthapi.repository.NgWordsRepository;
import com.mentalhealthapi.repository.RemarksRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;


@Service
public class MemberManagementService {
	@Autowired
	
	@PersistenceContext
	private EntityManager entityManager;	
	private final RemarksRepository remarksRepository;	
	private final NgWordsRepository ngWordsRepository;
	private final ConfigRepository configRepository;
	
	public MemberManagementService(
			RemarksRepository remarksRepository, NgWordsRepository ngWordsRepository, ConfigRepository configRepository) {
		
		this.remarksRepository = remarksRepository;		
		this.ngWordsRepository = ngWordsRepository;
		this.configRepository = configRepository;
	}
	
	public Map<String, Integer> getPendingReviewCount() {
        Map<String, Integer> fieldCounts = new HashMap<>();

        fieldCounts.put("normal", remarksRepository.countNormalByStatus());
        fieldCounts.put("anxiety", remarksRepository.countAnxietyByStatus());
        fieldCounts.put("blackmail", remarksRepository.countBlackmailByStatus());
        fieldCounts.put("other", remarksRepository.countOtherByStatus());
        fieldCounts.put("growingup", remarksRepository.countGrowingupByStatus());
        fieldCounts.put("addition1", remarksRepository.countAddition1ByStatus());
        fieldCounts.put("addition2", remarksRepository.countAddition2ByStatus());

        return fieldCounts;
    }
	
	public List<MemberResponse> viewAllRemarks(MemberRemarkDTO request) {
	    String type = request.getType();
	   
	    List<Object[]> memberList = remarksRepository.findTypeRemarks(type);
	   
	    List<MemberResponse> responseList = new ArrayList<>();

	    for (Object[] memberData : memberList) {
	        MemberResponse memberResponse = new MemberResponse();
	        memberResponse.setMember(createMemberMap(memberData));
	        Integer remarkId =(Integer) memberData[6];	
	        if (remarkId>0) {
	        List<Object[]> remarksList = remarksRepository.findRemarkHierarchyByTypeAndRemark(type, remarkId);		       
	        memberResponse.setRemarks(createRemarkMap(remarksList));
	        }else {
	        	 memberResponse.setRemarks(null);
	        }
	       
	        responseList.add(memberResponse);
	    }

	    return responseList;
	}
	
	 private Map<String, Object> createMemberMap(Object[] remarkData) {
		    Map<String, Object> memberMap = new HashMap<>();
		    memberMap.put("rid", remarkData[0]);
		    memberMap.put("remark_no", remarkData[1]);
		    memberMap.put("name", remarkData[2]);
		    memberMap.put("email_pc", remarkData[3]);
		    memberMap.put("title", remarkData[4]);
		    memberMap.put("text", remarkData[5]);
		    memberMap.put("remark", remarkData[6]);
		    memberMap.put("date", remarkData[7]);
		    memberMap.put("memberId", remarkData[8]);
		    return memberMap;
		}
	 private List<Map<String, Object>> createRemarkMap(List<Object[]> remarksList) {
		    List<Map<String, Object>> remarkMaps = new ArrayList<>();

		    for (Object[] remarkData : remarksList) {
		        Map<String, Object> remarkMap = new HashMap<>();
		        remarkMap.put("rid", remarkData[0]);
		        remarkMap.put("remark_no", remarkData[1]);
		        remarkMap.put("parent_id", remarkData[4]);
		        remarkMap.put("title", remarkData[2]);
		        remarkMap.put("text", remarkData[3]);
		        remarkMap.put("remark", remarkData[4]);
		        remarkMap.put("date", remarkData[5]);
		        remarkMaps.add(remarkMap);
		    }

		    return remarkMaps;
		}


	 

 
	 public void add(String type, int id, String text, String title) {
	        Remarks newRemark = new Remarks();
	        newRemark.setText(text);
	        newRemark.setRemarks(id);
	        newRemark.setStatus(1);
	        newRemark.setDate(LocalDateTime.now());
	        newRemark.setTitle(title);
	        newRemark.setOrgCreatedDate(LocalDateTime.now());
	        newRemark.setMember(0); // Unknown member
	       // Save the new remark in the database
	        remarksRepository.save(newRemark);

	        // Generate and save the remark number
	        newRemark.setRemarkNo(generateRMKNo(newRemark.getId()));
	        remarksRepository.save(newRemark);

	        // Send an email notification
	       // mailService.sendMailToVarsha(title, newRemark.getId());
	       
	    }
	 
	 private String generateRMKNo(Integer id) {
			return "RMK_" + id;
		}

	 
	 public ApiListResponse<List<RemarkSearchResponse>> searchRemarks(RemarkSearchDTO request) {
	        String remarkId = request.getRemarkid();
	        String memberId = request.getMemberid();
	        String title = request.getTitle();
	        Date fromDate = request.getFromDate();
	        Date toDate = request.getToDate();

	        try {
	        	List<Object[]> searchResults;
	        	if (remarkId==null && memberId==null && title==null && fromDate==null) {
	        		searchResults = remarksRepository.getAllRemarks();
	        	}
	        	else {	        		
	        		searchResults = remarksRepository.searchRemarks(remarkId, memberId, title, fromDate, toDate);
	        	}
	            List<RemarkSearchResponse> responseList = new ArrayList<>();

	            for (Object[] result : searchResults) {
	            	Integer id = (Integer) result[0];
	            	String remark_no=(String) result[1];
	            	Integer member = (Integer) result[2];
	            	Byte normal = (Byte) result[3];
	            	Byte anxiety = (Byte) result[4];
	            	Byte blackmail = (Byte) result[5];
	            	Byte other = (Byte) result[6];
	            	Byte growingup = (Byte) result[7];
	            	String addition1 =(String)result[8];
	            	String addition2 = (String) result[9];
	            	 title = (String) result[10];
	            	String text = (String) result[11];
	            	Integer remarks = (Integer) result[12];
	            	Timestamp date =(Timestamp) result[13];	            	
	            	String name = (String) result[16];
	            	String email_pc = (String) result[17];  
	            	String memberid = (String) result[18];
	            	
	            	// Convert the Timestamp to LocalDateTime
	            	LocalDateTime localDateTime=null;
	            	if(date!=null) {
	                 localDateTime = date.toLocalDateTime();
	               // LocalDateTime localDateTime2 = orgCreatedDate.toLocalDateTime();
	            	}

	                RemarkSearchResponse dto = new RemarkSearchResponse();
	                // Set values from Remarks and Member objects
	                dto.setId(id);
	                dto.setRemarkNo(remark_no);
	                dto.setMember(member);
	                dto.setNormal(normal);
	                dto.setAnxiety(anxiety);
	                dto.setBlackmail(blackmail);
	                dto.setOther(other);
	                dto.setGrowingup(growingup);
	                dto.setAddition1(addition1);
	                dto.setAddition2(addition2);
	                dto.setTitle(title);
	                dto.setText(text);
	                dto.setRemarks(remarks);
	                dto.setDate(localDateTime);
	                dto.setMemberid(memberid);	              
	                dto.setName(name);
	                dto.setEmailPc(email_pc);
	                responseList.add(dto);
	            }

	            Integer totalCount = remarksRepository.getTotalRemarkCount();
	            Integer searchCount = responseList.size();
	        	

	            return new ApiListResponse<>(searchCount, "", responseList, totalCount);
	        } catch (Exception e) {
	            return new ApiListResponse<>(0, e.getMessage(), null, 0);
	        }
	    }
	 
	 
	 
	
	 public Remarks updateRemarks(Integer id, Remarks remarks) {
		    Remarks existingRemarks = remarksRepository.findById(id)
		            .orElseThrow(() -> new ResourceNotFoundException("Remarks not found with id: " + id));

		    // Update the existing remarks object with new values from the request
		    if (remarks.getRemarkNo() != null) {
		        existingRemarks.setRemarkNo(remarks.getRemarkNo());
		    }
		    if (remarks.getMember() != null) {
		        existingRemarks.setMember(remarks.getMember());
		    }
		    if (remarks.getNormal() != 0) {
		        existingRemarks.setNormal(remarks.getNormal());
		    }
		    if (remarks.getAnxiety() != 0) {
		        existingRemarks.setAnxiety(remarks.getAnxiety());
		    }
		    if (remarks.getBlackmail() !=0) {
		        existingRemarks.setBlackmail(remarks.getBlackmail());
		    }
		    if (remarks.getOther() != 0) {
		        existingRemarks.setOther(remarks.getOther());
		    }
		    if (remarks.getGrowingup() != 0) {
		        existingRemarks.setGrowingup(remarks.getGrowingup());
		    }
		    if (remarks.getAddition1() != null) {
		        existingRemarks.setAddition1(remarks.getAddition1());
		    }
		    if (remarks.getAddition2() != null) {
		        existingRemarks.setAddition2(remarks.getAddition2());
		    }
		    if (remarks.getTitle() != null) {
		        existingRemarks.setTitle(remarks.getTitle());
		    }
		    if (remarks.getText() != null) {
		        existingRemarks.setText(remarks.getText());
		    }
		    if (remarks.getRemarks() != null) {
		        existingRemarks.setRemarks(remarks.getRemarks());
		    }
		    if (remarks.getDate() != null) {
		        existingRemarks.setDate(remarks.getDate());
		    }
		    if (remarks.getStatus() != null) {
		        existingRemarks.setStatus(remarks.getStatus());
		    }
		    if (remarks.getOrgCreatedDate() != null) {
		        existingRemarks.setOrgCreatedDate(remarks.getOrgCreatedDate());
		    }

		    return remarksRepository.save(existingRemarks);
		}


	 public Remarks updateRemarksView(Integer id, Remarks remarks) {
			// Check if both text and title are empty
			if ((remarks.getText() == null || remarks.getText().isEmpty()) && 
				(remarks.getTitle() == null || remarks.getTitle().isEmpty())) {
				
				// Find the existing Remarks entry
				Remarks existingRemarks = remarksRepository.findById(id)
						.orElseThrow(() -> new ResourceNotFoundException("Remarks not found with id: " + id));

				// Update the status to 1 (approve the comment)
				existingRemarks.setStatus(1);
				
				// Save the updated status and return the existing Remarks
				remarksRepository.save(existingRemarks);
				return existingRemarks;
			}
		    Remarks existingRemarks = remarksRepository.findById(id)
		            .orElseThrow(() -> new ResourceNotFoundException("Remarks not found with id: " + id));
		    existingRemarks.setStatus(1);
		    remarksRepository.save(existingRemarks);
		    Remarks rmk = new Remarks();
		    rmk.setRemarkNo(existingRemarks.getRemarkNo());
		    rmk.setMember(existingRemarks.getMember());
		    rmk.setNormal(existingRemarks.getNormal());
		    rmk.setAnxiety(existingRemarks.getAnxiety());
		    rmk.setBlackmail(existingRemarks.getBlackmail());
		    rmk.setOther(existingRemarks.getOther());
		    rmk.setGrowingup(existingRemarks.getGrowingup());
		    rmk.setAddition1(existingRemarks.getAddition1());
		    rmk.setAddition2(existingRemarks.getAddition2());
		   // rmk.setTitle(remarks.getTitle());
		    rmk.setText(remarks.getText());
		    rmk.setStatus(1);
		    rmk.setRemarks(id);
		    if (remarks.getTitle() != null) {
		        rmk.setTitle(remarks.getTitle());
		    }else {
		    rmk.setTitle(existingRemarks.getTitle());
		    }
		    if (remarks.getDate() != null) {
		        rmk.setDate(remarks.getDate());
		    }else {
		    rmk.setDate(LocalDateTime.now());
		    }
		    if (remarks.getOrgCreatedDate() != null) {
		        rmk.setOrgCreatedDate(remarks.getOrgCreatedDate());
		    }else {
		    	rmk.setOrgCreatedDate(LocalDateTime.now());
		    }
		   Remarks remk = remarksRepository.save(rmk);
		   remk.setRemarkNo(generateRMKNo(remk.getId()));
		   return remarksRepository.save(remk);
		}

	public String deleteRemarks(Integer id) {
		try {
            Remarks existingRemark = remarksRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Remark not found with id: " + id));

            remarksRepository.delete(existingRemark);
            return "Remark deleted successfully";
        } catch (ResourceNotFoundException e) {
            return "Remark id not found";
        } catch (Exception e) {
            return "Failed to delete remark";
        }
    }

	public NgWords addNgwords(NgWords ngwords) {
		
		return ngWordsRepository.save(ngwords);
	}

	public String deleteNgwords(Integer id) {
		try {
            NgWords existingNgwords = ngWordsRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("NgWords not found with id: " + id));

            ngWordsRepository.delete(existingNgwords);
            return "NgWord deleted successfully";
        } catch (ResourceNotFoundException e) {
            return "NgWord id not found";
        } catch (Exception e) {
            return "Failed to delete Ngword";
        }
    }

	public List<NgWords> listNgWords() {
		 return ngWordsRepository.findAll();
	}
	
	public List<Object[]> getAllRemarks(String type) {
		
		 List<Object[]> searchResults = remarksRepository.findTypeRemarks(type);
		return searchResults;
	}

	
	
	public String updateAutoApproval(Config config) {
	    String key = config.getKey();
	    Integer id=config.getId();
	    String value =config.getValue();
	    Config existingConfig = configRepository.findById(id)
	            .orElseThrow(() -> new IllegalArgumentException("Configuration not found with id: " + id));
	    // Save the updated config to the database
	     configRepository.updateConfigValue(id,value);
	    
	     return "Updated";
	}

	public List<Config> listConfig(Config config) {
	    String key = config.getKey();
	    if (key != null) {
	        // If the key is provided, return the specific config
	        return configRepository.findByKey(key)
	                               .map(Collections::singletonList)
	                               .orElse(Collections.emptyList());
	    } else {
	        // If the key is not provided, return the list of all configs
	        return configRepository.findAll();
	    }
	}

	public List<TreeDTO> treeView(MemberRemarkDTO request) {
	    String type = request.getType();
	    List<Object[]> result = remarksRepository.findRemarkHierarchyTreeByType(type);

	    List<TreeDTO> treeDTOList = result.stream()
	        .map(row -> new TreeDTO(
	            (int) row[0],      // id
	            (String) row[1],   // remarkNo
	            (int) row[2],      // ParentId
	            (String) row[3],   // title
	            (String) row[4],   // text
	            (int) row[5],      // remarks
	            row[6] != null ? ((Timestamp) row[6]).toLocalDateTime().toLocalDate() : null // date
	        ))
	        .collect(Collectors.toList());

	    return treeDTOList;
	}

	

	
	

	
	
	
	
	
	
	}