package com.nwic.gt.decoder.file.tracking.system.services.impl;

import com.nwic.gt.decoder.file.tracking.system.DTOs.DecoderApiResponse;
import com.nwic.gt.decoder.file.tracking.system.DTOs.DecoderLayerStationDTO;
import com.nwic.gt.decoder.file.tracking.system.projection.DecoderStationAgencyProjection;
import com.nwic.gt.decoder.file.tracking.system.services.DecoderLayerStationService;
import com.nwic.gt.decoder.file.tracking.system.repositories.DecoderFileTrackerLayerStationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class DecoderStationServiceImpl implements DecoderLayerStationService {

    @Autowired
    private DecoderFileTrackerLayerStationRepository layerStationRepository;

    @Override
    public ResponseEntity<DecoderApiResponse<DecoderStationAgencyProjection>> getStationBasedOnAgency(DecoderLayerStationDTO request)
    {
        DecoderApiResponse<DecoderStationAgencyProjection> apiResponse = new DecoderApiResponse<>();

        if (request == null || request.getAgencyIds() == null || request.getAgencyIds().isEmpty()) {
            apiResponse.setStatus(HttpStatus.BAD_REQUEST);
            apiResponse.setMessage("The provided agency IDs list is empty or null.");
            apiResponse.setData(Collections.emptyList());
            return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
        }
        List<Integer> agencyIdsList = request.getAgencyIds();
        try {
            List<DecoderStationAgencyProjection> stationAgencyDetails = layerStationRepository.findStationAndAgencyDetailsByAgencyIds(agencyIdsList);
            if (stationAgencyDetails.isEmpty()) {
                apiResponse.setStatus(HttpStatus.NOT_FOUND);
                apiResponse.setMessage("Station records not found!");
                apiResponse.setData(Collections.emptyList());
                return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
            }
            apiResponse.setStatus(HttpStatus.OK);
            apiResponse.setMessage("Station records found.");
            apiResponse.setData(stationAgencyDetails);
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);

        } catch (Exception e) {
            apiResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            apiResponse.setMessage("An error occurred while retrieving station records: " + e.getMessage());
            apiResponse.setData(Collections.emptyList());
            return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
