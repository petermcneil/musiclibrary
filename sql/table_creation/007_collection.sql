/*DROP TABLE IF EXISTS collection CASCADE;*/

CREATE TABLE IF NOT EXISTS collection(
  idcollection SERIAL PRIMARY KEY NOT NULL,
  idcollectiontype INTEGER REFERENCES collection_type(idcollectiontype),
  name VARCHAR(50),
  description TEXT,
  artwork VARCHAR(50)
);
