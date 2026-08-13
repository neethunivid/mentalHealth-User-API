package com.mentalhealthapi.controller;

import java.util.List;
import java.util.Map;

import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mentalhealthapi.dto.ArchieveRecordResponse;
import com.mentalhealthapi.dto.CheckSheetListDTO;
import com.mentalhealthapi.dto.CheckSheetTemplateDTO;
import com.mentalhealthapi.dto.CreateReplyDTO;
import com.mentalhealthapi.dto.DiagnoseCheckDTO;
import com.mentalhealthapi.dto.DiagnoseEditUserDTO;
import com.mentalhealthapi.dto.DiagnoseMailMagazineDTO;
import com.mentalhealthapi.dto.DiagnosisMailingResponse;
import com.mentalhealthapi.dto.MemberDTO;
import com.mentalhealthapi.dto.MemberDataDownloadDTO;
import com.mentalhealthapi.dto.MemberRequest;
import com.mentalhealthapi.dto.ReservationDTO;
import com.mentalhealthapi.helper.ApiListResponse;
import com.mentalhealthapi.helper.ApiResponse;
import com.mentalhealthapi.model.ChecksheetTemplate;
import com.mentalhealthapi.model.DiagnoseCheck;
import com.mentalhealthapi.model.DiagnoseMailMagazine;
import com.mentalhealthapi.model.Member;
import com.mentalhealthapi.repository.CheckSheetTemplateRepository;
import com.mentalhealthapi.service.CheckSheetMailService;
import com.mentalhealthapi.service.ReservationCounselingService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/checksheetmail")
public class CheckSheetMailController {
	private final CheckSheetMailService checksheetService;

	public CheckSheetMailController(CheckSheetMailService checksheetService) {
		this.checksheetService = checksheetService;
	}

	/**
	 * API list checksheet with filter can able to pass checkSheetType For A-E Links
	 **/
	@PostMapping("/listbytype")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<ApiListResponse<List<Object>>> listCheckSheetByType(@RequestBody CheckSheetListDTO request) {
		List<Object> result = checksheetService.listCheckSheet(request);
		Long count = checksheetService.countCheckSheet(request);
		ApiListResponse<List<Object>> response = new ApiListResponse<>(1, null, result, count);
		return ResponseEntity.ok(response);
	}
	/*
	 * Api to create reply for mail magazine according to the type method post req
	 * mail subject body etc
	 **/

	@PostMapping("/createReply")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<ApiResponse<DiagnoseMailMagazine>> createReply(@RequestBody CreateReplyDTO request) {
		try {
			DiagnoseMailMagazine result = checksheetService.createReply(request);
			ApiResponse<DiagnoseMailMagazine> response = new ApiResponse<>(1, null, result);
			return ResponseEntity.ok(response);
		} catch (IllegalArgumentException e) {
			ApiResponse<DiagnoseMailMagazine> response = new ApiResponse<>(0, e.getMessage(), null);
			return ResponseEntity.badRequest().body(response);
		}
	}

	 
	/**
	 * API for create mail
	 * @param request
	 * @return
	 */
	@PostMapping("/getuseremail")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<ApiResponse<String>> getUserEmail(@RequestBody CheckSheetListDTO request) {
		String result = checksheetService.getEmailAddresses(request.getDiagonseCheckId());
		ApiResponse<String> response = new ApiResponse<>(1, null, result);
		return ResponseEntity.ok(response);
	}
	
	/**
	 * to list the title of template by id
	 * @param id
	 * @return
	 */

	@GetMapping("/showTemplateText/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<ApiResponse<ChecksheetTemplate>> showTemplateText(@PathVariable Integer id) {
		try {
			ChecksheetTemplate result = checksheetService.showTemplateText(id);
			ApiResponse<ChecksheetTemplate> response = new ApiResponse<>(1, null, result);
			return ResponseEntity.ok(response);
		} catch (IllegalArgumentException e) {
			ApiResponse<ChecksheetTemplate> response = new ApiResponse<>(0, e.getMessage(), null);
			return ResponseEntity.badRequest().body(response);
		}
	}
	
