BEGIN;

CREATE OR REPLACE VIEW water_quality_observed_wipawoik AS (
    SELECT 
        a.entity_id,
        DATE(a.recording_timestamp) AS recording_date,
        ROW_NUMBER() OVER (PARTITION BY a.entity_id ORDER BY a.recording_timestamp DESC) AS row_number,
        a.recording_timestamp,
        'WaterQualityObserved'::TEXT AS type,
        'WaterQualityObserved'::TEXT AS dtype,
        'IVL'::TEXT AS data_provider,
        a.recording_timestamp AS date_modified,
        NULL::TIMESTAMP WITHOUT TIME ZONE AS date_created,
        a.recording_timestamp AS date_observed,
        b.lon AS lon,
        b.lat AS lat,
        ST_SETSRID(ST_MAKEPOINT(b.lon, b.lat), 4326) as geom, 
        NULL::text AS properties,
        NULL::TEXT AS address,
        NULL::text AS ref_point_of_interest,
        'IVL'::TEXT AS source,
        null::FLOAT AS temperature,
        null::TIMESTAMP WITHOUT TIME ZONE AS temperature_timestamp,
        null::FLOAT AS conductivity,
        null::TIMESTAMP WITHOUT TIME ZONE conductivity_timestamp,
        NULL::FLOAT AS conductance,
        NULL::TIMESTAMP WITHOUT TIME ZONE AS conductance_timestamp,
        NULL::FLOAT AS tss,
        NULL::TIMESTAMP AS tss_timestamp,
        NULL::FLOAT AS tds,
        NULL::TIMESTAMP WITHOUT TIME ZONE AS tds_timestamp,
        a.turbidity AS turbidity,
        a.recording_timestamp AS turbidity_timestamp,
        NULL::FLOAT AS salinity,
        NULL::TIMESTAMP WITHOUT TIME ZONE AS salinity_timestamp,
        NULL::FLOAT AS ph,
        NULL::TIMESTAMP AS ph_timestamp,
        NULL::FLOAT AS orp,
        NULL::TIMESTAMP WITHOUT TIME ZONE AS orp_timestamp,
        NULL::bytea AS measurand,
        NULL::FLOAT AS o2,
        NULL::TIMESTAMP WITHOUT TIME ZONE AS o2_timestamp,
        NULL::FLOAT AS chla,
        NULL::TIMESTAMP WITHOUT TIME ZONE AS chla_timestamp,
        NULL::FLOAT AS pe,
        NULL::TIMESTAMP WITHOUT TIME ZONE AS pe_timestamp,
        NULL::FLOAT AS pc,
        NULL::TIMESTAMP WITHOUT TIME ZONE AS pc_timestamp,
        NULL::FLOAT AS nh4,
        NULL::TIMESTAMP AS nh4_timestamp,
        NULL::FLOAT AS nh3,
        NULL::TIMESTAMP AS nh3_timestamp, 
        NULL::FLOAT AS cl,
        NULL::TIMESTAMP WITHOUT TIME ZONE AS cl_timestamp,
        NULL::FLOAT AS no3,
        NULL::TIMESTAMP WITHOUT TIME ZONE AS no3_timestamp
    FROM turbinator_measurement_wipawoik AS a 
    JOIN turbinator_location_wipawoik AS b ON a.entity_id = b.entity_id
);

ALTER TABLE water_quality_observed_wipawoik OWNER TO turbinator;

END;