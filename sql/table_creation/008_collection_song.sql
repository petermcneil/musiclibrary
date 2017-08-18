/*DROP TABLE IF EXISTS collection_song CASCADE;*/

CREATE TABLE IF NOT EXISTS collection_song(
  idsong INTEGER REFERENCES song(idsong),
  idcollection INTEGER REFERENCES collection(idcollection),
  PRIMARY KEY (idsong, idcollection)
);
