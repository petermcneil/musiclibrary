/*DROP TABLE IF EXISTS collection_type CASCADE;*/

CREATE TABLE IF NOT EXISTS collection_type(
  collectionTypeId INTEGER PRIMARY KEY NOT NULL,
  collectionType VARCHAR(15)
);
