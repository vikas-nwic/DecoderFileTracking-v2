package com.nwic.gt.decoder.file.tracking.system.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "telemetry_decoder_file_tracker_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DecoderFileTrackerDetails {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sensor_hub_code")
    private String sensorHubCode;

    @Column(name = "content_count")
    private Integer contentCount;

    @Column(name = "content_date")
    private String contentDate;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "insertion_date", nullable = false, updatable = false)
    private Date insertionDate;

}
