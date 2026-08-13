package com.mentalhealthapi.service;
import java.text.SimpleDateFormat;
import java.util.List;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Service;

import com.mentalhealthapi.dto.CheckSheetReportDTO;
import com.mentalhealthapi.repository.DiagnoseCheckRepository;
@Service
public class CheckSheetReportService {
	private final DiagnoseCheckRepository diagnoseCheckRepository;
	
	public CheckSheetReportService(DiagnoseCheckRepository diagnoseCheckRepository ) {
		this.diagnoseCheckRepository = diagnoseCheckRepository;
	}

	public List<Object> dailyReport(CheckSheetReportDTO request) {
		int year = request.getYear();
        int month = request.getMonth();

        String yearStr = Integer.toString(year);
        String monthStr = String.format("%02d", month);
        
        String start = yearStr + "-" + monthStr + "-01";
        String end = java.time.YearMonth.of(year, month).atEndOfMonth().toString();

        List<Object[]> resultList = diagnoseCheckRepository.getDiagnoseCheckCounts(start, end);

        Map<String, Map<Integer, Integer>> out = new HashMap<>();
        for (Object[] row : resultList) {
            int checkSheetType = row[2] instanceof Byte ? ((Byte) row[2]).intValue() : (int) row[2];
            String date = row[1].toString();
            int count = ((Number) row[0]).intValue();

            if (checkSheetType > 0 && !out.containsKey(date)) {
                out.put(date, new HashMap<>());
            }

            if (checkSheetType > 0) {
                out.get(date).put(checkSheetType, count);
            }
        }

        int daysInMonth = java.time.YearMonth.of(year, month).lengthOfMonth();
        List<Map<String, Object>> formattedData = new ArrayList<>();
        int total = 0;
        int A_total = 0;
        int B_total = 0;
        int C_total = 0;
        int D_total = 0;
        int E_total = 0;

        LocalDate startDate = LocalDate.parse(start);
        LocalDate endDate = LocalDate.parse(end);

        while (!startDate.isAfter(endDate)) {
            Map<String, Object> data = new HashMap<>();
            String dt = startDate.toString();
            String formattedDt = startDate.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
            data.put("dt", formattedDt);
            data.put("subtotal", 0);

            // Calculate and populate data for check sheet types 1 to 5
            for (int k = 1; k <= 5; k++) {
                if (out.containsKey(dt) && out.get(dt).containsKey(k)) {
                    int count = out.get(dt).get(k);
                    data.put(String.valueOf(k), count);
                    data.put("subtotal", (int) data.get("subtotal") + count);

                    switch (k) {
                        case 1:
                            A_total += count;
                            break;
                        case 2:
                            B_total += count;
                            break;
                        case 3:
                            C_total += count;
                            break;
                        case 4:
                            D_total += count;
                            break;
                        case 5:
                            E_total += count;
                            break;
                    }
                } else {
                    data.put(String.valueOf(k), 0);
                }
            }

            formattedData.add(data);
            total += (int) data.get("subtotal");

            startDate = startDate.plusDays(1); // Move to the next day
        }

        
        return convertToObjectsList(formattedData);
    }
	
	public List<Object> convertToObjectsList(List<Map<String, Object>> mapList) {
	    List<Object> objectsList = new ArrayList<>();
	    objectsList.addAll(mapList);
	    return objectsList;
	}

	
	

	    public List<Map<String, Object>> generateMonthlyReport(int year) {
	        List<Map<String, Object>> formattedData = new ArrayList<>();
	       
	        LocalDate startDate = LocalDate.of(year, 1, 1);
	        LocalDate endDate = LocalDate.of(year, 12, 31);

	        // call query result
	        List<Object[]> result = diagnoseCheckRepository.customMonthlyReportQuery(startDate, endDate);

	 
	        Map<String, Map<Integer, Integer>> out = new HashMap<>();
	        for (Object[] row : result) {

	            String term = (String) row[0];
	            Integer checkSheetType = ((Number) row[1]).intValue();
	            Long countLong = (Long) row[2];
	            Integer count = countLong.intValue();
	            if (checkSheetType <= 0) continue;

	            if (!out.containsKey(term)) {
	                out.put(term, new HashMap<>());
	            }
	            out.get(term).put(checkSheetType, count);
	        }

	     
	      
	        for (int month = 1; month <= 12; month++) {
	            String dt = String.format("%d/%02d", year, month);
	            Map<Integer, Integer> data = out.get(dt);
	            if (data == null) {
	                data = new HashMap<>();
	            }

	            Map<String, Object> dataEntry = new HashMap<>();
	            dataEntry.put("date", dt.substring(2, 4) + "" + dt.substring(4));
	            dataEntry.put("subtotal", 0);

	            for (int k = 1; k <= 5; k++) {
	            	
	                int value = data.getOrDefault(k, 0);
	                dataEntry.put(String.valueOf(k), value);
	                dataEntry.put("subtotal", (int) dataEntry.get("subtotal") + value);
	            }


	            formattedData.add(dataEntry);
	        }

	   
	        return formattedData;
	    }
	

