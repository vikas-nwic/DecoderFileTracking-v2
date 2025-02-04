package com.nwic.gt.decoder.file.tracking.system.services;

import com.nwic.gt.decoder.file.tracking.system.DTOs.DecoderApiResponse;
import com.nwic.gt.decoder.file.tracking.system.DTOs.DecoderLayerStationDTO;
import com.nwic.gt.decoder.file.tracking.system.projection.DecoderStationAgencyProjection;
import org.springframework.http.ResponseEntity;

public interface DecoderLayerStationService {
    ResponseEntity<DecoderApiResponse<DecoderStationAgencyProjection>> getStationBasedOnAgency(DecoderLayerStationDTO request);
}
