package com.nwic.gt.decoder.file.tracking.system.repositories;

import com.nwic.gt.decoder.file.tracking.system.constants.ApiConstants;
import com.nwic.gt.decoder.file.tracking.system.models.DecoderMasterAgency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface DecoderFileTrackerAgencyRepository extends JpaRepository<DecoderMasterAgency, Integer> {

    //LocalDate currentDate = LocalDate.now(); // Current date (today)
   // LocalDate previousWeekDate = currentDate.minusDays(ApiConstants.DAY_RESTRICTION); // Date from 7 days ago

    @Query(value = "SELECT DISTINCT ma.name, ls.owner_agency_id " +
            "FROM layer_station ls " +
            "INNER JOIN master_agency ma ON ls.owner_agency_id = ma.agency_id " +
            "WHERE ls.owner_agency_id IS NOT NULL " +
            "AND ls.subdivisional_office_id <> 99999 " +
            "AND ls.owner_agency_id <> 99999 " +
            "AND ls.tahsil_id >= 1000000000 " +
            "AND ls.owner_agency_id <> 888 " +
            "AND ls.telemetric = TRUE " +
            "ORDER BY ma.name", nativeQuery = true)
    List<Tuple> findAgenciesAsTuples();


    // query modification required
    @Query(value = "SELECT " +
            "COALESCE(SUM(a.expected_count), 0) AS total_files, " +
            "COALESCE(SUM(d.received_file_count), 0) AS current_date_files_count, " +
            "COALESCE(SUM(d.previous_received_cont), 0) AS previous_file_count, " +
            "COALESCE(SUM(CASE WHEN t.status = 'fail' THEN 1 ELSE 0 END), 0) AS issue_files, " +
            "COALESCE(SUM(CASE WHEN t.status = 'pass' THEN 1 ELSE 0 END), 0) AS correct_files " +
            "FROM telemetry_decoder_file_tracker_agencyconfig a " +
            "LEFT JOIN telemetry_decoder_file_tracker_details d ON a.agency_id = d.agency_id " +
            "LEFT JOIN telemetry_decoder_file_tracker t ON a.agency_id = t.agency_id " +
            "AND t.insertion_date BETWEEN CURRENT_DATE - INTERVAL '?1 days' AND CURRENT_DATE",
            nativeQuery = true)
    List<Object[]> getFileStatsRaw(int previousWeekDate);


}