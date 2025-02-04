package com.nwic.gt.decoder.file.tracking.system.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DecoderFileStatsDTO {
    private long totalFiles;
    private long currentDateFilesCount;
    private long previousFileCount;
    private long issueFiles;
    private long correctFiles;

}