package com.nwic.gt.decoder.file.tracking.system.services.impl;

import com.nwic.gt.decoder.file.tracking.system.DTOs.DecoderApiResponse;
import com.nwic.gt.decoder.file.tracking.system.DTOs.DecoderRequestDTO;
import com.nwic.gt.decoder.file.tracking.system.DTOs.DecoderDownloadRequestDTO;
import com.nwic.gt.decoder.file.tracking.system.exception.ResourceNotFoundException;
import com.nwic.gt.decoder.file.tracking.system.models.DecoderFileTracker;
import com.nwic.gt.decoder.file.tracking.system.projection.DecoderProjection;
import com.nwic.gt.decoder.file.tracking.system.repositories.DecoderFileTrackerRepo;
import com.nwic.gt.decoder.file.tracking.system.services.DecoderFileTrackerService;
import com.nwic.gt.decoder.file.tracking.system.utils.AppUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;


@Slf4j
@Service
public class DecoderFileTrackerServiceImpl implements DecoderFileTrackerService {

    private static final Logger logger = LoggerFactory.getLogger(DecoderFileTrackerServiceImpl.class);


    @Autowired
    private DecoderFileTrackerRepo decoderFileTrackerRepoRepo;

    @Value("${day.restriction}")
    private int dateRangeDays;

//    public DecoderFileTrackerServiceImpl(DecoderFileTrackerRepo repository) {
//        this.decoderFileTrackerRepoRepo = repository;
//    }
//@Override
//public List<TelemetryDecoderFileTrackerProjection> getAllFileTrackers(TelemetryDecoderFileTrackerRequestDTO request) {
//    System.out.println("stats service hit...");
//
//    return decoderFileTrackerRepoRepo.getTelemetryDecoderFileTrackers(request.getAgencyIds()
//            , request.getStationCodes()
//            , request.getStartDate()
//            , request.getEndDate());
//}