	    public List<Object> dailyChecksheetReport(CheckSheetReportDTO request) {
	        int year = request.getYear();
	        int month = request.getMonth();
	        String checksheetType = request.getCheckType();

	        String yearStr = Integer.toString(year);
	        String monthStr = String.format("%02d", month);

	        String start = yearStr + "-" + monthStr + "-01";
	        String end = java.time.YearMonth.of(year, month).atEndOfMonth().toString();
	        List<Object[]> list;

	        if (checksheetType != null && !checksheetType.isEmpty()) {
	            list = diagnoseCheckRepository.customQueryWithChecksheetType(start, end, checksheetType);
	        } else {
	            list = diagnoseCheckRepository.customQueryWithoutChecksheetType(start, end);
	        }

	        // Process the list of results as needed for your report
	        Map<String, Map<String, Integer>> out = new HashMap<>();
	        int t = java.time.YearMonth.of(year, month).lengthOfMonth();
	        List<Map<String, Object>> formattedData = new ArrayList<>();
	        int total = 0, A_total = 0, B_total = 0, C_total = 0, D_total = 0;

	        // Populate the 'out' map with data from the database query
	        for (Object[] result : list) {
	            int count = ((Number) result[0]).intValue(); // Convert to int
	            LocalDate date = ((Date) result[1]).toLocalDate();
	            String dt = date.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
	            String checkSheetType = result[2].toString();

	            // Check if the date exists in the map, create if not
	            out.putIfAbsent(dt, new HashMap<>());

	            // Add the count for the corresponding check sheet type and date
	            out.get(dt).put(checkSheetType, count);
	        }

	        // Continue with the rest of your existing code to process the 'out' map
	        for (int i = 0; i < t; i++) {
	            LocalDate date = java.time.LocalDate.parse(start).plusDays(i);
	            String dt = date.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));

	            Map<String, Object> data = new HashMap<>();
	            data.put("dt", dt);
	            int dailyTotal = 0;

	            for (int k = 1; k <= 4; k++) {
	                String checkSheetType = Integer.toString(k);
	                if (out.containsKey(dt) && out.get(dt).containsKey(checkSheetType)) {
	                    int count = out.get(dt).get(checkSheetType);
	                    data.put(checkSheetType, count);
	                    dailyTotal += count;
	                } else {
	                    data.put(checkSheetType, 0);
	                }
	            }

	            data.put("subtotal", dailyTotal);
	            formattedData.add(data);
	            total += dailyTotal;
	            A_total += (int) data.get("1");
	            B_total += (int) data.get("2");
	            C_total += (int) data.get("3");
	            D_total += (int) data.get("4");
	        }

	        return convertToObjectsList(formattedData);
	    }

	public List<Map<String, Object>> monthlyChecksheetReport(CheckSheetReportDTO request) {
		 LocalDate startDate = LocalDate.of(request.getYear(), 1, 1);
	     LocalDate endDate = LocalDate.of(request.getYear(), 12, 31);
	     List<Map<String, Object>> formattedData = new ArrayList<>(); 
	     List<Object[]> result;
	     if(request.getCheckType()!=null)
		 result=diagnoseCheckRepository.getDiagnoseCheckCountsByDateAndType(startDate, endDate, request.getCheckType());
		 else
	     result=diagnoseCheckRepository.getDiagnoseCheckCountsByDate(startDate, endDate);
		 
        Map<String, Map<Integer, Integer>> out = new HashMap<>();
        for (Object[] row : result) {
        	Long countLong = (Long) row[0];
            String term = (String) row[1];
            Integer checkSheetType = (Integer) row[2];
            
            Integer count = countLong.intValue();
            if (checkSheetType <= 0) continue;

            if (!out.containsKey(term)) {
                out.put(term, new HashMap<>());
            }
            out.get(term).put(checkSheetType, count);
        }

     
      
        for (int month = 1; month <= 12; month++) {
            String dt = String.format("%d/%02d", request.getYear(), month);
            Map<Integer, Integer> data = out.get(dt);
            if (data == null) {
                data = new HashMap<>();
            }

            Map<String, Object> dataEntry = new HashMap<>();
            dataEntry.put("date", dt.substring(2, 4) + "" + dt.substring(4));
            dataEntry.put("subtotal", 0);

            for (int k = 1; k <= 5; k++) {
            	
                int value = data.getOrDefault(k, 0);
                dataEntry.put(String.valueOf(k), value);
                dataEntry.put("subtotal", (int) dataEntry.get("subtotal") + value);
            }


            formattedData.add(dataEntry);
        }

   
        return formattedData;
	}

}
