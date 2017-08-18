/*DROP TABLE IF EXISTS featured_artist CASCADE;*/

CREATE TABLE IF NOT EXISTS featured_artist(
  songId INTEGER REFERENCES song(songid),
  artistId INTEGER REFERENCES artist(artistid),
  leadArtist BOOLEAN DEFAULT FALSE,
  PRIMARY KEY (songId, artistId)
);
