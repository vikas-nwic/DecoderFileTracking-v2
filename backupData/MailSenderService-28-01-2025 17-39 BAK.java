package com.nwic.gt.decoder.file.tracking.system.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nwic.gt.decoder.file.tracking.system.constants.ApiConstants;
import com.nwic.gt.decoder.file.tracking.system.repositories.DecoderFileTrackerRepo;
import com.nwic.gt.decoder.file.tracking.system.utils.AppUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.*;

@Service
public class MailSenderService {

    private static final Logger logger = LoggerFactory.getLogger(MailSenderService.class);

    @Autowired
    private DecoderFileTrackerRepo decoderFileTrackerRepoRepo;

    @Value("${day.restriction}")
    private int dateRangeDays;

    @Value("${ticket-group-list-api}")
    private String ticketGroupListApi;

    @Value("${ticket-query-data-api}")
    private String ticketQueryDataApi;

    @Value("${ticket-creation-api}")
    private String ticketCreationApi;

    @Value("${ticket-no-att-api}")
    private String ticketNoAttApi;

    // ticket gen
    @Value("${ticket-loggedby}")
    private String ticketLoggedby;

    @Value("${ticket-loggedon}")
    private String ticketLoggedon;

    @Value("${ticket-description}")
    private String ticketDescription;

    @Value("${ticket-emailid}")
    private String ticketEmailId;

    @Value("${ticket-phoneno}")
    private String ticketPhoneNo;

    //@Value("${ticket-query-data-api}")
    //private String ticketStatusId;

    @Value("${ticket-assigtogroup}")
    private String ticketAssignToGroup;// must 6

    @Value("${ticket-query_type_id}")
    private String ticketQueryTypeId; // must 5

    @Autowired
    private RestTemplate restTemplate;

    //  getting group type here with respect to query_type_id
//    public Map<String, String> getQueryType() {
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Content-Type", "application/json");
//        HttpEntity<String> entity = new HttpEntity<>(headers);
//        Map<String, String> queryDataMap = new HashMap<>();
//        logger.info("ticketQueryDataApi: "+ticketQueryDataApi);
//        try {
//            ResponseEntity<String> response = restTemplate.exchange(ticketQueryDataApi, HttpMethod.POST, entity, String.class);
//            ObjectMapper objectMapper = new ObjectMapper();
//            Map<String, Object> responseMap = objectMapper.readValue(response.getBody(), new TypeReference<Map<String, Object>>(){});
//            List<Map<String, Object>> groupList = (List<Map<String, Object>>) responseMap.get("data");
//
//            for (Map<String, Object> group : groupList) {
//                String queryTypeId = String.valueOf(group.get("query_type_id"));  // Convert to String if necessary
//                String description = String.valueOf(group.get("description"));  // Convert to String if necessary
//
//                logger.info("queryTypeId: " + queryTypeId + " description: " + description);
//                queryDataMap.put(queryTypeId, description);
//            }
//            return queryDataMap;
//        } catch (HttpClientErrorException e) {
//            System.out.println("Error calling getQueryType API: " + e.getMessage());
//            return Collections.emptyMap();
//        } catch (JsonProcessingException e) {
//            System.out.println("Error parsing JSON response: " + e.getMessage());
//            return Collections.emptyMap();
//        }
//    }

    public Map<String, String> getQueryType() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        HttpEntity<String> entity = new HttpEntity<>(headers);
        Map<String, String> queryDataMap = new HashMap<>();
        logger.info("ticketQueryDataApi: " + ticketQueryDataApi);

        try {
            ResponseEntity<String> response = restTemplate.exchange(ticketQueryDataApi, HttpMethod.POST, entity, String.class);
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> responseMap = objectMapper.readValue(response.getBody(), new TypeReference<Map<String, Object>>(){});
            List<Map<String, Object>> groupList = (List<Map<String, Object>>) responseMap.get("data");

            for (Map<String, Object> group : groupList) {
                String queryTypeId = String.valueOf(group.get("query_type_id"));  // Convert to String if necessary
                String description = String.valueOf(group.get("description"));  // Convert to String if necessary

                logger.info("queryTypeId: " + queryTypeId + " description: " + description);
                queryDataMap.put(queryTypeId, description);
            }

            // Check if queryDataMap contains the query_type_id of "4" and return that entry
            if (queryDataMap.containsKey("5")) {
                return Collections.singletonMap("5", queryDataMap.get("5"));
            } else {
                return Collections.emptyMap();  // Return empty map if "4" is not found
            }

        } catch (HttpClientErrorException e) {
            System.out.println("Error calling getQueryType API: " + e.getMessage());
            return Collections.emptyMap();
        } catch (JsonProcessingException e) {
            System.out.println("Error parsing JSON response: " + e.getMessage());
            return Collections.emptyMap();
        }
    }

    //  getting group name here with respect to group id
