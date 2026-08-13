package com.mentalhealthapi.controller;


import com.mentalhealthapi.dto.AgeReportDTO;
import com.mentalhealthapi.dto.CounsellingReportResponse;
import com.mentalhealthapi.dto.GenderReportDTO;
import com.mentalhealthapi.dto.IllnessReportResponse;
import com.mentalhealthapi.dto.TelConsellingReportRequest;
import com.mentalhealthapi.dto.TelCounsellingDTO;
import com.mentalhealthapi.dto.TelCounsellingSearchDTO;
import com.mentalhealthapi.helper.ApiListResponse;
import com.mentalhealthapi.helper.ApiResponse;
import com.mentalhealthapi.model.RouteToCounselling;
import com.mentalhealthapi.model.TelConselling;
import com.mentalhealthapi.model.TelMainIssue;
import com.mentalhealthapi.service.TelCounsellingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/telcounselling")
public class TelCounsellingController {
    private final TelCounsellingService telCounsellingService;

    @Autowired
    public TelCounsellingController(TelCounsellingService telCounsellingService) {
        this.telCounsellingService = telCounsellingService;
    }

    /**
     * Controller for saving new TelCounselling information.
     * @param request The DTO containing the details to be saved.
     * @return ResponseEntity with ApiResponse indicating success or failure.
     */
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse> saveTelCounsellingInfo(@RequestBody TelCounsellingDTO request) {
        try {
            ResponseEntity<ApiResponse> result = telCounsellingService.saveTelCounsellingInfo(request);
            return result;
        } catch (IllegalArgumentException e) {
            ApiResponse<Object> response = new ApiResponse<>(0, e.getMessage(), null);
            return ResponseEntity.badRequest().body(response);
        }
    }

    /**
     * Controller for editing existing TelCounselling information based on the ID.
     * @param id The ID of the TelCounselling entry to edit.
     * @param request The DTO containing updated details.
     * @return ResponseEntity with ApiResponse indicating success or failure.
     */
    @PutMapping("/edit/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse> editTelCounsellingInfo(@PathVariable int id, @RequestBody TelCounsellingDTO request) {
        try {
            ResponseEntity<ApiResponse> result = telCounsellingService.editTelCounsellingInfo(id, request);
            return result;
        } catch (IllegalArgumentException e) {
            ApiResponse<Object> response = new ApiResponse<>(0, e.getMessage(), null);
            return ResponseEntity.badRequest().body(response);
        }
    }

