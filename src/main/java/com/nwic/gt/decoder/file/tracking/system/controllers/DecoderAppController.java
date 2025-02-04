package com.nwic.gt.decoder.file.tracking.system.controllers;

import com.nwic.gt.decoder.file.tracking.system.DTOs.*;
import com.nwic.gt.decoder.file.tracking.system.models.DecoderFileTracker;
import com.nwic.gt.decoder.file.tracking.system.models.DecoderTicketCreationEntity;
import com.nwic.gt.decoder.file.tracking.system.projection.DecoderStationAgencyProjection;
import com.nwic.gt.decoder.file.tracking.system.projection.DecoderProjection;
import com.nwic.gt.decoder.file.tracking.system.repositories.MDTicketCreationRepo;
import com.nwic.gt.decoder.file.tracking.system.services.DecoderAgencyService;
import com.nwic.gt.decoder.file.tracking.system.services.DecoderFileTrackerService;
import com.nwic.gt.decoder.file.tracking.system.services.DecoderLayerStationService;
import com.nwic.gt.decoder.file.tracking.system.services.impl.MailSenderService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * This controller will use to hit all end points that related to ...
 * @author Vikas Predhva
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/decoder")
public class DecoderAppController {

    @Autowired
    private DecoderFileTrackerService fileTrackerService;

    @Autowired
    private DecoderAgencyService agencyService;

    @Autowired
    private DecoderLayerStationService layerStationService;

    @Autowired
    private MailSenderService excelMailer;


    @Operation(summary = "Test", description = "This is a test API.")
    @GetMapping("/test")
    public String  testAPI() {
        return "Api tested successfully ";
    }

    @Operation(summary = "All Agencies records", description = "This API will give Agency records.")
    @PostMapping("/agencies/getAllAgency")
    public ResponseEntity<DecoderApiResponse<DecoderAgencyDTO>> getAllAgenciesInfo() {
        return agencyService.getAllAgenciesInfo();
    }

    @Operation(summary = "All Stations records", description = "This API will give Station basis on Agency records.")
    @PostMapping("/stations/getAllStations")
    public ResponseEntity<DecoderApiResponse<DecoderStationAgencyProjection>> getStationBasedOnAgency(@RequestBody DecoderLayerStationDTO request) {
        return layerStationService.getStationBasedOnAgency(request);
    }

    @Operation(summary = "Dashboard Stats records", description = "This API will give Dashboard Stats records.")
    @PostMapping("/getDashboardStatsData")
    public ResponseEntity<DecoderApiResponse<DecoderProjection>> getDashboardStatsData(@RequestBody DecoderRequestDTO request) {
        return fileTrackerService.getAllFileTrackers(request);
    }

    @Operation(summary = "All Error Files records", description = "This API will give all telemetry error files records ")
    @PostMapping("/getAllErrorFiles")
    public ResponseEntity<DecoderApiResponse<DecoderProjection>> getAllErrorFiles(@RequestBody DecoderRequestDTO request) {
        return fileTrackerService.getAllErrorFiles(request);
    }

    @Operation(summary = "All Agency Stats till dated", description = "This API will give All Agency Stats. last seven day data in which total Files, processed Files, non-Process Files")
    @PostMapping("/agencies/getAllAgencyStats")
    public DecoderApiResponse<Map<String, Object>> getFileStats() {
        return agencyService.getFileStats();
    }

    @Operation(summary = "Post data decoder File Tracker Data", description = "This API will Post data decoder File Tracker")
    @PostMapping("/saveFileData")
    public ResponseEntity<DecoderApiResponse<DecoderFileTracker>> createDecoderFileTracker(@RequestBody DecoderFileTracker decoderFileTracker) {
        return  fileTrackerService.createDecoderFileTracker(decoderFileTracker);
    }


    // status is fails make excel and attach this to mail api

