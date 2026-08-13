package com.mentalhealthapi.service;

import java.io.FileWriter;
import java.text.DateFormat;
import com.mentalhealthapi.helper.ApiListResponse;
import com.opencsv.CSVWriter;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Map;
import java.util.LinkedHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.mentalhealthapi.dto.AgeReportDTO;
import com.mentalhealthapi.dto.CanceledReservtnResponse;
import com.mentalhealthapi.dto.CheckSheetListDTO;
import com.mentalhealthapi.dto.CheckSheetReportDTO;
import com.mentalhealthapi.dto.CounsellingReportResponse;
import com.mentalhealthapi.dto.DiagnoseCheckDTO;
import com.mentalhealthapi.dto.HistoryExportDTO;
import com.mentalhealthapi.dto.IllnessReportResponse;
import com.mentalhealthapi.dto.MailMagDTO;
import com.mentalhealthapi.dto.MemberWithdrawExportDTO;
import com.mentalhealthapi.dto.RemarkDownloadDTO;
import com.mentalhealthapi.dto.ReservationDTO;
import com.mentalhealthapi.dto.TelCounsellingSearchDTO;
import com.mentalhealthapi.dto.GenderReportDTO;
import com.mentalhealthapi.dto.TelConsellingReportRequest;
import com.mentalhealthapi.model.GrantRequest;
import com.mentalhealthapi.model.Member;
import com.mentalhealthapi.model.ReservationDetails;
import com.mentalhealthapi.model.ReservationSlot;
import com.mentalhealthapi.model.TelConselling;
import com.mentalhealthapi.repository.MailMagJobRepository;
import com.mentalhealthapi.repository.MailmagazineBodyRepository;
import com.mentalhealthapi.repository.MailmagazineEmailRepository;
import com.mentalhealthapi.repository.MailmagazineUserRepository;
import com.mentalhealthapi.repository.RemarksRepository;
import com.mentalhealthapi.repository.ReservationDetailsRepository;
import com.mentalhealthapi.repository.TelCounsellingRepository;
import com.mentalhealthapi.model.MailMagJob;
import com.mentalhealthapi.model.MailMagazineEmail;

import io.jsonwebtoken.io.IOException;
import jakarta.servlet.http.HttpServletResponse;
import com.mentalhealthapi.repository.RemarksRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@Service
public class ExportService {

	@Autowired
	private GrantRequestService grService;
	@Autowired
	private MemberService memberService;
	private MemberManagementService memberManagementService;
	private RemarksRepository remarksRepository;
	private final MailmagazineUserRepository mailmagazineUserRepository;
	private MailmagazineBodyRepository mailmagazineBodyRepository;
	private MailMagJobRepository mailMagJobRepository;
	private MailmagazineEmailRepository mailmagazineEmailRepository;
	private ReservationDetailsRepository detailsRepository;
	private CheckSheetMailService checkSheetMailService;
	private CheckSheetReportService checkSheetReportService;
	private final TelCounsellingRepository telCounsellingRepository;
	private final TelCounsellingService telCounsellingService;

	public ExportService(GrantRequestService grService, MemberService memberService,
                         MemberManagementService memberManagementService, RemarksRepository remarksRepository,
                         MailmagazineUserRepository mailmagazineUserRepository, ReservationDetailsRepository detailsRepository,
                         MailmagazineBodyRepository mailmagazineBodyRepository, MailMagJobRepository mailMagJobRepository,
                         MailmagazineEmailRepository mailmagazineEmailRepository, CheckSheetMailService checkSheetMailService,
                         CheckSheetReportService checkSheetReportService, TelCounsellingRepository telCounsellingRepository, TelCounsellingService telCounsellingService) {
		this.grService = grService;
		this.detailsRepository = detailsRepository;
		this.memberService = memberService;
		this.memberManagementService = memberManagementService;
		this.remarksRepository = remarksRepository;
		this.mailmagazineUserRepository = mailmagazineUserRepository;
		this.mailmagazineBodyRepository = mailmagazineBodyRepository;
		this.mailMagJobRepository = mailMagJobRepository;
		this.mailmagazineEmailRepository = mailmagazineEmailRepository;
		this.checkSheetMailService = checkSheetMailService;
		this.checkSheetReportService =checkSheetReportService;
        this.telCounsellingRepository = telCounsellingRepository;
        this.telCounsellingService = telCounsellingService;
    }

