package com.mentalhealthapi.controller;

import java.io.IOException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.mentalhealthapi.dto.CheckSheetListDTO;
import com.mentalhealthapi.dto.CheckSheetReportDTO;
import com.mentalhealthapi.dto.HistoryExportDTO;
import com.mentalhealthapi.dto.MailMagDTO;
import com.mentalhealthapi.dto.MemberWithdrawExportDTO;
import com.mentalhealthapi.dto.RemarkDownloadDTO;
import com.mentalhealthapi.dto.ReservationDTO;
import com.mentalhealthapi.dto.TelCounsellingSearchDTO;
import com.mentalhealthapi.dto.TelConsellingReportRequest;
import com.mentalhealthapi.helper.ApiResponse;
import com.mentalhealthapi.service.ExportService;
import com.mentalhealthapi.service.GrantRequestService;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/export")
public class CSVDownloadController {

	private ExportService exportService;
	private GrantRequestService grService;

	public CSVDownloadController(ExportService exportService, GrantRequestService grService) {
		this.exportService = exportService;
		this.grService = grService;
	}

	/**
	 * csv data download for grand request
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@PostMapping("/grant-request")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<ApiResponse<String>> exportToCSV(@RequestBody HistoryExportDTO request, HttpServletResponse response) throws IOException {
	    try {
	        String msg = exportService.exportGrantReqCsvService(request, response);

	        ApiResponse<String> resp = new ApiResponse<>(1, null, msg);
	        return ResponseEntity.ok(resp);
	    } catch (IllegalArgumentException e) {
	        ApiResponse<String> resp = new ApiResponse<>(0, e.getMessage(), null);
	        return ResponseEntity.ok(resp);
	    }
	}
	/**
	 * expoert member data to csv
	 * @param request
	 * @param response
	 * @return
	 */
	@PostMapping("/member")
	@PreAuthorize("hasAuthority('ADMIN')")
		public ResponseEntity<ApiResponse<String>> exportmemberToCSV(@RequestBody MemberWithdrawExportDTO request, HttpServletResponse response){
	    try {
	        String msg = exportService.exportMemberCsvService(request, response);

	        ApiResponse<String> resp = new ApiResponse<>(1, null, msg);
	        return ResponseEntity.ok(resp);
	    } catch (IllegalArgumentException e) {
	        ApiResponse<String> resp = new ApiResponse<>(0, e.getMessage(), null);
	        return ResponseEntity.ok(resp);
	    }
	}
	
	/**
	 * remark download csv api
	 * @param request
	 * @param response
	 * @return
	 */
	@PostMapping("/remarksDownload")
	@PreAuthorize("hasAuthority('ADMIN')")
		public ResponseEntity<ApiResponse<String>> csvRemarks(@RequestBody RemarkDownloadDTO request, HttpServletResponse response){
	    try {
	        String msg = exportService.remarksToCsvService(request, response);

	        ApiResponse<String> resp = new ApiResponse<>(1, null, msg);
	        return ResponseEntity.ok(resp);
	    } catch (IllegalArgumentException e) {
	        ApiResponse<String> resp = new ApiResponse<>(0, e.getMessage(), null);
	        return ResponseEntity.ok(resp);
	    }
	}
	
	/**
	 * mail magazine user email list download
	 * @param request
	 * @param response
	 * @return
	 */
	
