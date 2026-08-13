package com.mentalhealthapi.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.*;
import java.util.Optional;
import java.util.stream.Collectors;
import java.sql.Timestamp;
import java.time.LocalTime;
import java.util.Arrays;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.mentalhealthapi.dto.MemberDTO;
import com.mentalhealthapi.dto.MemberRequest;
import com.mentalhealthapi.dto.ReservationAddDTO;
import com.mentalhealthapi.dto.ReservationRequest;
import com.mentalhealthapi.dto.ReservationSlotResponse;
import com.mentalhealthapi.dto.RespPersonRequest;
import com.mentalhealthapi.helper.ApiResponse;
import com.mentalhealthapi.model.Member;
import com.mentalhealthapi.dto.CanceledReservtnResponse;
import com.mentalhealthapi.dto.CounselingListResponse;
import com.mentalhealthapi.dto.ReservationSlotListResponse;
import com.mentalhealthapi.dto.ReservationDTO;

import com.mentalhealthapi.model.Region;
import com.mentalhealthapi.model.ReservationDetails;
import com.mentalhealthapi.model.ReservationRegion;
import com.mentalhealthapi.model.ReservationSlot;
import com.mentalhealthapi.model.RespPerson;
import com.mentalhealthapi.repository.ConfigRepository;
import com.mentalhealthapi.repository.RegionRepository;
import com.mentalhealthapi.repository.ReservationCounsellingSlotRepo;
import com.mentalhealthapi.repository.ReservationDetailsRepository;
import com.mentalhealthapi.repository.ReservationSlotRepository;
import com.mentalhealthapi.repository.RespPersonRepository;

import jakarta.mail.MessagingException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Service
public class ReservationCounselingService {

	private final RegionRepository regionRepository;
	private final ReservationCounsellingSlotRepo reservationSlotRepository;
	private final RespPersonRepository respPersonRepository;
	private final ReservationDetailsRepository detailsRepository;
	private final EmailService emailService;
	@PersistenceContext
	private EntityManager entityManager;
	public ReservationCounselingService(RegionRepository regionRepository, RespPersonRepository respPersonRepository,
			ReservationCounsellingSlotRepo reservationSlotRepository, ReservationSlotRepository slotRepository,
			ReservationDetailsRepository detailsRepository,EmailService emailService) {
		this.regionRepository = regionRepository;
		this.reservationSlotRepository = reservationSlotRepository;
		this.respPersonRepository = respPersonRepository;
		this.detailsRepository = detailsRepository;
		this.emailService =emailService;
		
	
	}