	public String exportGrantReqCsvService(HistoryExportDTO request, HttpServletResponse response) {
		try {

			response.setContentType("text/csv");
			response.setCharacterEncoding("UTF-8"); // Set the character encoding
			response.setContentType("text/csv; charset=UTF-8");
			DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
			String currentDateTime = dateFormatter.format(new Date());

			String headerKey = "Content-Disposition";
			String headerValue = "attachment; filename=grantrequest_" + currentDateTime + ".csv";
			response.setHeader(headerKey, headerValue);

			List<GrantRequest> gr_list = null;
			if (request.isApplicationDetails()) {
				gr_list = grService.getYearWiseGrantRequests();
			} else if (request.isApplicationHistory()) {
				if (request.getYear() != 0) {
					gr_list = grService.getYearBetweenGrantRequests(request.getYear());
				} else {
					gr_list = grService.getPreviousYearGrantRequests();
				}
			} else {
				gr_list = grService.getYearWiseGrantRequests();
			}
			// List<GrantRequest> gr_list = grService.getAllGrantRequests();
			String[] csvHeader = null;
			ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
			if (request.isApplicationDetails()) {
				csvHeader = new String[] { "受付日付", "新規・継続区分", "研究活動区分", "申請者", "年齢", "推薦者(メール)", "推薦者", "推薦者題名",
						"研究総額(万円)", "申請金額(万円)" };
			} else if (request.isApplicationHistory()) {
				csvHeader = new String[] { "受付日付", "新規・継続区分", "研究活動区分", "申請者", "年齢", "推薦者(メール)", "推薦者", "推薦者題名",
						"研究総額(万円)", "申請金額(万円)" };
			}
			String[] nameMapping = { "id", "newCont", "classification", "institution", "age", "email2",
					"recomdPernInst", "researchName", "expTotal", "subTotal" };
			String[] nameMapping2 = { "dateCreated", null, null, "dept", "age" };
			String[] nameMapping3 = { null, null, "remark", "representative", null, null, "recomdPernFullname", null,
					null };
			csvWriter.writeHeader(csvHeader);

			for (GrantRequest gr : gr_list) {
				csvWriter.write(gr, nameMapping);
				csvWriter.flush();
//				String[] data = {gr.getDept(), gr.getDept(), gr.getDept(), gr.getDept(), gr.getDept(), gr.getDept(), };
				csvWriter.write(gr, nameMapping2);
				csvWriter.flush();
				csvWriter.write(gr, nameMapping3);
				csvWriter.flush();
				// csvWriter.write(new Object());
				// response.getWriter().println();
			}
			csvWriter.close();

			return "created csv";
		} catch (Exception e) {
			return "Failed to export file";
		}
	}

	public String exportMemberCsvService(MemberWithdrawExportDTO request, HttpServletResponse response) {

		response.setContentType("text/csv");
		response.setCharacterEncoding("UTF-8"); // Set the character encoding
		response.setContentType("text/csv; charset=UTF-8");

		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=csvmember_details.csv";
		response.setHeader(headerKey, headerValue);
		List<Object[]> memberList = null;
		if (request.isWithdrawList())
			memberList = memberService.getAllMemberData();

		String[] csvHeader = null;

		csvHeader = new String[] { "変更日", "会員番号", "名前（漢字）", "ID", "パスワード", "性別", "生年月日", "職業", "入会日", "メールアドレス", "理由" };

		// try (CSVWriter csvWriter = new CSVWriter(new
		// FileWriter("csvmember_remove.csv"))) {
		try (CSVWriter csvWriter = new CSVWriter(response.getWriter())) {
			// Write the CSV header
			csvWriter.writeNext(csvHeader);

			for (Object[] objects : memberList) {
				String[] csvRow = new String[objects.length];
				for (int i = 0; i < objects.length; i++) {
					Object value = objects[i];
					if (value != null) {
						if (i == 5) {
							int sexValue = (int) value;
							csvRow[i] = (sexValue == 0) ? "女性" : "男性";
						} else if (i == 7) {
							// Perform mapping for the "job" field if necessary
							// Example: csvRow[i] = mapJobValue((int) value);
							// Replace `mapJobValue` with your custom mapping logic
							csvRow[i] = value.toString();
						} else {
							csvRow[i] = value.toString();
						}
					} else {
						csvRow[i] = "";
					}
				}
				csvWriter.writeNext(csvRow);
			}
			return "CSV file created successfully.";
		} catch (IOException | java.io.IOException e) {
			System.out.println("Error while writing CSV file: " + e.getMessage());
			return "Failed to export file";
		}

	}

