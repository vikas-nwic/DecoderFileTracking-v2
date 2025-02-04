package com.nwic.gt.decoder.file.tracking.system.projection;

import java.time.LocalDateTime;

public interface DecoderProjection {
   // Integer getAgencyconfigId();
   // String getAgencyId();
   // String getAgencyName();
    //String getTransmissionType();
  //  String getFrequencyRate();
   // Boolean getGroundWater();
   // Boolean getSurfaceWater();
    Integer getExpectedCount();
    //Integer getDetailId();
    String getSensorHubCode();
    Integer getPreviousReceivedCont();
    String getPreviousReceivedDate();
    Integer getReceivedFileCount();
    String getReceivedFileDate();
    //LocalDateTime getDetailCreatedDate();
   // Integer getTrackerId();
    String getTrackerAgencyName();
    String getStationCode();
    String getStationName();
    //LocalDateTime getFileCreatedDate();
  //  String getFileName();
    LocalDateTime getInsertionDate();
   // Boolean getIsSensitiveData();
    String getLoggerId();
    String getRemarks();
    String getTrackerStatus();
    Integer getTrackerAgencyId();
}
