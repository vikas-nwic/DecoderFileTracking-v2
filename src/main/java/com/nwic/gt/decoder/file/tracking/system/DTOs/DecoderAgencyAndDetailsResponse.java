package com.nwic.gt.decoder.file.tracking.system.DTOs;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DecoderAgencyAndDetailsResponse {
    //private Integer agencyConfigId;
    private Integer agencyId;
    private String agencyName;
    private String transmissionType;
    private Integer frequencyRate;
    private Integer groundWater;
    private Integer surfaceWater;
    private Integer expectedCount;
    private Long detailId;
    private String sensorHubCode;
    private Integer previousReceivedCont;
    private String previousReceivedDate;
    private Integer receivedFileCount;
    private String receivedFileDate;
    private LocalDateTime detailCreatedDate;
}