	public List<ReservationRegion> getAllRegion() {
		try {
			List<ReservationRegion> regions = regionRepository.findAll();
			return regions;

		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	public List<RespPerson> getRespPerson(int region_id) {
		try {
			// List<RespPerson> regions=respPersonRepository.findAll();
			List<RespPerson> regions = respPersonRepository.findByRegionIdAndStatus(region_id,1);
			return regions;

		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	public ResponseEntity<ApiResponse> saveReservationSlot(ReservationRequest request) {
		ApiResponse response = new ApiResponse();
		try {
			if (this.checkReservationExists(request) == 0) {
				ReservationRegion rg = regionRepository.findById(request.getRegionId());
				Optional<RespPerson> rp=respPersonRepository.findById(request.getRespPerson());
				ReservationSlot reservationSlot = new ReservationSlot(request.getDate(), request.getTimeStart(),
						request.getTimeEnd(), rp.get(), request.getStatus(),
						request.getCreatedOn(), request.getCreatedBy(), request.getModifiedOn(),
						request.getModifiedBy(), request.getReservationStatus(), null, rg,rp.get().getName(),request.getAge(),request.getType_of_counselling());
				ReservationSlot reservatnSlot = reservationSlotRepository.save(reservationSlot);
				response.setCode(1);
				response.setError(null);
				response.setData(reservatnSlot);

			} else {
				response.setCode(1);
				response.setError(null);
				response.setData("Slot already exits");
			}

		} catch (Exception e) {
			response.setCode(1);
			response.setError(null);
			response.setData(e.getMessage());
		}
		
		return ResponseEntity.ok(response);
	}

	public int checkReservationExists(ReservationRequest request) {
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			String formattedDate = request.getDate().format(formatter);

			int count = reservationSlotRepository.countByDateAndTimeStartAndRegionId(request.getDate(),
					request.getTimeStart(), request.getRegionId());

			return count > 0 ? 1 : 0;

		} catch (Exception e) {
			System.out.println(e.getMessage());
			return 0;
		}
	}
	///Create resp person(counselor)
	public RespPerson createCounselor(RespPersonRequest request) {
		try {
			ReservationRegion rg = regionRepository.findById(request.getRegion());
			RespPerson rsp = new RespPerson();
			rsp.setRegion(rg);
			rsp.setName(request.getName());
			rsp.setCreatedOn(request.getCreatedOn());
			rsp.setCreatedBy(request.getCreatedBy());
			rsp.setStatus(request.getStatus());

			return respPersonRepository.save(rsp);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}
	//Get all counselor
	public List<RespPerson> getCouselorList() {
		return respPersonRepository.findActiveList();
	}

	public List<CounselingListResponse> getReservationSlots(Integer page, Integer rpp) {
		Integer start = (page - 1) * rpp;
		List<Object[]> slotData = reservationSlotRepository.findReservationSlots(start, rpp);
		List<CounselingListResponse> responseList = new ArrayList<>();
		for (Object[] slotRow : slotData) {
			CounselingListResponse response = new CounselingListResponse();

			Object nameObj = slotRow[9];
			if (nameObj instanceof Character) {
				response.setGender(String.valueOf(nameObj));
			} else if (nameObj instanceof String) {
				response.setGender((String) nameObj);
			}

			java.sql.Date sqlDate = (java.sql.Date) slotRow[0];
			response.setDate(sqlDate.toLocalDate());
			// response.setDate((LocalDate) slotRow[0]);
			response.setTimeStart((String) slotRow[1]);
			response.setTimeEnd((String) slotRow[2]);
			response.setName((String) slotRow[3]);
			response.setId((Integer) slotRow[4]);
			response.setReservationStatus((String) slotRow[5]);
			response.setReservationId((Integer) slotRow[6]);
			response.setPersonName((String) slotRow[7]);
			response.setSurname((String) slotRow[8]);
			// response.setGender((String)slotRow[9]);
			response.setJob((String) slotRow[10]);
			response.setPostalCode((String) slotRow[11]);
			response.setPref((String) slotRow[12]);
			response.setTown((String) slotRow[13]);
			response.setAddress((String) slotRow[14]);
			response.setTelephone((String) slotRow[15]);
			response.setEmail((String) slotRow[16]);
			response.setRegionId((Integer) slotRow[17]);
			response.setRegion((String) slotRow[18]);

			responseList.add(response);
		}

		return responseList;

	}



	public List<CanceledReservtnResponse> listTodaysCancellation(Integer page, Integer rpp) {
		Integer start = (page - 1) * rpp;
		List<Object[]> slotData = reservationSlotRepository.findCanceledReservations(start, rpp);
		 List<CanceledReservtnResponse> crp_list = new ArrayList<>();
		 
		   for (Object[] slot : slotData) {	  
			      
		        	  CanceledReservtnResponse crp = new CanceledReservtnResponse(
		        			    (java.sql.Date) slot[0], (String) slot[1], (String) slot[2],
		        			    (String) slot[3],(String) slot[4],(String) slot[5], (Integer) slot[6], (String) slot[7],
		        			    (Integer) slot[8], (String) slot[9], (String) slot[10],
		        			    (Character) slot[11], (String) slot[12], (String) slot[13],
		        			    (String) slot[14], (String) slot[15], (String) slot[16],
		        			    (String) slot[17], (String) slot[18], (String) slot[19]
		        			);

		            crp_list.add(crp);
		        }
		    //}
		return crp_list;
	}
	public Long countTodaysCancellation() {
		return reservationSlotRepository.countCanceledReservations();
	}
	public List<Object[]> listRsvConfirmation(Integer page, Integer rpp) {
		Integer start = (page - 1) * rpp;
		Pageable pageable = PageRequest.of(page - 1, rpp);
		Page<Object[]> slotData = reservationSlotRepository.findTemporarySlots(pageable);
		// List<CanceledReservtnResponse> crp_list = new ArrayList<>();
		 
		  
		return slotData.getContent();
	}
	public Long getCountOfTemporarySlot()
	{
		return reservationSlotRepository.countTemporarySlots();
	}
	public String deleteEmptySlot(Integer id) {
		ReservationSlot slot = reservationSlotRepository.findById(id).orElse(null);

		if (slot != null && slot.getReservationId() != null) {
			ReservationDetails details = detailsRepository
					.findByReservationId(slot.getReservationId().getReservationId());

			if (details != null && !checkPastDate(id)) {
				RespPerson respPerson =null;
				if(slot.getRespPerson()!=null)
				respPerson= respPersonRepository.findById(slot.getRespPerson().getId()).orElse(null);

				if (respPerson != null) {
					sendReservationUserEmail( details);
				}
			}
		}
		if (slot != null) {
			reservationSlotRepository.delete(slot);
		}
		return "Deleted";
	}

	public String deleteCounselor(Integer id) {
		RespPerson counselor = respPersonRepository.findById(id).orElse(null);
		if (counselor != null) {
			counselor.setStatus(0);
			respPersonRepository.save(counselor); // Save the updated counselor object
			return "Counselor with ID " + id + " has been marked as deleted.";
		} else {
			return "Counselor with ID " + id + " not found.";
		}
	}

	private void sendNotificationEmail(ReservationSlot slot, ReservationDetails details, RespPerson respPerson) {
		// Implement email sending logic using JavaMailSender or other email libraries
		// You can access slot.getDate(), slot.getTimeStart(), details.getEmail(),
		// respPerson.getName(), etc.
	}

//	public List<Object> searchReservationSlots(ReservationDTO request) {
//		   ReservationRegion region = null; // Initialize region as null
//		   Integer regionId = request.getRegionId();
//			Integer start = (request.getPageNumber() - 1) * request.getPageSize();
//		  // Pageable pageable = PageRequest.of(request.getPageNumber() - 1, request.getPageSize()); 
//		   System.out.println(regionId);
//	        if (regionId != null && regionId!=0) {
//	            region = new ReservationRegion();
//	            region.setId(request.getRegionId());
//	        }
//	        System.out.println(request.getStatus());
//	        System.out.println(request.getFromDate());
//	        System.out.println(request.getToDate());
//	        List<Object> result = reservationSlotRepository.searchReservationSlots(request.getStatus(),regionId,
//				request.getFromDate(),
//				request.getToDate());
//		return result;
//	}

	   public Map<String, Object> searchReservationSlots(ReservationDTO request) {
	        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
	        CriteriaQuery<Object> criteriaQuery = criteriaBuilder.createQuery(Object.class);
	        Root<ReservationSlot> root = criteriaQuery.from(ReservationSlot.class);
	       // Join<ReservationSlot, RespPerson> respPersonJoin = root.join("respPerson", JoinType.LEFT);

	        
	        Predicate predicate = buildPredicate(request, criteriaBuilder, root);
	        criteriaQuery.where(predicate);
	        criteriaQuery.orderBy(criteriaBuilder.desc(root.get("date")), criteriaBuilder.asc(root.get("timeStart")));

	        
	        List<Object> result = entityManager.createQuery(criteriaQuery)
	                .setFirstResult((request.getPageNumber() - 1) * request.getPageSize())
	                .setMaxResults(request.getPageSize())
	                .getResultList();
	        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
	        Root<ReservationSlot> countRoot = countQuery.from(ReservationSlot.class);
	        
	        Predicate countPredicate = buildPredicate(request, criteriaBuilder, countRoot);
	        countQuery.select(criteriaBuilder.count(countRoot));
	        countQuery.where(countPredicate);

	        Long count = entityManager.createQuery(countQuery).getSingleResult();

	        Map<String, Object> resultWithCount = new HashMap<>();
	        resultWithCount.put("result", result);
	        resultWithCount.put("count", count);

	        return resultWithCount;
	        
	        
	    }

	    private Predicate buildPredicate(ReservationDTO request, CriteriaBuilder criteriaBuilder, Root<ReservationSlot> root) {
	        List<Predicate> predicates = new ArrayList<>();

	        if (request.getStatus() != null) {
	            predicates.add(criteriaBuilder.equal(root.get("reservationStatus"), request.getStatus()));
	        }

	        if (request.getRegionId() != 0) {
	            Join<ReservationSlot, ReservationRegion> regionJoin = root.join("region");
	            predicates.add(criteriaBuilder.equal(regionJoin.get("id"), request.getRegionId()));
	        }

	        if (request.getFromDate() != null) {
	            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("date"), request.getFromDate()));
	        }

	        if (request.getToDate() != null) {
	            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("date"), request.getToDate()));
	        }

	        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
	    }
	    
