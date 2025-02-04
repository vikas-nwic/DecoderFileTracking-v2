package com.nwic.gt.decoder.file.tracking.system.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "master_agency")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DecoderMasterAgency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "agency_id")
    private Integer agencyId;

    @Column(name = "name", length = 150)
    private String name;

    @Column(name = "agency_type_code", length = 6)
    private String agencyTypeCode;

    @Column(name = "saved_by_user_id")
    private Integer savedByUserId;

    @Column(name = "saved_at")
    @Temporal(TemporalType.DATE)
    private Date savedAt;

    @Column(name = "code")
    private String code;
}
