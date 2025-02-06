package com.nwic.gt.decoder.file.tracking.system.repositories;

import com.nwic.gt.decoder.file.tracking.system.models.DecoderFileTrackerDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DecoderFileTrackerDetailsRepository extends JpaRepository<DecoderFileTrackerDetails, Long> {

    List<DecoderFileTrackerDetails> findByFilename(String filename);

}
