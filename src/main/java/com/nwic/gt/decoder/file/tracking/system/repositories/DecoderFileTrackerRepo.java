package com.nwic.gt.decoder.file.tracking.system.repositories;

import com.nwic.gt.decoder.file.tracking.system.constants.ApiConstants;
import com.nwic.gt.decoder.file.tracking.system.models.DecoderFileTracker;
import com.nwic.gt.decoder.file.tracking.system.projection.DecoderProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface DecoderFileTrackerRepo extends JpaRepository<DecoderFileTracker, Long> {

    //LocalDate currentDate = LocalDate.now(); // Current date (today)
    //LocalDate previousWeekDate = currentDate.minusDays(ApiConstants.DAY_RESTRICTION); // Date from 7 days ago



/*    @Query(value = "SELECT " +
            "a.agencyconfig_id AS agencyconfigId, " +
            "a.agency_id AS agencyId, " +
            "a.agency_name AS agencyName, " +
            "a.transmission_type AS transmissionType, " +
            "a.frequency_rate AS frequencyRate, " +
            "a.ground_water AS groundWater, " +
            "a.surface_water AS surfaceWater, " +
            "a.expected_count AS expectedCount, " +
            "d.id AS detailId, " +
            "d.sensor_hub_code AS sensorHubCode, " +
            "d.previous_received_cont AS previousReceivedCont, " +
            "d.previous_received_date AS previousReceivedDate, " +
            "d.received_file_count AS receivedFileCount, " +
            "d.received_file_date AS receivedFileDate, " +
            "d.created_date AS detailCreatedDate, " +
            "t.tracker_id AS trackerId, " +
            "t.agency_name AS trackerAgencyName, " +
            "t.station_code AS stationCode, " +
            "t.station_name AS stationName, " +
            "t.file_created_date AS fileCreatedDate, " +
            "t.file_name AS fileName, " +
            "t.insertion_date AS insertionDate, " +
            "t.is_sensitive_data AS isSensitiveData, " +
            "t.logger_id AS loggerId, " +
            "t.remarks AS remarks, " +
            "t.status AS trackerStatus, " +
            "t.agency_id AS trackerAgencyId " +
            "FROM telemetry_decoder_file_tracker_agencyconfig a " +
            "JOIN telemetry_decoder_file_tracker_details d ON a.agency_id = d.agency_id " +
            "LEFT JOIN telemetry_decoder_file_tracker t ON a.agency_id = CAST(t.agency_id AS INTEGER)  " +
            " WHERE t.insertion_date BETWEEN :startDate AND :endDate" +
            "AND t.agency_id IN (:agencyIds) "+
            "AND t.station_code IN (:stationCodes) ",
            nativeQuery = true)
    List<TelemetryDecoderFileTrackerProjection> getTelemetryDecoderFileTrackers(
            @Param("agencyIds") List<String> agencyIds,
            @Param("stationCodes") List<String> stationCodes,
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate
    );*/


    // for getDashboardStatsData
    @Query(value = "SELECT " +
            "DISTINCT  t.agency_id AS trackerAgencyId, " +
            //"a.agencyconfig_id AS agencyconfigId, " +
           // "a.agency_id AS agencyId, " +
          //  "a.agency_name AS agencyName, " +
          /*  "a.transmission_type AS transmissionType, " +
            "a.frequency_rate AS frequencyRate, " +
            "a.ground_water AS groundWater, " +
            "a.surface_water AS surfaceWater, " +*/
            "a.expected_count AS expectedCount, " +
           // "d.id AS detailId, " +
            "d.sensor_hub_code AS sensorHubCode, " +
            "d.previous_received_cont AS previousReceivedCont, " +
            "d.previous_received_date AS previousReceivedDate, " +
            "d.received_file_count AS receivedFileCount, " +
            "d.received_file_date AS receivedFileDate, " +
          /*  "d.created_date AS detailCreatedDate, " +
            "t.tracker_id AS trackerId, " +*/
            "t.agency_name AS trackerAgencyName, " +
            "t.station_code AS stationCode, " +
            "t.station_name AS stationName, " +
           /* "t.file_created_date AS fileCreatedDate, " +
            "t.file_name AS fileName, " +*/
            "t.insertion_date AS insertionDate, " +
            //"t.is_sensitive_data AS isSensitiveData, " +
            "t.logger_id AS loggerId, " +
            "t.remarks AS remarks, " +
            "t.status AS trackerStatus " +
//            "DISTINCT  t.agency_id AS trackerAgencyId " +
            "FROM telemetry_decoder_file_tracker_agencyconfig a " +
            "JOIN telemetry_decoder_file_tracker_details d ON a.agency_id = d.agency_id " +
            "LEFT JOIN telemetry_decoder_file_tracker t ON a.agency_id = CAST(t.agency_id AS INTEGER)" +
            "WHERE t.insertion_date BETWEEN :startDate AND :endDate " +
            "AND t.agency_id IN (:agencyIds) " +
            "AND t.station_code IN (:stationCodes)",
            nativeQuery = true)
    List<DecoderProjection> getTelemetryDecoderFileTrackers(
            @Param("agencyIds") List<Integer> agencyIds,
            @Param("stationCodes") List<String> stationCodes,
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate
    );

    @Query(value = "SELECT " +
           // "a.agencyconfig_id AS agencyconfigId, " +
           // "a.agency_id AS agencyId, " +
            "a.agency_name AS agencyName, " +
        /*    "a.transmission_type AS transmissionType, " +
            "a.frequency_rate AS frequencyRate, " +
            "a.ground_water AS groundWater, " +
            "a.surface_water AS surfaceWater, " +*/
            "a.expected_count AS expectedCount, " +
           // "d.id AS detailId, " +
            "d.sensor_hub_code AS sensorHubCode, " +
            "d.previous_received_cont AS previousReceivedCont, " +
            "d.previous_received_date AS previousReceivedDate, " +
            "d.received_file_count AS receivedFileCount, " +
            "d.received_file_date AS receivedFileDate, " +
          //  "d.created_date AS detailCreatedDate, " +
            "t.tracker_id AS trackerId, " +
            "t.agency_name AS trackerAgencyName, " +
            "t.station_code AS stationCode, " +
            "t.station_name AS stationName, " +
           // "t.file_created_date AS fileCreatedDate, " +
          //  "t.file_name AS fileName, " +
            "t.insertion_date AS insertionDate, " +
           // "t.is_sensitive_data AS isSensitiveData, " +
            "t.logger_id AS loggerId, " +
            "t.remarks AS remarks, " +
            "t.status AS trackerStatus, " +
            "t.agency_id AS trackerAgencyId " +
            "FROM telemetry_decoder_file_tracker_agencyconfig a " +
            "JOIN telemetry_decoder_file_tracker_details d ON a.agency_id = d.agency_id " +
            "LEFT JOIN telemetry_decoder_file_tracker t ON a.agency_id = t.agency_id " +
            "WHERE t.insertion_date BETWEEN :startDate AND :endDate " +
            "AND t.agency_id IN (:agencyIds) " +
            "AND t.station_code IN (:stationCodes)" +
            "and status='Fail' ",
            nativeQuery = true)
    List<DecoderProjection> getTelemetryDecoderFileTrackersErrorFiles(
            @Param("agencyIds") List<Integer> agencyIds,
            @Param("stationCodes") List<String> stationCodes,
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate
    );


// excel file download
    @Query(value = "SELECT * FROM telemetry_decoder_file_tracker d WHERE d.agency_id IN :agencyIds " +
            "AND d.station_code IN :stationCodes AND d.insertion_date BETWEEN :fromDate AND :toDate " +
            "AND d.status = 'Fail'", nativeQuery = true)
    List<DecoderFileTracker> findByAgencyIdInAndStationCodeInAndDataDateBetween(
            @Param("agencyIds") List<Integer> agencyIds,
            @Param("stationCodes") List<String> stationCodes,
            @Param("fromDate") Date fromDate,
            @Param("toDate") Date toDate);
    List<DecoderFileTracker> findByAgencyIdInAndStationCodeIn(
            List<Integer> agencyId,
            List<String> stationCode);


    @Query(value = "SELECT agency_name, station_code, file_name, insertion_date, logger_id, status, remarks " +
            "FROM telemetry_decoder_file_tracker " +
            " WHERE insertion_date BETWEEN CURRENT_DATE - INTERVAL '7 days' AND CURRENT_DATE " +
            "AND status = 'Fail'", nativeQuery = true)
    List<Object[]> findFailedData();//int previousWeekDate


}
