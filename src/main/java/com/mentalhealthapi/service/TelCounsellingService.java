package com.mentalhealthapi.service;

import com.mentalhealthapi.dto.AgeReportDTO;
import com.mentalhealthapi.dto.CounsellingReportResponse;
import com.mentalhealthapi.dto.GenderReportDTO;
import com.mentalhealthapi.dto.IllnessReportResponse;
import com.mentalhealthapi.dto.TelCounsellingDTO;
import com.mentalhealthapi.dto.TelCounsellingSearchDTO;
import com.mentalhealthapi.helper.ApiListResponse;
import com.mentalhealthapi.helper.ApiResponse;
import com.mentalhealthapi.model.RouteToCounselling;
import com.mentalhealthapi.model.TelConselling;
import com.mentalhealthapi.model.TelMainIssue;
import com.mentalhealthapi.repository.RouteToCounsellingRepository;
import com.mentalhealthapi.repository.TelCounsellingRepository;
import com.mentalhealthapi.repository.TelMainIssueRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

@Service
public class TelCounsellingService {
    private final TelCounsellingRepository telCounsellingRepository;
    private final RouteToCounsellingRepository routeToCounsellingRepository;
    private final TelMainIssueRepository telMainIssueRepository;
    @PersistenceContext
    private  EntityManager entityManager;

    @Autowired
    public TelCounsellingService(TelCounsellingRepository telCounsellingRepository, RouteToCounsellingRepository routeToCounsellingRepository, TelMainIssueRepository telMainIssueRepository, EntityManager entityManager) {
        this.telCounsellingRepository = telCounsellingRepository;
        this.routeToCounsellingRepository = routeToCounsellingRepository;
        this.telMainIssueRepository = telMainIssueRepository;
    }

    /**
     * Saves new TelCounselling information to the database.
     * @param request The DTO containing the details to be saved.
     * @return ResponseEntity containing ApiResponse with success or failure message.
     */
    public ResponseEntity<ApiResponse> saveTelCounsellingInfo(TelCounsellingDTO request) {
        try {
            TelConselling telConselling = new TelConselling();
            telConselling.setDate(request.getDate());
            telConselling.setSessions(request.getSessions());
            telConselling.setRoute(request.getRoute());
            telConselling.setCounsellor(request.getCounsellor());
            telConselling.setGender(request.getGender());
            telConselling.setAge(request.getAge());
            telConselling.setJob(request.getJob());
            telConselling.setName(request.getName());
            telConselling.setProblemDetails(request.getProblemDetails());
            telConselling.setAdviceDetails(request.getAdviceDetails());
            telConselling.setMainIssues(request.getMainIssues());
            telConselling.setAdvice(request.getAdvice());
            telConselling.setUserType(request.getUserType());
            telConselling.setStatus(1);
            String[] mainIssues = request.getMainIssues().split(",");
            if (mainIssues.length > 0) {
                for (int i = 0; i < mainIssues.length; i++) {
                    String issue = mainIssues[i].trim();
                    switch (issue) {
                        case "普通神経症":
                            telConselling.setIllness1("普通神経症");
                            break;
                        case "不安神経症":
                            telConselling.setIllness2("不安神経症");
                            break;
                        case "パニック症":
                            telConselling.setIllness3("パニック症");
                            break;
                        case "強迫観念・強迫症":
                            telConselling.setIllness4("強迫観念・強迫症");
                            break;
                        case "対人・社交不安症":
                            telConselling.setIllness5("対人・社交不安症");
                            break;
                        case "気分障害":
                            telConselling.setIllness6("気分障害");
                            break;
                        case "その他神経症":
                            telConselling.setIllness7("その他神経症");
                            break;
                        case "その他":
                            telConselling.setIllness8("その他");
                            break;
                        default:
                            break;
                    }
                }
            }
            String[] advices = request.getAdvice().split(",");
            if (advices.length > 0) {
                for (int i = 0; i < advices.length; i++) {
                    String advice = advices[i].trim();
                    switch (advice) {
                        case "森田療法":
                            telConselling.setAdvice1("森田療法");
                            break;
                        case "医療機関":
                            telConselling.setAdvice2("医療機関");
                            break;
                        case "発見会":
                            telConselling.setAdvice3("発見会");
                            break;
                        case "体験フォーラム":
                            telConselling.setAdvice4("体験フォーラム");
                            break;
                        case "書籍動画":
                            telConselling.setAdvice5("書籍動画");
                            break;
                        case "他治療法":
                            telConselling.setAdvice6("他治療法");
                            break;
                        case "その他":
                            telConselling.setAdvice7("その他");
                            break;
                        default:
                            break;
                    }
                }
            }

            TelConselling savedEntity = telCounsellingRepository.save(telConselling);
            Integer id = savedEntity.getId();
            Map<String, Integer> responseData = new HashMap<>();
            responseData.put("id", id);

            ApiResponse<Object> response = new ApiResponse<>(1, null,responseData);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            ApiResponse<Object> response = new ApiResponse<>(0, e.getMessage(), null);
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            ApiResponse<Object> response = new ApiResponse<>(0, "Failed to save", null);
            return ResponseEntity.internalServerError().body(response);
        }
    }

