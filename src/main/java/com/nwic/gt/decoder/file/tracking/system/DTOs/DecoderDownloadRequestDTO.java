package com.nwic.gt.decoder.file.tracking.system.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor@NoArgsConstructor
public class DecoderDownloadRequestDTO {
    private List<Integer> agencyIds;
    private List<String> stationCodes;
    private Date fromDate;
    private Date toDate;
}
