package petermcneil.musiclibrary.services.database;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.EmptySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import petermcneil.domain.Artist;
import petermcneil.domain.Bio;
import petermcneil.musiclibrary.services.CRUDService;

import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


@Service
public class ArtistService implements CRUDService<Artist> {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final CRUDService<Bio> bioService;
    private static final Logger LOG = LoggerFactory.getLogger(ArtistService.class);

    public ArtistService(final NamedParameterJdbcTemplate jdbcTemplate, CRUDService<Bio> bio){
        this.jdbcTemplate = jdbcTemplate;
        this.bioService = bio;
    }


    @Override
    public Artist get(Integer artistId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("artistId", artistId);
        return jdbcTemplate.query("SELECT * FROM artist WHERE idartist=:artistId", params, new ArtistExtractor());
    }

    @Override
    public List<Artist> getList() {
        return jdbcTemplate.query("SELECT * FROM artist", new EmptySqlParameterSource(), new ResultSetExtractor<List<Artist>>() {
            @Override
            public List<Artist> extractData(ResultSet rs) throws SQLException, DataAccessException {
                List<Artist> result = new ArrayList<>();
                while(rs.next()){
                    Artist.Builder artist =  Artist.artistBuilder();

                    artist.name(rs.getString("name"));
                    artist.type(getType(rs.getInt("idartisttype")));
                    artist.bio(bioService.get(rs.getInt("idbio")));

                    result.add(artist.build());
                }
                return result;
            }
        });
    }


    @Override
    public Integer post(Artist artist) {
        MapSqlParameterSource params = new MapSqlParameterSource();

        params.addValue("name", artist.getName());
        params.addValue("idartisttype", artist.getType());
        params.addValue("idbio", bioService.post(artist.getBio()));

        
        return null;
    }

    @Override
    public void put(Artist object, Integer objectId) {

    }

    @Override
    public void delete(Integer objectId) {

    }


    private String getType(Integer typeId){
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("typeId", typeId);
        return jdbcTemplate.query("SELECT artisttype FROM artist_type WHERE idartisttype=:typeId", params, new ResultSetExtractor<String>() {
            @Override
            public String extractData(ResultSet rs) throws SQLException, DataAccessException {
                String result = "";
                while(rs.next()){
                    result = rs.getString("artisttype");
                }
                return result;
            }
        });
    }


    private class ArtistExtractor implements  ResultSetExtractor<Artist>{
        @Override
        public Artist extractData(ResultSet resultSet) throws SQLException, DataAccessException {
            Artist.Builder artist = Artist.artistBuilder();
            while (resultSet.next()) {
                artist.name(resultSet.getString("name"));
                artist.type(getType(resultSet.getInt("idartisttype")));
                artist.bio(bioService.get(resultSet.getInt("idbio")));
            }
            return artist.build();
        }
    }

}
