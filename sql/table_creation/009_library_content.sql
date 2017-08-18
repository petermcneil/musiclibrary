/*DROP TABLE IF EXISTS library_content CASCADE;*/

CREATE TABLE IF NOT EXISTS library_content(
  libraryId INTEGER REFERENCES library(libraryid),
  collectionId INTEGER REFERENCES collection(collectionid),
  PRIMARY KEY (libraryId, collectionId)
);
