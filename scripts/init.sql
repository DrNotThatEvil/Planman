SET TIME ZONE 'Europe/Amsterdam';
CREATE USER planman_db_user WITH PASSWORD 'batteryhorsestaple'; -- Every project needs a xkcd reference (not really but still)
CREATE DATABASE planmandb;
ALTER DATABASE planmandb set timezone TO 'Europe/Amsterdam';

GRANT ALL PRIVILEGES ON DATABASE planman TO planman_db_user;