	public String remarksToCsvService(RemarkDownloadDTO request, HttpServletResponse response) {

		// response.setCharacterEncoding("UTF-8"); // Set the character encoding
		response.setContentType("text/csv; charset=UTF-8");
		response.setHeader("Content-Disposition", "attachment; filename=csvmember_details.csv");

		List<Object[]> remarkList = null;
		String type = null;
		String remarkId = request.getRemarkid();
		String memberId = request.getMemberid();
		String title = request.getTitle();

		LocalDateTime fromDate = null;
		LocalDateTime toDate = null;

		if (request.getFromDate() != null) {
			fromDate = LocalDateTime.parse(request.getFromDate().toString());
		}

		if (request.getToDate() != null) {
			toDate = LocalDateTime.parse(request.getToDate().toString());
		}
//		if(!request.getRemarkid().isEmpty() ) {
//			remarkList = remarksRepository.searchRemarksDnwldRmk(remarkId);
//		}
//		if (!request.getMemberid().isEmpty())
//        {
//			remarkList = remarksRepository.searchRemarksDnwldMem(memberId);
//        }
//		if (!request.getTitle().isEmpty())
//		{
//			remarkList = remarksRepository.searchRemarksDnwldTitle(title);
//		}
//		if (request.getFromDate()!=null)
//		{			
//			remarkList = remarksRepository.searchRemarksDnwldDate(fromDate,toDate);			
//		}

		if (request.getChkall() && (remarkList == null || remarkList.isEmpty())) {

//			remarkList = remarksRepository.findAllRemarksDwnld();
			remarkList = remarksRepository.searchRemarksDnwld(remarkId, memberId, title, fromDate, toDate);

		} else if (request.getChkspecify() && (remarkList == null || remarkList.isEmpty())) {
			if (request.getNormal()) {
				type = "normal";
			}
			if (request.getAnxiety()) {
				type = "anxiety";
			}
			if (request.getBlackmail()) {
				type = "blackmail";
			}
			if (request.getOther()) {
				type = "other";
			}
			if (request.getGrowingup()) {
				type = "growingup";
			}
			if (request.getAddition1()) {
				type = "addition1";
			}
			if (request.getAddition2()) {
				type = "addition2";
			}
			remarkList = remarksRepository.findTypeRemarksDwnld(type);
		} else {
			remarkList = remarksRepository.searchRemarksDnwld(remarkId, memberId, title, fromDate, toDate);

		}

		String[] csvHeader = null;

		csvHeader = new String[] { "発言No", "日にち", "メンバー名", "ID", "Mail", "タイトル", "文章" };

		try (CSVWriter csvWriter = new CSVWriter(response.getWriter())) {
			// Write the CSV header
			csvWriter.writeNext(csvHeader);

			if (remarkList != null && !remarkList.isEmpty()) {
				for (Object[] objects : remarkList) {
					String[] csvRow = new String[objects.length];
					for (int i = 0; i < objects.length; i++) {

						Object value = objects[i];
						csvRow[i] = value != null ? value.toString() : ""; // Convert the value to a string and handle
																			// null values
					}
					csvWriter.writeNext(csvRow);
				}
			}
			return "CSV file created successfully.";
		} catch (IOException | java.io.IOException e) {
			System.out.println("Error while writing CSV file: " + e.getMessage());
			return "Failed to export file";
		}

	}

	public String mailmagUser(MailMagDTO request, HttpServletResponse response) {
		response.setContentType("text/csv");
		response.setCharacterEncoding("UTF-8"); // Set the character encoding
		response.setContentType("text/csv; charset=UTF-8");
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=mailingMember.csv";
		response.setHeader(headerKey, headerValue);
		List<Object[]> muserList = null;
		String type = request.getType();
		String listType = request.getListType();

		if ("PC".equals(type)) {
			if ("BBS".equals(listType)) {
				muserList = mailmagazineUserRepository.getAllBbsList();
			}
			if ("GENERAL".equals(listType)) {
				muserList = mailmagazineUserRepository.getAllGeneralList();
			}
		}
		if ("MOB".equals(type)) {
			if ("BBS".equals(listType)) {
				muserList = mailmagazineUserRepository.getAllBbsMobList();
			}
			if ("GENERAL".equals(listType)) {
				muserList = mailmagazineUserRepository.getAllGeneralMobList();
			}
		}
		String[] csvHeader = null;
		if ("BBS".equals(listType)) {
		csvHeader = new String[] { "id", "登録日", "E-Mail", "名前", "会員ID" };

		}
		else {
			csvHeader = new String[] { "id", "登録日", "E-Mail", "名前" };

		}
		try (CSVWriter csvWriter = new CSVWriter(response.getWriter())) {
			// Write the CSV header
			csvWriter.writeNext(csvHeader);

			if (muserList != null && !muserList.isEmpty()) {
				for (Object[] objects : muserList) {
					String[] csvRow = new String[objects.length];
					for (int i = 0; i < objects.length; i++) {
						Object value = objects[i];
						csvRow[i] = value != null ? value.toString() : ""; // Convert the value to a string and handle
																			// null values
					}
					csvWriter.writeNext(csvRow);
				}
			}
			return "CSV file created successfully.";
		} catch (IOException | java.io.IOException e) {
			System.out.println("Error while writing CSV file: " + e.getMessage());
			return "Failed to export file";
		}

	}

