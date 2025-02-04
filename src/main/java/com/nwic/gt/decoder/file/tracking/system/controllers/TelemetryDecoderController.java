package com.nwic.gt.decoder.file.tracking.system.controllers;

import com.nwic.gt.decoder.file.tracking.system.agencies.APGroundWaterAgency;
import com.nwic.gt.decoder.file.tracking.system.DTOs.DecoderOwnerAgencyIdDTO;
import com.nwic.gt.decoder.file.tracking.system.exception.ResourceNotFoundException;
import com.nwic.gt.decoder.file.tracking.system.services.impl.DecoderFileTrackerDetailsService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/decoder")
public class TelemetryDecoderController {

    @Autowired
    private DecoderFileTrackerDetailsService telemetryGprsDecoderService;

    @Autowired
    private APGroundWaterAgency apgwAgency;
    //@Scheduled(fixedRate = 1800000) // 30 minutes = 1800000 milliseconds
    //@Scheduled(fixedRate = 3600000) // 1 hour = 3600000 milliseconds
    @Operation(summary = "Save telemetry decoder file data", description = "This API will count number of files of current date and seven day older and  Save there data e.g. suppose today is 13 January 2025 then it will minus 7 day from current date , 13-7 days means it will read files from 07 JANUARY 2025  TO 07 JANUARY 2025")
    @PostMapping("/saveAgencyFileData")
    public ResponseEntity<Map<String, Object>> processCsvFiles() {
        Map<String, Object> response = new HashMap<>();

        try {
            apgwAgency.readAllDirectoryFiles(); // Process the files
            response.put("status_code", "200");
            response.put("message", "Files processed successfully.");
            return ResponseEntity.ok(response);
        } catch (ResourceNotFoundException e) {
            response.put("status_code", "404");
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (IOException e) {
            response.put("status_code", "500");
            response.put("message", "Error reading files: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        } catch (Exception e) {
            response.put("status_code", "500");
            response.put("message", "Internal Server Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/agency/owner-agency-ids")
    public ResponseEntity<List<DecoderOwnerAgencyIdDTO>> getOwnerAgencyIdsBySensorHubCode(@RequestParam String sensorHubCode) {
        List<DecoderOwnerAgencyIdDTO> ownerAgencyIds = telemetryGprsDecoderService.getOwnerAgencyIdsBySensorHubCode(sensorHubCode);
        if (ownerAgencyIds.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ownerAgencyIds);
    }

    //  @PostMapping("/GprsGwFile")
//    public ApiResponse<String> processCsvFiles() throws IOException {
//    ApiResponse<String> response = new ApiResponse<>();
//        List<Long> isFileExist = Collections.singletonList(telemetryutils.getFileCount());
//    System.out.println("file Size: "+isFileExist);
//        try {
//            if(isFileExist.size() == 0){
//                response.setStatus(HttpStatus.NOT_FOUND);
//                response.setMessage("No Record found!");
//                response.setData(Collections.singletonList("No File found"));
//            }else
//            {
//                telemetryutils.processCsvFiles();
//                response.setStatus(HttpStatus.OK);
//                response.setMessage("files processed successfully.");
//                response.setData(Collections.singletonList("files processed successfully."));
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
//            response.setMessage("Failed to process CSV files: " + e.getMessage());
//            response.setData(Collections.emptyList());
//        } catch (Exception e) {
//            e.printStackTrace();
//            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
//            response.setMessage("Internal Server Error: " + e.getMessage());
//            response.setData(Collections.emptyList());
//        }
//        return response;
//    }

//    public Map<String, Object> processCsvFiles() throws IOException {
//        Map<String, Object> response = new HashMap<>();
//
//        List<Long> isFileExist = Collections.singletonList(telemetryutils.getFileCount());
//        System.out.println("File Size: " + isFileExist);
//       // String IsDataExist = APGWDecoderUtils.checkCSVFile();
//        //System.out.println("@@@@@@ IsDataExist: "+IsDataExist);
//
//        try {
//            if (isFileExist.size() == 0 || isFileExist.get(0) == 0) {
//                response.put("status_code", "404");
//                response.put("message", "No Record found!");
//            } else {
//                telemetryutils.processCsvFiles();
//                //System.out.println(telemetryutils.processCsvFiles());
//                response.put("status_code", "200");
//                response.put("message", "Files processed successfully.");
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//            response.put("status_code", "500");
//           // response.put("status", HttpStatus.INTERNAL_SERVER_ERROR);
//            response.put("error", "Failed to process CSV files: " + e.getMessage());
//        } catch (Exception e) {
//            e.printStackTrace();
//            response.put("status_code", "500");
//           // response.put("status", HttpStatus.INTERNAL_SERVER_ERROR);
//            response.put("error", "Internal Server Error: " + e.getMessage());
//            //  response.put("data", Collections.emptyList());
//        }
//
//        return response;
//    }


//    @PostMapping("/GprsGwFile")
//    public ResponseEntity<Map<String, Object>> processCsvFiles() {
//        Map<String, Object> response = new HashMap<>();
//
//        try {
//            long fileCount = telemetryUtils.getFileCount();
//            if (fileCount == 0) {
//                response.put("status_code", "404");
//                response.put("message", "No files found in the directory to process.");
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
//            }
//
//            telemetryUtils.processCsvFiles(); // Process the files
//            response.put("status_code", "200");
//            response.put("message", "Files processed successfully.");
//            return ResponseEntity.ok(response);
//        } catch (IOException e) {
//            response.put("status_code", "500");
//            response.put("message", "Error reading files: " + e.getMessage());
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
//        } catch (IllegalArgumentException e) {
//            response.put("status_code", "400");
//            response.put("message", "Validation error: " + e.getMessage());
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
//        } catch (Exception e) {
//            response.put("status_code", "500");
//            response.put("message", "Internal Server Error: " + e.getMessage());
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
//        }
//    }





//    @GetMapping("/telemetry/extractDate")
//    public String extractDate(@RequestParam String fileName) {
//        final long processedTime = System.currentTimeMillis();
//        System.out.println("system time: "+processedTime);
//        Optional<LocalDate> extractedDate = telemetryutils.extractDateFromFileName(fileName);
//        return extractedDate
//                .map(date -> "Extracted date: " + date)
//                .orElse("Date could not be extracted.");
//    }


    /////////////////////////////////////////// previous code
    //    @Autowired
//    private TelemetryGprsDecoderAPGWService telemetryGprsDecoderService;
//
//    @Autowired
//    private Telemetryutils telemetryutils;
//
//    @PostMapping("/telemetry/gprs/gw")
//    public ResponseEntity<DecoderFileTrackerApiResponse<List<String>>> processCsvFiles() throws IOException {
//        DecoderFileTrackerApiResponse<List<String>> response = telemetryGprsDecoderService.GprsGwFileProcessing();
//        return new ResponseEntity<>(response, response.getStatus());
//    }
//
//
//    @GetMapping("/telemetry/extractDate")
//    public String extractDate(@RequestParam String fileName) {
//        final long processedTime = System.currentTimeMillis();
//        System.out.println("system time: "+processedTime);
//        Optional<LocalDate> extractedDate = telemetryutils.extractDateFromFileName(fileName);
//        return extractedDate
//                .map(date -> "Extracted date: " + date)
//                .orElse("Date could not be extracted.");
//    }

}
