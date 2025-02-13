
-- Telemetric Agency 

select distinct ma.name,ls.owner_agency_id from layer_station ls
inner join master_agency ma on
ls.owner_agency_id=ma.agency_id
and ls.owner_agency_id is NOT NULL and  ls.subdivisional_office_id<> 99999  and ls.owner_agency_id <> 99999 and ls.tahsil_id >= 1000000000 and ls.owner_agency_id <> 888
and ls.telemetric=true
order by ma.name;


-- Telemetry station

select distinct ls.name as station_name,ls.station_code,ma.name as agency,ls.owner_agency_id from layer_station ls
inner join master_agency ma on
ls.owner_agency_id=ma.agency_id
and ls.owner_agency_id is NOT NULL and  ls.subdivisional_office_id<> 99999  and ls.owner_agency_id <> 99999 and ls.tahsil_id >= 1000000000 and ls.owner_agency_id <> 888
and ls.telemetric=true
and ls.owner_agency_id=41
order by ls.name;

------------- 27-11-2024 -----------------------

CREATE TABLE dft_count (
    received_count INT,
    received_file_date TIMESTAMP,
    agency_id INT REFERENCES dft_configuration(agency_id),  -- Foreign key to dft_configuration table
    agency_name VARCHAR(255),
    station_code VARCHAR(50),
    station_name VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP  -- Automatically sets the timestamp when the record is created or updated
);




CREATE TABLE telemetry_decoder_file_tracker_agencyConfig (
    agencyConfig_id SERIAL PRIMARY KEY,                   -- Auto-incrementing unique identifier for the agency
	agency_id INT,                   --  id of agency
    agency_name VARCHAR(255) NOT NULL,               -- Name of the agency
     --station_code VARCHAR(50),                        -- Code for the station
    transmission_type VARCHAR(50),                   -- Type of data (e.g., GPRS, INSAT)
    frequency_rate INT,                              -- Number of files per day for frequency
    ground_water INT,                               -- Number of files per day for ground water
    surface_water INT,                              -- Number of files per day for surface water
    expected_count INT                               -- Number of files expected from the agency per day
);



-------------------------------------------------------------- Date: 28-01-2025 --------------------------------------------------------------   

ALTER TABLE telemetry_decoder_file_tracker_details RENAME previous_received_cont TO previous_content
ALTER TABLE telemetry_decoder_file_tracker_details RENAME previous_received_date TO previous_content_date
ALTER TABLE telemetry_decoder_file_tracker_details RENAME received_file_count TO current_content
ALTER TABLE telemetry_decoder_file_tracker_details RENAME received_file_date TO current_content_date
ALTER TABLE telemetry_decoder_file_tracker_details RENAME created_date TO insertion_date

ALTER TABLE telemetry_decoder_file_tracker ALTER COLUMN file_created_date TYPE varchar (30);
ALTER TABLE telemetry_decoder_file_tracker ALTER COLUMN csv_date TYPE varchar (30);

CREATE TABLE IF NOT EXISTS public.telemetry_decoder_file_tracker_details
(
    id SERIAL,
    sensor_hub_code character varying(15) COLLATE pg_catalog."default",
    content_count integer,
    content_date character varying(20) COLLATE pg_catalog."default",
    insertion_date timestamp without time zone,
    CONSTRAINT telemetry_decoder_file_tracker_details_pkey PRIMARY KEY (id)
)



-------------------------------------------------------------- Date: 31-01-2025 --------------------------------------------------------------
--for future implementation
CREATE TABLE job_schedule (
    id SERIAL PRIMARY KEY,
    job_name VARCHAR(100) NOT NULL,
    cron_expression VARCHAR(50) NOT NULL,
    active BOOLEAN DEFAULT TRUE
);

INSERT INTO job_schedule (job_name, cron_expression, active)
VALUES ('Job A', '0 0 9 * * *', true);

INSERT INTO job_schedule (job_name, cron_expression, active)
VALUES ('Job B', '0 0/15 * * * *', true);

INSERT INTO job_schedule (job_name, cron_expression, active)
VALUES ('Job C', '0 30 12 * * *', false);



-------------------------------------------------------------- Date: 29-01-2025 --------------------------------------------------------------

-- Table: public.telemetry_decoder_file_tracker

-- DROP TABLE IF EXISTS public.telemetry_decoder_file_tracker;

CREATE TABLE IF NOT EXISTS public.telemetry_decoder_file_tracker
(
    tracker_id serial ,
    agency_id integer NOT NULL,
    agency_name character varying(255) COLLATE pg_catalog."default" NOT NULL,
	logger_id character varying(255) COLLATE pg_catalog."default" NOT NULL,
    csv_date timestamp without time zone NOT NULL,
    file_name character varying(100) COLLATE pg_catalog."default" NOT NULL,
    insertion_date timestamp without time zone NOT NULL,
    is_sensitive_data boolean NOT NULL,
    remarks character varying(100) COLLATE pg_catalog."default",
    station_code character varying(255) COLLATE pg_catalog."default" NOT NULL,
    station_name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    status character varying(10) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT telemetry_decoder_file_tracker_pkey PRIMARY KEY (tracker_id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.telemetry_decoder_file_tracker
    OWNER to postgres;
------------------
-- Table: public.telemetry_decoder_file_tracker_details

-- DROP TABLE IF EXISTS public.telemetry_decoder_file_tracker_details;

-- Table: public.telemetry_decoder_file_tracker_details

-- DROP TABLE IF EXISTS public.telemetry_decoder_file_tracker_details;

------------------------------- 07-02-2025 ---------------------------------------

-- Table: public.telemetry_decoder_file_tracker_details

-- DROP TABLE IF EXISTS public.telemetry_decoder_file_tracker_details;

CREATE TABLE IF NOT EXISTS public.telemetry_decoder_file_tracker_details
(
    id integer NOT NULL DEFAULT nextval('telemetry_decoder_file_tracker_details_id_seq'::regclass),
    sensor_hub_code character varying(15) COLLATE pg_catalog."default",
    content_count integer,
    content_date character varying(20) COLLATE pg_catalog."default",
    file_name character varying(100) COLLATE pg_catalog."default" DEFAULT ''::character varying,
    insertion_date timestamp without time zone,
    CONSTRAINT telemetry_decoder_file_tracker_details_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.telemetry_decoder_file_tracker_details
    OWNER to postgres;

------------------------------- 13-02-2025 ---------------------------------------

alter table telemetry_decoder_file_tracker_details add folder_name varchar(60) default '';