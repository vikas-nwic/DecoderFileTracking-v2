package com.nwic.gt.decoder.file.tracking.system.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "layer_station")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DecoderLayerStation {

    @Id
    @Column(name = "station_code", length = 15)
    private String stationCode;

    @Column(name = "name", length = 100)
    private String name;

    @Column(name = "tahsil_id")
    private int tahsilId;

    @Column(name = "ref_toposheet_no", length = 10)
    private String refToposheetNo;

    @Column(name = "altitude")
    private Double altitude;

    @Column(name = "zero_rl")
    private int zeroRl;

    @Column(name = "stream_localriver_id")
    private Integer streamLocalriverId;

    @Column(name = "catchment_area")
    private Double catchmentArea;

    @Column(name = "subdivisional_office_id")
    private int subdivisionalOfficeId;

    @Column(name = "section_office_id")
    private Integer sectionOfficeId;

    @Column(name = "owner_agency_id")
    private Integer ownerAgencyId;
    @Column(name = "location_details", columnDefinition = "TEXT")
    private String locationDetails;

    @Column(name = "bus_stand", length = 50)
    private String busStand;

    @Column(name = "station", length = 50)
    private String station;

    @Column(name = "airport", length = 50)
    private String airport;

    @Column(name = "town", length = 50)
    private String town;

    @Column(name = "address", columnDefinition = "TEXT")
    private String address;

    @Column(name = "other_information", columnDefinition = "TEXT")
    private String otherInformation;

    @Column(name = "station_setup", columnDefinition = "TEXT")
    private String stationSetup;

    @Column(name = "accessibility", columnDefinition = "TEXT")
    private String accessibility;

    @Column(name = "station_history", columnDefinition = "TEXT")
    private String stationHistory;

    @Column(name = "date_of_establishment")
    @Temporal(TemporalType.DATE)
    private Date dateOfEstablishment;

    @Column(name = "date_of_closure")
    @Temporal(TemporalType.DATE)
    private Date dateOfClosure;

    @Column(name = "combined_with", length = 50)
    private String combinedWith;

    @Column(name = "distance_to_outlet")
    private Float distanceToOutlet;

    @Column(name = "digital_water_level_recorder_no", length = 50)
    private String digitalWaterLevelRecorderNo;

    @Column(name = "transfer_selected")
    private Boolean transferSelected;

    @Column(name = "station_operational")
    private Boolean stationOperational;

//    @Column(name = "bank")
//    //@Enumerated(EnumType.STRING)
//    private BankType bank;  //BankType

    @Column(name = "saved_by_user_id")
    private Integer savedByUserId;

    @Column(name = "saved_at")
    private Date savedAt;

    @Lob
    @Column(name = "thumbnail")
    private byte[] thumbnail;

    @Column(name = "village_id")
    private Integer villageId;

    @Column(name = "station_type_id")
    private int stationTypeId;

    @Column(name = "well_type_id")
    private Integer wellTypeId;

    @Column(name = "well_subtype_id")
    private Integer wellSubtypeId;

    @Column(name = "well_use_id")
    private Integer wellUseId;

    @Column(name = "lifting_device_id")
    private Integer liftingDeviceId;

    @Column(name = "imagestring", columnDefinition = "TEXT")
    private String imageString;

    @Column(name = "telemetric")
    private Boolean telemetric;

    @Lob
    @Column(name = "the_geom")
    private byte[] theGeom;

    @Column(name = "station_telemetry_type_id")
    private Integer stationTelemetryTypeId;

    @Column(name = "well_aquifier_type")
    private Integer wellAquiferType;

    @Column(name = "well_depth")
    private Float wellDepth;

    @Column(name = "well_measuring_point")
    private Float wellMeasuringPoint;

    @Column(name = "well_major_acquifier_id", length = 4)
    private String wellMajorAquifierId;

    @Column(name = "remarks_abandoned")
    private String remarksAbandoned;

    @Column(name = "date_of_abandoned")
    private Date dateOfAbandoned;

    @Column(name = "remarks_revive", length = 150)
    private String remarksRevive;

    @Column(name = "reservoir_code", length = 15)
    private String reservoirCode;

    @Column(name = "structure_code", length = 15)
    private String structureCode;

    @Column(name = "project_code", length = 15)
    private String projectCode;

    @Column(name = "reservoir")
    private Boolean reservoir;

    @Column(name = "groundwater_project_name", length = 80)
    private String groundwaterProjectName;

    @Column(name = "well_purpose_id")
    private Integer wellPurposeId;

    @Column(name = "station_unique_code", length = 40)
    private String stationUniqueCode;

    @Column(name = "type_b", columnDefinition = "TEXT")
    private String typeB;

    @Column(name = "gram_panchayat", length = 80)
    private String gramPanchayat;

    @Column(name = "station_label", length = 50)
    private String stationLabel;

    @Column(name = "package_name", length = 50)
    private String packageName;

    @Column(name = "msl_lower_limit")
    private Double mslLowerLimit;

    @Column(name = "msl_upper_limit")
    private Double mslUpperLimit;

    @Column(name = "hfl_ho")
    private Double hflHo;

    @Column(name = "station_located", length = 50)
    private String stationLocated;
}
