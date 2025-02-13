package com.nwic.gt.decoder.file.tracking.system.agencies;

import com.nwic.gt.decoder.file.tracking.system.constants.ApiConstants;
import com.nwic.gt.decoder.file.tracking.system.exception.InvalidSensorHubCodeFoundException;
import com.nwic.gt.decoder.file.tracking.system.exception.ResourceNotFoundException;
import com.nwic.gt.decoder.file.tracking.system.models.DecoderFileTrackerDetails;
import com.nwic.gt.decoder.file.tracking.system.repositories.DecoderFileTrackerDetailsRepository;
import com.nwic.gt.decoder.file.tracking.system.services.impl.DecoderFileTrackerDetailsService;
import com.nwic.gt.decoder.file.tracking.system.utils.DecoderUtils;
import lombok.var;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Author: Vikas Predhva
 * Organization: Grant Thornton
 * Date: 05-02-2025
 * Description: Telemetry Decoder File Tracker Andhra Pradesh Ground Water Agency
 */

@Component
public class AndhraPradeshGroundWaterAgency {

    @Autowired
    private DecoderFileTrackerDetailsService decoderFileTrackerDetailsService;

    private static final Logger logger = LoggerFactory.getLogger(AndhraPradeshGroundWaterAgency.class);
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yy HH:mm");
    private static final String  folderPath = ApiConstants.FTP_DATA_AP_GW;
    private static final Integer  DAY_RESTRICTION = ApiConstants.DAY_RESTRICTION;


    @Autowired
    private DecoderFileTrackerDetailsRepository repository;

    // Method to process CSV files in the provided folder path
    public void readAllDirectoryFiles() throws IOException {
        logger.info("ap_gw readAllDirectoryFiles start...");
        Path folder = Paths.get(folderPath);
        if (!Files.isDirectory(folder)) {
            throw new IOException(String.format("Directory not found: %s", folderPath));
        }

        LocalDate currentDate = LocalDate.now();
        LocalDate lastDate = currentDate.minusDays(DAY_RESTRICTION);
        try (var files = Files.walk(folder)) {
            List<Path> fileList = files.filter(path -> path.toString().endsWith(".csv")).collect(Collectors.toList());
            boolean recordFound = false;
            boolean fileFound = !fileList.isEmpty();
            for (Path file : fileList) {
                try {
                    String fileName = file.getFileName().toString();
                    List<DecoderFileTrackerDetails> fileTrackerDetails = repository.findByFilename(fileName);
                    logger.info("fileTrackerDetails: " + fileTrackerDetails);

                    // Check if the fileName already exists in the fileTrackerDetails list
                    boolean fileAlreadyProcessed = fileTrackerDetails.stream()
                            .anyMatch(detail -> detail.getFilename().equals(fileName));
                    if (fileAlreadyProcessed) {
                        continue;  // Skip this file if it has already been processed
                    }

                    boolean fileHasRecords = readSingleFile(file, currentDate, lastDate);
                    if (fileHasRecords) {
                        recordFound = true;
                    }
                } catch (FileNotFoundException e) {
                    logger.error("File not found: " + e.getMessage());
                } catch (Exception e) {
                    logger.error("Error processing file: {}", file.toString(), e);
                    throw e;
                }
            }
            if (!fileFound) {
                throw new ResourceNotFoundException("No file found");
            }
            if (!recordFound) {
                throw new ResourceNotFoundException("No record found in the file");
            }
        } catch (IOException e) {
            logger.error("Error reading directory: {}", folderPath, e);
            throw e;
        }
        //saveProcessedFiles(processedFiles, ApiConstants.AP_GW_PROCESSED_FILES_PATH);  // temporary comment out for testing purpose
        logger.info("ap_gw readAllDirectoryFiles() end...");
    }