    /**
     * Edits existing TelCounselling information based on the provided ID.
     * @param id The ID of the TelCounselling entry to be edited.
     * @param request The DTO containing updated details.
     * @return ResponseEntity containing ApiResponse with success or failure message.
     */
    public ResponseEntity<ApiResponse> editTelCounsellingInfo(int id, TelCounsellingDTO request) {
        try {
            Optional<TelConselling> existingEntity = telCounsellingRepository.findById(id);
            if (existingEntity.isPresent()) {
                TelConselling telConselling = existingEntity.get();
                Optional.ofNullable(request.getDate()).ifPresent(telConselling::setDate);
                Optional.ofNullable(request.getSessions()).ifPresent(telConselling::setSessions);
                Optional.ofNullable(request.getRoute()).ifPresent(telConselling::setRoute);
                Optional.ofNullable(request.getCounsellor()).ifPresent(telConselling::setCounsellor);
                Optional.ofNullable(request.getGender()).ifPresent(telConselling::setGender);
                Optional.ofNullable(request.getAge()).ifPresent(telConselling::setAge);
                Optional.ofNullable(request.getJob()).ifPresent(telConselling::setJob);
                Optional.ofNullable(request.getName()).ifPresent(telConselling::setName);
                Optional.ofNullable(request.getProblemDetails()).ifPresent(telConselling::setProblemDetails);
                Optional.ofNullable(request.getAdviceDetails()).ifPresent(telConselling::setAdviceDetails);
                Optional.ofNullable(request.getMainIssues()).ifPresent(telConselling::setMainIssues);
                Optional.ofNullable(request.getAdvice()).ifPresent(telConselling::setAdvice);
                Optional.ofNullable(request.getUserType()).ifPresent(telConselling::setUserType);
                if (request.getMainIssues() != null) {
                    String[] mainIssues = request.getMainIssues().split(",");
                    // Clear all existing illness fields
                    telConselling.setIllness1(null);
                    telConselling.setIllness2(null);
                    telConselling.setIllness3(null);
                    telConselling.setIllness4(null);
                    telConselling.setIllness5(null);
                    telConselling.setIllness6(null);
                    telConselling.setIllness7(null);
                    telConselling.setIllness8(null);
                    for (String issue : mainIssues) {
                        issue = issue.trim();
                        switch (issue) {
                            case "普通神経症":
                                telConselling.setIllness1("普通神経症");
                                break;
                            case "不安神経症":
                                telConselling.setIllness2("不安神経症");
                                break;
                            case "パニック症":
                                telConselling.setIllness3("パニック症");
                                break;
                            case "強迫観念・強迫症":
                                telConselling.setIllness4("強迫観念・強迫症");
                                break;
                            case "対人・社交不安症":
                                telConselling.setIllness5("対人・社交不安症");
                                break;
                            case "気分障害":
                                telConselling.setIllness6("気分障害");
                                break;
                            case "その他神経症":
                                telConselling.setIllness7("その他神経症");
                                break;
                            case "その他":
                                telConselling.setIllness8("その他");
                                break;
                            default:
                                break;
                        }
                    }
                }

                if (request.getAdvice() != null) {
                    String[] advices = request.getAdvice().split(",");
                    // Clear all existing advice fields
                    telConselling.setAdvice1(null);
                    telConselling.setAdvice2(null);
                    telConselling.setAdvice3(null);
                    telConselling.setAdvice4(null);
                    telConselling.setAdvice5(null);
                    telConselling.setAdvice6(null);
                    telConselling.setAdvice7(null);
                    for (String advice : advices) {
                        advice = advice.trim();
                        switch (advice) {
                            case "森田療法":
                                telConselling.setAdvice1("森田療法");
                                break;
                            case "医療機関":
                                telConselling.setAdvice2("医療機関");
                                break;
                            case "発見会":
                                telConselling.setAdvice3("発見会");
                                break;
                            case "体験フォーラム":
                                telConselling.setAdvice4("体験フォーラム");
                                break;
                            case "書籍動画":
                                telConselling.setAdvice5("書籍動画");
                                break;
                            case "他治療法":
                                telConselling.setAdvice6("他治療法");
                                break;
                            case "その他":
                                telConselling.setAdvice7("その他");
                                break;
                            default:
                                break;
                        }
                    }
                }

                telCounsellingRepository.save(telConselling);

                ApiResponse<Object> response = new ApiResponse<>(1, null, "Updated");
                return ResponseEntity.ok(response);
            } else {
                ApiResponse<Object> response = new ApiResponse<>(0, "Entity not found", null);
                return ResponseEntity.badRequest().body(response);
            }
        } catch (IllegalArgumentException e) {
            ApiResponse<Object> response = new ApiResponse<>(0, e.getMessage(), null);
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            ApiResponse<Object> response = new ApiResponse<>(0, "Failed to update", null);
            return ResponseEntity.internalServerError().body(response);
        }
    }


