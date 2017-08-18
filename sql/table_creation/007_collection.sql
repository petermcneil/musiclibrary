/*DROP TABLE IF EXISTS collection CASCADE;*/

CREATE TABLE IF NOT EXISTS collection(
  collectionId INTEGER PRIMARY KEY NOT NULL,
  collectionTypeId INTEGER REFERENCES collection_type(collectiontypeid),
  name VARCHAR(50),
  description TEXT,
  artwork VARCHAR(50)
);