	public String mailmagEmail(Integer id, HttpServletResponse response) {

		response.setContentType("text/csv");
		response.setCharacterEncoding("UTF-8"); // Set the character encoding
		response.setContentType("text/csv; charset=UTF-8");
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=mailingMember.csv";
		response.setHeader(headerKey, headerValue);
		List<MailMagazineEmail> muserEmailList = null;
		muserEmailList = mailmagazineEmailRepository.findMailMagEmailsByJobId(id);
		String[] csvHeader = null;
		csvHeader = new String[] { "id", "E-Mail" };

		try (CSVWriter csvWriter = new CSVWriter(response.getWriter())) {
			// Write the CSV header
			csvWriter.writeNext(csvHeader);

			if (muserEmailList != null && !muserEmailList.isEmpty()) {
				for (MailMagazineEmail email : muserEmailList) {
					String[] csvRow = new String[] { String.valueOf(email.getId()), email.getEmail() };
					csvWriter.writeNext(csvRow);
				}
			}
			return "CSV file created successfully.";
		} catch (IOException | java.io.IOException e) {
			System.out.println("Error while writing CSV file: " + e.getMessage());
			return "Failed to export file";
		}

	}

	public String reservationListDownload(ReservationDTO request, HttpServletResponse response) {

		response.setContentType("text/csv");
		response.setCharacterEncoding("UTF-8"); // Set the character encoding
		response.setContentType("text/csv; charset=UTF-8");
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=mailingMember.csv";
		response.setHeader(headerKey, headerValue);
		List<Object> data;

		if (request.getAllFilter())
			data = detailsRepository.findReservationBatchWise();
		else {
		if(request.getRegionId()==0) {
			data=detailsRepository.findReservationSlotsByDate(request.getFromDate(),request.getToDate());
		}else if(request.getFromDate()==null)
		{
			data=detailsRepository.findReservationSlotsByRegion(request.getRegionId());
				
		}else {
			data=detailsRepository.findReservationSlots(request.getRegionId(),request.getFromDate(),request.getToDate());
		}
		}
			
		String[] csvHeader = null;
		csvHeader = new String[] { "希望日時", "氏名", "領域", "ふりがな", "性別", "職業", "都道府県", "市町村", "TEL", "email", "当日キャンセル" };

		try (CSVWriter csvWriter = new CSVWriter(response.getWriter())) {
			// Write the CSV header
			csvWriter.writeNext(csvHeader);

			if (data != null && !data.isEmpty()) {
				for (Object details : data) {
					ReservationSlot rslot = (ReservationSlot) details;
					String[] csvRow = new String[] { String.valueOf(rslot.getDate()),
							rslot.getReservationId().getPersonName(), rslot.getRegion().getName(),
							rslot.getReservationId().getSurname(), rslot.getReservationId().getGender(),
							rslot.getReservationId().getJob(), rslot.getReservationId().getPref(),
							rslot.getReservationId().getTown(), rslot.getReservationId().getTelephone(),
							rslot.getReservationId().getEmail(), "" };
					csvWriter.writeNext(csvRow);
				}
			}
			return "CSV file created successfully.";
		} catch (IOException | java.io.IOException e) {
			System.out.println("Error while writing downloads file: " + e.getMessage());
			return "Failed to export file";
		}

	}

	public String checkSheetDownload(CheckSheetListDTO request, HttpServletResponse response) {
		response.setContentType("text/csv");
		response.setCharacterEncoding("UTF-8"); // Set the character encoding
		response.setContentType("text/csv; charset=UTF-8");
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=Report.csv";
		response.setHeader(headerKey, headerValue);
		List<DiagnoseCheckDTO> data = null;
		if (request.getFinYear()>0) {
			data = checkSheetMailService.reservationsYearlylist(request);
	      }
		String[] csvHeader = null;
		csvHeader = new String[] { "No", "受付","名前","E-mail","返信日付","チェックシート名 ","デバイス名","診断結果","相談内容" };
		try (CSVWriter csvWriter = new CSVWriter(response.getWriter())) {
			// Write the CSV header
			csvWriter.writeNext(csvHeader);

			if (data != null && !data.isEmpty()) {
				for (Object details : data) {
					DiagnoseCheckDTO rslot = (DiagnoseCheckDTO) details;
					String[] csvRow = new String[] { String.valueOf(rslot.getId()),
							rslot.getDate(), rslot.getUsername(),
							rslot.getUserEmail(), rslot.getMailSendDate(),
							rslot.getTitle(), rslot.getDeviceinfo(),
							rslot.getDiagnosisResult(), rslot.getChecksheetContent() };
					csvWriter.writeNext(csvRow);
				}
			}
			return "CSV file created successfully.";
		} catch (IOException | java.io.IOException e) {
			System.out.println("Error while writing downloads file: " + e.getMessage());
			return "Failed to export file";
		}
	}

