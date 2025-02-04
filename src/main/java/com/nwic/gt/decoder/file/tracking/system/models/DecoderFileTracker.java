package com.nwic.gt.decoder.file.tracking.system.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "telemetry_decoder_file_tracker") // old decoder_file_tracker
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DecoderFileTracker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tracker_id")
    private Long trackerId;

    ////

//    @ManyToOne
//    @JoinColumn(name = "agency_id", referencedColumnName = "agency_id")
//    private DecoderFileTrackerAgencyConfig agencyConfig;
//
//    @OneToMany(mappedBy = "telemetry_decoder_file_tracker_details")
//    private List<DecoderFileTrackerDetails> details;

    ///
    @Column(name = "agency_id")//, nullable = false
    private int agencyId;

    @Column(name = "agency_name")//, nullable = false
    private String agencyName;

    @Column(name = "station_code")//, nullable = false
    private String stationCode;

    @Column(name = "station_name")//, nullable = false
    private String stationName;

    @Column(name = "logger_id", nullable = false)
    private String loggerId;

    @Column(name = "file_name", length = 100)//nullable = false,
    private String fileName;

    @Column(name = "csv_date")//, nullable = false
    @Temporal(TemporalType.TIMESTAMP)
    private Date fileCreatedDate;

    @CreationTimestamp
    @Column(name = "insertion_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date insertionDate;

    @Column(name = "status", nullable = false, length = 10)
    private String status;

    @Column(name = "is_sensitive_data")//, nullable = false
    private Boolean isSensitiveData;

    @Column(name = "remarks", length = 100)
    private String remarks;
}
