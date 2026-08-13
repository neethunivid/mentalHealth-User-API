package com.mentalhealthapi.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mentalhealthapi.dto.MagaddUserDTO;
import com.mentalhealthapi.dto.ReservationDTO;
import com.mentalhealthapi.dto.ReservationRequest;
import com.mentalhealthapi.dto.ReservationResponseDTO;
import com.mentalhealthapi.dto.ReservationSlotResponse;
import com.mentalhealthapi.helper.ApiResponse;
import com.mentalhealthapi.model.MailmagazineUser;
import com.mentalhealthapi.service.ReservationCounselingUserService;

@RestController
@RequestMapping("/api/reservation")
public class ReservationCounselingUserController {
private final ReservationCounselingUserService reservationCounselingUserService;

public ReservationCounselingUserController(ReservationCounselingUserService reservationCounselingUserService) {
	this.reservationCounselingUserService =reservationCounselingUserService;
}

@PostMapping("/addslot")
@PreAuthorize("hasAuthority('USER')")
public ResponseEntity<ApiResponse> addReservationSlot(@RequestBody ReservationRequest request) 
{
    try {
    	ResponseEntity<ApiResponse> result = reservationCounselingUserService.addReservationSlot(request);
        return result;
    } catch (IllegalArgumentException e) {
        ApiResponse<Object> response = new ApiResponse<>(0, e.getMessage(), null);
        return ResponseEntity.badRequest().body(response);
    }
}

@PostMapping("/counselingReservationPage")
//@PreAuthorize("hasAuthority('USER')")
 public ResponseEntity<ApiResponse<List<ReservationResponseDTO>>> counselingReservationPage(@RequestBody ReservationDTO request) {    	
	  List<ReservationResponseDTO> result = reservationCounselingUserService.listReservationSlot(request);    	 
	  ApiResponse<List<ReservationResponseDTO>> response = new ApiResponse<>(1, null, result);	        
	  return ResponseEntity.ok(response);
    }

@PostMapping("/reserveUserSlot")
// @PreAuthorize("hasAuthority('USER')")
public ResponseEntity<ApiResponse> reserveUserSlot(@RequestBody Map<String, String> requestParams){
    try {
    	ResponseEntity<ApiResponse> result = reservationCounselingUserService.reserveSlot(requestParams);
        return result;
    } catch (IllegalArgumentException e) {
        ApiResponse<Object> response = new ApiResponse<>(0, e.getMessage(), null);
        return ResponseEntity.badRequest().body(response);
    }
}


}
