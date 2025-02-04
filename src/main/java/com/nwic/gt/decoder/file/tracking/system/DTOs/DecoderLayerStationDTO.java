package com.nwic.gt.decoder.file.tracking.system.DTOs;//package com.nwic.gt.decoder.file.tracking.system.models;

import lombok.Data;

import java.util.List;

@Data
public class DecoderLayerStationDTO {

    private String stationName;
    private String stationCode;
    private String agency;
    private Integer ownerAgencyId;

    private List<Integer> agencyIds;  // used for Layer station list of data
}
