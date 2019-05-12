-----------------------------------------------------------
--                     DB CREATION                       --
-----------------------------------------------------------

-----------------------------------------------------------
-- Create database pml                    --
-----------------------------------------------------------
drop database if exists pml;
create database pml
WITH LC_COLLATE = 'C'
TEMPLATE template0;

CREATE USER pml_java WITH PASSWORD 'pml_java' VALID UNTIL 'infinity';

GRANT CONNECT ON DATABASE pml TO pml_java;

\connect pml
GRANT USAGE ON SCHEMA public TO pml_java;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO pml_java;
ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT SELECT,INSERT,UPDATE,DELETE ON TABLES TO pml_java;
ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT USAGE, SELECT, UPDATE ON SEQUENCES TO pml_java;

