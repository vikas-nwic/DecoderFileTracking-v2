package com.nwic.gt.decoder.file.tracking.system.repositories;

import com.nwic.gt.decoder.file.tracking.system.models.DecoderTicketCreationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MDTicketCreationRepo extends JpaRepository<DecoderTicketCreationEntity, Integer> {
}
