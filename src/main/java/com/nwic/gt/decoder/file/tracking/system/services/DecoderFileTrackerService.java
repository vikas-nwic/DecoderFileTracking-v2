package com.nwic.gt.decoder.file.tracking.system.services;

import com.nwic.gt.decoder.file.tracking.system.DTOs.*;
import com.nwic.gt.decoder.file.tracking.system.models.DecoderFileTracker;
import com.nwic.gt.decoder.file.tracking.system.projection.DecoderProjection;
import org.springframework.http.ResponseEntity;

public interface DecoderFileTrackerService {

   // public List<TelemetryDecoderFileTrackerProjection> getAllFileTrackers(TelemetryDecoderFileTrackerRequestDTO request);

   public ResponseEntity<DecoderApiResponse<DecoderProjection>> getAllFileTrackers(DecoderRequestDTO request);

   public ResponseEntity<DecoderApiResponse<DecoderProjection>> getAllErrorFiles(DecoderRequestDTO request);

   public ResponseEntity<DecoderApiResponse<DecoderFileTracker>> createDecoderFileTracker(DecoderFileTracker decoderFileTracker);


  // ResponseEntity<byte[]> downloadExcel(List<String> agencyIds, List<String> stationCode, Date fromDate, Date toDate);

   ResponseEntity<byte[]> downloadExcel(DecoderDownloadRequestDTO requestDto);

 //  public byte[] generateExcelFile() throws IOException;


}