    /**
     * Retrieves all TelCounselling records from the repository
     * @return ApiResponse containing a list of TelCounsellingDTOs or an error message.
     */
    public ResponseEntity<ApiListResponse<List<TelConselling>>> getAllTelCounsellingInfo(TelCounsellingSearchDTO request) {
        try {
            PageRequest pageRequest = PageRequest.of(request.getPageNumber() - 1, request.getPageSize());
            Page<TelConselling> telConsellingPage = telCounsellingRepository.findAllActive(pageRequest);
            long totalRows = telConsellingPage.getTotalElements();
            List<TelConselling> telConsellingList = telConsellingPage.getContent();
            ApiListResponse<List<TelConselling>> response = new ApiListResponse<>(1, null, telConsellingList,totalRows);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiListResponse<List<TelConselling>> response = new ApiListResponse<>(0, "Failed to retrieve data", null,0);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * Retrieves all route to counselling from repository
     * @return ApiResponse containing a list of TelCounsellingDTOs or an error message.
     */
    public ResponseEntity<ApiResponse<List<RouteToCounselling>>> getRouteToCounselling() {
        try {
            List<RouteToCounselling> routeToCounsellings = routeToCounsellingRepository.findAll();
            ApiResponse<List<RouteToCounselling>> response = new ApiResponse<>(1, null, routeToCounsellings);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse<List<RouteToCounselling>> response = new ApiResponse<>(0, "Failed to retrieve data", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


    /**
     * Retrieves all TelCounselling Main issues from the repository
     * @return ApiResponse containing a list of TelCounsellingDTOs or an error message.
     */
    public ResponseEntity<ApiResponse<List<TelMainIssue>>> getTelMainIssues() {
        try {
            List<TelMainIssue> telMainIssues = telMainIssueRepository.findAll();
            ApiResponse<List<TelMainIssue>> response = new ApiResponse<>(1, null, telMainIssues);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse<List<TelMainIssue>> response = new ApiResponse<>(0, "Failed to retrieve data", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    //gender Report 
    public List<GenderReportDTO> genderReport(int year, Integer month) {
        List<GenderReportDTO> formattedData = new ArrayList<>();

        LocalDate startDate;
        LocalDate endDate;

        // Adjust the date range based on whether a specific month is provided
        if (month != null) {
            startDate = LocalDate.of(year, month, 1);
            endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());
            // Fetch daily report if month is specified
            List<Object[]> result = telCounsellingRepository.customDailyReportQuery(startDate, endDate);
            processReport(result, formattedData, "yyyy-MM-dd", startDate, endDate, true);
        } else {
            startDate = LocalDate.of(year, 1, 1);
            endDate = LocalDate.of(year, 12, 31);
            // Fetch monthly report if only year is provided
            List<Object[]> result = telCounsellingRepository.customMonthlyReportQuery(startDate, endDate);
            processReport(result, formattedData, "yyyy-MM", startDate, endDate, false);
        }

        return formattedData;
    }

    private void processReport(List<Object[]> result, List<GenderReportDTO> formattedData, String dateFormat, LocalDate startDate, LocalDate endDate, boolean isDaily) {
        Map<String, int[]> reportData = new HashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);

        for (Object[] row : result) {
            String dateKey;

            if (row[0] instanceof Long) {
                Long timestamp = (Long) row[0];
                dateKey = sdf.format(new Date(timestamp));
            } else if (row[0] instanceof java.sql.Date) {
                java.sql.Date sqlDate = (java.sql.Date) row[0];
                dateKey = sdf.format(sqlDate);
            } else if (row[0] instanceof String) {
                dateKey = (String) row[0];
            } else {
                throw new IllegalArgumentException("Unsupported date key type: " + row[0].getClass().getName());
            }

            String genderTypeString = (String) row[1];
            String userTypeString = (String) row[2];
            Long countLong = (Long) row[3];
            Integer count = countLong.intValue();

            int male = genderTypeString != null && genderTypeString.equals("男性") ? count : 0;
            int female = genderTypeString != null && genderTypeString.equals("女性") ? count : 0;
            int unknown = (genderTypeString == null || genderTypeString.trim().isEmpty()) ? count : 0;

            int himself = userTypeString.equals("彼自身") ? count : 0;
            int others = !userTypeString.equals("彼自身") ? count : 0;

            reportData.putIfAbsent(dateKey, new int[6]);
            int[] counts = reportData.get(dateKey);
            counts[0] += himself;
            counts[1] += others;
            counts[2] += male;
            counts[3] += female;
            counts[4] += unknown;
            counts[5] += count;
        }

        // Fill in the missing months with zeros for monthly report
        if (!isDaily) {
            LocalDate currentMonth = startDate.withDayOfMonth(1);
            while (!currentMonth.isAfter(endDate)) {
                String dateKey = sdf.format(java.sql.Date.valueOf(currentMonth));

                if (!reportData.containsKey(dateKey)) {
                    reportData.put(dateKey, new int[6]); // Initialize with zeros
                }

                int[] counts = reportData.get(dateKey);
                GenderReportDTO dto = new GenderReportDTO(dateKey,
                        counts[0],
                        counts[1],
                        counts[2],
                        counts[3],
                        counts[4],
                        counts[5]
                );
                formattedData.add(dto);

                currentMonth = currentMonth.plusMonths(1); // Increment month
            }
        } else {
            // Fill in the missing days with zeros for daily report
            LocalDate currentDate = startDate;
            while (!currentDate.isAfter(endDate)) {
                String dateKey = sdf.format(java.sql.Date.valueOf(currentDate));

                if (!reportData.containsKey(dateKey)) {
                    reportData.put(dateKey, new int[6]); 
                }

                int[] counts = reportData.get(dateKey);
                GenderReportDTO dto = new GenderReportDTO(dateKey,
                        counts[0],
                        counts[1],
                        counts[2],
                        counts[3],
                        counts[4],
                        counts[5]
                );
                formattedData.add(dto);

                currentDate = currentDate.plusDays(1); 
            }
        }
    }


    // Age Report Service
    public List<AgeReportDTO> ageReport(int year, Integer month) {
        List<AgeReportDTO> formattedData = new ArrayList<>();
        LocalDate startDate;
        LocalDate endDate;

        // Determine the date range based on whether a specific month is provided
        if (month != null) {
            startDate = LocalDate.of(year, month, 1);
            endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());
            List<Object[]> result = telCounsellingRepository.customDailyAgeReportQuery(startDate, endDate);
            processAgeReport(result, formattedData, "yyyy-MM-dd", startDate, endDate, true);
        } else {
            startDate = LocalDate.of(year, 1, 1);
            endDate = LocalDate.of(year, 12, 31);
            List<Object[]> result = telCounsellingRepository.customMonthlyAgeReportQuery(startDate, endDate);
            processAgeReport(result, formattedData, "yyyy-MM", startDate, endDate, false);
        }

        return formattedData;
    }

    private void processAgeReport(List<Object[]> result, List<AgeReportDTO> formattedData, String dateFormat, LocalDate startDate, LocalDate endDate, boolean isDaily) {
        Map<String, int[]> reportData = new HashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);

        for (Object[] row : result) {
            String dateKey;

            if (row[0] instanceof Long) {
                Long timestamp = (Long) row[0];
                dateKey = sdf.format(new Date(timestamp));
            } else if (row[0] instanceof java.sql.Date) {
                java.sql.Date sqlDate = (java.sql.Date) row[0];
                dateKey = sdf.format(sqlDate);
            } else if (row[0] instanceof String) {
                dateKey = (String) row[0];
            } else {
                throw new IllegalArgumentException("Unsupported date key type: " + row[0].getClass().getName());
            }

            // Determine the age value
            Integer age = null;
            if (row[1] instanceof String) {
                String ageStr = (String) row[1];
                age = (ageStr == null || ageStr.trim().isEmpty()) ? null : Integer.parseInt(ageStr);
            } else if (row[1] instanceof Integer) {
                age = (Integer) row[1];
            }

            Long countLong = (Long) row[2];
            Integer count = countLong.intValue();

            // Determine the age group, treating null as 80 for "unknown"
            Integer ageGroup = (age == null) ? 80 : getAgeGroup(age);

            // Initialize the counts array if not already present
            reportData.putIfAbsent(dateKey, new int[9]);
            int[] counts = reportData.get(dateKey);

            // Increment the appropriate age group count
            if (ageGroup >= 10 && ageGroup <= 80) {
                int index = (ageGroup / 10) - 1;
                counts[index] += count;
            }
            counts[8] += count; // Total count
        }

        // Fill in the missing months or days with zeros
        if (!isDaily) {
            LocalDate currentMonth = startDate.withDayOfMonth(1);
            while (!currentMonth.isAfter(endDate)) {
                String dateKey = sdf.format(java.sql.Date.valueOf(currentMonth));

                if (!reportData.containsKey(dateKey)) {
                    reportData.put(dateKey, new int[9]); 
                }

                int[] counts = reportData.get(dateKey);
                AgeReportDTO dto = new AgeReportDTO(dateKey,
                        counts[0],
                        counts[1],
                        counts[2],
                        counts[3],
                        counts[4],
                        counts[5],
                        counts[6],
                        counts[7],
                        counts[8]
                );
                formattedData.add(dto);

                currentMonth = currentMonth.plusMonths(1); 
            }
        } else {
            LocalDate currentDate = startDate;
            while (!currentDate.isAfter(endDate)) {
                String dateKey = sdf.format(java.sql.Date.valueOf(currentDate));

                if (!reportData.containsKey(dateKey)) {
                    reportData.put(dateKey, new int[9]); 
                }

                int[] counts = reportData.get(dateKey);
                AgeReportDTO dto = new AgeReportDTO(dateKey,
                        counts[0],
                        counts[1],
                        counts[2],
                        counts[3],
                        counts[4],
                        counts[5],
                        counts[6],
                        counts[7],
                        counts[8]
                );
                formattedData.add(dto);

                currentDate = currentDate.plusDays(1); 
            }
        }
    }
    private Integer getAgeGroup(Integer age) {
        // Treat age 0 as "unknown"
        if (age == null || age == 0) {
            return 80; // 80 will represent "unknown"
        }

        // Map age to age groups
        if (age >= 1 && age <= 10) {
            return 10; 
        } else if (age >= 11 && age <= 20) {
            return 20; 
        } else if (age >= 21 && age <= 30) {
            return 30; 
        } else if (age >= 31 && age <= 40) {
            return 40; 
        } else if (age >= 41 && age <= 50) {
            return 50; 
        } else if (age >= 51 && age <= 60) {
            return 60; 
        } else {
            return 70; //more then 70
        }
    }



    //Mental illness report
    public List<IllnessReportResponse> illnessReport(int year, Integer month) {
        List<IllnessReportResponse> formattedData = new ArrayList<>();

        LocalDate startDate;
        LocalDate endDate;

        // Determine the date range based on whether a specific month is provided
        if (month != null) {
            startDate = LocalDate.of(year, month, 1);
            endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());
            // Fetch daily report if month is specified
            List<Object[]> result = telCounsellingRepository.customIllnessDailyReportQuery(startDate, endDate);
            processIllnessReport(result, formattedData, "yyyy-MM-dd", startDate, endDate, true);
        } else {
            startDate = LocalDate.of(year, 1, 1);
            endDate = LocalDate.of(year, 12, 31);
            // Fetch monthly report if only year is provided
            List<Object[]> result = telCounsellingRepository.customIllnessMonthlyReportQuery(startDate, endDate);
            processIllnessReport(result, formattedData, "yyyy-MM", startDate, endDate, false);
        }

        return formattedData;
    }

    private void processIllnessReport(List<Object[]> result, List<IllnessReportResponse> formattedData, String dateFormat, LocalDate startDate, LocalDate endDate, boolean isDaily) {
        Map<String, int[]> illnessCountByDate = new HashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);

        for (Object[] row : result) {
            String dateKey;

            // Determine the date key based on its type
            if (row[0] instanceof Long) {
                Long timestamp = (Long) row[0];
                dateKey = sdf.format(new Date(timestamp));
            } else if (row[0] instanceof java.sql.Date) {
                java.sql.Date sqlDate = (java.sql.Date) row[0];
                dateKey = sdf.format(sqlDate);
            } else if (row[0] instanceof String) {
                dateKey = (String) row[0];
            } else {
                throw new IllegalArgumentException("Unsupported date key type: " + row[0].getClass().getName());
            }

            // Initialize illness counts for each date
            int[] illnessCounts = illnessCountByDate.getOrDefault(dateKey, new int[8]);

            // Count occurrences of each illness
            for (int i = 1; i <= 8; i++) {
                String illness = (String) row[i];
                if (illness != null && !illness.trim().isEmpty()) {
                    illnessCounts[i - 1]++;
                }
            }

            illnessCountByDate.put(dateKey, illnessCounts);
        }

        // Fill in the missing dates or months with zeros
        if (!isDaily) {
            LocalDate currentMonth = startDate.withDayOfMonth(1);
            while (!currentMonth.isAfter(endDate)) {
                String dateKey = sdf.format(java.sql.Date.valueOf(currentMonth));

                if (!illnessCountByDate.containsKey(dateKey)) {
                    illnessCountByDate.put(dateKey, new int[8]); 
                }

                int[] counts = illnessCountByDate.get(dateKey);
                int subtotal = Arrays.stream(counts).sum(); 

                IllnessReportResponse response = new IllnessReportResponse(
                    dateKey,
                    counts[0],
                    counts[1],
                    counts[2],
                    counts[3],
                    counts[4],
                    counts[5],
                    counts[6],
                    counts[7],
                    subtotal
                );

                formattedData.add(response);
                currentMonth = currentMonth.plusMonths(1); 
            }
        } else {
            LocalDate currentDate = startDate;
            while (!currentDate.isAfter(endDate)) {
                String dateKey = sdf.format(java.sql.Date.valueOf(currentDate));

                if (!illnessCountByDate.containsKey(dateKey)) {
                    illnessCountByDate.put(dateKey, new int[8]); 
                }

                int[] counts = illnessCountByDate.get(dateKey);
                int subtotal = Arrays.stream(counts).sum(); 

                IllnessReportResponse response = new IllnessReportResponse(
                    dateKey,
                    counts[0],
                    counts[1],
                    counts[2],
                    counts[3],
                    counts[4],
                    counts[5],
                    counts[6],
                    counts[7],
                    subtotal
                );

                formattedData.add(response);
                currentDate = currentDate.plusDays(1); 
            }
        }
    }

    /**
     * search Telcounselling details
     * @return ResponseEntity containing ApiListResponse with success or failure message.
     */
    @Transactional(readOnly = true)
    public ApiListResponse<List<TelConselling>> searchTelCounsell(TelCounsellingSearchDTO request) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<TelConselling> criteriaQuery = criteriaBuilder.createQuery(TelConselling.class);
        Root<TelConselling> root = criteriaQuery.from(TelConselling.class);

        Predicate predicate = buildPredicate(request, criteriaBuilder, root);
        criteriaQuery.where(predicate);
        criteriaQuery.orderBy(criteriaBuilder.desc(root.get("date")), criteriaBuilder.asc(root.get("id")));

        List<TelConselling> result = entityManager.createQuery(criteriaQuery)
                .getResultList();

        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<TelConselling> countRoot = countQuery.from(TelConselling.class);
        Predicate countPredicate = buildPredicate(request, criteriaBuilder, countRoot);
        countQuery.select(criteriaBuilder.count(countRoot));
        countQuery.where(countPredicate);

        Long count = entityManager.createQuery(countQuery).getSingleResult();
        return new ApiListResponse<>(1, null, result, count);
    }

    private Predicate buildPredicate(TelCounsellingSearchDTO request, CriteriaBuilder criteriaBuilder, Root<TelConselling> root) {
        Predicate predicate = criteriaBuilder.conjunction();
        predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("status"), 1));