	public boolean checkPastDate(Integer id) {
		ReservationSlot slot = reservationSlotRepository.findById(id).orElse(null);
		ReservationDetails details = detailsRepository.findByReservationId(slot.getReservationId().getReservationId());

		if (slot != null && details != null) {
			LocalDate reservationDate = slot.getDate();
			LocalDate createdDate = details.getCreatedOn().toLocalDate();

			return reservationDate.isBefore(createdDate);
		}

		return false;
	}
	public  Map<String, Object> getAllReservationList(ReservationDTO request) {
		PageRequest pageable = PageRequest.of(request.getPageNumber() - 1, request.getPageSize());
		Long count;
		 Map<String, Object> resultWithCount = new HashMap<>();
		if(!request.getAllFilter()) {
			 List<Object> all_Results;
			 
			if(request.getRegionId()==0) {
				all_Results=detailsRepository.findReservationSlotsByDate(request.getFromDate(),request.getToDate(),pageable);
				count=detailsRepository.countReservationSlotsByDate(request.getFromDate(),request.getToDate());
			}else if(request.getFromDate()==null)
			{
				 all_Results=detailsRepository.findReservationSlotsByRegion(request.getRegionId(),pageable);
				 count=detailsRepository.countReservationSlotsByRegion(request.getRegionId());
						
			}else {
				all_Results=detailsRepository.findReservationSlots(request.getRegionId(),request.getFromDate(),request.getToDate(),pageable);
				count=detailsRepository.countReservationSlots(request.getRegionId(),request.getFromDate(),request.getToDate());
				
			}
		   
	        resultWithCount.put("result", all_Results);
	        resultWithCount.put("count", count);	
			return resultWithCount;
		}
		else
		{
			List<Object> all_Results=detailsRepository.findReservationBatchWise(pageable);
			count=detailsRepository.countReservationBatchWise();
		    resultWithCount.put("result", all_Results);
	        resultWithCount.put("count", count);
			return resultWithCount;
		}
		
	}

