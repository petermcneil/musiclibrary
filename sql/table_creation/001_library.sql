/*DROP TABLE IF EXISTS library CASCADE;*/

CREATE TABLE IF NOT EXISTS library(
  idlibrary INTEGER PRIMARY KEY NOT NULL,
  idowner INTEGER
);