package com.nwic.gt.decoder.file.tracking.system.DTOs;

import lombok.Data;

@Data
public class DecoderTicketResponseDTO {

    private int statusCode;
    private String message;
    private DecoderTicketCreation data;  // This will contain ticket details
}
