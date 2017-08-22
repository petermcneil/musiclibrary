package petermcneil.musiclibrary.services.database;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import petermcneil.domain.Song;
import petermcneil.musiclibrary.services.CRUDService;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Service
public class SongService implements CRUDService<Song> {
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private static final Logger LOG = LoggerFactory.getLogger(SongService.class);

    public SongService(final NamedParameterJdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
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
                }
                return song.build();
            }
        });
    }

    @Override
    public List<Song> getList() {
        return null;
    }

    @Override
    public Integer post(Song object) {
        return null;
    }

    @Override
    public void put(Song object, Integer objectId) {

    }

    @Override
    public void delete(Integer objectId) {

    }
}