	public List<ReservationSlotResponse> listReservationSlot(ReservationDTO request) {
		List<ReservationSlot> list = new ArrayList<>();
        List<LocalDate> dateList = new ArrayList<>();
        List<LocalTime> timeList = new ArrayList<>();
        List<String> reservationStatus = Arrays.asList("UNRESERVED", "RESERVED", "TEMPORARY");
        if(request.getRegionId()>0) {        	
        	 list = reservationSlotRepository.findByRegionIdAndReservationStatusInOrderByDateDescTimeStartAsc(
                    request.getRegionId(), reservationStatus);        	       	
        }else {
        	 list = reservationSlotRepository.findByRegionIdAndReservationStatusInOrderByDateDescTimeStartAsc(
                    1, reservationStatus);
        	}
        
        List<String> dateTimeList = new ArrayList<>();
        for (ReservationSlot slot : list) {
        	RespPerson respPerson=null;
        
        	if(slot.getRespPerson()!=null) {
        	  respPerson= respPersonRepository.findById(slot.getRespPerson().getId())
                     .orElse(null);
        	}
             if (respPerson != null) {
                 slot.setRespPersonName(respPerson.getName());
             }
             
            dateList.add(slot.getDate());

            LocalTime timeStart = LocalTime.parse(slot.getTimeStart(), DateTimeFormatter.ofPattern("HH:mm"));
            timeList.add(timeStart);

            dateTimeList.add(slot.getDate() + "-" + slot.getTimeStart());
        }       

        List<LocalDate> dateResult = new ArrayList<>(dateList);
        List<LocalTime> timeResult = new ArrayList<>(timeList);
        
        List<LocalDate> finalDateList = dateResult.stream()
                .filter(date -> date != null)
                .collect(Collectors.toList());

        List<LocalTime> finalTimeList = timeResult.stream()
                .filter(time -> time != null)
                .sorted()
                .collect(Collectors.toList());

        int timeslotColspan = finalTimeList.size();
        ReservationSlotResponse reservationSlotResponse = new ReservationSlotResponse();
        
        reservationSlotResponse.setDate_time(dateTimeList); // Set the dateTimeList
        reservationSlotResponse.setTimeslot_colspan(finalTimeList); // Set the finalTimeList
        reservationSlotResponse.setFinaltimelist(finalTimeList); // Set the finalTimeList
        reservationSlotResponse.setFinaldatelist(finalDateList); // Set the finalDateList
        reservationSlotResponse.setList(list);
        List<ReservationSlotResponse> responseList = new ArrayList<>();
        responseList.add(reservationSlotResponse);

        return responseList;
	}

