package com.mentalhealthapi.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mentalhealthapi.dto.MemberRequest;
import com.mentalhealthapi.dto.ReservationAddDTO;
import com.mentalhealthapi.dto.ReservationRequest;
import com.mentalhealthapi.dto.ReservationSlotResponse;
import com.mentalhealthapi.dto.RespPersonRequest;
import com.mentalhealthapi.dto.CanceledReservtnResponse;
import com.mentalhealthapi.dto.CounselingListResponse;
import com.mentalhealthapi.dto.MemberDTO;
import com.mentalhealthapi.dto.ReservationDTO;
import com.mentalhealthapi.helper.ApiListResponse;
import com.mentalhealthapi.helper.ApiResponse;
import com.mentalhealthapi.model.Member;
import com.mentalhealthapi.model.Region;
import com.mentalhealthapi.model.ReservationDetails;
import com.mentalhealthapi.model.ReservationRegion;
import com.mentalhealthapi.model.ReservationSlot;
import com.mentalhealthapi.model.RespPerson;
import com.mentalhealthapi.service.MemberService;
import com.mentalhealthapi.service.ReservationCounselingService;

@RestController
@RequestMapping("/api/reservation")
public class ReservationCounselingController {
	   private final ReservationCounselingService reservatnCounslngService;
	   
	        
	    public ReservationCounselingController(ReservationCounselingService reservatnCounslngService) {
	        this.reservatnCounslngService = reservatnCounslngService;
	    }
	    /**
	     * API for getting all region list
	     * @return List<ReservationRegion> 
	     */
	    @GetMapping("/getregion")
	    @PreAuthorize("hasAuthority('ADMIN')")
	    public ResponseEntity<ApiResponse<List<ReservationRegion>>> findAllReservationRegion() {
	        try {
	        
	        	List<ReservationRegion> result = reservatnCounslngService.getAllRegion();
	            ApiResponse<List<ReservationRegion>> response = new ApiResponse<>(1, null, result);
	            return ResponseEntity.ok(response);
	        } catch (IllegalArgumentException e) {
	            ApiResponse<List<ReservationRegion>> response = new ApiResponse<>(0, e.getMessage(), null);
	            return ResponseEntity.badRequest().body(response);
	        }
	    }
		  
	    /**
	     * Api for getting manager list(resp person) in a specific region
	     * @param id
	     * @return list of responsible person
	     */
	    @GetMapping("/respperson/{id}")
	    @PreAuthorize("hasAuthority('ADMIN')")
	    public ResponseEntity<ApiResponse<List<RespPerson>>> getRespPersonByRegionId(@PathVariable int id) {
	        try {
	        
	        	List<RespPerson> result = reservatnCounslngService.getRespPerson(id);
	            ApiResponse<List<RespPerson>> response = new ApiResponse<>(1, null, result);
	            return ResponseEntity.ok(response);
	        } catch (IllegalArgumentException e) {
	            ApiResponse<List<RespPerson>> response = new ApiResponse<>(0, e.getMessage(), null);
	            return ResponseEntity.badRequest().body(response);
	        }
	    }
	    
	    @PostMapping("/saveslot")
	    @PreAuthorize("hasAuthority('ADMIN')")
	    public ResponseEntity<ApiResponse> saveReservationSlot(@RequestBody ReservationRequest request) 
	    {
	        try {
	        	ResponseEntity<ApiResponse> result = reservatnCounslngService.saveReservationSlot(request);
	            return result;
	        } catch (IllegalArgumentException e) {
	            ApiResponse<Object> response = new ApiResponse<>(0, e.getMessage(), null);
	            return ResponseEntity.badRequest().body(response);
	        }
	    }
	    
	    
	    /**
	     * API for create new counselor/resp person
	     * @param request
	     * @return
	     */
	    @PostMapping("/createcounselor")
	    @PreAuthorize("hasAuthority('ADMIN')")
	    public ResponseEntity<ApiResponse<RespPerson>> createCounselor(@RequestBody RespPersonRequest request) 
	    {
	        try {
	        
	        	RespPerson result = reservatnCounslngService.createCounselor(request);
	            ApiResponse<RespPerson> response = new ApiResponse<>(1, null, result);
	            return ResponseEntity.ok(response);
	        } catch (IllegalArgumentException e) {
	            ApiResponse<RespPerson> response = new ApiResponse<>(0, e.getMessage(), null);
	            return ResponseEntity.badRequest().body(response);
	        }
	    }
	  