    public ResponseEntity<DecoderApiResponse<DecoderProjection>> getAllFileTrackers(DecoderRequestDTO request) {
        try {
            List<DecoderProjection> trackers = decoderFileTrackerRepoRepo.getTelemetryDecoderFileTrackers(request.getAgencyIds(), request.getStationCodes(), request.getStartDate(), request.getEndDate());

            System.out.println("trackers size: "+trackers.size());
            if (trackers.isEmpty()) {
                DecoderApiResponse<DecoderProjection> noRecordResponse = new DecoderApiResponse<>(HttpStatus.NOT_FOUND, "No records found for the given criteria", Collections.emptyList());
                return new ResponseEntity<>(noRecordResponse, HttpStatus.NOT_FOUND);
            }
            DecoderApiResponse<DecoderProjection> apiResponse = new DecoderApiResponse<>(HttpStatus.OK, "Records found", trackers);
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        } catch (Exception e) {
            DecoderApiResponse<DecoderProjection> errorResponse = new DecoderApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to retrieve file trackers: " + e.getMessage(), Collections.emptyList());
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /////////// error file
    public ResponseEntity<DecoderApiResponse<DecoderProjection>> getAllErrorFiles(DecoderRequestDTO request) {
        try {
            List<DecoderProjection> trackers = decoderFileTrackerRepoRepo.getTelemetryDecoderFileTrackersErrorFiles(request.getAgencyIds(), request.getStationCodes(), request.getStartDate(), request.getEndDate());
            if (trackers.isEmpty()) {
                throw new ResourceNotFoundException("No records found!");
            }
            DecoderApiResponse<DecoderProjection> apiResponse = new DecoderApiResponse<>(HttpStatus.OK, "Records found", trackers);
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        } catch (ResourceNotFoundException rnf) {
            DecoderApiResponse<DecoderProjection> noRecordResponse = new DecoderApiResponse<>(HttpStatus.NOT_FOUND, rnf.getMessage(), Collections.emptyList());
            return new ResponseEntity<>(noRecordResponse, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            DecoderApiResponse<DecoderProjection> errorResponse = new DecoderApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to retrieve file trackers: " + e.getMessage(), Collections.emptyList());
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<DecoderApiResponse<DecoderFileTracker>> createDecoderFileTracker(DecoderFileTracker decoderFileTracker) {
        try {
            decoderFileTrackerRepoRepo.save(decoderFileTracker);
            DecoderApiResponse<DecoderFileTracker> apiResponse = new DecoderApiResponse<>(HttpStatus.CREATED, "Decoder File Tracker created successfully", Collections.singletonList(decoderFileTracker));
            return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
        } catch (Exception e) {
            DecoderApiResponse<DecoderFileTracker> errorResponse = new DecoderApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to create DecoderFileTracker: " + e.getMessage(), Collections.emptyList());
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @Override
//    public byte[] generateExcelFile() throws IOException {
//            logger.info("generateExcelFile invoked............");
//        List<Object[]> data = decoderFileTrackerRepoRepo.findFailedData();
////    logger.info("object list size: "+data.size());
////        for (Object[] row : data) {
////            // Print each column value in the row (assuming 7 columns)
////            logger.info("Agency Name: " + row[0]);
////            logger.info("Station Code: " + row[1]);
////            logger.info("File Name: " + row[2]);
////            logger.info("Insertion Date: " + row[3]);
////            logger.info("Logger ID: " + row[4]);
////            logger.info("Status: " + row[5]);
////            logger.info("Remarks: " + row[6]);
////            logger.info("------------------------------------------------");
////        }
//        // Create a workbook and a sheet
//        Workbook workbook = new XSSFWorkbook();
//        Sheet sheet = workbook.createSheet("Failed Records");
//
//        // Create header row
//        Row header = sheet.createRow(0);
//        header.createCell(0).setCellValue("Agency Name");
//        header.createCell(1).setCellValue("Station Code");
//        header.createCell(2).setCellValue("File Name");
//        header.createCell(3).setCellValue("Insertion Date");
//        header.createCell(4).setCellValue("Logger ID");
//        header.createCell(5).setCellValue("Status");
//        header.createCell(6).setCellValue("Remarks");
//
//        // Fill in data rows
//        int rowNum = 1;
//        for (Object[] row : data) {
//            Row dataRow = sheet.createRow(rowNum++);
//            dataRow.createCell(0).setCellValue((String) row[0]); // agencyName
//            dataRow.createCell(1).setCellValue((String) row[1]); // stationCode
//            dataRow.createCell(2).setCellValue((String) row[2]); // fileName
//            dataRow.createCell(3).setCellValue(row[3].toString()); // insertionDate
//            dataRow.createCell(4).setCellValue((String) row[4]); // loggerId
//            dataRow.createCell(5).setCellValue((String) row[5]); // statuscreateDecoderFileTracker
//            dataRow.createCell(6).setCellValue((String) row[6]); // remarks
//        }
//
//        // Write to ByteArrayOutputStream
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        workbook.write(outputStream);
//        workbook.close();
//
//        return outputStream.toByteArray();
//    }

    public ResponseEntity<byte[]> downloadExcel(DecoderDownloadRequestDTO requestDto) {
        System.out.println("download service ");
        try {
            System.out.println("agencyIds: " + requestDto.getAgencyIds());
            System.out.println("stationCode: " + requestDto.getStationCodes());
            System.out.println("fromDate: " + requestDto.getFromDate());
            System.out.println("toDate: " + requestDto.getToDate());

            // Generate a unique and valid filename
            String filename = AppUtils.generateUniqueFilename("TrackReport", "xlsx");

            // Retrieve all records for the given agencyIds and stationCode
            List<DecoderFileTracker> agencyData = decoderFileTrackerRepoRepo.findByAgencyIdInAndStationCodeIn(
                    requestDto.getAgencyIds(), requestDto.getStationCodes());

            // Check if agencyData is empty
            if (agencyData.isEmpty()) {
                String errorMessage = "No data found for the given agencyIds and stationCodes: " + requestDto.getStationCodes();
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage.getBytes());
            }

            // Filter data by date range using the DTO
            List<DecoderFileTracker> filteredData = filterByDateRange(agencyData, requestDto.getFromDate(), requestDto.getToDate());
            if (filteredData.isEmpty()) {
                String errorMessage = "No data found for the given date range.";
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage.getBytes());
            }

            // Generate the Excel report
            ByteArrayInputStream in = exportToExcel(requestDto.getAgencyIds(), requestDto.getStationCodes(), requestDto.getFromDate(), requestDto.getToDate());
            System.out.println("check1: ");

            // Manually read all bytes from ByteArrayInputStream
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            int byteRead;
            while ((byteRead = in.read()) != -1) {
                byteArrayOutputStream.write(byteRead);
            }
            byte[] byteArray = byteArrayOutputStream.toByteArray();

            // Prepare the headers for the response
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "attachment; filename=" + filename + ".xlsx");
            System.out.println("check2: " + filename);

            // Return the Excel file as a response
            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(byteArray);

        } catch (IOException e) {
            System.out.println("check3: ");

            // Handle the exception, log it, and rethrow as a runtime exception
            throw new RuntimeException("Error generating the Excel report: " + e.getMessage(), e);
        }
    }

    // Helper method for filtering by date range
    private List<DecoderFileTracker> filterByDateRange(List<DecoderFileTracker> agencyData, Date fromDate, Date toDate) {
        return agencyData.stream()
                .filter(data -> !data.getInsertionDate().before(fromDate) && !data.getInsertionDate().after(toDate))
                .collect(Collectors.toList());
    }

    // excel download
    public ByteArrayInputStream exportToExcel(List<Integer> agencyIds, List<String> stationCodes, Date fromDate, Date toDate) throws IOException {
        // Retrieve data based on agencyIds and stationIds, filtered by date range
        //  List<DecoderFileTracker> data = decoderFileTrackerRepoRepo.findByAgencyIdInAndStationIdIn(agencyIds, stationCodes, fromDate, toDate);
        List<DecoderFileTracker> data = decoderFileTrackerRepoRepo.findByAgencyIdInAndStationCodeInAndDataDateBetween(agencyIds, stationCodes, fromDate, toDate);

        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            Sheet sheet = workbook.createSheet("Tracker-Report");

            // Create a title row (merged cells for title)
            Row titleRow = sheet.createRow(0);
            Cell titleCell = titleRow.createCell(0);
            titleCell.setCellValue("Decoder File Tracker Report");
            CellStyle titleStyle = AppUtils.createTitleStyle(workbook);
            titleCell.setCellStyle(titleStyle);

            // Merge cells for the title
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 6));

            // Create Header Row (second row, after title)
            Row headerRow = sheet.createRow(1);
            String[] headers = {"SN", "Agency Name", "Station ID", "File Name", "Date", "Status", "Remarks"};
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
            for (DecoderFileTracker entity : data) {
                Row row = sheet.createRow(rowIdx++);
                CellStyle rowStyle = (rowIdx % 2 == 0) ? evenRowStyle : oddRowStyle;

                AppUtils.createStyledCell(row, 0, String.valueOf(cnt++), rowStyle);
                AppUtils.createStyledCell(row, 1, entity.getAgencyName(), rowStyle);
                AppUtils.createStyledCell(row, 2, entity.getStationCode(), rowStyle);
                AppUtils.createStyledCell(row, 3, entity.getFileName(), rowStyle);
                AppUtils.createStyledCell(row, 4, entity.getInsertionDate().toString(), rowStyle);
                AppUtils.createStyledCell(row, 5, entity.getStatus(), rowStyle);
                AppUtils.createStyledCell(row, 6, entity.getRemarks(), rowStyle);
            }

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




}
