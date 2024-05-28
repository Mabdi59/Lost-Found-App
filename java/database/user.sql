-- ********************************************************************************
-- This script creates the database users and grants them the necessary permissions
-- ********************************************************************************

CREATE USER lostfound_owner
WITH PASSWORD 'lostfoundownerpassword';

GRANT ALL
ON ALL TABLES IN SCHEMA public
TO lostfound_owner;

GRANT ALL
ON ALL SEQUENCES IN SCHEMA public
TO lostfound_owner;

CREATE USER lostfound_appuser
WITH PASSWORD 'lostfoundappuserpassword';

GRANT SELECT, INSERT, UPDATE, DELETE
ON ALL TABLES IN SCHEMA public
TO lostfound_appuser;

GRANT USAGE, SELECT
ON ALL SEQUENCES IN SCHEMA public
TO lostfound_appuser;