    // Method to process an individual file
//    private boolean readSingleFile(Path csvFile, LocalDate currentDate, LocalDate lastDate) throws IOException {
//
//        boolean recordFound = false;
//        String contentDate = null;
//        int contentCount = 0;
//        String sensorHubCode = null;
//        String line;
//        logger.info("ap_gw readSingleFile() start..."+currentDate);
//
//        try (BufferedReader reader = Files.newBufferedReader(csvFile)) {
//
//            String fileName = csvFile.getFileName().toString();
//            //fileName = fileName.substring(0, fileName.length() - 4);
//            fileName = fileName.replaceAll("\\.csv$", "");
//            boolean isValidFile = DecoderUtils.fileDateValidation(fileName);
//            logger.info("isValidFile: "+isValidFile);
//
//        if(isValidFile){
//            logger.info("============ isValidFile condition ============");
////            if ((line = reader.readLine()) != null) {
////                logger.info("Skipping header: " + line);
////            }
//            while ((line = reader.readLine()) != null) {
//
//                if (line.trim().isEmpty()) continue; // Skip blank lines
//                String[] columns = line.split(",");
//                if (columns.length < 2) continue; // Skip invalid lines
//
//                sensorHubCode = columns[0].trim();
//                contentDate = columns[1].trim();
//
//                boolean isValidContentDate = DecoderUtils.contentDateValidation(contentDate);
//                logger.info("isValidContentDate: "+isValidContentDate);
//
//                if(isValidContentDate){
//                    LocalDateTime dateTime = LocalDateTime.parse(contentDate, DATE_TIME_FORMATTER);
//                    logger.info("currentDate: " + currentDate + " lastDate: " + lastDate + "content date: " + contentDate);
//                    contentCount++;
//                        contentDate = contentDate;
//                        recordFound = true;
//                        logger.info("sensorHubCode: " + sensorHubCode);
//                        if (sensorHubCode.startsWith("&")) {
//                            String cleanedSensorHubCode = sensorHubCode.substring(1).trim();
//                            logger.info("Inserting for sensorHubCode: " + cleanedSensorHubCode);
//                            detailsService.insertTelemetryData(cleanedSensorHubCode, contentDate);
//                        } else {
//                            throw new InvalidSensorHubCodeFoundException("Invalid Sensor Hub Code ");
//                        }
//                } // end content date check
//            }  // end while loop
//        } // is file valid check
//            else {
//            throw new ResourceNotFoundException("No file find to process");
//        }
//
//        }
//        logger.info("ap_gw readSingleFile() end...");
//        return recordFound;
//    }

    // Method to process an individual file
    private boolean readSingleFile(Path csvFile, LocalDate currentDate, LocalDate lastDate) throws IOException {
        boolean recordFound = false;
        String contentDate = null;
        int contentCount = 0;
        String sensorHubCode = null;
        String line;
        logger.info("readSingleFile() start... " + currentDate);
        try (BufferedReader reader = Files.newBufferedReader(csvFile)) {
            String fileName = csvFile.getFileName().toString();
            //fileName = fileName.substring(0, fileName.length() - 4);
            fileName = fileName.replaceAll("\\.csv$", "");
            boolean isValidFile = DecoderUtils.fileDateValidation(fileName);
            logger.info("isValidFile: " + isValidFile);
            if (isValidFile) {
                logger.info("Processing valid file: " + fileName);
                while ((line = reader.readLine()) != null) {
                    if (line.trim().isEmpty()) continue; // Skip blank lines
                    String[] columns = line.split(",");
                    if (columns.length < 2) continue; // Skip invalid lines
                    sensorHubCode = columns[0].trim();
                    contentDate = columns[1].trim();
                    boolean isValidContentDate = DecoderUtils.contentDateValidation(contentDate);
                    logger.info("isValidContentDate: " + isValidContentDate);
                    if (isValidContentDate) {
                        //LocalDateTime dateTime = LocalDateTime.parse(contentDate, DATE_TIME_FORMATTER);
                        contentCount++;
                        recordFound = true;
                        logger.info("Record found: Sensor Hub Code: " + sensorHubCode + ", Date: " + contentDate);
                        if (sensorHubCode.startsWith("&")) {
                            String cleanedSensorHubCode = sensorHubCode.substring(1).trim();
                            // Inserting data into database or processing logic
                            decoderFileTrackerDetailsService.insertTelemetryData(cleanedSensorHubCode, contentDate, csvFile.getFileName().toString(), "ap_gw");
                    } else {
                        //throw new InvalidSensorHubCodeFoundException("Invalid Sensor Hub Code: " + sensorHubCode);
                        String cleanedSensorHubCode = sensorHubCode.trim();
                        decoderFileTrackerDetailsService.insertTelemetryData(cleanedSensorHubCode, contentDate, csvFile.getFileName().toString(), "ap_gw");
                    }
                    }
                }
            } else {
                logger.warn("File " + csvFile + " is invalid and will be skipped.");
            }
        } catch (IOException e) {
            logger.error("Error reading file: " + csvFile, e);
        } catch (InvalidSensorHubCodeFoundException e) {
            logger.error("Invalid sensor hub code in file: " + csvFile, e);
        }
        logger.info("readSingleFile() end...");
        return recordFound;
    }

}
