package com.nwic.gt.decoder.file.tracking.system.DTOs;

import lombok.Data;

import java.util.Date;

@Data
public class DecoderTicketCreation {
    public int ticketno;
    public String loggedby;
    public String loggedon;
    public String description;
    public String emailid;
    public String phoneno;
    public String attachment;
    public int status_id;
    public int assigtogroup;
    public String comment_note;
    public String updatedby;
    public Date updatedon;
    public String query_type_id;
}