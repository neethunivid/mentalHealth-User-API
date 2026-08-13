package com.mentalhealthapi.service;
import org.springframework.data.domain.Sort;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Safelist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import com.mentalhealthapi.dto.MemberRemarkDTO;
import com.mentalhealthapi.dto.RemarkDTO;
import com.mentalhealthapi.dto.RemarkResponseDTO;
import com.mentalhealthapi.dto.RemarkWithMemberDetailsDTO;
import com.mentalhealthapi.dto.TreeDTO;
import com.mentalhealthapi.dto.TreeListDTO;
import com.mentalhealthapi.model.Config;
import com.mentalhealthapi.model.Member;
import com.mentalhealthapi.model.Remarks;
import com.mentalhealthapi.repository.ConfigRepository;
import com.mentalhealthapi.repository.MemberRepository;
import com.mentalhealthapi.repository.NgWordsRepository;
import com.mentalhealthapi.repository.RemarksRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class TreeService {
	 private final MemberRepository memberRepository;
	 private final ConfigRepository configRepository;
	 private final RemarksRepository remarkRepository;
	 private final NgWordsRepository ngWordRepository;
	 
	 @Autowired
	 public TreeService(MemberRepository memberRepository,ConfigRepository configRepository,
			 RemarksRepository remarkRepository,NgWordsRepository ngWordRepository) {
		 this.memberRepository = memberRepository;
		 this.configRepository = configRepository;
		 this.remarkRepository = remarkRepository;
		 this.ngWordRepository = ngWordRepository;
	 }
	 
	 public Remarks saveReply(RemarkDTO request) {
		 List<Integer> ar = null; 
		    String title = generateTitle(request.getRemarkId(),ar);
		    Remarks rmks = remarkRepository.findById(request.getRemarkId()).orElse(null);
		    int type= findNodType(request.getRemarkId());
		    int cnt = replyCount(request.getRemarkId())+1;
		   
		    String rmk_title = "re:" + cnt + " " + rmks.getTitle();
		    String cleanedText = request.getText();
	        String cleanedRmkTitle ="";
		    String rmkTitle = (request.getTitle() != null && !request.getTitle().isEmpty()) ? request.getTitle() : request.getRemarkTitle();

		    if (remarksAutoApproval() && !isBlockedMember(request.getMemberId())) {
		         cleanedText = xssClean(request.getText());
		         cleanedRmkTitle = xssClean(rmkTitle);
		    } else {
		        // No cleaning needed if auto-approval is off or member is blocked
		         cleanedText = request.getText();
		         cleanedRmkTitle = rmkTitle;
		    }

		    boolean isApproved = false;
		    int approvedStatus=0;

		    // Check if the member is blocked or not
		    if (!isBlockedMember(request.getMemberId())) {
		        // Check if auto-approval is turned ON
		        if (remarksAutoApproval()) {
		            // Auto-approving status
		            isApproved = isNGWordsChecking(cleanedText) && isNGWordsChecking(cleanedRmkTitle);
		            if(isApproved) {approvedStatus=1;}
		        }
		    }

		    // Initialize and save the new remark
		    Remarks newRemark = new Remarks();
		    newRemark.setMember(request.getMemberId());
		    newRemark.setTitle(cleanedRmkTitle);
		    newRemark.setText(cleanedText);
		    newRemark.setDate(LocalDateTime.now());
		    newRemark.setOrgCreatedDate(LocalDateTime.now());
		    newRemark.setRemarks(request.getParentRemark());
		    newRemark.setStatus(approvedStatus);
		    newRemark.setRemarkNo("RMK");

		    // Set the appropriate type flags based on 'type'
		    switch (request.getType()) {
		        case 1:
		        	newRemark.setNormal((byte) 1);
		            break;
		        case 2:
		            newRemark.setAnxiety((byte) 1);
		            break;
		        case 3:
		            newRemark.setBlackmail((byte) 1);
		            break;
		        case 4:
		            newRemark.setOther((byte) 1);
		            break;		       
		        case 5:
		            newRemark.setGrowingup((byte) 1);
		            break;
		    }

		    remarkRepository.save(newRemark);

		    // Generate and set remark number
		    String remarkNo = generateRMKNo(newRemark.getId());
		    newRemark.setRemarkNo(remarkNo);
		    remarkRepository.save(newRemark);		  
		    return newRemark;
		}

	
	 
	 public int replyCount(int remarkId) {
	        
	        int status = 1;	        
	        // Use the repository method to count replies for the given remark ID
	        int count = remarkRepository.countByRemarksAndStatus(remarkId, status);
	        // Convert the long count to int and return it
	        return count;
	    }

		 public int findNodType(int nodId) {
		        Remarks remark = remarkRepository.findById(nodId).orElse(null);

		        if (remark != null) {
		            if (remark.getNormal() == 1) {
		                return 1;
		            } else if (remark.getAnxiety() == 1) {
		                return 2;
		            } else if (remark.getBlackmail() == 1) {
		                return 3;
		            } else if (remark.getOther() == 1) {
		                return 4;
		            } else if (remark.getGrowingup() == 1) {
		                return 5;
		            }
		        }

		        // Return a default value or handle the case when no type is found
		        return 0; 
		    }
//
//		private String generateTitle(int remarkId) {
//		// TODO Auto-generated method stub
//		return null;
//	}

		private String generateTitle(int id, List<Integer> ar) {
			if (ar == null) {
		        ar = new ArrayList<>();
		    }
		        Optional<Remarks> optionalRemarks = remarkRepository.findById(id);
		        if (optionalRemarks.isPresent()) {
		            Remarks remarks = optionalRemarks.get();
		            ar.add(getPosition(remarks.getId(), remarks.getRemarks())); 

		            if (remarks.getRemarks() != null && remarks.getRemarks() > 0) {
		                return generateTitle(remarks.getRemarks(), ar);
		            } else {
		                Collections.reverse(ar);
		                int cnt = ar.size();
		                ar.set(cnt - 1, ar.get(cnt - 1) + 1);
		                String title = ar.stream().map(Object::toString).collect(Collectors.joining("-"));
		                return "Re" + title + " " + remarks.getTitle();
		            }
		        } else {
		            return ""; 
		        }
		    }
		 
		 public int getPosition(int id, int parentId) {		       

		        List<Integer> remarkIds = remarkRepository.findIdsByRemarksOrderByid(parentId);
		        int position = 1;

		        for (Integer remarkId : remarkIds) {
		            if (remarkId.equals(id)) {
		                break;
		            }
		            position++;
		        }

		        return position;
		    }

	public String xssClean(String input) {
		    // Remove invisible characters
		    input = input.trim();

		    // URL decode
		    input = URLDecoder.decode(input, StandardCharsets.UTF_8);

		    // Use Jsoup to sanitize HTML
		    Safelist safelist = Safelist.relaxed(); 
		    Document document = Jsoup.parseBodyFragment(input);
		    document.outputSettings().prettyPrint(false); // Prevent pretty-printing
		    String cleanHtml = Jsoup.clean(document.body().html(), safelist);

		    return cleanHtml;
		}

	

		private boolean isNGWordsChecking(String input) {
			 input = input.trim();
		        if (input.isEmpty()) {
		            return true;
		        }

		        String[] keywords = input.split(" ");
		        for (String keyword : keywords) {
		            if (!keyword.isEmpty()) {
		                long ngWordCount = ngWordRepository.countByTextContaining(keyword);
		                if (ngWordCount > 0) {
		                    return false; // NG word found, not approved
		                }
		            }
		        }

		        return true; // No NG words found, approved
		}

		

		private String generateRMKNo(long remarkId) {
		    return "RMK" + remarkId;
		    
		}

		
 
	 public boolean isBlockedMember(int memberId) {
	        // Assuming your Member entity has a property named "ngMember"
	        Member member = memberRepository.findById(memberId).orElse(null);

	        if (member != null && member.getNgMember() ==1) {
	            return true;
	        } else {
	            return false;
	        }
	    }
	 
	 public boolean remarksAutoApproval() {		   
		    Optional<Config> configOptional = configRepository.findByKey("AUTO_APPROVAL");

		    if (configOptional.isPresent()) {
		        Config config = configOptional.get();		        
		        return Boolean.parseBoolean(config.getValue());
		    } else {
		        return false; 
		    }
		}

	 public List<TreeDTO> treeView(MemberRemarkDTO request) {
		    String type = request.getType();
		    List<Object[]> result = remarkRepository.findRemarkHierarchyTreeByType(type);

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
	 
	 
	 public List<TreeListDTO> treeList(MemberRemarkDTO request, int page, int size) {
		    Logger logger = LoggerFactory.getLogger(this.getClass());
		    String type = request.getType();
		    Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "id")); 			  
		    Page<Object[]> resultPage = remarkRepository.findRemarkHierarchyTreeListByType(type, pageable);
           
		    if (resultPage.hasContent()) {
		        return resultPage.getContent().stream()
		            .map(row -> {
		                int parentId = (int) row[0];
		                int childCount = remarkRepository.findChildCount(type,parentId);

		                return new TreeListDTO(
		                    (int) row[0],       // id
		                    (String) row[1],    // remarkNo
		                    (int) row[2],       // parentId
		                    (String) row[3],    // title
		                    (String) row[4],    // text
		                    (int) row[5],       // remarks
		                    row[6] != null ? ((Timestamp) row[6]).toLocalDateTime() : null,  // date
		                    (int) row[7],       // memberId
		                    (String) row[8],    // memberName
		                    childCount          // count
		                );
		            })
		            .collect(Collectors.toList());
		    } else {
		        logger.debug("No results found for type: {}", type);
		        return Collections.emptyList();
		    }
		}

	 
	 

	public Remarks addRemarks(RemarkDTO request) {
		 Remarks remarks = new Remarks();
		 Integer type=request.getType();
		 switch (type) {
         case 1:
             remarks.setNormal((byte) 1);
             break;
         case 2:
             remarks.setAnxiety((byte) 1);
             break;
         case 3:
             remarks.setBlackmail((byte) 1);
             break;
         case 4:
             remarks.setOther((byte) 1);
             break;
         case 5:
             remarks.setGrowingup((byte) 1);
             break;
         default:
             // Handle invalid type
             break;
     }
     remarks.setTitle(request.getTitle());
     remarks.setText(request.getText());
     remarks.setMember(request.getMemberId());
     remarks.setStatus(0);
     remarks.setDate(LocalDateTime.now());
     remarks.setRemarkNo("RMK");
     remarks.setRemarks(0);
     remarkRepository.save(remarks);
     remarks.setRemarkNo(generateRMKNo(remarks.getId()));
     return  remarkRepository.save(remarks);
 
		
	}	

	public Long getCountOfFilteredMembers(MemberRemarkDTO request) {
		Long result = remarkRepository.countRemarkHierarchyTreeByType(request.getType());		
		return result;
	}
	
	public List<RemarkResponseDTO> getAllChildRemarks(Integer parentId) {
        List<RemarkResponseDTO> childRemarks = new ArrayList<>();
        fetchChildRemarks(parentId, childRemarks);
        return childRemarks;
    }

    private void fetchChildRemarks(Integer parentId, List<RemarkResponseDTO> childRemarks) {
        List<Object[]> directChildRemarks = remarkRepository.findByRemarksAndStatus(parentId, "1");
        for (Object[] row : directChildRemarks) {
            RemarkResponseDTO remark = new RemarkResponseDTO(
                (Integer) row[0], // id
                (String) row[1],  // remarkNo
                (String) row[2],  // title
                (String) row[3],  // text
                (LocalDateTime) row[4], // date
                (Integer) row[5], // status
                (Integer) row[6], //member
                (Integer) row[7], //remarks
                (String) row[8],  // memberName
                (Integer) row[9]  // memberSex
            );
            childRemarks.add(remark);
            fetchChildRemarks(remark.getId(), childRemarks);
        }
    }
	 

}
