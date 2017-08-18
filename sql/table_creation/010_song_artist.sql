/*DROP TABLE IF EXISTS song_artist CASCADE;*/

CREATE TABLE IF NOT EXISTS song_artist(
  idsong INTEGER REFERENCES song(idsong),
  idartist INTEGER REFERENCES artist(idartist),
  leadartist BOOLEAN DEFAULT FALSE,
  PRIMARY KEY (idsong, idartist)
);