        if (request.getCounsellor() != null) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("counsellor"), request.getCounsellor()));
        }
        if (request.getGender() != null) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("gender"), request.getGender()));
        }
        if (request.getAges() != null && !request.getAges().isEmpty()) {
            predicate = criteriaBuilder.and(predicate, root.get("age").in(request.getAges()));
        }
        if (request.getName() != null) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("name"), "%" + request.getName() + "%"));
        }

        if (request.getMainIssues() != null && !request.getMainIssues().isEmpty()) {
            List<Predicate> issuePredicates = new ArrayList<>();

            for (String issue : request.getMainIssues()) {
                issue = issue.trim();
                Predicate issuePredicate = criteriaBuilder.or(
                        criteriaBuilder.like(root.get("illness1"), issue),
                        criteriaBuilder.like(root.get("illness2"), issue ),
                        criteriaBuilder.like(root.get("illness3"), issue ),
                        criteriaBuilder.like(root.get("illness4"), issue),
                        criteriaBuilder.like(root.get("illness5"), issue),
                        criteriaBuilder.like(root.get("illness6"), issue),
                        criteriaBuilder.like(root.get("illness7"), issue),
                        criteriaBuilder.like(root.get("illness8"), issue)
                );
                issuePredicates.add(issuePredicate);
            }
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.and(issuePredicates.toArray(new Predicate[0])));
        }

        if (request.getUserType() != null) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("userType"), request.getUserType()));
        }
        if (request.getFromDate() != null || request.getToDate() != null) {
            Predicate datePredicate = criteriaBuilder.conjunction();
            if (request.getFromDate() != null) {
                datePredicate = criteriaBuilder.and(datePredicate, criteriaBuilder.greaterThanOrEqualTo(root.get("date"), request.getFromDate()));
            }
            if (request.getToDate() != null) {
                datePredicate = criteriaBuilder.and(datePredicate, criteriaBuilder.lessThanOrEqualTo(root.get("date"), request.getToDate()));
            }
            predicate = criteriaBuilder.and(predicate, datePredicate);
        }
        return predicate;
    }

    /**
     * Retrieves a TelConselling entity by its ID.
     * @param id the ID of the TelConselling entity to retrieve
     * @return ApiResponse containing the requested TelConselling data or an error message
     */
    public ApiResponse<TelConselling> telCounsellViewById(int id) {
        try {
            Optional<TelConselling> telConselling = telCounsellingRepository.findById(id);
            if (telConselling.isPresent()) {
                return new ApiResponse<>(1, null, telConselling.get());
            } else {
                return new ApiResponse<>(0, "Entity not found", null);
            }
        } catch (Exception e) {
            return new ApiResponse<>(0, "Failed to retrieve data", null);
        }
    }

    /**
     * Soft deletes a TelConselling entity by updating its status to 0.
     * @param id the ID of the TelConselling entity to delete
     * @return ApiResponse indicating success or failure of the delete operation
     */
    public ApiResponse deleteTelCounsell(Integer id) {
        try {
            Optional<TelConselling> existingEntity = telCounsellingRepository.findById(id);
            if (existingEntity.isPresent()) {
                TelConselling telConselling = existingEntity.get();
                telConselling.setStatus(0);
                telCounsellingRepository.save(telConselling);
                return new ApiResponse<>(1, null, "Deleted successfully");
            } else {
                return new ApiResponse<>(0, "Entity not found", null);
            }
        } catch (Exception e) {
            return new ApiResponse<>(0, "Failed to delete", null);
        }
    }
    
 // Counselling Report
    public List<CounsellingReportResponse> counsellingReport(int year, Integer month) {
        List<CounsellingReportResponse> formattedData = new ArrayList<>();

        LocalDate startDate;
        LocalDate endDate;

        // Determine the date range based on whether a specific month is provided
        if (month != null) {
            startDate = LocalDate.of(year, month, 1);
            endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());
            // Fetch daily report if month is specified
            List<Object[]> result = telCounsellingRepository.counsellingDailyReportQuery(startDate, endDate);
            processCounsellingReport(result, formattedData, "yyyy-MM-dd", startDate, endDate, true);
        } else {
            startDate = LocalDate.of(year, 1, 1);
            endDate = LocalDate.of(year, 12, 31);
            // Fetch monthly report if only year is provided
            List<Object[]> result = telCounsellingRepository.counsellingMonthlyReportQuery(startDate, endDate);
            processCounsellingReport(result, formattedData, "yyyy-MM", startDate, endDate, false);
        }

        return formattedData;
    }

    private void processCounsellingReport(List<Object[]> result, List<CounsellingReportResponse> formattedData, String dateFormat, LocalDate startDate, LocalDate endDate, boolean isDaily) {
        Map<String, int[]> counsellingCountByDate = new HashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);

        for (Object[] row : result) {
            String dateKey;

            // Determine the date key based on its type
            if (row[0] instanceof Long) {
                Long timestamp = (Long) row[0];
                dateKey = sdf.format(new Date(timestamp));
            } else if (row[0] instanceof java.sql.Date) {
                java.sql.Date sqlDate = (java.sql.Date) row[0];
                dateKey = sdf.format(sqlDate);
            } else if (row[0] instanceof String) {
                dateKey = (String) row[0];
            } else {
                throw new IllegalArgumentException("Unsupported date key type: " + row[0].getClass().getName());
            }

            // Initialize counselling counts for each date
            int[] counsellingCounts = counsellingCountByDate.getOrDefault(dateKey, new int[7]);

            // Count occurrences of each counselling advice (only 7 fields)
            for (int i = 1; i <= 7; i++) {
                String advice = (String) row[i];
                if (advice != null && !advice.trim().isEmpty()) {
                    counsellingCounts[i - 1]++;
                }
            }

            counsellingCountByDate.put(dateKey, counsellingCounts);
        }

        // Fill in the missing dates or months with zeros
        if (!isDaily) {
            LocalDate currentMonth = startDate.withDayOfMonth(1);
            while (!currentMonth.isAfter(endDate)) {
                String dateKey = sdf.format(java.sql.Date.valueOf(currentMonth));

                if (!counsellingCountByDate.containsKey(dateKey)) {
                    counsellingCountByDate.put(dateKey, new int[7]); 
                }

                int[] counts = counsellingCountByDate.get(dateKey);
                int subtotal = Arrays.stream(counts).sum(); 

                CounsellingReportResponse response = new CounsellingReportResponse(
                    dateKey,
                    counts[0],
                    counts[1],
                    counts[2],
                    counts[3],
                    counts[4],
                    counts[5],
                    counts[6],
                    subtotal
                );

                formattedData.add(response);
                currentMonth = currentMonth.plusMonths(1); 
            }
        } else {
            LocalDate currentDate = startDate;
            while (!currentDate.isAfter(endDate)) {
                String dateKey = sdf.format(java.sql.Date.valueOf(currentDate));

                if (!counsellingCountByDate.containsKey(dateKey)) {
                    counsellingCountByDate.put(dateKey, new int[7]); 
                }

                int[] counts = counsellingCountByDate.get(dateKey);
                int subtotal = Arrays.stream(counts).sum(); 

                CounsellingReportResponse response = new CounsellingReportResponse(
                    dateKey,
                    counts[0],
                    counts[1],
                    counts[2],
                    counts[3],
                    counts[4],
                    counts[5],
                    counts[6],
                    subtotal
                );

                formattedData.add(response);
                currentDate = currentDate.plusDays(1); 
            }
        }
    }


}
