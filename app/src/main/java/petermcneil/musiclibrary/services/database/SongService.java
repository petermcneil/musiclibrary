package petermcneil.musiclibrary.services.database;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.EmptySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;
import petermcneil.domain.Artist;
import petermcneil.domain.Bio;
import petermcneil.domain.Song;
import petermcneil.musiclibrary.services.CRUDService;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class SongService implements CRUDService<Song> {
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final CRUDService<Bio> bioService;
    private final CRUDService<Artist> artistService;
    private static final Logger LOG = LoggerFactory.getLogger(SongService.class);

    public SongService(final NamedParameterJdbcTemplate jdbcTemplate, CRUDService<Bio> bioService, CRUDService<Artist> artistService){
        this.jdbcTemplate = jdbcTemplate;
        this.bioService = bioService;
        this.artistService = artistService;
    }

    @Override
    public Song get(Integer songId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("songId", songId);
        LOG.info("RESPONSE: Returning the song at the id ({})", songId);
        return jdbcTemplate.query("SELECT * FROM song WHERE idsong=:songId", params, new ResultSetExtractor<Song>() {
            @Override
            public Song extractData(ResultSet rs) throws SQLException, DataAccessException {
                Song.Builder song = Song.songBuilder();
                while (rs.next()){
                    song.songId(rs.getInt("idsong"));
                    song.title(rs.getString("title"));
                    song.length(rs.getInt("length"));
                    song.artwork(rs.getString("artwork"));
                    song.lyrics(rs.getString("lyrics"));
                    song.playcount(rs.getInt("playcount"));
                    song.leadArtist(getArtist(songId));
                    song.featuredArtists(produceFtArtistList(songId));
                }
                return song.build();
            }
        });
    }

    @Override
    public List<Song> getList() {
        return jdbcTemplate.query("SELECT * FROM song", new EmptySqlParameterSource(), new ResultSetExtractor<List<Song>>() {
            @Override
            public List<Song> extractData(ResultSet rs) throws SQLException, DataAccessException {
                List<Song> songs = new ArrayList<>();
                while(rs.next()){
                    Song.Builder song = Song.songBuilder();
                    Integer songId = rs.getInt("idsong");

                    song.songId(songId);
                    song.title(rs.getString("title"));
                    song.length(rs.getInt("length"));
                    song.artwork(rs.getString("artwork"));
                    song.lyrics(rs.getString("lyrics"));
                    song.playcount(rs.getInt("playcount"));
                    song.leadArtist(getArtist(songId));
                    song.featuredArtists(produceFtArtistList(songId));

                    songs.add(song.build());
                }
                return songs;
            }
        });
    }

    @Override
    public Integer post(Song song) {
        MapSqlParameterSource params = new MapSqlParameterSource();

        params.addValue("songTitle", song.getTitle());
        params.addValue("songLength", song.getLength());
        params.addValue("songArtwork", song.getArtwork());
        params.addValue("songLyrics", song.getLyrics());
        params.addValue("songPlaycount", song.getPlaycount());

        KeyHolder songKey = new GeneratedKeyHolder();
        jdbcTemplate.update("INSERT INTO song(title, length, artwork, lyrics, playcount)\n" +
                "VALUES (:songTitle, :songLength, :songArtwork, :songLyrics, :songPlaycount)", params, songKey);

        Map<String, Object> songData = songKey.getKeyList().get(0);
        Integer songId = Integer.parseInt(songData.get("idsong").toString());

        MapSqlParameterSource artistParams = new MapSqlParameterSource();
        artistParams.addValue("songId", songId);
        artistParams.addValue("songLeadArtistId", song.getLeadArtist().getArtistId());
        artistParams.addValue("songLeadArtistName", song.getLeadArtist().getName());

        Boolean artistExist = jdbcTemplate.queryForObject("SELECT COUNT(1) FROM artist WHERE idartist=:songLeadArtistId OR name=:songLeadArtistName",
                artistParams, Boolean.class);

        if(artistExist){
            jdbcTemplate.update("INSERT INTO song_artist VALUES (:songId, :songLeadArtistId, TRUE)", artistParams);
        }else{
            Integer artistId = artistService.post(song.getLeadArtist());
            artistParams.addValue("updatedArtId", artistId);
            jdbcTemplate.update("INSERT INTO song_artist VALUES (:songId, :updatedArtId, TRUE)", artistParams);
        }

        List<Artist> ftArtists = song.getFeaturedArtists();

        for(Artist artist : ftArtists){
            MapSqlParameterSource ftArtistParams = new MapSqlParameterSource();
            ftArtistParams.addValue("songId", songId);
            ftArtistParams.addValue("songArtistId", artist.getArtistId());
            ftArtistParams.addValue("songArtistName", artist.getName());

            Boolean ftArtistExist = jdbcTemplate.queryForObject("SELECT COUNT(1) FROM artist WHERE idartist=:songArtistId OR name=:songArtistName",
                    ftArtistParams, Boolean.class);

            if(ftArtistExist){
                jdbcTemplate.update("INSERT INTO song_artist VALUES (:songId, :songArtistId, FALSE)", artistParams);
            }else{
                Integer artistId = artistService.post(artist);
                artistParams.addValue("updatedArtId", artistId);
                jdbcTemplate.update("INSERT INTO song_artist VALUES (:songId, :updatedArtId, FALse)", artistParams);
            }
        }
        
        return songId;
    }

    @Override
    public void put(Song object, Integer objectId) {

    }

    @Override
    public void delete(Integer objectId) {

    }

    private Artist getArtist(Integer songId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("songId", songId);

        return jdbcTemplate.query(
                "SELECT artist.idartist, artist.name, artist.idartisttype, artist.idbio\n" +
                        "FROM artist\n" +
                        "INNER JOIN song_artist ON artist.idartist = song_artist.idartist\n" +
                        "INNER JOIN song ON song_artist.idsong = song.idsong\n" +
                        "WHERE song.idsong=:songId AND\n" +
                        "song_artist.leadartist = TRUE;", params, new ResultSetExtractor<Artist>() {
                    @Override
                    public Artist extractData(ResultSet rs) throws SQLException, DataAccessException {
                        Artist.Builder artist = Artist.artistBuilder();
                        while (rs.next()) {
                            artist.artistId(rs.getInt(1));
                            artist.name(rs.getString(2));
                            artist.type(rs.getString(3));
                            artist.bio(bioService.get(rs.getInt(4)));
                        }
                        return artist.build();
                    }
                });
    }

    private List<Artist> produceFtArtistList(Integer songId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("songId", songId);

        return jdbcTemplate.query("SELECT artist.idartist, artist.name, artist.idartisttype, artist.idbio\n" +
                "FROM artist\n" +
                "INNER JOIN song_artist ON artist.idartist = song_artist.idartist\n" +
                "INNER JOIN song ON song_artist.idsong = song.idsong\n" +
                "WHERE song.idsong=:songId AND\n" +
                "song_artist.leadartist = FALSE;", params, new ResultSetExtractor<List<Artist>>() {
            @Override
            public List<Artist> extractData(ResultSet rs) throws SQLException, DataAccessException {
                List<Artist> artists = new ArrayList<>();
                while(rs.next()){
                    Artist.Builder artist = Artist.artistBuilder();

                    artist.artistId(rs.getInt(1));
                    artist.name(rs.getString(2));
                    artist.type(rs.getString(3));
                    artist.bio(bioService.get(rs.getInt(4)));

                    artists.add(artist.build());
                }
                return artists;
            }
        });
    }
}