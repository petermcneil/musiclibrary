/*DROP TABLE IF EXISTS library CASCADE;*/

CREATE TABLE IF NOT EXISTS library(
  idlibrary SERIAL PRIMARY KEY NOT NULL ,
  idowner INTEGER
);
