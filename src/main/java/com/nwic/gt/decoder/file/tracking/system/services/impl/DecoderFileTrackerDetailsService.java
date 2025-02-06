
/**
 * Author ${Vikas Predhva} on ${DAY} ${MONTH_NAME_SHORT}, ${YEAR}
 */

package com.nwic.gt.decoder.file.tracking.system.services.impl;

import com.nwic.gt.decoder.file.tracking.system.DTOs.DecoderOwnerAgencyIdDTO;
import com.nwic.gt.decoder.file.tracking.system.models.DecoderFileTrackerDetails;
import com.nwic.gt.decoder.file.tracking.system.repositories.DecoderFileTrackerDetailsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.List;

@Service
public class DecoderFileTrackerDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(DecoderFileTrackerDetailsService.class);

    @Autowired
    private DecoderFileTrackerDetailsRepository fileDetailsRepo;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional
    public void saveTelemetryData(DecoderFileTrackerDetails telemetryData) {
        try {
            if (telemetryData == null) {
                throw new IllegalArgumentException("Telemetry data cannot be null");
            }
            fileDetailsRepo.save(telemetryData);
            logger.info("Successfully saved telemetry data: {}", telemetryData);
        } catch (Exception e) {
            logger.error("Error saving data to the database Transactional ", e);
            throw new RuntimeException("Failed to save telemetry data", e);
        }
    }

    // Insert data into the database tbl telemetry_decoder_file_tracker_details
    public void insertTelemetryData( String sensorHubCode, String contentDate, String fileName) { //, int agencyId
       logger.info("Inserting data: sensorHubCode = {}, contentDate{} ",  sensorHubCode, contentDate );
        try {
            DecoderFileTrackerDetails telemetryData = telemetryData = new DecoderFileTrackerDetails();
                telemetryData.setSensorHubCode(sensorHubCode);
                telemetryData.setContentCount(1);
                telemetryData.setContentDate(contentDate);
                telemetryData.setFilename(fileName);
            fileDetailsRepo.saveAndFlush(telemetryData);  // Save to the repositorylogger.info("Data inserted successfully for receivedFileCount = {}", receivedDataCount);
        } catch (Exception e) {
                logger.error("Error saving data to the database{} ", e.getMessage());
        }
    }


    // heere behalf of sensorHubCode we are getting agency id
    public List<DecoderOwnerAgencyIdDTO> getOwnerAgencyIdsBySensorHubCode(String sensorHubCode) {
        String sql = "SELECT ls.owner_agency_id " +
                "FROM public.layer_station ls " +
                "INNER JOIN public.tel_sensor_hub tsh ON tsh.station_id = ls.station_code " +
                "WHERE tsh.sensor_hub_code = '"+sensorHubCode+"' " +
                "AND ls.owner_agency_id IS NOT NULL " +
                "AND ls.subdivisional_office_id <> 99999 " +
                "AND ls.owner_agency_id <> 99999 " +
                "AND ls.tahsil_id >= 1000000000 " +
                "AND ls.owner_agency_id <> 888 " +
                "ORDER BY ls.owner_agency_id";
        // Custom RowMapper to map the query result to OwnerAgencyIdDTO
        RowMapper<DecoderOwnerAgencyIdDTO> rowMapper = (rs, rowNum) -> new DecoderOwnerAgencyIdDTO(rs.getInt("owner_agency_id"));
        return jdbcTemplate.query(sql, new Object[]{sensorHubCode}, rowMapper);
    }


}
