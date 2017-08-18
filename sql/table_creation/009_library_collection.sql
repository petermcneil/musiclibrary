/*DROP TABLE IF EXISTS library_collection CASCADE;*/

CREATE TABLE IF NOT EXISTS library_collection(
  idlibrary INTEGER REFERENCES library(idlibrary),
  idcollection INTEGER REFERENCES collection(idcollection),
  PRIMARY KEY (idlibrary, idcollection)
);