	public String checkSheetDailyDownload(CheckSheetReportDTO request, HttpServletResponse response) {
		response.setContentType("text/csv");
		response.setCharacterEncoding("UTF-8"); // Set the character encoding
		response.setContentType("text/csv; charset=UTF-8");
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=DailyReport.csv";
		response.setHeader(headerKey, headerValue);
		List<Object> data = null;		 
			data = checkSheetReportService.dailyReport(request);	
			// Create a mapping between JSON keys and desired column header names
		    Map<String, String> columnHeaderMapping = new LinkedHashMap<>();
		    columnHeaderMapping.put("dt", "Date");
		    columnHeaderMapping.put("1", "Column1Name");
		    columnHeaderMapping.put("2", "Column2Name");		   
		    columnHeaderMapping.put("3", "Column3Name");
		    columnHeaderMapping.put("4", "Column4Name");
		    columnHeaderMapping.put("5", "Column5Name");
		    columnHeaderMapping.put("subtotal", "Total");
	      
		String[] csvHeader = null;
		csvHeader = new String[] { "Date", "森田神経質","対人恐怖","パニック","うつ病","強迫性障害 ","小計" };
		try (CSVWriter csvWriter = new CSVWriter(response.getWriter())) {
			// Write the CSV header
			csvWriter.writeNext(csvHeader);

			if (data != null && !data.isEmpty()) {
	            for (Object rowDataObj : data) {
	                Map<String, Object> rowData = (Map<String, Object>) rowDataObj;
	                String[] csvRow = new String[csvHeader.length];
	                int columnIndex = 0;
	                for (String jsonKey : columnHeaderMapping.keySet()) {
	                    Object columnValue = rowData.get(jsonKey);
	                    csvRow[columnIndex] = (columnValue != null) ? columnValue.toString() : ""; // Convert to String
	                    columnIndex++;
	                }
	                csvWriter.writeNext(csvRow);
	            }
	        }
			return "CSV file created successfully.";
		} catch (IOException | java.io.IOException e) {
			System.out.println("Error while writing downloads file: " + e.getMessage());
			return "Failed to export file";
		}
	}

	public String dailyByChecksheetDownload(CheckSheetReportDTO request, HttpServletResponse response) {
		response.setContentType("text/csv");
		response.setCharacterEncoding("UTF-8"); // Set the character encoding
		response.setContentType("text/csv; charset=UTF-8");
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=ReportDailyChecksheet.csv";
		response.setHeader(headerKey, headerValue);
		List<Object> data = null;
					data = checkSheetReportService.dailyChecksheetReport(request);
					// Create a mapping between JSON keys and desired column header names
				    Map<String, String> columnHeaderMapping = new LinkedHashMap<>();
				    columnHeaderMapping.put("dt", "Date");
				    columnHeaderMapping.put("1", "Column1Name");
				    columnHeaderMapping.put("2", "Column2Name");		   
				    columnHeaderMapping.put("3", "Column3Name");
				    columnHeaderMapping.put("4", "Column4Name");
				    columnHeaderMapping.put("subtotal", "Total");
		String[] csvHeader = null;
		csvHeader = new String[] { "Date", "症状","治療方法","治療機関","その他","小計 " };
		try (CSVWriter csvWriter = new CSVWriter(response.getWriter())) {
			// Write the CSV header
			csvWriter.writeNext(csvHeader);

			if (data != null && !data.isEmpty()) {
	            for (Object rowDataObj : data) {
	                Map<String, Object> rowData = (Map<String, Object>) rowDataObj;
	                String[] csvRow = new String[csvHeader.length];
	                int columnIndex = 0;
	                for (String jsonKey : columnHeaderMapping.keySet()) {
	                    Object columnValue = rowData.get(jsonKey);
	                    csvRow[columnIndex] = (columnValue != null) ? columnValue.toString() : ""; // Convert to String
	                    columnIndex++;
	                }
	                csvWriter.writeNext(csvRow);
	            }
	        }
			return "CSV file created successfully.";
		} catch (IOException | java.io.IOException e) {
			System.out.println("Error while writing downloads file: " + e.getMessage());
			return "Failed to export file";
		}
	}