	/**
	 * list of title for template 
	 * @param request
	 * @return
	 */

	@PostMapping("/listTemplateText")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<ApiResponse<List<ChecksheetTemplate>>> searchMembers(@RequestBody CheckSheetListDTO request) {
		List<ChecksheetTemplate> result = checksheetService.listTemplateText(request);
		ApiResponse<List<ChecksheetTemplate>> response = new ApiResponse<>(1, null, result);
		return ResponseEntity.ok(response);
	}

	/**
	 * link F
	 * list all diagnose_mail_magazine details with pagination
	 * */
	@PostMapping("/diagnosis-mailing-list")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<ApiListResponse<List<DiagnosisMailingResponse>>> diagnosisMailingList(
			@RequestBody CheckSheetListDTO request) {
		try {
			List<DiagnosisMailingResponse> result = checksheetService.diagnosisMailingList(request);
			int totalCount = checksheetService.diagnosisMailingDistinctCount();
			ApiListResponse<List<DiagnosisMailingResponse>> response = new ApiListResponse<>(1, null, result,
					totalCount);
			return ResponseEntity.ok(response);
		} catch (IllegalArgumentException e) {
			ApiListResponse<List<DiagnosisMailingResponse>> response = new ApiListResponse<>(0, e.getMessage(), null,
					0);
			return ResponseEntity.badRequest().body(response);
		}

	}

	/**
	 * Api to delete email by id diagnoseMailMagazine
	 * @param id
	 * @return
	 */
	@DeleteMapping("/deletemail/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<ApiResponse<String>> deleteEmail(@PathVariable Integer id) {
		String result = checksheetService.deleteEmail(id);
		ApiResponse<String> response = new ApiResponse<>(1, null, result);
		return ResponseEntity.ok(response);
	}
	
	/**
	 * save data to diagnoseMailMagazine and email to diagnoseMailUsers
	 * @param request
	 * @return
	 */

	@PostMapping("/saveemail")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<ApiResponse<DiagnoseMailMagazine>> saveEmail(@RequestBody DiagnoseMailMagazineDTO request) {
		try {
			DiagnoseMailMagazine result = checksheetService.saveEmail(request);
			ApiResponse<DiagnoseMailMagazine> response = new ApiResponse<>(1, null, result);
			return ResponseEntity.ok(response);
		} catch (IllegalArgumentException e) {
			ApiResponse<DiagnoseMailMagazine> response = new ApiResponse<>(0, e.getMessage(), null);
			return ResponseEntity.badRequest().body(response);
		}
	}
	
