package com.nwic.gt.decoder.file.tracking.system.repositories;

import com.nwic.gt.decoder.file.tracking.system.projection.DecoderStationAgencyProjection;
import com.nwic.gt.decoder.file.tracking.system.models.DecoderLayerStation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DecoderFileTrackerLayerStationRepository extends JpaRepository<DecoderLayerStation, String> {

    @Query("SELECT DISTINCT ls.name AS stationName, ls.stationCode AS stationCode, ma.name AS agency, ls.ownerAgencyId AS ownerAgencyId " +
            "FROM DecoderLayerStation ls " +
            "JOIN DecoderMasterAgency ma ON ls.ownerAgencyId = ma.agencyId " +
            "WHERE ls.ownerAgencyId IS NOT NULL " +
            "AND ls.subdivisionalOfficeId <> 99999 " +
            "AND ls.ownerAgencyId <> 99999 " +
            "AND ls.tahsilId >= 1000000000 " +
            "AND ls.ownerAgencyId <> 888 " +
            "AND ls.telemetric = true " +
            "AND ls.ownerAgencyId = 41 " +
            "ORDER BY ls.name")
    List<DecoderStationAgencyProjection> findStationAndAgencyDetails();

//    @Query("SELECT DISTINCT ls.name AS stationName, ls.stationCode AS stationCode, ma.name AS agency, ls.ownerAgencyId AS ownerAgencyId " +
//            "FROM LayerStation ls " +
//            "JOIN MasterAgency ma ON ls.ownerAgencyId = ma.agencyId " +
//            "WHERE ls.ownerAgencyId IS NOT NULL " +
//            "AND ls.subdivisionalOfficeId <> 99999 " +
//            "AND ls.ownerAgencyId <> 99999 " +
//            "AND ls.tahsilId >= 1000000000 " +
//            "AND ls.ownerAgencyId <> 888 " +
//            "AND ls.telemetric = true " +
//            "AND ls.ownerAgencyId IN :ownerAgencyId " +    // Updated the parameter name
//            "ORDER BY ls.name")
//    List<StationAgencyProjection> findStationAndAgencyDetailsByAgencyIds(@Param("ownerAgencyId") List<Integer> ownerAgencyId);
  // List<StationAgencyProjection> findStationAndAgencyDetailsByAgencyIds(@Param("ownerAgencyId") List<Long> ownerAgencyId);

    @Query("SELECT DISTINCT ls.name AS stationName, ls.stationCode AS stationCode, ma.name AS agency, ls.ownerAgencyId AS ownerAgencyId " +
            "FROM DecoderLayerStation ls " +
            "JOIN DecoderMasterAgency ma ON ls.ownerAgencyId = ma.agencyId " +
            "WHERE ls.ownerAgencyId IS NOT NULL " +
            "AND ls.subdivisionalOfficeId <> 99999 " +
            "AND ls.ownerAgencyId <> 99999 " +
            "AND ls.tahsilId >= 1000000000 " +
            "AND ls.ownerAgencyId <> 888 " +
            "AND ls.telemetric = true " +
            "AND ls.ownerAgencyId IN :agencyIdsList " +  // Updated to match the controller parameter
            "ORDER BY ls.name")
    List<DecoderStationAgencyProjection> findStationAndAgencyDetailsByAgencyIds(@Param("agencyIdsList") List<Integer> agencyIdsList);




}
