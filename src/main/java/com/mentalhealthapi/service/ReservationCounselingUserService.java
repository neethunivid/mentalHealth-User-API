package com.mentalhealthapi.service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.mentalhealthapi.dto.ReservationDTO;
import com.mentalhealthapi.dto.ReservationRequest;
import com.mentalhealthapi.dto.ReservationResponseDTO;
import com.mentalhealthapi.dto.ReservationSlotResponse;
import com.mentalhealthapi.helper.ApiResponse;
import com.mentalhealthapi.model.ReservationDetails;
import com.mentalhealthapi.model.ReservationRegion;
import com.mentalhealthapi.model.ReservationSlot;
import com.mentalhealthapi.model.RespPerson;
import com.mentalhealthapi.repository.RegionRepository;
import com.mentalhealthapi.repository.ReservationCounsellingSlotRepo;
import com.mentalhealthapi.repository.ReservationDetailsRepository;
import com.mentalhealthapi.repository.ReservationSlotRepository;
import com.mentalhealthapi.repository.RespPersonRepository;

import jakarta.transaction.Transactional;
@Service
public class ReservationCounselingUserService {
	private final RegionRepository regionRepository;
	private final ReservationCounsellingSlotRepo reservationSlotRepository;
	private final RespPersonRepository respPersonRepository;
	private final ReservationDetailsRepository detailsRepository;
	private final EmailService emailService;
	
	public ReservationCounselingUserService(RegionRepository regionRepository, RespPersonRepository respPersonRepository,
			ReservationCounsellingSlotRepo reservationSlotRepository, ReservationSlotRepository slotRepository,
			ReservationDetailsRepository detailsRepository,EmailService emailService) {
		this.regionRepository = regionRepository;
		this.reservationSlotRepository = reservationSlotRepository;
		this.respPersonRepository = respPersonRepository;
		this.detailsRepository = detailsRepository;
		this.emailService =emailService;
	}
	
	@Transactional
    public ResponseEntity<ApiResponse> reserveSlot(Map<String, String> requestParams) {
		ApiResponse response = new ApiResponse();
        if ("INSERT".equals(requestParams.get("dbaction"))) {
            ReservationDetails reservationDetails = new ReservationDetails();
            reservationDetails.setPersonName(requestParams.get("name"));
            reservationDetails.setSurname(requestParams.get("furigana"));
            reservationDetails.setGender(requestParams.get("sex"));
            reservationDetails.setJob(requestParams.get("job"));
            reservationDetails.setPostalcode(requestParams.get("postalcode"));
            reservationDetails.setPref(requestParams.get("pref"));
            reservationDetails.setTown(requestParams.get("town"));
            reservationDetails.setAddress(requestParams.get("address"));
            reservationDetails.setTelephone(requestParams.get("phone"));
            reservationDetails.setAge(requestParams.get("age"));
            reservationDetails.setType_of_counselling(requestParams.get("type_of_consultation"));
            reservationDetails.setEmail(requestParams.get("email"));
            reservationDetails.setMobile(requestParams.get("mobile_reservation"));
            LocalDateTime now = LocalDateTime.now();
            reservationDetails.setCreatedOn(now);
            reservationDetails.setModifiedOn(now);
            reservationDetails.setCreatedBy(1);
            reservationDetails.setModifiedBy(1);
           

            reservationDetails = detailsRepository.save(reservationDetails);

            Integer reservationSlotId = Integer.parseInt(requestParams.get("reservation_id"));
            Optional<ReservationSlot> reservationSlotOptional = reservationSlotRepository.findById(reservationSlotId);
            ReservationSlot reservationSlot = reservationSlotOptional.orElseThrow(() -> new RuntimeException("Reservation slot not found"));
            reservationSlot.setModifiedOn(now);            
            reservationSlot.setModifiedBy(1);
            reservationSlot.setReservationStatus("TEMPORARY");
            reservationSlot.setReservationId(reservationDetails);

            reservationSlotRepository.save(reservationSlot);
            String timeStart = reservationSlot.getTimeStart(); // Extract timeStart value
            LocalDate date1 = reservationSlot.getDate(); 
            
            emailService.sendUserSlotReservationEmail(reservationDetails,reservationSlot.getRegion(),timeStart ,date1);
           // sendMail(requestParams, reservationSlot.getRegion());
        } else if ("UPDATE".equals(requestParams.get("dbaction"))) {
        	 Integer reservationId = Integer.parseInt(requestParams.get("reservation_id"));
             ReservationDetails reservationDetails = detailsRepository.findById(reservationId).orElseThrow(() -> new RuntimeException("Reservation details not found"));
             
            reservationDetails.setPersonName(requestParams.get("name"));
            reservationDetails.setSurname(requestParams.get("furigana"));
            reservationDetails.setGender(requestParams.get("sex"));
            reservationDetails.setJob(requestParams.get("job"));
            reservationDetails.setPostalcode(requestParams.get("postalcode"));
            reservationDetails.setPref(requestParams.get("pref"));
            reservationDetails.setTown(requestParams.get("town"));
            reservationDetails.setAddress(requestParams.get("address"));
            reservationDetails.setTelephone(requestParams.get("phone"));
            reservationDetails.setEmail(requestParams.get("email"));
            reservationDetails.setAge(requestParams.get("age"));
            reservationDetails.setType_of_counselling(requestParams.get("type_of_consultation"));
            reservationDetails.setModifiedOn(LocalDateTime.now());
            reservationDetails.setModifiedBy(1);

            detailsRepository.save(reservationDetails);
            
        }
        response.setCode(1);
		response.setError(null);
		response.setData("Success");
		return ResponseEntity.ok(response);
		
    }

	
	public ResponseEntity<ApiResponse> addReservationSlot(ReservationRequest request) {
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
	
	public List<ReservationResponseDTO> listReservationSlot(ReservationDTO request) {
	    List<ReservationResponseDTO> responseData = new ArrayList<>();
	    
	    // Get the type_of_counselling from the request
	    String typeOfCounselling = request.getTypeOfCounselling(); // Assuming you have this method in ReservationDTO

	    List<Object[]> list = reservationSlotRepository.getCustomData(typeOfCounselling);

	    for (Object[] row : list) {
	        ReservationResponseDTO response = new ReservationResponseDTO();
	        
	        // Map the row data to the DTO
	        Date date = (Date) row[0];
	        response.setDate(date.toLocalDate()); // Convert java.sql.Date to java.time.LocalDate
	        response.setTimeStart((String) row[1]);
	        response.setTimeEnd((String) row[2]);
	        response.setTypeOfCounselling((String) row[4]);
	        response.setName((String) row[5]);
	        response.setId((Integer) row[6]);
	        response.setReservationstatus((String) row[7]);
	        response.setRegionId((Integer) row[19]);
	        response.setRegionName((String) row[20]);

	        // Add the response to the responseData list
	        responseData.add(response);
	    }

	    // Return the processed data
	    return responseData;
	}
}