	public String monthlyDownload(CheckSheetReportDTO request, HttpServletResponse response) {
		response.setContentType("text/csv");
		response.setCharacterEncoding("UTF-8"); // Set the character encoding
		response.setContentType("text/csv; charset=UTF-8");
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=ReportDailyChecksheet.csv";
		response.setHeader(headerKey, headerValue);
		List<Map<String, Object>> data = null;
					data = checkSheetReportService.generateMonthlyReport(request.getYear());
					 Map<String, String> columnHeaderMapping = new LinkedHashMap<>();
					    columnHeaderMapping.put("date", "Date");
					    columnHeaderMapping.put("1", "Column1Name");
					    columnHeaderMapping.put("2", "Column2Name");		   
					    columnHeaderMapping.put("3", "Column3Name");
					    columnHeaderMapping.put("4", "Column4Name");
					    columnHeaderMapping.put("5", "Column5Name");
					    columnHeaderMapping.put("subtotal", "Total");
		String[] csvHeader = null;
		csvHeader = new String[] { "Date",  "森田神経質","対人恐怖","パニック","うつ病","強迫性障害 ","小計" };
		try (CSVWriter csvWriter = new CSVWriter(response.getWriter())) {
			// Write the CSV header
			csvWriter.writeNext(csvHeader);
			if (data != null && !data.isEmpty()) {
	            for (Object rowDataObj : data) {
	                Map<String, Object> rowData = (Map<String, Object>) rowDataObj;
	                String[] csvRow = new String[csvHeader.length];
	                int columnIndex = 0;
	                for (String jsonKey : columnHeaderMapping.keySet()) {
	                    Object columnValue = rowData.get(jsonKey);
	                    csvRow[columnIndex] = (columnValue != null) ? columnValue.toString() : ""; // Convert to String
	                    columnIndex++;
	                }
	                csvWriter.writeNext(csvRow);
	            }
	        }
			return "CSV file created successfully.";
		} catch (IOException | java.io.IOException e) {
			System.out.println("Error while writing downloads file: " + e.getMessage());
			return "Failed to export file";
		}
	
		
	}
	public String montlyByChecksheetDownload(CheckSheetReportDTO request, HttpServletResponse response) {
		response.setContentType("text/csv");
		response.setCharacterEncoding("UTF-8"); // Set the character encoding
		response.setContentType("text/csv; charset=UTF-8");
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=ReportDailyChecksheet.csv";
		response.setHeader(headerKey, headerValue);
		List<Map<String, Object>> data = null;
					data = checkSheetReportService.monthlyChecksheetReport(request);
					 Map<String, String> columnHeaderMapping = new LinkedHashMap<>();
					    columnHeaderMapping.put("date", "Date");
					    columnHeaderMapping.put("1", "Column1Name");
					    columnHeaderMapping.put("2", "Column2Name");		   
					    columnHeaderMapping.put("3", "Column3Name");
					    columnHeaderMapping.put("4", "Column4Name");
					    columnHeaderMapping.put("5", "Column5Name");
					    columnHeaderMapping.put("subtotal", "Total");
		String[] csvHeader = null;
		csvHeader = new String[] { "Date",  "森田神経質","対人恐怖","パニック","うつ病","強迫性障害 ","小計" };
		try (CSVWriter csvWriter = new CSVWriter(response.getWriter())) {
			// Write the CSV header
			csvWriter.writeNext(csvHeader);
			if (data != null && !data.isEmpty()) {
	            for (Object rowDataObj : data) {
	                Map<String, Object> rowData = (Map<String, Object>) rowDataObj;
	                String[] csvRow = new String[csvHeader.length];
	                int columnIndex = 0;
	                for (String jsonKey : columnHeaderMapping.keySet()) {
	                    Object columnValue = rowData.get(jsonKey);
	                    csvRow[columnIndex] = (columnValue != null) ? columnValue.toString() : ""; // Convert to String
	                    columnIndex++;
	                }
	                csvWriter.writeNext(csvRow);
	            }
	        }
			return "CSV file created successfully.";
		} catch (IOException | java.io.IOException e) {
			System.out.println("Error while writing downloads file: " + e.getMessage());
			return "Failed to export file";
		}
	
	}