	    /**
	     * API for get all counselor
	     * @param request
	     * @return
	     */
	    @PostMapping("/counselorlist")
	    @PreAuthorize("hasAuthority('ADMIN')")
		 public ResponseEntity<ApiResponse<List<RespPerson>>> conselorList(@RequestBody ReservationDTO request) {    	
	    	  List<RespPerson> result = reservatnCounslngService.getCouselorList();    	 
	    	  ApiResponse<List<RespPerson>> response = new ApiResponse<>(1, null, result);	        
	    	  return ResponseEntity.ok(response);
		    }
	    
	  
	    /**
	     * Delete counselor
	     * @param id
	     * @return
	     */
	    @DeleteMapping("/deletecouselor/{id}")
	    @PreAuthorize("hasAuthority('ADMIN')")
		 public ResponseEntity<ApiResponse<String>> conselorDelete(@PathVariable("id") Integer id) {    	
	    	  String result = reservatnCounslngService.deleteCounselor(id);    	 
	    	  ApiResponse<String> response = new ApiResponse<>(1, null, result);	        
	    	  return ResponseEntity.ok(response);
		    }
	   
	    /**
	     * List reservation with status 1
	     * C link
	     * @param request
	     * @return
	     */
	    @PostMapping("/list")
	    @PreAuthorize("hasAuthority('ADMIN')")
		 public ResponseEntity<ApiResponse<List<CounselingListResponse>>> consultationList(@RequestBody ReservationDTO request) {    	
	    	  List<CounselingListResponse> result = reservatnCounslngService.getReservationSlots(request.getPageNumber(),request.getPageSize());    	 
	    	  ApiResponse<List<CounselingListResponse>> response = new ApiResponse<>(1, null, result);	        
	    	  return ResponseEntity.ok(response);
		    }
	    
	   /**
	    * List all cancelled reservations
	    * @param request
	    * @return
	    */
	    @PostMapping("/listTodaysCancellation")
	    @PreAuthorize("hasAuthority('ADMIN')")
		 public ResponseEntity<ApiListResponse<List<CanceledReservtnResponse>>> listTodaysCancellation(@RequestBody ReservationDTO request) {    	
	    	  List<CanceledReservtnResponse> result = reservatnCounslngService.listTodaysCancellation(request.getPageNumber(),request.getPageSize());    	 
	    	  ApiListResponse<List<CanceledReservtnResponse>> response = new ApiListResponse<>(1, null, result,reservatnCounslngService.countTodaysCancellation());	        
	    	  return ResponseEntity.ok(response);
		    }
	    
	    /**
	     * API for search reservation slot with details using,date,region id,status
	     * C link search and list(without any condition)
	     * @param request
	     * @return
	     */
	    @PostMapping("/searchslot")
	    @PreAuthorize("hasAuthority('ADMIN')")
		 public ResponseEntity<ApiListResponse<List<Object>>> searchReservationSlot(@RequestBody ReservationDTO request) {    	
	    	  Map<String, Object> result = reservatnCounslngService.searchReservationSlots(request);    	 
	    	  List<Object> res = (List<Object>) result.get("result");
	    	  ApiListResponse<List<Object>> response = new ApiListResponse<>(1, null, res,(Long)result.get("count"));	        
	    	  return ResponseEntity.ok(response);
		    }
	    
	    /**
	     * Delete reservation slot(Hard delete)
	     * @param id
	     * @return string
	     */
	    @DeleteMapping("/deleteEmptySlot/{id}")
	    @PreAuthorize("hasAuthority('ADMIN')")
		public ResponseEntity<ApiResponse<String>> deleteRemark(@PathVariable("id") Integer id) {  
			 try {
		            String result = reservatnCounslngService.deleteEmptySlot(id);
		            ApiResponse<String> response = new ApiResponse<>(1, null, result);
		            return ResponseEntity.ok(response);
		        } catch (IllegalArgumentException e) {
		            ApiResponse<String> response = new ApiResponse<>(0, e.getMessage(), null);
		            return ResponseEntity.badRequest().body(response);
		        }
		    }
	    
	    /**
	     * List reservation slot with available slot array
	     * @param request
	     * @return
	     */
	    @PostMapping("/list-reservation-slot")
	    @PreAuthorize("hasAuthority('ADMIN')")
		 public ResponseEntity<ApiResponse<List<ReservationSlotResponse>>> listReservationSlot(@RequestBody ReservationDTO request) {    	
	    	  List<ReservationSlotResponse> result = reservatnCounslngService.listReservationSlot(request);    	 
	    	  ApiResponse<List<ReservationSlotResponse>> response = new ApiResponse<>(1, null, result);	        
	    	  return ResponseEntity.ok(response);
		    }

	    /**
	     * E link
	     * TEMPORARY,RESERVED or CANCELLED list and search with region and status
	     * @param request
	     * @return
	     */
	    @PostMapping("/reservationlist")
	    @PreAuthorize("hasAuthority('ADMIN')")
	    public ResponseEntity<ApiListResponse<List<Object>>> getReservationList(@RequestBody ReservationDTO request) {  
	        try {
	        	 Map<String, Object> result = reservatnCounslngService.getAllReservationList(request); 
	        	 
	        	 ApiListResponse<List<Object>> response = new ApiListResponse<>(1, null, (List<Object>)result.get("result"),(Long)result.get("count"));	        
	            return ResponseEntity.ok(response);
	        } catch (IllegalArgumentException e) {
	        	ApiListResponse<List<Object>> response = new ApiListResponse<>(0, e.getMessage(), null,0);
	            return ResponseEntity.badRequest().body(response);
	        }
	    }

