/*DROP TABLE IF EXISTS artist_type CASCADE;*/

CREATE TABLE IF NOT EXISTS artist_type(
  idartisttype SERIAL PRIMARY KEY NOT NULL,
  artisttype VARCHAR(15)
);

INSERT INTO artist_type(artisttype) VALUES ('Solo');
INSERT INTO artist_type(artisttype) VALUES ('Band');
INSERT INTO artist_type(artisttype) VALUES ('Duo');