	public String deleteResevationSlot(Integer id) {
		ReservationSlot slot = reservationSlotRepository.findById(id).orElse(null);
		slot.setStatus((byte) 1);
		slot.setModifiedOn(LocalDateTime.now());
		slot.setModifiedBy(1);
		slot.setReservationStatus("UNRESERVED");
		slot.setReservationId(null);
		reservationSlotRepository.save(slot);		
		return "DELETED";
	}
	public String deleteReservedSlot(Integer id) {
		ReservationSlot slot = reservationSlotRepository.findById(id).orElse(null);
		slot.setStatus((byte) 1);
		 LocalDate currentDate = LocalDate.now();
		slot.setModifiedOn(LocalDateTime.now());
		slot.setModifiedBy(1); 
        if (currentDate.equals(slot.getDate())) {
     	   slot.setReservationStatus("CANCELLED");
        } else {
     	   slot.setReservationId(null);
     	   slot.setReservationStatus("UNRESERVED");
        }
		reservationSlotRepository.save(slot);		
		ReservationDetails resdet = slot.getReservationId();
		 if(this.checkPastDate(id)){
		  sendReservationUserEmail(resdet);
		 }
		return "DELETED";
	}
	public String confirmReservationSlot(Integer slotId) {
		 ReservationSlot slot = reservationSlotRepository.findById(slotId).orElse(null);
        if (slot != null) {
            // Update the reservation slot status to "RESERVED"
            slot.setReservationStatus("RESERVED");
            slot.setModifiedOn(LocalDateTime.now());
            slot.setModifiedBy(1); // Assuming a user ID
            reservationSlotRepository.save(slot);
            
            // Fetch related reservation details
            ReservationDetails details = slot.getReservationId(); 
           // ReservationDetails details = detailsRepository.findById(slot.getReservationId()).orElse(null);
            
            if (details != null) {
              if(this.checkPastDate(slotId)){
                sendConfirmationEmail(details);
              }
              
            }
        }		return "Comfirmed";
	}
	
	private void sendConfirmationEmail(ReservationDetails details) {
		ReservationDetails resdetails = detailsRepository.findById(details.getReservationId()).orElse(null);
		
    }