	@PostMapping("/mailmagUserDownload")
	@PreAuthorize("hasAuthority('ADMIN')")
		public ResponseEntity<ApiResponse<String>> mailmagUser(@RequestBody MailMagDTO request, HttpServletResponse response){
	    try {
	        String msg = exportService.mailmagUser(request, response);

	        ApiResponse<String> resp = new ApiResponse<>(1, null, msg);
	        return ResponseEntity.ok(resp);
	    } catch (IllegalArgumentException e) {
	        ApiResponse<String> resp = new ApiResponse<>(0, e.getMessage(), null);
	        return ResponseEntity.ok(resp);
	    }
	}
	
	
	@GetMapping("/mailmagEmailDownload/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
		public ResponseEntity<ApiResponse<String>> mailmagEmail(@PathVariable int id, HttpServletResponse response){
	    try {
	        String msg = exportService.mailmagEmail(id, response);

	        ApiResponse<String> resp = new ApiResponse<>(1, null, msg);
	        return ResponseEntity.ok(resp);
	    } catch (IllegalArgumentException e) {
	        ApiResponse<String> resp = new ApiResponse<>(0, e.getMessage(), null);
	        return ResponseEntity.ok(resp);
	    }
	}
	/**
	 * Download Reservation list by region or by date
	 * @param request
	 * @param response
	 * @return data
	 */
	@PostMapping("/reservationdownload")
	@PreAuthorize("hasAuthority('ADMIN')")
		public ResponseEntity<ApiResponse<String>> reservationListDownload(@RequestBody ReservationDTO request, HttpServletResponse response){
	    try {
	        String msg = exportService.reservationListDownload(request,response);

	        ApiResponse<String> resp = new ApiResponse<>(1, null, msg);
	        return ResponseEntity.ok(resp);
	    } catch (IllegalArgumentException e) {
	        ApiResponse<String> resp = new ApiResponse<>(0, e.getMessage(), null);
	        return ResponseEntity.ok(resp);
	    }
	}
	/*
	 * Download checksheet details by finyear
	 */
	@PostMapping("/checkSheetdownload")
	@PreAuthorize("hasAuthority('ADMIN')")
		public ResponseEntity<ApiResponse<String>> checksheetDownload(@RequestBody CheckSheetListDTO request, HttpServletResponse response){
	    try {
	        String msg = exportService.checkSheetDownload(request,response);

	        ApiResponse<String> resp = new ApiResponse<>(1, null, msg);
	        return ResponseEntity.ok(resp);
	    } catch (IllegalArgumentException e) {
	        ApiResponse<String> resp = new ApiResponse<>(0, e.getMessage(), null);
	        return ResponseEntity.ok(resp);
	    }
	}
	/**
	 * daily checksheet report with row total
	 * for all days in a month
	 * @param request
	 * @param response
	 * @return
	 */
	@PostMapping("/dailyDownload")
	@PreAuthorize("hasAuthority('ADMIN')")
		public ResponseEntity<ApiResponse<String>> checksheetDailyDownload(@RequestBody CheckSheetReportDTO request, HttpServletResponse response){
	    try {
	        String msg = exportService.checkSheetDailyDownload(request,response);

	        ApiResponse<String> resp = new ApiResponse<>(1, null, msg);
	        return ResponseEntity.ok(resp);
	    } catch (IllegalArgumentException e) {
	        ApiResponse<String> resp = new ApiResponse<>(0, e.getMessage(), null);
	        return ResponseEntity.ok(resp);
	    }
	}
	/**
	 * Download daily checksheet by checksheet type(all days in a month)
	 * @param request
	 * @param response
	 * @return
	 */
	@PostMapping("/dailyByChecksheetDownload")
	@PreAuthorize("hasAuthority('ADMIN')")
		public ResponseEntity<ApiResponse<String>> dailyByChecksheetDownload(@RequestBody CheckSheetReportDTO request, HttpServletResponse response){
	    try {
	        String msg = exportService.dailyByChecksheetDownload(request,response);

	        ApiResponse<String> resp = new ApiResponse<>(1, null, msg);
	        return ResponseEntity.ok(resp);
	    } catch (IllegalArgumentException e) {
	        ApiResponse<String> resp = new ApiResponse<>(0, e.getMessage(), null);
	        return ResponseEntity.ok(resp);
	    }
	}
	
	/**
	 * Download monthly checksheet count for all months in an year
	 * @param request
	 * @param response
	 * @return
	 */
	@PostMapping("/monthlyDownload")
	@PreAuthorize("hasAuthority('ADMIN')")
		public ResponseEntity<ApiResponse<String>> monthlyDownload(@RequestBody CheckSheetReportDTO request, HttpServletResponse response){
	    try {
	        String msg = exportService.monthlyDownload(request,response);

	        ApiResponse<String> resp = new ApiResponse<>(1, null, msg);
	        return ResponseEntity.ok(resp);
	    } catch (IllegalArgumentException e) {
	        ApiResponse<String> resp = new ApiResponse<>(0, e.getMessage(), null);
	        return ResponseEntity.ok(resp);
	    }
	}
	/**
	 * Download monthly checksheet report for all months with checksheet type
	 * @param request
	 * @param response
	 * @return
	 */
	@PostMapping("/montlyByChecksheetDownload")
	@PreAuthorize("hasAuthority('ADMIN')")
		public ResponseEntity<ApiResponse<String>> montlyByChecksheetDownload(@RequestBody CheckSheetReportDTO request, HttpServletResponse response){
	    try {
	        String msg = exportService.montlyByChecksheetDownload(request,response);

	        ApiResponse<String> resp = new ApiResponse<>(1, null, msg);
	        return ResponseEntity.ok(resp);
	    } catch (IllegalArgumentException e) {
	        ApiResponse<String> resp = new ApiResponse<>(0, e.getMessage(), null);
	        return ResponseEntity.ok(resp);
	    }
	}

	/**
	 * controller to Download Tel Counselling details
	 * @param request
	 * @param response
	 * @return
	 */
	@PostMapping("/telCounsellDownload")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<ApiResponse<String>> telCouncellListDownload(@RequestBody TelCounsellingSearchDTO request, HttpServletResponse response){
		try {
			String msg = exportService.telCounsellListDownload(request,response);

			ApiResponse<String> resp = new ApiResponse<>(1, null, msg);
			return ResponseEntity.ok(resp);
		} catch (IllegalArgumentException e) {
			ApiResponse<String> resp = new ApiResponse<>(0, e.getMessage(), null);
			return ResponseEntity.ok(resp);
		}
	}

	/**
	 * Handles the request to generate and download a gender report as a CSV file.
	 * @param request TelConsellingReportRequest
	 * @param response The HTTP response object used to write the CSV output
	 * @return ResponseEntity
	 */
	@PostMapping("/genderReportDownload")
	public ResponseEntity<ApiResponse<String>> genderReportDownload(@RequestBody TelConsellingReportRequest request, HttpServletResponse response){
		try {
			String msg = exportService.genderReportDownload(request,response);

			ApiResponse<String> resp = new ApiResponse<>(1, null, msg);
			return ResponseEntity.ok(resp);
		} catch (IllegalArgumentException e) {
			ApiResponse<String> resp = new ApiResponse<>(0, e.getMessage(), null);
			return ResponseEntity.ok(resp);
		}
	}

	/**
	 * Handles the request to generate and download a age report as a CSV file.
	 * @param request TelConsellingReportRequest
	 * @param response The HTTP response object used to write the CSV output
	 * @return ResponseEntity
	 */
	@PostMapping("/ageReportDownload")
	public ResponseEntity<ApiResponse<String>> ageReportDownload(@RequestBody TelConsellingReportRequest request, HttpServletResponse response){
		try {
			String msg = exportService.ageReportDownload(request,response);

			ApiResponse<String> resp = new ApiResponse<>(1, null, msg);
			return ResponseEntity.ok(resp);
		} catch (IllegalArgumentException e) {
			ApiResponse<String> resp = new ApiResponse<>(0, e.getMessage(), null);
			return ResponseEntity.ok(resp);
		}
	}
	/**
	 * Handles the request to generate and download a illness report as a CSV file.
	 * @param request TelConsellingReportRequest
	 * @param response The HTTP response object used to write the CSV output
	 * @return ResponseEntity
	 */
	@PostMapping("/illnessReportDownload")
	public ResponseEntity<ApiResponse<String>> illnessReportDownload(@RequestBody TelConsellingReportRequest request, HttpServletResponse response){
		try {
			String msg = exportService.illnessReportDownload(request,response);

			ApiResponse<String> resp = new ApiResponse<>(1, null, msg);
			return ResponseEntity.ok(resp);
		} catch (IllegalArgumentException e) {
			ApiResponse<String> resp = new ApiResponse<>(0, e.getMessage(), null);
			return ResponseEntity.ok(resp);
		}
	}
	
	/**
	 * Handles the request to generate and download a counselling report as a CSV file.
	 * @param request TelConsellingReportRequest
	 * @param response The HTTP response object used to write the CSV output
	 * @return ResponseEntity
	 */
	@PostMapping("/counsellingReportDownload")
	public ResponseEntity<ApiResponse<String>> counsellingReportDownload(@RequestBody TelConsellingReportRequest request, HttpServletResponse response){
		try {
			String msg = exportService.counsellingReportDownload(request,response);

			ApiResponse<String> resp = new ApiResponse<>(1, null, msg);
			return ResponseEntity.ok(resp);
		} catch (IllegalArgumentException e) {
			ApiResponse<String> resp = new ApiResponse<>(0, e.getMessage(), null);
			return ResponseEntity.ok(resp);
		}
	}
}
