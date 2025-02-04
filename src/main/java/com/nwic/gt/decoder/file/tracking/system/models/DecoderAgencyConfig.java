package com.nwic.gt.decoder.file.tracking.system.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*; // Use this package for JPA annotations

@Entity
@Table(name = "telemetry_decoder_file_tracker_agencyconfig")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DecoderAgencyConfig {

    @Id // Correct JPA annotation
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "agencyconfig_id")
    private Integer agencyConfigId;

    @Column(name = "agency_id")
    private Integer agencyId;

    @Column(name = "agency_name", length = 255, nullable = false)
    private String agencyName;

    @Column(name = "transmission_type", length = 50)
    private String transmissionType;

    @Column(name = "frequency_rate")
    private Integer frequencyRate;

    @Column(name = "ground_water")
    private Integer groundWater;

    @Column(name = "surface_water")
    private Integer surfaceWater;

    @Column(name = "expected_count")
    private Integer expectedCount;

    @Column(name = "no_of_file")
    private Integer numberOfFile;

}