    @GetMapping("/download/file")
    public ResponseEntity<byte[]> downloadExcel(
            @RequestParam List<Integer> agencyId,
            @RequestParam List<String> stationCodes,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fromDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date toDate)
            throws IOException {
        DecoderDownloadRequestDTO requestDTO = new DecoderDownloadRequestDTO();
        requestDTO.setAgencyIds(agencyId);
        requestDTO.setStationCodes(stationCodes);
        requestDTO.setFromDate(fromDate);
        requestDTO.setToDate(toDate);
        return fileTrackerService.downloadExcel(requestDTO);
    }

//    @GetMapping("/sendMailAttachment")
//    public void exportExcel(HttpServletResponse response) throws IOException {
//        ByteArrayInputStream excelFile = fileTrackerService.generateExcelFile();
//
//        String filename = AppUtils.generateUniqueFilename("Agency_DataIssues-Report", "xlsx");
//        // Set the response headers
//        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
//        response.setHeader("Content-Disposition", "attachment; filename="+filename+"");
//
//        // Write the file content to the response output stream
//        try (OutputStream out = response.getOutputStream()) {
//            byte[] buffer = new byte[1024];
//            int bytesRead;
//            while ((bytesRead = excelFile.read(buffer)) != -1) {
//                out.write(buffer, 0, bytesRead);
//            }
//            out.flush();
//        } catch (IOException e) {
//            // Log the exception if needed
//            e.printStackTrace();
//            throw new RuntimeException("Error while writing Excel file to response", e);
//        }
//    }

//    @PostMapping("/stats")
//    public List<TelemetryDecoderFileTrackerDTO> getTelemetryDecoderFileTrackers(
//            @RequestBody TelemetryDecoderFileTrackerRequestDTO request) {
//        return fileTrackerService.getTelemetryDecoderFileTrackers(
//                request.getAgencyIds(),
//                request.getStationCodes(),
//                request.getStartDate(),
//                request.getEndDate()
//        );
//    }


//    @PostMapping
//    public ResponseEntity<DecoderFileTracker> createDecoderFileTracker(@RequestBody DecoderFileTracker decoderFileTracker) {
//        // Save the entity and return the saved object in the response
//        DecoderFileTracker savedEntity = fileTrackerService.saveDecoderFileTracker(decoderFileTracker);
//        return new ResponseEntity<>(savedEntity, HttpStatus.CREATED);
//    }
//
//    @GetMapping("/tracker/download/file")
//    public ResponseEntity<byte[]> downloadExcel(
//            @RequestParam List<String> agencyId,
//            @RequestParam List<String> stationCodes,
//            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fromDate,
//            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date toDate)
//            throws IOException {
//        DownloadRequestDTO requestDTO = new DownloadRequestDTO();
//        requestDTO.setAgencyIds(agencyId);
//        requestDTO.setStationCodes(stationCodes);
//        requestDTO.setFromDate(fromDate);
//        requestDTO.setToDate(toDate);
//        return fileTrackerService.downloadExcel(requestDTO);
//    }

   // @PostMapping("/tracker/download/files")
//    @GetMapping("/download/getExcelReport")
//    public ResponseEntity<byte[]> downloadExcel(@RequestBody DownloadRequestDTO requestDto) {
//        return fileTrackerService.downloadExcel(requestDto);
//    }

//    @PostMapping("/getDashboardStatsData")//agency-details
//    public ApiResponse<AgencyDetailsResponse> getAgencyDetails() {
//        List<AgencyDetailsResponse> agencyDetailsList = fileTrackerService.getAllAgencyDetails();
//
//        if(agencyDetailsList.isEmpty()){
//            ApiResponse<AgencyDetailsResponse> response = new ApiResponse<>();
//            response.setStatus(HttpStatus.NOT_FOUND);
//            response.setMessage("No agency data found!");
//            response.setData(agencyDetailsList);
//        }
//        ApiResponse<AgencyDetailsResponse> response = new ApiResponse<>();
//        response.setStatus(HttpStatus.valueOf(HttpStatus.OK.value()));
//        response.setMessage("Agency data found");
//        response.setData(agencyDetailsList);
//        return response;
//    }


    // new 05-01-2025
//    @PostMapping("/testStats")
//    public List<AgencyStationDateDTO> getStartAndEndDates(
//            @RequestBody DateRangeRequestDTO request) {
//        return fileTrackerService.getStartAndEndDates(
//                request.getAgencyIds(),
//                request.getStationCodes(),
//                request.getStartDate(),
//                request.getEndDate());
//    }

    // created date 09-01-2025

//    @PostMapping("/agencies/getAllAgencyStats")
//    public Map<String, Object> getFileStats() {
//        return agencyService.getFileStats(); // This returns a Map with key-value pairs
//    }


    @GetMapping("/genTicketAndSendAtt")
    public void exportExcel(HttpServletResponse response) throws IOException {
        try {
            excelMailer.sendEmailWithAttachment();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Autowired
    private MDTicketCreationRepo repository;


    @PostMapping("/ticketGen")
    public ResponseEntity<DecoderApiResponse<DecoderTicketCreationEntity>> ticketGenerate(@RequestBody DecoderTicketCreationEntity ticket) {
        try {
           // repository.save(ticket);
            DecoderApiResponse<DecoderTicketCreationEntity> apiResponse = new DecoderApiResponse<>(HttpStatus.CREATED, "Assigned successfully", Collections.singletonList(ticket));
            return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
        } catch (Exception e) {
            DecoderApiResponse<DecoderTicketCreationEntity> errorResponse = new DecoderApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR, "data not saved: " + e.getMessage(), Collections.emptyList());
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/genTicket")
    public void sendMail(HttpServletResponse response) throws IOException {
        try {
            excelMailer.sendEmailWithAttachment();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}