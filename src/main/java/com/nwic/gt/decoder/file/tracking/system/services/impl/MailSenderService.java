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
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    private String ticketNumAndAttApi;

    // ticket gen
    @Value("${ticket-loggedby}")
    private String ticketLoggedby;

//    @Value("${ticket-loggedon}")
//    private String ticketLoggedon;

    @Value("${ticket-description}")
    private String ticketDescription;

    @Value("${ticket-emailid}")
    private String ticketEmailId;

    @Value("${ticket-phoneno}")
    private String ticketPhoneNo;

    @Value("${ticket-status_id}")
    private String ticketStatusId;

    @Value("${ticket-assigtogroup}")
    private String ticketAssignToGroup;// must 6

    @Value("${ticket-query_type_id}")
    private String ticketQueryTypeId; // must 5

    @Autowired
    private RestTemplate restTemplate;

    public Map<String, String> getQueryType() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        HttpEntity<String> entity = new HttpEntity<>(headers);
        Map<String, String> queryDataMap = new HashMap<>();

        try {
            ResponseEntity<String> response = restTemplate.exchange(ticketQueryDataApi, HttpMethod.POST, entity, String.class);
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> responseMap = objectMapper.readValue(response.getBody(), new TypeReference<Map<String, Object>>(){});
            List<Map<String, Object>> groupList = (List<Map<String, Object>>) responseMap.get("data");
            for (Map<String, Object> group : groupList) {
                String queryTypeId = String.valueOf(group.get("query_type_id"));
                String description = String.valueOf(group.get("description"));
                logger.info("queryTypeId: " + queryTypeId + " description: " + description);
                queryDataMap.put(queryTypeId, description);
            }
            if (queryDataMap.containsKey(ticketQueryTypeId)) {
                return Collections.singletonMap(ticketQueryTypeId, queryDataMap.get(ticketQueryTypeId));
            } else {
                return Collections.emptyMap();
            }
        } catch (HttpClientErrorException e) {
            System.out.println("Error calling getQueryType API: " + e.getMessage());
            return Collections.emptyMap();
        } catch (JsonProcessingException e) {
            System.out.println("Error parsing JSON response: " + e.getMessage());
            return Collections.emptyMap();
        }
    }

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
            for (Map<String, Object> group : groupList) {
                String groupId = String.valueOf(group.get("group_id"));
                String groupName = String.valueOf(group.get("group_name"));
                logger.info("groupId: " + groupId + " groupName: " + groupName);
                if (ticketAssignToGroup.equals(groupId)) {
                    groupMap.put(groupId, groupName);
                    return groupMap;
                }
            }
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

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SS");
        String ticketLoggedon = now.format(formatter);
        try {
            Map<String, Object> requestPayload = new HashMap<>();
            requestPayload.put("loggedby", ticketLoggedby);
            requestPayload.put("loggedon", ticketLoggedon);
            requestPayload.put("description", ticketDescription);
            requestPayload.put("emailid", ticketEmailId);
            requestPayload.put("phoneno", ticketPhoneNo);
            requestPayload.put("status_id", ticketStatusId);
            requestPayload.put("assigtogroup", ticketAssignToGroup);
            requestPayload.put("query_type_id", ticketQueryTypeId);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestPayload, headers);
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> responseEntity = restTemplate.postForEntity(ticketCreationApi, requestEntity, String.class);
            if (responseEntity.getBody() != null) {
                // Parse the response body into JsonNode for dynamic handling
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode responseJson = objectMapper.readTree(responseEntity.getBody());
                System.out.println("Full Response: " + responseEntity.getBody());
                JsonNode data = responseJson.get("data");
                if (data != null && data.isObject()) {
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

     //@Scheduled(cron = "0 0 9 * * ?")  // CRON expression to run every hour
//    public void sendEmailWithAttachment() throws IOException {
//        String filename = AppUtils.generateUniqueFilename("TrackReport", "xlsx");
//        ByteArrayInputStream excelFileInputStream = generateExcelFile();
//        byte[] fileBytes = inputStreamToByteArray(excelFileInputStream);
//
//        Map<String, String> groupType = getQueryType();  // query_type_id: 5, description: Telemetry File Processing, displaysequence: "5"
//        Map<String, String> groupMap = getGroupNameApi();  // Group ID: 6, Group Name: Telemetry
//        Map<String, String> ticketMap = GenerateTicketAndGetResponse();  // ticket gen = 17
//
//        String groupTypeId = null;
//        String groupDesc = null;
//        String groupId = null;
//        String groupName = null;
//        String ticketKey = null;
//        String ticketValue = null;
//
//        if (groupType != null && !groupType.isEmpty()) {
//            for (Map.Entry<String, String> entry : groupType.entrySet()) {
//                groupTypeId = entry.getKey();
//                groupDesc = entry.getValue();
//            }
//        }
//        if (groupMap != null && !groupMap.isEmpty()) {
//            for (Map.Entry<String, String> entry : groupMap.entrySet()) {
//                groupId = entry.getKey();
//                groupName = entry.getValue();
//            }
//        }
//        if (ticketMap != null && !ticketMap.isEmpty()) {
//            for (Map.Entry<String, String> entry : ticketMap.entrySet()) {
//                ticketKey = entry.getKey();
//                ticketValue = entry.getValue();
//            }
//        }
//
//        if (groupTypeId.equals(ticketQueryTypeId) && groupId.equals(ticketAssignToGroup) ) {
//            String apiUrl = ticketNumAndAttApi + ticketMap.get("TicketNo");
//
//            HttpURLConnection connection = (HttpURLConnection) new URL(apiUrl).openConnection();
//            connection.setRequestMethod("POST");
//            connection.setDoOutput(true);
//            connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW");
//            String boundary = "----WebKitFormBoundary7MA4YWxkTrZu0gW";
//            connection.getOutputStream().write(("--" + boundary + "\r\n").getBytes());
//            connection.getOutputStream().write(("Content-Disposition: form-data; name=\"ticketno\"\r\n\r\n" + ticketMap.get("TicketNo") + "\r\n").getBytes());
//            connection.getOutputStream().write(("--" + boundary + "\r\n").getBytes());
//            connection.getOutputStream().write(("Content-Disposition: form-data; name=\"attachment\"; filename=\""+filename+".xlsx\"\r\n").getBytes());
//            connection.getOutputStream().write(("Content-Type: application/vnd.openxmlformats-officedocument.spreadsheetml.sheet\r\n\r\n").getBytes());
//            connection.getOutputStream().write(fileBytes);
//            connection.getOutputStream().write(("\r\n--" + boundary + "--\r\n").getBytes());
//            int responseCode = connection.getResponseCode();
//            if (responseCode == HttpURLConnection.HTTP_OK) {
//                logger.info("Attachment sent successfully to ticket " + ticketMap.get("TicketNo") );
//            } else {
//                logger.warn("Failed to send attachment. Response Code: " + responseCode);
//            }
//        } else {
//            logger.info("group type & group id not matching check either in DB or properties file");
//        }
//    }


    public void sendEmailWithAttachment() throws IOException {
        String filename = AppUtils.generateUniqueFilename("TrackReport", "xlsx");
        //ByteArrayInputStream excelFileInputStream = generateExcelFile();
        //byte[] fileBytes = inputStreamToByteArray(excelFileInputStream);

        Map<String, String> groupType = getQueryType();  // query_type_id: 5, description: Telemetry File Processing, displaysequence: "5"
        Map<String, String> groupMap = getGroupNameApi();  // Group ID: 6, Group Name: Telemetry
        Map<String, String> ticketMap = GenerateTicketAndGetResponse();  // ticket gen = 17

        String groupTypeId = null;
        String groupDesc = null;
        String groupId = null;
        String groupName = null;
        String ticketKey = null;
        String ticketValue = null;

        ByteArrayInputStream excelFileInputStream = null;
        OutputStream os = null;
        InputStream is = null;

        if (groupType != null && !groupType.isEmpty()) {
            for (Map.Entry<String, String> entry : groupType.entrySet()) {
                groupTypeId = entry.getKey();
                groupDesc = entry.getValue();
            }
        }
        if (groupMap != null && !groupMap.isEmpty()) {
            for (Map.Entry<String, String> entry : groupMap.entrySet()) {
                groupId = entry.getKey();
                groupName = entry.getValue();
            }
        }
        if (ticketMap != null && !ticketMap.isEmpty()) {
            for (Map.Entry<String, String> entry : ticketMap.entrySet()) {
                ticketKey = entry.getKey();
                ticketValue = entry.getValue();
            }
        }

        if (groupTypeId.equals(ticketQueryTypeId) && groupId.equals(ticketAssignToGroup) ) {
            String apiUrl = ticketNumAndAttApi + ticketMap.get("TicketNo");

            try {
                // Generate the Excel file and convert it to byte array
                excelFileInputStream = generateExcelFile();
                byte[] fileBytes = inputStreamToByteArray(excelFileInputStream);

                // Prepare the cURL-like request to upload the file as an attachment
                HttpURLConnection connection = (HttpURLConnection) new URL(apiUrl).openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);

                // Dynamically generate boundary and set Content-Type header
                String boundary = "----WebKitFormBoundary" + System.currentTimeMillis();
                connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

                os = connection.getOutputStream();

                // Write multipart data
                os.write(("--" + boundary + "\r\n").getBytes());
                os.write(("Content-Disposition: form-data; name=\"ticketno\"\r\n\r\n "+ticketMap.get("TicketNo")+" \r\n").getBytes());

                os.write(("--" + boundary + "\r\n").getBytes());
                os.write(("Content-Disposition: form-data; name=\"attachment\"; filename=\"" + filename + "\"\r\n").getBytes());
                os.write(("Content-Type: application/vnd.openxmlformats-officedocument.spreadsheetml.sheet\r\n\r\n").getBytes());

                os.write(fileBytes);
                os.write(("\r\n--" + boundary + "--\r\n").getBytes());

                // Get response code
                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    logger.info("Attachment sent successfully to ticket "+ticketMap.get("TicketNo"));
                } else {
                    // Read the error stream for debugging if failed
                    is = connection.getErrorStream();
                    StringBuilder errorResponse = new StringBuilder();
                    try (BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
                        String line;
                        while ((line = reader.readLine()) != null) {
                            errorResponse.append(line).append("\n");
                        }
                    }
                    logger.warn("Failed to send attachment. Response Code: " + responseCode + ", Error: " + errorResponse.toString());
                }

            } catch (IOException e) {
                logger.error("Error sending email with attachment", e);
            } finally {
                // Close streams safely in the finally block
                try {
                    if (excelFileInputStream != null) {
                        excelFileInputStream.close();
                    }
                    if (os != null) {
                        os.close();
                    }
                    if (is != null) {
                        is.close();
                    }
                } catch (IOException e) {
                    logger.error("Error closing streams", e);
                }
            }


        } else {
            logger.info("group type & group id not matching check either in DB or properties file");
        }
    }

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

    // generate Excel file
    public ByteArrayInputStream generateExcelFile() throws IOException {
        LocalDate currentDate = LocalDate.now(); // Current date (today)
        LocalDate previousWeekDate = currentDate.minusDays(ApiConstants.DAY_RESTRICTION);
        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            List<Object[]> data = decoderFileTrackerRepoRepo.findFailedData();//dateRangeDays
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
            String[] headers = {"SN", "Agency Name", "Logger ID", "File Name", "Date", "Status", "Error Description"};
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
                AppUtils.createStyledCell(dataRow, 2, (String) rowObj[4], rowStyle); // Logger ID

                //  AppUtils.createStyledCell(dataRow, 2, (String) rowObj[1], rowStyle); //Station Code
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

    // check excel
//
//        public void sendEmailWithAttachmentTemp() throws IOException {
//        String filename = AppUtils.generateUniqueFilename("TrackReport", "xlsx");
//        ByteArrayInputStream excelFileInputStream = generateExcelFile();
//        byte[] fileBytes = inputStreamToByteArray(excelFileInputStream);
//            String apiUrl = ticketNumAndAttApi+16;
//            HttpURLConnection connection = (HttpURLConnection) new URL(apiUrl).openConnection();
//            connection.setRequestMethod("POST");
//            connection.setDoOutput(true);
//            connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW");
//            String boundary = "----WebKitFormBoundary7MA4YWxkTrZu0gW";
//            connection.getOutputStream().write(("--" + boundary + "\r\n").getBytes());
//            connection.getOutputStream().write(("Content-Disposition: form-data; name=\"ticketno\"\r\n\r\n 16 \r\n").getBytes());
//            connection.getOutputStream().write(("--" + boundary + "\r\n").getBytes());
//            connection.getOutputStream().write(("Content-Disposition: form-data; name=\"attachment\"; filename=\""+filename+".xlsx\"\r\n").getBytes());
//            connection.getOutputStream().write(("Content-Type: application/vnd.openxmlformats-officedocument.spreadsheetml.sheet\r\n\r\n").getBytes());
//            connection.getOutputStream().write(fileBytes);
//            connection.getOutputStream().write(("\r\n--" + boundary + "--\r\n").getBytes());
//            int responseCode = connection.getResponseCode();
//            if (responseCode == HttpURLConnection.HTTP_OK) {
//                logger.info("Attachment sent successfully to ticket 16" );
//            } else {
//                logger.warn("Failed to send attachment. Response Code: " + responseCode);
//            }
//
//    }
}
