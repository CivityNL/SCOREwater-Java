BEGIN;

CREATE TABLE IF NOT EXISTS turbinator_measurement_wipawoik (
    entity_id VARCHAR(256),
    recording_timestamp TIMESTAMP,
    turbidity INTEGER,
    water_level FLOAT
);

ALTER TABLE turbinator_measurement_wipawoik ADD CONSTRAINT pk_turbinator_measurement_wipawoik PRIMARY KEY(entity_id, recording_timestamp);

ALTER TABLE turbinator_measurement_wipawoik OWNER TO turbinator;

CREATE TABLE IF NOT EXISTS turbinator_location_wipawoik (
    entity_id VARCHAR(256),
    recording_timestamp TIMESTAMP,
    lon FLOAT,
    lat FLOAT
);

ALTER TABLE turbinator_location_wipawoik ADD CONSTRAINT pk_turbinator_location_wipawoik PRIMARY KEY(entity_id, recording_timestamp);

ALTER TABLE turbinator_location_wipawoik OWNER TO turbinator;

END;