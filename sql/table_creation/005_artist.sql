/*DROP TABLE IF EXISTS artist CASCADE;*/

CREATE TABLE IF NOT EXISTS artist(
  artistId INTEGER PRIMARY KEY NOT NULL,
  artistTypeId INTEGER REFERENCES artist_type(artistTypeId),
  bioId INTEGER REFERENCES bio(bioid),
  name VARCHAR(100)
);
