/*DROP TABLE IF EXISTS song CASCADE;*/

CREATE TABLE IF NOT EXISTS song(
  songId INTEGER PRIMARY KEY NOT NULL,
  title VARCHAR(20),
  length INT,
  artwork VARCHAR(100),
  lyrics TEXT, /* Possibly return as a JSON blob? */
  playcount INT
);