	public String telCounsellListDownload(TelCounsellingSearchDTO request, HttpServletResponse response) {

		response.setContentType("text/csv; charset=UTF-8");
		response.setHeader("Content-Disposition", "attachment; filename=telCounsellingDetails.csv");

		try {
			ApiListResponse<List<TelConselling>> apiListResponse = telCounsellingService.searchTelCounsell(request);

			if (apiListResponse == null || apiListResponse.getData() == null) {
				return "No data available for export.";
			}
			List<TelConselling> data = (List<TelConselling>) apiListResponse.getData();
			String[] csvHeader = { "受付日", "相談員", "名前", "性別", "年代", "職業", "主訴", "回数", "認知経路" };

			try (CSVWriter csvWriter = new CSVWriter(response.getWriter())) {
				csvWriter.writeNext(csvHeader);

				for (TelConselling ts : data) {
					String[] csvRow = new String[]{
							String.valueOf(ts.getDate()),
							ts.getCounsellor(),
							ts.getName(),
							ts.getGender(),
							ts.getAge(),
							ts.getJob(),
							ts.getMainIssues(),
							String.valueOf(ts.getSessions()),
							ts.getRoute()
					};
					csvWriter.writeNext(csvRow);
				}
			} catch (IOException e) {
				return "Failed to export file.";
			}
			return "CSV file created successfully.";
		} catch (Exception e) {
			return "Failed to export file.";
		}
	}
	/**
	 * Generates and downloads a CSV report containing gender report for a specified year and month.
	 * @param request The request object containing parameters for the report, including the year and month.
	 * @param response The HTTP response object used to write the CSV output to the client.
	 * @return A message indicating the success or failure of the CSV file creation.
	 */
	public String genderReportDownload(TelConsellingReportRequest request, HttpServletResponse response) {
		response.setContentType("text/csv");
		response.setCharacterEncoding("UTF-8"); // Set the character encoding
		response.setContentType("text/csv; charset=UTF-8");
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=Report.csv";
		response.setHeader(headerKey, headerValue);
		int year = request.getYear();
		Integer month = request.getMonth();
		List<GenderReportDTO> data = telCounsellingService.genderReport(year, month);
		String[] csvHeader = new String[] { "date", "本人","家族他","男性","女性","不詳","合計" };
		try (CSVWriter csvWriter = new CSVWriter(response.getWriter())) {
			csvWriter.writeNext(csvHeader);

			if (data != null && !data.isEmpty()) {
				for (Object details : data) {
					GenderReportDTO genderReport = (GenderReportDTO) details;
					String[] csvRow = new String[] {
							String.valueOf(genderReport.getDt()),
							String.valueOf(genderReport.getHimself()), String.valueOf(genderReport.getOthers()),
							String.valueOf(genderReport.getMale()), String.valueOf(genderReport.getFemale()),
							String.valueOf(genderReport.getUnknown()), String.valueOf(genderReport.getSubtotal())};
					csvWriter.writeNext(csvRow);
				}
			}
			return "CSV file created successfully.";
		} catch (IOException | java.io.IOException e) {
			System.out.println("Error while writing downloads file: " + e.getMessage());
			return "Failed to export file";
		}
	}

	/**
	 * Generates and downloads a CSV report containing age report for a specified year and month.
	 * @param request The request object containing parameters for the report, including the year and month.
	 * @param response The HTTP response object used to write the CSV output to the client.
	 * @return A message indicating the success or failure of the CSV file creation.
	 */
	public String ageReportDownload(TelConsellingReportRequest request, HttpServletResponse response) {
		response.setContentType("text/csv");
		response.setCharacterEncoding("UTF-8"); // Set the character encoding
		response.setContentType("text/csv; charset=UTF-8");
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=Report.csv";
		response.setHeader(headerKey, headerValue);
		int year = request.getYear();
		Integer month = request.getMonth();
		List<AgeReportDTO> data = telCounsellingService.ageReport(year, month);
		String[] csvHeader = new String[] { "date", "10代","20代","３０代","４０代","５０代","６０代","70代以上","不詳","合計" };
		try (CSVWriter csvWriter = new CSVWriter(response.getWriter())) {
			csvWriter.writeNext(csvHeader);

			if (data != null && !data.isEmpty()) {
				for (Object details : data) {
					AgeReportDTO ageReport = (AgeReportDTO) details;
					String[] csvRow = new String[] {
							String.valueOf(ageReport.getDt()),
							String.valueOf(ageReport.getGroup10()), String.valueOf(ageReport.getGroup20()),
							String.valueOf(ageReport.getGroup30()), String.valueOf(ageReport.getGroup40()),
							String.valueOf(ageReport.getGroup50()), String.valueOf(ageReport.getGroup60()),
							String.valueOf(ageReport.getGroup70()), String.valueOf(ageReport.getUnknown()),
							String.valueOf(ageReport.getSubtotal())};
					csvWriter.writeNext(csvRow);
				}
			}
			return "CSV file created successfully.";
		} catch (IOException | java.io.IOException e) {
			System.out.println("Error while writing downloads file: " + e.getMessage());
			return "Failed to export file";
		}
	}
	