	public String reserveSlot(ReservationAddDTO request) {
		   try {
	            ReservationSlot reservationSlot = reservationSlotRepository.findById(request.getId())
	                    .orElseThrow(() -> new IllegalArgumentException("Reservation slot not found"));

	            if ("INSERT".equals(request.getDbaction())) {
	            	 ReservationDetails resDetail = new ReservationDetails();
	            	 resDetail.setPersonName(request.getName());
	            	 resDetail.setSurname(request.getFurigana());
	            	 resDetail.setGender(request.getSex());
	            	 resDetail.setJob(request.getJob());
	            	 resDetail.setAge(request.getAge());
	            	 resDetail.setPostalcode(request.getPostalcode());
	            	 resDetail.setPref(request.getPref());
	            	 resDetail.setTown(request.getTown());
	            	 resDetail.setAddress(request.getAddress());
	            	 resDetail.setTelephone(request.getPhone());
	            	 resDetail.setEmail(request.getEmail());
	            	 resDetail.setType_of_counselling(request.getType_of_counselling());
	            	 resDetail.setCreatedBy(1);
	            	 resDetail.setCreatedOn(LocalDateTime.now());
	            	 resDetail.setModifiedBy(1);
	            	 resDetail.setModifiedOn(LocalDateTime.now());
	            	 
	            	 ReservationDetails  detrep = detailsRepository.save(resDetail);
	            	// updating reservation_slot with reservation_details_id
	            	 reservationSlot.setModifiedBy(1);
	            	 reservationSlot.setModifiedOn(LocalDateTime.now());
	            	 reservationSlot.setReservationStatus("TEMPORARY");
	            	 reservationSlot.setReservationId(detrep);
	            	 reservationSlot.setAge(request.getAge());
	            	 reservationSlot.setType_of_counselling(request.getType_of_counselling());
	            	 ReservationRegion region = regionRepository.findById(request.getRegion_id());	                      
	                 reservationSlot.setRegion(region);	            	 
	            	 reservationSlotRepository.save(reservationSlot);
	            	 if(this.checkPastDate(request.getId())){
	                     sendReservationUserEmail(detrep);
	                     sendReservationAdminEmail(detrep);
	                   }
	                return "Reservation and details saved successfully.";
	            } else if ("UPDATE".equals(request.getDbaction())) {
	            	ReservationDetails resdetails = detailsRepository.findById(request.getReservation_id()).orElse(null);
	            	resdetails.setPersonName(request.getName());
	            	resdetails.setSurname(request.getFurigana());
	            	resdetails.setGender(request.getSex());
	            	resdetails.setJob(request.getJob());
	            	resdetails.setAge(request.getAge());
	            	resdetails.setPostalcode(request.getPostalcode());
	            	resdetails.setPref(request.getPref());
	            	resdetails.setTown(request.getTown());
	            	resdetails.setAddress(request.getAddress());
	            	resdetails.setTelephone(request.getPhone());
	            	resdetails.setEmail(request.getEmail());	  
	            	resdetails.setType_of_counselling(request.getType_of_counselling());
	            	resdetails.setModifiedBy(1);
	            	resdetails.setModifiedOn(LocalDateTime.now());
	            	ReservationDetails resdetailsnew = detailsRepository.save(resdetails);
	            	 LocalDateTime currentDateTime = LocalDateTime.now();
	                 LocalDateTime requestedDateTime = request.getDate();

	                 if (requestedDateTime.isAfter(currentDateTime) || requestedDateTime.isEqual(currentDateTime)) {
	                	 sendReservationAdminEmail(resdetails); 
	                	 
	                 }

	                return "Reservation details updated successfully.";
	            } else {
	                return "Unknown operation.";
	            }
	        } catch (Exception e) {
	            throw new IllegalArgumentException("Error processing reservation: " + e.getMessage());
	        }
	    
	}
	
	private void sendReservationAdminEmail(ReservationDetails detrep) {
		try {
			Optional<ReservationSlot> reservationSlot = reservationSlotRepository.findById(detrep.getReservationId());
			String date1="00/00";
			String time1="00:00";
			String region="";
			if (reservationSlot.isPresent()) {
			    LocalDate resdate = reservationSlot.get().getDate();			    
			    time1 = reservationSlot.get().getTimeStart();
			    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd");
			    date1 = resdate.format(dateFormatter);
			    int reg = reservationSlot.get().getRegion().getId();
			    if ("2".equals(reg)) {
			    	 region="東京";
			    }else {
			    	region="大阪";
			    }
			}
			emailService.sendReservationAddMail(detrep,date1,time1,region);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



	private void sendReservationUserEmail(ReservationDetails detrep) {
		try {
			Optional<ReservationSlot> reservationSlot = reservationSlotRepository.findById(detrep.getReservationId());
			String date1="00/00";
			String time1="00:00";
			if (reservationSlot.isPresent()) {
			    LocalDate resdate = reservationSlot.get().getDate();			    
			    time1 = reservationSlot.get().getTimeStart();
			    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd");
			    date1 = resdate.format(dateFormatter);
			}
			emailService.sendReservationUserMail(detrep,date1,time1);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	public String cancelReservationSlot(Integer slotId) {
		 ReservationSlot slot = reservationSlotRepository.findById(slotId).orElse(null);
       if (slot != null) {
    	   LocalDate currentDate = LocalDate.now();
           
           if (currentDate.equals(slot.getDate())) {
        	   slot.setReservationStatus("CANCELLED");
           } else {
        	   slot.setReservationId(null);
        	   slot.setReservationStatus("UNRESERVED");
           }
           slot.setModifiedOn(LocalDateTime.now());
           slot.setModifiedBy(1); // Assuming a user ID
           reservationSlotRepository.save(slot);
           
           // Fetch related reservation details
           ReservationDetails details = slot.getReservationId(); 
          // ReservationDetails details = detailsRepository.findById(slot.getReservationId()).orElse(null);
           
           if (details != null) {
        	   sendCancellationEmail(details);
             if(this.checkPastDate(slotId)){
               //sendCancellationEmail(details);
             }
             
           }
       }		return "Cancelled";
	}
	private void sendCancellationEmail(ReservationDetails details) {
		try {			
			emailService.sendReservationCancelMail(details);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
