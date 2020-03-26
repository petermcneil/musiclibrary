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