    /**
     * Controller for getting list of TelCounselling details
     * @return ResponseEntity with ApiResponse indicating success or failure.
     */
    @PostMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiListResponse<List<TelConselling>>> getAllTelCounsellingInfo(@RequestBody TelCounsellingSearchDTO telCounsellingSearchDTO) {
        try {
            return telCounsellingService.getAllTelCounsellingInfo(telCounsellingSearchDTO);
        } catch (Exception e) {
            ApiListResponse<List<TelConselling>> response = new ApiListResponse<>(0, e.getMessage(), null,0);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * Controller for getting list of TelCounselling Route information.
     * @return ResponseEntity with ApiResponse indicating success or failure.
     */
    @GetMapping("/route")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse<List<RouteToCounselling>>> getRouteToCounselling() {
        try {
            return telCounsellingService.getRouteToCounselling();
        } catch (Exception e) {
            ApiResponse<List<RouteToCounselling>> response = new ApiResponse<>(0, e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * Controller for getting list of Main Issues.
     * @return ResponseEntity with ApiResponse indicating success or failure.
     */
    @GetMapping("/mainissues")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse<List<TelMainIssue>>> getTelMainIssues() {
        try {
            return telCounsellingService.getTelMainIssues();
        } catch (Exception e) {
            ApiResponse<List<TelMainIssue>> response = new ApiResponse<>(0, e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    /*
     * API to list gender report in telephone counselling by year and month.
     */
    @PostMapping("/genderReport")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse<List<GenderReportDTO>>> genderReport(@RequestBody TelConsellingReportRequest request) {
        try {
            // Call service method with year and optional month
            List<GenderReportDTO> result = telCounsellingService.genderReport(request.getYear(), request.getMonth());
            
            ApiResponse<List<GenderReportDTO>> response = new ApiResponse<>(1, null, result);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            ApiResponse<List<GenderReportDTO>> response = new ApiResponse<>(0, e.getMessage(), null);
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    /*
     * API to list age report in telephone counselling by year and month.
     */
    @PostMapping("/ageReport")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse<List<AgeReportDTO>>> ageReport(@RequestBody TelConsellingReportRequest request) {
        try {
            // Call service method with year and optional month
            List<AgeReportDTO> result = telCounsellingService.ageReport(request.getYear(), request.getMonth());
            
            ApiResponse<List<AgeReportDTO>> response = new ApiResponse<>(1, null, result);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            ApiResponse<List<AgeReportDTO>> response = new ApiResponse<>(0, e.getMessage(), null);
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    /*
     * API to list illness report in telephone counselling by year and month.
     */
    @PostMapping("/illnessReport")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse<List<IllnessReportResponse>>> illnessReport(@RequestBody TelConsellingReportRequest request) {
        try {
            // Call service method with year and optional month
            List<IllnessReportResponse> result = telCounsellingService.illnessReport(request.getYear(), request.getMonth());
            
            ApiResponse<List<IllnessReportResponse>> response = new ApiResponse<>(1, null, result);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            ApiResponse<List<IllnessReportResponse>> response = new ApiResponse<>(0, e.getMessage(), null);
            return ResponseEntity.badRequest().body(response);
        }
    }



    /**
     * Controller searching TelCounselling details.
     * @return ResponseEntity with ApiListResponse indicating success or failure.
     */
    @PostMapping("/search")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiListResponse<List<TelConselling>>> searchTellCounselling(@RequestBody TelCounsellingSearchDTO request) {
        try {
            ApiListResponse<List<TelConselling>> response = telCounsellingService.searchTelCounsell(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiListResponse<List<TelConselling>> response = new ApiListResponse<>(0, e.getMessage(), null ,0 );
            return ResponseEntity.badRequest().body(response);
        }
    }


    /**
     * Retrieves the details of a TelConselling entity by its ID.
     * @param id the ID of the TelConselling entity to retrieve
     * @return ResponseEntity containing the API response with the requested TelConselling data
     */
    @GetMapping("view/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse<TelConselling>> searchRemarks(@PathVariable int id) {
        try {
            ApiResponse<TelConselling> response = telCounsellingService.telCounsellViewById(id);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse<TelConselling> response = new ApiResponse<>(0, e.getMessage(), null);
            return ResponseEntity.badRequest().body(response);
        }
    }

    /**
     * Controller to delete a TelConselling entity by id.
     * @param id the ID of the TelConselling entity to delete
     * @return ResponseEntity containing the API response indicating success or failure of the delete operation
     */
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse> deleteTelCounsellingInfo(@PathVariable("id") Integer id) {
        try {
            ApiResponse response = telCounsellingService.deleteTelCounsell(id);
            if (response.getCode() == 1) {
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.badRequest().body(response);
            }
        } catch (IllegalArgumentException e) {
            ApiResponse<Object> errorResponse = new ApiResponse<>(0, e.getMessage(), null);
            return ResponseEntity.badRequest().body(errorResponse);
        } catch (Exception e) {
            ApiResponse<Object> errorResponse = new ApiResponse<>(0, "Failed to delete", null);
            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }
    
    /*
     * API to list counselling report in telephone counselling by year and month.
     */
    @PostMapping("/counsellingReport")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse<List<CounsellingReportResponse>>> counsellingReport(@RequestBody TelConsellingReportRequest request) {
        try {
            // Call service method with year and optional month
            List<CounsellingReportResponse> result = telCounsellingService.counsellingReport(request.getYear(), request.getMonth());
            
            ApiResponse<List<CounsellingReportResponse>> response = new ApiResponse<>(1, null, result);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            ApiResponse<List<CounsellingReportResponse>> response = new ApiResponse<>(0, e.getMessage(), null);
            return ResponseEntity.badRequest().body(response);
        }
    }
}
