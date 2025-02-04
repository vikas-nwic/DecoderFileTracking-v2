package com.nwic.gt.decoder.file.tracking.system.services.impl;

import com.nwic.gt.decoder.file.tracking.system.DTOs.DecoderAgencyDTO;
import com.nwic.gt.decoder.file.tracking.system.DTOs.DecoderApiResponse;
import com.nwic.gt.decoder.file.tracking.system.repositories.DecoderFileTrackerAgencyRepository;
import com.nwic.gt.decoder.file.tracking.system.services.DecoderAgencyService;
import com.nwic.gt.decoder.file.tracking.system.utils.AppUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.Tuple;
import java.util.*;

@Service
public class DecoderAgencyServiceImpl implements DecoderAgencyService {

    @Autowired
    private DecoderFileTrackerAgencyRepository agencyRepository;

    private static final Logger logger = LoggerFactory.getLogger(DecoderAgencyServiceImpl.class);

    @Value("${day.restriction}")
    private int dateRangeDays;


    @Override
    public ResponseEntity<DecoderApiResponse<DecoderAgencyDTO>> getAllAgenciesInfo() {
        try {
            List<Tuple> tuples = agencyRepository.findAgenciesAsTuples();
            List<DecoderAgencyDTO> agencyInfoList = new ArrayList<>();
            for (Tuple tuple : tuples) {
                String name = tuple.get("name", String.class);
                Integer ownerAgencyId = tuple.get("owner_agency_id", Integer.class);
                DecoderAgencyDTO agencyInfoDTO = new DecoderAgencyDTO(ownerAgencyId, name);
                agencyInfoList.add(agencyInfoDTO);
            }
            if (agencyInfoList.isEmpty()) {
                DecoderApiResponse<DecoderAgencyDTO> response = new DecoderApiResponse<>(HttpStatus.NOT_FOUND, "Agency records not found!", agencyInfoList);
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
            DecoderApiResponse<DecoderAgencyDTO> response = new DecoderApiResponse<>(HttpStatus.OK, "Agency records found", agencyInfoList);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public DecoderApiResponse<Map<String, Object>> getFileStats() {

        DecoderApiResponse<Map<String, Object>> response = null;
        Map<String, Object> resultMap = new HashMap<>();

        try {
            List<Object[]> result = agencyRepository.getFileStatsRaw( dateRangeDays);
            logger.info("result: "+result.size());
            response = new DecoderApiResponse<>();
            if (!result.isEmpty()) {
                Object[] row = result.get(0);
                long totalFile = AppUtils.convertToLong(row[0]);
                long currentDateFilesCount = AppUtils.convertToLong(row[1]);
                long previousFileCount = AppUtils.convertToLong(row[2]);
                long issueFiles = AppUtils.convertToLong(row[3]);
                long correctFiles = AppUtils.convertToLong(row[4]);
                long processFiles = (currentDateFilesCount + previousFileCount );//+ correctFiles   (currentDateFilesCount + previousFileCount );
                long dataNotReceivedFiles = totalFile - (currentDateFilesCount + previousFileCount);
                long nonProcessFiles = dataNotReceivedFiles - issueFiles;
                logger.info("totalFile: " + totalFile + " currentDateFilesCount: " + currentDateFilesCount + " previousFileCount:" + previousFileCount+ " issueFiles: "+issueFiles+ " correctFiles: "+correctFiles);
                resultMap.put("totalFiles", totalFile);  // total files
                resultMap.put("processFiles", processFiles); // total process files
                resultMap.put("nonProcessFiles", nonProcessFiles);  // total non-process files
                resultMap.put("dataNotReceivedFiles", dataNotReceivedFiles); // data not received file
                response.setStatus(HttpStatus.OK);
                response.setMessage("Agency File statistics found.");
                response.setData(new ArrayList<>(Arrays.asList(resultMap)));
            } else {
                 response.setStatus(HttpStatus.NO_CONTENT);
                 response.setMessage("Agency File statistics not found!");
                 response.setData(new ArrayList<>());
            }
        }
        catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setMessage("Internal server error.");
            resultMap.put("Exception @ getAllAgenciesInfo{e}", e.getMessage());
            response.setData(Collections.singletonList(resultMap));
        }
        return response;
    }
}
