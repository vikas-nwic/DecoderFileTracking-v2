package com.nwic.gt.decoder.file.tracking.system.repositories;

import com.nwic.gt.decoder.file.tracking.system.models.DecoderFileTrackerDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DecoderFileTrackerDetailsRepository extends JpaRepository<DecoderFileTrackerDetails, Long> {
}