	    /**
	     * List TEMPORARY reservation slots with status 1
	     * With pagination
	     * @param request
	     * @return
	     */
	    @PostMapping("/listrsvconfirmation")
	    @PreAuthorize("hasAuthority('ADMIN')")
		 public ResponseEntity<ApiListResponse<List<Object[]>>> listRsvConfirmation(@RequestBody ReservationDTO request) {    	
	    	  List<Object[]> result = reservatnCounslngService.listRsvConfirmation(request.getPageNumber(),request.getPageSize());   
	    	  Long count=reservatnCounslngService.getCountOfTemporarySlot();
	    	  ApiListResponse<List<Object[]>> response = new ApiListResponse<>(1, null, result,count);	        
	    	  return ResponseEntity.ok(response);
		 }
	    
	    /**
	     * Unreserve reservation slot
	     * @param id
	     * @return
	     */
	    @DeleteMapping("/deleteResSlot/{id}")
	    @PreAuthorize("hasAuthority('ADMIN')")
		public ResponseEntity<ApiResponse<String>> deleteReservationSlot(@PathVariable("id") Integer id) {  
			 try {
		            String result = reservatnCounslngService.deleteResevationSlot(id);
		            ApiResponse<String> response = new ApiResponse<>(1, null, result);
		            return ResponseEntity.ok(response);
		        } catch (IllegalArgumentException e) {
		            ApiResponse<String> response = new ApiResponse<>(0, e.getMessage(), null);
		            return ResponseEntity.badRequest().body(response);
		        }
		    }
		/**
		 * Change status to UNRESERVED or CANCELLED 
		 * send email to user 
		 * @param id
		 * @return
		 */
	    @DeleteMapping("/deleteReservedSlot/{id}")
	    @PreAuthorize("hasAuthority('ADMIN')")
		public ResponseEntity<ApiResponse<String>> deleteReservedSlot(@PathVariable("id") Integer id) {  
			 try {
		            String result = reservatnCounslngService.deleteReservedSlot(id);
		            ApiResponse<String> response = new ApiResponse<>(1, null, result);
		            return ResponseEntity.ok(response);
		        } catch (IllegalArgumentException e) {
		            ApiResponse<String> response = new ApiResponse<>(0, e.getMessage(), null);
		            return ResponseEntity.badRequest().body(response);
		        }
		    }
	    /**
	     * change reservation status to RESERVED and send email to customer
	     * @param slotId
	     * @return
	     */
	    @PostMapping("/confirm/{slotId}")
	    @PreAuthorize("hasAuthority('ADMIN')")
	    public ResponseEntity<ApiResponse<String>> confirmReservation(@PathVariable Integer slotId) {
	    	try {
	            String result = reservatnCounslngService.confirmReservationSlot(slotId);
	            ApiResponse<String> response = new ApiResponse<>(1, null, result);
	            return ResponseEntity.ok(response);
	        } catch (IllegalArgumentException e) {
	            ApiResponse<String> response = new ApiResponse<>(0, e.getMessage(), null);
	            return ResponseEntity.badRequest().body(response);
	        }
	    }
	    
	    //* API to add and update reservation slot vacant slot add and reserved slot edit*/
	    @PostMapping("/reserveSlot")
	    @PreAuthorize("hasAuthority('ADMIN')")
	    public ResponseEntity<ApiResponse<String>> reserveSlot(@RequestBody ReservationAddDTO request) {
	    	try {
	            String result = reservatnCounslngService.reserveSlot(request);
	            ApiResponse<String> response = new ApiResponse<>(1, null, result);
	            return ResponseEntity.ok(response);
	        } catch (IllegalArgumentException e) {
	            ApiResponse<String> response = new ApiResponse<>(0, e.getMessage(), null);
	            return ResponseEntity.badRequest().body(response);
	        }
	    }
	   
	    
	    /**
	     * api to change reservation status to cancelled(same day cancellation) or unreserved
	     * @param id
	     * @return
	     */
	    @PostMapping("/cancelResSlot/{id}")
	    @PreAuthorize("hasAuthority('ADMIN')")
	    public ResponseEntity<ApiResponse<String>> cancelResSlot(@PathVariable Integer id) {
	    	try {
	            String result = reservatnCounslngService.cancelReservationSlot(id);
	            ApiResponse<String> response = new ApiResponse<>(1, null, result);
	            return ResponseEntity.ok(response);
	        } catch (IllegalArgumentException e) {
	            ApiResponse<String> response = new ApiResponse<>(0, e.getMessage(), null);
	            return ResponseEntity.badRequest().body(response);
	        }
	    }
	    
}
