/*
 * Create database and database user
 * Usage: psql -U postgres -h host.docker.internal -p 5432 -f 00_create_database.sql
 */
CREATE USER turbinator WITH PASSWORD 'KanaycsudenBaj';

CREATE DATABASE turbinator OWNER turbinator;