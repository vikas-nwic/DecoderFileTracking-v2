package com.nwic.gt.decoder.file.tracking.system.repositories;

import com.nwic.gt.decoder.file.tracking.system.DTOs.DecoderAgencyAndDetailsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AgencyDetailsRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<DecoderAgencyAndDetailsResponse> getAgencyDetails() {
        String sql = "SELECT a.agencyconfig_id, a.agency_id, a.agency_name, a.transmission_type, a.frequency_rate, " +
                "a.ground_water, a.surface_water, a.expected_count, " +
                "d.id AS detail_id, d.sensor_hub_code, d.previous_received_cont, d.previous_received_date, " +
                "d.received_file_count, d.received_file_date, d.created_date AS detail_created_date " +
                "FROM telemetry_decoder_file_tracker_agencyconfig a " +
                "JOIN telemetry_decoder_file_tracker_details d ON a.agency_id = d.agency_id";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(DecoderAgencyAndDetailsResponse.class));
    }

}