	/**
	 * C section
	 * Edit mail magazine send status to 0 and DiagnoseCheck send status to 0
	 * Remove mail mag user and create new entry
	 */
	@PutMapping("/edituserssenddate/{magid}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<ApiResponse<String>> editUsersSenddate(@PathVariable Integer magid,@RequestBody DiagnoseEditUserDTO request) {
		try {
			String result = checksheetService.editUsersSenddate(magid,request);
			ApiResponse<String> response = new ApiResponse<>(1, null, result);
			return ResponseEntity.ok(response);

		} catch (Exception e) {
			ApiResponse<String> response = new ApiResponse<>(0, e.getMessage(), null);
			return ResponseEntity.badRequest().body(response);
		}
	}
	/**
	 * Edit mail magazine time 
	 * */
	@PutMapping("/edit/{magid}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<ApiResponse<String>> editUser(@PathVariable Integer magid,@RequestBody DiagnoseMailMagazineDTO request) {
		try {
			String result = checksheetService.editUsers(magid,request);
			ApiResponse<String> response = new ApiResponse<>(1, null, result);
			return ResponseEntity.ok(response);

		} catch (Exception e) {
			ApiResponse<String> response = new ApiResponse<>(0, e.getMessage(), null);
			return ResponseEntity.badRequest().body(response);
		}
	}
	//Email edit in checksheet F
	@PutMapping("/emailedit/{checkId}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<ApiResponse<String>> emailEditUser(@PathVariable int checkId,@RequestBody CheckSheetListDTO request) {
		try {
			String result = checksheetService.editEmails(checkId,request);
			ApiResponse<String> response = new ApiResponse<>(1, null, result);
			return ResponseEntity.ok(response);

		} catch (Exception e) {
			ApiResponse<String> response = new ApiResponse<>(0, e.getMessage(), null);
			return ResponseEntity.badRequest().body(response);
		}
	}
	/**
	 * list of ArchiveRecordList
	 * @param request
	 * @return
	 */
	@PostMapping("/archiverecordlist")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<ApiListResponse<ArchieveRecordResponse>> archiveRecordList(@RequestBody CheckSheetListDTO request) {
		ArchieveRecordResponse result = checksheetService.getArchiveRecordList(request);
		int totalCount=checksheetService.getCountArchiveRecordList(request);
		ApiListResponse<ArchieveRecordResponse> response = new ApiListResponse<>(1, null, result,totalCount);
		return ResponseEntity.ok(response);
	}

	/**
	 * set isarchived to 1
	 * @param request
	 * @return
	 */
	@PostMapping("/setArchived")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse<String>> setArchived(@RequestBody DiagnoseEditUserDTO request){
	    try {
	        String result = checksheetService.setarchived(request);

	        ApiResponse<String> resp = new ApiResponse<>(1, null, result);
	        return ResponseEntity.ok(resp);
	    } catch (IllegalArgumentException e) {
	        ApiResponse<String> resp = new ApiResponse<>(0, e.getMessage(), null);
	        return ResponseEntity.ok(resp);
	    }
	}
	
	/**
	 * set isdeleted to 1
	 * @param request
	 * @return
	 */
	
	@PostMapping("/isdeleted")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse<String>> isDeleted(@RequestBody DiagnoseEditUserDTO request){
	    try {
	        String result = checksheetService.isDeleted(request);

	        ApiResponse<String> resp = new ApiResponse<>(1, null, result);
	        return ResponseEntity.ok(resp);
	    } catch (IllegalArgumentException e) {
	        ApiResponse<String> resp = new ApiResponse<>(0, e.getMessage(), null);
	        return ResponseEntity.ok(resp);
	    }
	}
	
	/**
	 * Yearly list of reservation 
	 * @param request
	 * @return
	 */
	
	@PostMapping("/reservationsYearlylist")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<ApiListResponse<List<DiagnoseCheckDTO>>> reservationsYearlylist(
			@RequestBody CheckSheetListDTO request) {
		try {
			List<DiagnoseCheckDTO> result = checksheetService.reservationsYearlylist(request);
			int totalCount = result.size();
			ApiListResponse<List<DiagnoseCheckDTO>> response = new ApiListResponse<>(1, null, result,
					totalCount);
			return ResponseEntity.ok(response);
		} catch (IllegalArgumentException e) {
			ApiListResponse<List<DiagnoseCheckDTO>> response = new ApiListResponse<>(0, e.getMessage(), null,
					0);
			return ResponseEntity.badRequest().body(response);
		}

	}
	/*** update checksheet_template isdefault 1 */
	@PutMapping("/setasdefaulttemplate/{templateId}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<ApiResponse<String>> setAsDeafultTemplate(@PathVariable int templateId) {
		try {
			String result = checksheetService.isdefaultCheckSheetTemplate(templateId);
			ApiResponse<String> response = new ApiResponse<>(1, null, result);
			return ResponseEntity.ok(response);
		} catch (IllegalArgumentException e) {
			ApiResponse<String> response = new ApiResponse<>(0, e.getMessage(), null);
			return ResponseEntity.badRequest().body(response);
		}

	}
	