//    public Map<String, String> getGroupNameApi() {
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Content-Type", "application/json");
//        HttpEntity<String> entity = new HttpEntity<>(headers);
//        Map<String, String> groupMap = new HashMap<>();
//        try {
//            ResponseEntity<String> response = restTemplate.exchange(ticketGroupListApi, HttpMethod.POST, entity, String.class);
//            ObjectMapper objectMapper = new ObjectMapper();
//            Map<String, Object> responseMap = objectMapper.readValue(response.getBody(), new TypeReference<Map<String, Object>>(){});
//            List<Map<String, Object>> groupList = (List<Map<String, Object>>) responseMap.get("data");
//            for (Map<String, Object> group : groupList) {
//                String groupId = String.valueOf(group.get("group_id"));  // Convert to String if necessary
//                String groupName = String.valueOf(group.get("group_name"));  // Convert to String if necessary
//                logger.info("groupId: " + groupId + " groupName: " + groupName);
//                groupMap.put(groupId, groupName);
//            }
//            return groupMap;
//        } catch (HttpClientErrorException e) {
//            System.out.println("Error calling getAllDataFromApi API: " + e.getMessage());
//            return Collections.emptyMap();
//        } catch (JsonProcessingException e) {
//            System.out.println("Error parsing JSON response: " + e.getMessage());
//            return Collections.emptyMap();
//        }
//    }

    public Map<String, String> getGroupNameApi() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        HttpEntity<String> entity = new HttpEntity<>(headers);
        Map<String, String> groupMap = new HashMap<>();

        try {
            ResponseEntity<String> response = restTemplate.exchange(ticketGroupListApi, HttpMethod.POST, entity, String.class);
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> responseMap = objectMapper.readValue(response.getBody(), new TypeReference<Map<String, Object>>(){});
            List<Map<String, Object>> groupList = (List<Map<String, Object>>) responseMap.get("data");

            // Iterate through the groupList and check for group_id "4"
            for (Map<String, Object> group : groupList) {
                String groupId = String.valueOf(group.get("group_id"));  // Convert to String if necessary
                String groupName = String.valueOf(group.get("group_name"));  // Convert to String if necessary

                // Log each group info (optional)
                logger.info("groupId: " + groupId + " groupName: " + groupName);

                // If group_id is "4", add to the map and return immediately
                if ("6".equals(groupId)) {
                    groupMap.put(groupId, groupName);
                    return groupMap; // Return the result for group_id "4"
                }
            }

            // If group_id "4" was not found, return an empty map
            return Collections.emptyMap();

        } catch (HttpClientErrorException e) {
            System.out.println("Error calling getAllDataFromApi API: " + e.getMessage());
            return Collections.emptyMap();
        } catch (JsonProcessingException e) {
            System.out.println("Error parsing JSON response: " + e.getMessage());
            return Collections.emptyMap();
        }
    }

    //// generate ticket
    public Map<String, String> GenerateTicketAndGetResponse() {
        logger.info("GenerateTicketAndGetResponse() load...");
        Map<String, String> resultMap = new HashMap<>();

        try {

            String ticketCreationApi = "http://172.16.1.24:8087/api/v1/ticket/ticketCreation";
            // Create a request payload as a Map
            Map<String, Object> requestPayload = new HashMap<>();
            requestPayload.put("loggedby", "Vikas");
            requestPayload.put("loggedon", "2025-01-14T08:36:25.4500");
            requestPayload.put("description", "This is for testing only");
            requestPayload.put("emailid", "vikas.nwic@gmail.com");
            requestPayload.put("phoneno", "1234567890");
            requestPayload.put("status_id", 1);
            requestPayload.put("assigtogroup", 6);
            requestPayload.put("query_type_id", "5");

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestPayload, headers);

            // Create RestTemplate instance
            RestTemplate restTemplate = new RestTemplate();
            // Send POST request and receive response as JsonNode
            ResponseEntity<String> responseEntity = restTemplate.postForEntity(ticketCreationApi, requestEntity, String.class);

            if (responseEntity.getBody() != null) {
                // Parse the response body into JsonNode for dynamic handling
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode responseJson = objectMapper.readTree(responseEntity.getBody());
                System.out.println("Full Response: " + responseEntity.getBody());
                JsonNode data = responseJson.get("data");
                if (data != null && data.isObject()) {
                    // Access and put fields from the data object into the result map
                    resultMap.put("TicketNo", data.has("ticketno") ? String.valueOf(data.get("ticketno").asInt()) : "N/A");
                    resultMap.put("TicketLoggedBy", data.has("loggedby") ? data.get("loggedby").asText() : "N/A");
                    resultMap.put("TicketgroupId", data.has("assigtogroup") ? data.get("assigtogroup").asText() : "N/A");
                    resultMap.put("TicketEmail", data.has("emailid") ? data.get("emailid").asText() : "N/A");
                    resultMap.put("TicketgroupTypeId", data.has("query_type_id") ? data.get("query_type_id").asText() : "N/A");
                }
                else {
                    System.out.println("Error: 'data' field is null, empty, or not an array.");
                }
            } else {
                logger.error("Error: Response body is null");
            }
        } catch (Exception e) {
            logger.error("Exception occurred while making API call: ", e);
        }
        return resultMap;
    }

    public void sendEmailWithAttachment() throws IOException {
        // test ticket no = 17
        String filename = AppUtils.generateUniqueFilename("TrackReport", "xlsx");
        ByteArrayInputStream excelFileInputStream = generateExcelFile();
        byte[] fileBytes = inputStreamToByteArray(excelFileInputStream);  // Convert ByteArrayInputStream to byte array

        // Assuming `ticketQueryTypeId` and `ticketAssignToGroup` are provided
        String ticketQueryTypeId = "5";  // Placeholder value for ticketQueryTypeId
        String ticketAssignToGroup = "6";  // Placeholder value for ticketAssignToGroup

        // Get data from APIs
        Map<String, String> groupType = getQueryType();  // query_type_id: 5, description: Telemetry File Processing, displaysequence: "5"
        Map<String, String> groupMap = getGroupNameApi();  // Group ID: 6, Group Name: Telemetry
        Map<String, String> ticketMap = GenerateTicketAndGetResponse();  // ticket gen = 17

        String groupTypeId = null;
        String groupDesc = null;
        String groupId = null;
        String groupName = null;

        // Process groupType map
        if (groupType != null && !groupType.isEmpty()) {
            for (Map.Entry<String, String> entry : groupType.entrySet()) {
                groupTypeId = entry.getKey();  // Get the key (groupTypeId)
                groupDesc = entry.getValue();  // Get the value (groupDesc)
                logger.info("Group Type Id: " + groupTypeId + ", Group Desc: " + groupDesc);
            }
        }

        // Process groupMap
        if (groupMap != null && !groupMap.isEmpty()) {
            for (Map.Entry<String, String> entry : groupMap.entrySet()) {
                groupId = entry.getKey();  // Get the key (groupId)
                groupName = entry.getValue();  // Get the value (groupName)
                logger.info("Group ID: " + groupId + ", Group Name: " + groupName);
            }
        }

        // Processing ticketMap
        String ticketKey = null;
        String ticketValue = null;

        if (ticketMap != null && !ticketMap.isEmpty()) {
            for (Map.Entry<String, String> entry : ticketMap.entrySet()) {
                ticketKey = entry.getKey();  // Get the key (ticketKey)
                ticketValue = entry.getValue();  // Get the value (ticketValue)
                System.out.println("ticketKey: " + ticketKey + " ticketValue: " + ticketValue);
            }
        }

        // Logging comparison of groupTypeId and groupId with ticketQueryTypeId and ticketAssignToGroup
        logger.info("groupTypeId: " + groupTypeId + ", ticketQueryTypeId: " + ticketQueryTypeId + " groupId: " + groupId + " ticketAssignToGroup: " + ticketAssignToGroup);

        // Validate if groupTypeId matches ticketQueryTypeId and groupId matches ticketAssignToGroup
        if (groupTypeId.equals(ticketQueryTypeId) && groupId.equals(ticketAssignToGroup) ) {
            logger.info("true...");  // Conditions matched
        } else {
            logger.info("group type & group id not matching check either in DB or properties file");
        }
    }
