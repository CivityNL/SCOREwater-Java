/*
 * Create extensions
 * Usage: psql -U postgres -h host.docker.internal -p 5432 -d turbinator -f 01_create_extensions.sql
 */
BEGIN;

CREATE EXTENSION postgis;

END;