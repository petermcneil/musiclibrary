/*DROP TABLE IF EXISTS artist CASCADE;*/

CREATE TABLE IF NOT EXISTS artist(
  idartist INTEGER PRIMARY KEY NOT NULL,
  idartisttype INTEGER REFERENCES artist_type(idartisttype),
  idbio INTEGER REFERENCES bio(idbio),
  name VARCHAR(100)
);
