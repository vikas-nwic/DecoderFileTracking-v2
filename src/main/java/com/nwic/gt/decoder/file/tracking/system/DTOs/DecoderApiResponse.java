package com.nwic.gt.decoder.file.tracking.system.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DecoderApiResponse<T> {

    private HttpStatus status;
    private String message;
    private List<T> data;
}