	/**
	 * Generates and downloads a CSV report containing illness report for a specified year and month.
	 * @param request The request object containing parameters for the report, including the year and month.
	 * @param response The HTTP response object used to write the CSV output to the client.
	 * @return A message indicating the success or failure of the CSV file creation.
	 */
	public String illnessReportDownload(TelConsellingReportRequest request, HttpServletResponse response) {
		response.setContentType("text/csv");
		response.setCharacterEncoding("UTF-8"); // Set the character encoding
		response.setContentType("text/csv; charset=UTF-8");
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=Report.csv";
		response.setHeader(headerKey, headerValue);
		int year = request.getYear();
		Integer month = request.getMonth();
		List<IllnessReportResponse> data = telCounsellingService.illnessReport(year, month);
		String[] csvHeader = new String[] { "date", "普通","不安","パニック","強迫","社交対人","気分障害","他神経症","その他","合計" };
		try (CSVWriter csvWriter = new CSVWriter(response.getWriter())) {
			csvWriter.writeNext(csvHeader);

			if (data != null && !data.isEmpty()) {
				for (Object details : data) {
					IllnessReportResponse illnessReport = (IllnessReportResponse) details;
					String[] csvRow = new String[] {
							String.valueOf(illnessReport.getDt()),
							String.valueOf(illnessReport.getNormal()), String.valueOf(illnessReport.getAnxiety()),
							String.valueOf(illnessReport.getPanic()), String.valueOf(illnessReport.getObsessiveCompulsive()),
							String.valueOf(illnessReport.getSocialInterpersonal()), String.valueOf(illnessReport.getMoodDisorder()),
							String.valueOf(illnessReport.getOtherNeuroses()), String.valueOf(illnessReport.getOthers()),
							String.valueOf(illnessReport.getSubtotal())};
					csvWriter.writeNext(csvRow);
				}
			}
			return "CSV file created successfully.";
		} catch (IOException | java.io.IOException e) {
			System.out.println("Error while writing downloads file: " + e.getMessage());
			return "Failed to export file";
		}
	}
	
	/**
	 * Generates and downloads a CSV report containing counselling report for a specified year and month.
	 * @param request The request object containing parameters for the report, including the year and month.
	 * @param response The HTTP response object used to write the CSV output to the client.
	 * @return A message indicating the success or failure of the CSV file creation.
	 */
	public String counsellingReportDownload(TelConsellingReportRequest request, HttpServletResponse response) {
		response.setContentType("text/csv");
		response.setCharacterEncoding("UTF-8"); // Set the character encoding
		response.setContentType("text/csv; charset=UTF-8");
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=Report.csv";
		response.setHeader(headerKey, headerValue);
		int year = request.getYear();
		Integer month = request.getMonth();
		List<CounsellingReportResponse> data = telCounsellingService.counsellingReport(year, month);
		String[] csvHeader = new String[] { "date", "森田療法","医療機関","発見会","体験フォーラム","書籍動画","他治療法","その他","合計" };
		try (CSVWriter csvWriter = new CSVWriter(response.getWriter())) {
			csvWriter.writeNext(csvHeader);

			if (data != null && !data.isEmpty()) {
				for (Object details : data) {
					CounsellingReportResponse counsellingReprot = (CounsellingReportResponse) details;
					String[] csvRow = new String[] {
							String.valueOf(counsellingReprot.getDt()),
							String.valueOf(counsellingReprot.getMoritaTherapy()), String.valueOf(counsellingReprot.getMedicalInstitutions()),
							String.valueOf(counsellingReprot.getDiscoveryParty()), String.valueOf(counsellingReprot.getExperienceForum()),
							String.valueOf(counsellingReprot.getBooksVideos()), String.valueOf(counsellingReprot.getOtherTreatments()),
							String.valueOf(counsellingReprot.getOthers()), String.valueOf(counsellingReprot.getSubtotal())};
					csvWriter.writeNext(csvRow);
				}
			}
			return "CSV file created successfully.";
		} catch (IOException | java.io.IOException e) {
			System.out.println("Error while writing downloads file: " + e.getMessage());
			return "Failed to export file";
		}
	}

}