	/** delete checksheet_template (isdelete to 1)*/
	@DeleteMapping("/deletetemplate/{templateId}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<ApiResponse<String>> setAsDeleteTemplate(@PathVariable int templateId) {
		try {
			String result = checksheetService.deleteCheckSheetTemplate(templateId);
			ApiResponse<String> response = new ApiResponse<>(1, null, result);
			return ResponseEntity.ok(response);
		} catch (IllegalArgumentException e) {
			ApiResponse<String> response = new ApiResponse<>(0, e.getMessage(), null);
			return ResponseEntity.badRequest().body(response);
		}

	}
	
	/**Edit in checksheet_template */
	@PutMapping("/editchecksheettemplate/{editId}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<ApiResponse<String>> getCheckSheetTemplate(@PathVariable int editId ,@RequestBody CheckSheetTemplateDTO request) {
		try {
			String result = checksheetService.editCheckSheetTemplate(editId,request);
			
			ApiResponse<String> response = new ApiResponse<>(1, null, result);
			return ResponseEntity.ok(response);
		} catch (IllegalArgumentException e) {
			ApiResponse<String> response = new ApiResponse<>(0, e.getMessage(), null);
			return ResponseEntity.badRequest().body(response);
		}

	}
	/*
	 * Api to create template save data to checksheetTemplate table
	 * */
	
	@PostMapping("/createTemplate")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<ApiResponse<ChecksheetTemplate>> createTemplate(@RequestBody ChecksheetTemplate request) {
		try {
			ChecksheetTemplate result = checksheetService.createTemplate(request);
			ApiResponse<ChecksheetTemplate> response = new ApiResponse<>(1, null, result);
			return ResponseEntity.ok(response);
		} catch (IllegalArgumentException e) {
			ApiResponse<ChecksheetTemplate> response = new ApiResponse<>(0, e.getMessage(), null);
			return ResponseEntity.badRequest().body(response);
		}
	}
	
	/**
	 * Api to check template exist
	 * */
	@PostMapping("/checkExistingTemplateOrNot")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<ApiResponse<Integer>> checkExistingTemplateOrNot(@RequestBody ChecksheetTemplate request) {
		try {
			Integer result = checksheetService.checkExistingTemplateOrNot(request);
			ApiResponse<Integer> response = new ApiResponse<>(1, null, result);
			return ResponseEntity.ok(response);
		} catch (IllegalArgumentException e) {
			ApiResponse<Integer> response = new ApiResponse<>(0, e.getMessage(), null);
			return ResponseEntity.badRequest().body(response);
		}
	}
	/* List of deleted emails */
	@PostMapping("/showDeletedMail")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiListResponse<List<DiagnoseCheck>>> showDeletedMail(@RequestBody CheckSheetListDTO request){
	    try {
	    	List<DiagnoseCheck> dc = checksheetService.showDeletedMail(request);
	    	int totalCount=checksheetService.countOfDeletedMail();
	    	ApiListResponse<List<DiagnoseCheck>> resp = new ApiListResponse<>(1, null, dc,totalCount);
	        return ResponseEntity.ok(resp);
	    } catch (IllegalArgumentException e) {
	    	ApiListResponse<List<DiagnoseCheck>> resp = new ApiListResponse<>(0, e.getMessage(), null,0);
	        return ResponseEntity.ok(resp);
	    }
	}
	
	/*set restore email */
	@PostMapping("/setRestoreEmail")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse<String>> setRestoreEmail(@RequestBody DiagnoseEditUserDTO request){
	    try {
	    	String dc = checksheetService.setRestoreEmail(request);
	    	ApiResponse<String> resp = new ApiResponse<>(1, null, dc);
	        return ResponseEntity.ok(resp);
	    } catch (IllegalArgumentException e) {
	    	ApiResponse<String> resp = new ApiResponse<>(0, e.getMessage(), null);
	        return ResponseEntity.ok(resp);
	    }
	}
	
}