//            // New method to send the Excel file as an attachment
//    public void sendEmailWithAttachment() throws IOException {  // String ticketNumber
//
//      // test ticket no = 17
//
//        String filename = AppUtils.generateUniqueFilename("TrackReport", "xlsx");
//        ByteArrayInputStream excelFileInputStream = generateExcelFile();
//        byte[] fileBytes = inputStreamToByteArray(excelFileInputStream);    // Convert ByteArrayInputStream to byte array
//
//        Map<String, String> groupType = getQueryType();  //query_type_id: 5, description: Telemetry File Processing, displaysequence: "5"
//        Map<String, String> groupMap = getGroupNameApi();  // Group ID: 6, Group Name: Telemetry
//        Map<String, String> ticketMap = GenerateTicketAndGetResponse();  // ticket gen = 17
//
//        GenerateTicketAndGetResponse();
//        String groupTypeId = null;
//        String groupDesc = null;
//        String groupId = null;
//        String groupName = null;
//
//        if (groupType != null && !groupType.isEmpty()) {
//            for (Map.Entry<String, String> entry : groupType.entrySet()) {
//                groupTypeId = entry.getKey(); // Get the key (groupTypeId)
//                groupDesc = entry.getValue(); // Get the value (groupDesc)
//                logger.info("Group Type Id: " + groupTypeId + ", Group Desc: " + groupDesc);
//                logger.info("==== "+groupType.containsValue(ticketQueryTypeId));
//            }
//        }
//        if (groupMap != null && !groupMap.isEmpty()) {
//            for (Map.Entry<String, String> entry : groupMap.entrySet()) {
//                groupId = entry.getKey(); // Get the key (groupId)
//                 groupName = entry.getValue(); // Get the value (groupName)
//                logger.info("Group ID: " + groupId + ", Group Name: " + groupName);
//
//            }
//        }
//
//        System.out.println("group Id: "+groupMap.get("groupId")+" groupTypeId: "+groupMap.get("groupTypeId"));
//
//        int ticketNo;
//        int assigToGroup;
//        String queryTypeId;
//
//        String ticketKey = null;
//        String ticketValue = null;
////
//
////if (groupType.equals("5") && groupId.equals("6")  )
//        logger.info("groupTypeId: " + groupTypeId + ", ticketQueryTypeId: " + ticketQueryTypeId+ " groupId: "+groupId+" ticketAssignToGroup: "+ticketAssignToGroup);
//
//        if (ticketMap != null && !ticketMap.isEmpty()) {
//            for (Map.Entry<String, String> entry : ticketMap.entrySet()) {
//                ticketKey = entry.getKey(); // Get the key (ticketKey)
//                ticketValue = entry.getValue(); // Get the value (ticketValue)
//                System.out.println("groupId: "+groupMap.get("groupId")+" groupTypeId: "+groupMap.get("groupTypeId"));
//            }
//        }
//
//       // if (groupTypeId.equals(ticketQueryTypeId) && groupId.equals(ticketAssignToGroup)){
//
//        if (groupType.containsValue(ticketQueryTypeId) && groupMap.containsValue(ticketAssignToGroup)){
//            logger.info("true...");
//
//        }else{
//            logger.info("group type & group id not matching check either in DB or properties file");
//        }
//
////        ResponseEntity<String> entity = restTemplate.getForEntity(ticketCreationApi, String.class);
////        String body = entity.getBody();
////        MediaType contentType = entity.getHeaders().getContentType();
////        HttpStatus statusCode = entity.getStatusCode();
////        MDTicketCreation dd = new MDTicketCreation();
//
////
//        //String apiUrl = ticketStgApiUrl+"/api/v1/ticket/uploadAttachment?ticketno=" + ticketNumber;
//       // logger.info("apiUril:===================> "+apiUrl);
//
////        // Prepare the cURL-like request to upload the file as an attachment
////        HttpURLConnection connection = (HttpURLConnection) new URL(apiUrl).openConnection();
////        connection.setRequestMethod("POST");
////        connection.setDoOutput(true);
////        connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW");
////        // Prepare the multipart/form-data body with file
////        String boundary = "----WebKitFormBoundary7MA4YWxkTrZu0gW";
////        connection.getOutputStream().write(("--" + boundary + "\r\n").getBytes());
////        connection.getOutputStream().write(("Content-Disposition: form-data; name=\"ticketno\"\r\n\r\n" + ticketNumber + "\r\n").getBytes());
////        connection.getOutputStream().write(("--" + boundary + "\r\n").getBytes());
////        connection.getOutputStream().write(("Content-Disposition: form-data; name=\"attachment\"; filename=\"Decoder-Error-Report.xlsx\"\r\n").getBytes());
////        connection.getOutputStream().write(("Content-Type: application/vnd.openxmlformats-officedocument.spreadsheetml.sheet\r\n\r\n").getBytes());
////        connection.getOutputStream().write(fileBytes);
////        connection.getOutputStream().write(("\r\n--" + boundary + "--\r\n").getBytes());
////        int responseCode = connection.getResponseCode();
////        if (responseCode == HttpURLConnection.HTTP_OK) {
////            logger.info("Attachment sent successfully to ticket " + ticketNumber);
////        } else {
////            logger.warn("Failed to send attachment. Response Code: " + responseCode);
////        }
//    }

    // Helper method to convert InputStream (ByteArrayInputStream) to byte array
    private byte[] inputStreamToByteArray(InputStream inputStream) throws IOException {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[1024]; // Buffer for reading in chunks
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, bytesRead);
            }
            return byteArrayOutputStream.toByteArray();
        }
    }

    // send mail attachment
    public ByteArrayInputStream generateExcelFile() throws IOException {
        LocalDate currentDate = LocalDate.now(); // Current date (today)
        LocalDate previousWeekDate = currentDate.minusDays(Long.parseLong(ApiConstants.DAY_RESTRICTION)); // Date from 7 days ago
        logger.info("findFailedData previousWeekDate: "+previousWeekDate);
        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            List<Object[]> data = decoderFileTrackerRepoRepo.findFailedData(dateRangeDays);

//            for (Object[] row : data) {
//                // Print each column value in the row (assuming 7 columns)
//                logger.info("Agency Name: " + row[0]);
//                logger.info("Station Code: " + row[1]);
//                logger.info("File Name: " + row[2]);
//                logger.info("Insertion Date: " + row[3]);
//                logger.info("Logger ID: " + row[4]);
//                logger.info("Status: " + row[5]);
//                logger.info("Remarks: " + row[6]);
//                logger.info("------------------------------------------------");
//            }
            Sheet sheet = workbook.createSheet("Agency_Data-Issues");

            // Create a title row (merged cells for title)
            Row titleRow = sheet.createRow(0);
            Cell titleCell = titleRow.createCell(0);
            titleCell.setCellValue("Telemetry Decoder File Tracker Report");
            CellStyle titleStyle = AppUtils.createTitleStyle(workbook);
            titleCell.setCellStyle(titleStyle);

            // Merge cells for the title
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 6));

            // Create Header Row (second row, after title)
            Row headerRow = sheet.createRow(1);
            String[] headers = {"SN", "Agency Name", "Station ID", "File Name", "Date", "Status", "Error Description"};
            CellStyle headerCellStyle = AppUtils.createHeaderCellStyle(workbook);

            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerCellStyle);
            }

            // Fill data rows with alternating colors and centered content
            CellStyle evenRowStyle = AppUtils.createRowStyle(workbook, IndexedColors.GREY_25_PERCENT);
            CellStyle oddRowStyle = AppUtils.createRowStyle(workbook, IndexedColors.WHITE);

            int rowIdx = 2;
            int cnt = 1;

            // Start after the title and header rows
            for (Object[] rowObj : data) {
                Row dataRow = sheet.createRow(rowIdx++);
                CellStyle rowStyle = (rowIdx % 2 == 0) ? evenRowStyle : oddRowStyle;

                AppUtils.createStyledCell(dataRow, 0, String.valueOf(cnt++), rowStyle);
                AppUtils.createStyledCell(dataRow, 1, (String) rowObj[0], rowStyle); //Agency Name
                AppUtils.createStyledCell(dataRow, 2, (String) rowObj[1], rowStyle); //Station Code
                AppUtils.createStyledCell(dataRow, 3, (String) rowObj[2], rowStyle); //File Name
                AppUtils.createStyledCell(dataRow, 4, (String) rowObj[3].toString(), rowStyle); //Insertion Date
                // AppUtils.createStyledCell(dataRow, 5, (String) rowObj[4], rowStyle); // Logger ID
                AppUtils.createStyledCell(dataRow, 5, (String) rowObj[5], rowStyle); //Status
                AppUtils.createStyledCell(dataRow, 6, (String) rowObj[6], rowStyle); //Remarks
            }

            // Add the computer-generated message at the end
            Row footerRow = sheet.createRow(rowIdx);
            Cell footerCell = footerRow.createCell(0);
            footerCell.setCellValue("This is a system generated report");
            CellStyle footerStyle = workbook.createCellStyle();
            Font footerFont = workbook.createFont();
            footerFont.setItalic(true);
            footerStyle.setFont(footerFont);
            footerCell.setCellStyle(footerStyle);
            // Merge cells for the footer message
            sheet.addMergedRegion(new CellRangeAddress(rowIdx, rowIdx, 0, headers.length - 1));
            // Auto-size all columns
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            // Freeze the header row
            sheet.createFreezePane(0, 2);

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }

    }
    ///// end mail attachment


}
