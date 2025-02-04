package com.nwic.gt.decoder.file.tracking.system.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ticket_creation") // old decoder_file_tracker
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DecoderTicketCreationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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