--
-- PostgreSQL database cluster dump
--

-- Started on 2023-10-27 22:08:55

SET default_transaction_read_only = off;

SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;

--
-- Roles
--

CREATE ROLE wallet;
ALTER ROLE wallet WITH SUPERUSER INHERIT CREATEROLE CREATEDB LOGIN REPLICATION BYPASSRLS;

