/*DROP TABLE IF EXISTS collection_song CASCADE;*/

CREATE TABLE IF NOT EXISTS collection_song(
  songId INTEGER REFERENCES song(songId),
  collectionId INTEGER REFERENCES collection(collectionId),
  PRIMARY KEY (songId, collectionId)
);
