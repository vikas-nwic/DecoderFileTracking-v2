package com.nwic.gt.decoder.file.tracking.system.services;

import com.nwic.gt.decoder.file.tracking.system.DTOs.DecoderAgencyDTO;
import com.nwic.gt.decoder.file.tracking.system.DTOs.DecoderApiResponse;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface DecoderAgencyService {

    //ResponseEntity<ApiResponse<MasterAgency>> getAgencyById(Integer agencyId);
    ResponseEntity<DecoderApiResponse<DecoderAgencyDTO>> getAllAgenciesInfo();
    DecoderApiResponse<Map<String, Object>> getFileStats();

}
