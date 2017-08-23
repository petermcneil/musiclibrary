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
import petermcneil.musiclibrary.services.CRUDService;

import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


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
        LOG.info("RESPONSE: Returning the artist at the id: {}", artistId);
        return jdbcTemplate.query("SELECT * FROM artist WHERE idartist=:artistId", params, new ResultSetExtractor<Artist>() {
            @Override
            public Artist extractData(ResultSet resultSet) throws SQLException, DataAccessException {
                Artist.Builder artist = Artist.artistBuilder();
                while (resultSet.next()) {
                    artist.artistId(resultSet.getInt("idartist"));
                    artist.name(resultSet.getString("name"));
                    artist.type(getType(resultSet.getInt("idartisttype")));
                    artist.bio(bioService.get(resultSet.getInt("idbio")));
                }
                return artist.build();
            }
        });
    }

    public Artist getByName(String artistName) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("artistName", artistName);
        LOG.info("RESPONSE: Returning the artist at the name: {}", artistName);
        return jdbcTemplate.query("SELECT * FROM artist WHERE name=:artistName", params, new ResultSetExtractor<Artist>() {
            @Override
            public Artist extractData(ResultSet resultSet) throws SQLException, DataAccessException {
                Artist.Builder artist = Artist.artistBuilder();
                if (resultSet.next()) {
                    artist.artistId(resultSet.getInt("idartist"));
                    artist.name(resultSet.getString("name"));
                    artist.type(getType(resultSet.getInt("idartisttype")));
                    artist.bio(bioService.get(resultSet.getInt("idbio")));
                    return artist.build();
                }
                return null;
            }
        });
    }

    @Override
    public List<Artist> getList() {
        return jdbcTemplate.query("SELECT * FROM artist", new EmptySqlParameterSource(), new ResultSetExtractor<List<Artist>>() {
            @Override
            public List<Artist> extractData(ResultSet rs) throws SQLException, DataAccessException {
                List<Artist> result = new ArrayList<>();
                while(rs.next()){
                    Artist.Builder artist =  Artist.artistBuilder();

                    artist.artistId(rs.getInt("idartist"));
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
        params.addValue("idartisttype", getTypeId(artist.getType()));

        LOG.info("REQUEST : Add a new bio for {} and return the key", artist.getName());
        params.addValue("idbio", bioService.post(artist.getBio()));

        KeyHolder key = new GeneratedKeyHolder();
        jdbcTemplate.update("INSERT INTO artist(name, idartisttype, idbio) VALUES (:name, :idartisttype, :idbio)", params, key);

        Map<String, Object> artistData = key.getKeyList().get(0);
        Integer artistId = Integer.parseInt(artistData.get("idartist").toString());

        LOG.info("RESPONSE: Passing back the id ({}) to the GET controller", artistId);
        return artistId;
    }

    @Override
    public void put(Artist artist, Integer artistId) {
        MapSqlParameterSource params = new MapSqlParameterSource();

        params.addValue("artistId", artistId);
        params.addValue("artistName", artist.getName());
        params.addValue("artistType", getTypeId(artist.getType()));

        bioService.put(artist.getBio(), artist.getBio().getBioId());
        params.addValue("artistBio", artist.getBio());

        jdbcTemplate.update("UPDATE artist SET name=:artistName, idartisttype=:artistType WHERE idartist=:artistId", params);
    }

    @Override
    public void delete(Integer artistId) {
        MapSqlParameterSource bioParams = new MapSqlParameterSource();
        bioParams.addValue("artistId", artistId);

        Integer bioId = jdbcTemplate.queryForObject("SELECT idbio FROM artist WHERE idartist=:artistId", bioParams, Integer.class);


        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("artist", artistId);

        jdbcTemplate.update("DELETE FROM artist WHERE idartist=:artist", params);

        LOG.info("REQUEST : DELETE the bio at the id ({})", bioId);
        bioService.delete(bioId);

        LOG.info("RESPONSE: DELETEd the artist at the id ({})", artistId);
    }





    private String getType(Integer typeId){
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("typeId", typeId);

        //LOG.info("REQUEST : Retrieve artist type from the id ({})", typeId);
        String result = jdbcTemplate.queryForObject("SELECT artisttype FROM artist_type WHERE idartisttype=:typeId", params, String.class);

        //LOG.info("RESPONSE: Returned the type of ({}) for the typeId ({})", result, typeId);
        return result;
    }

    private Integer getTypeId(String type){
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("typename", type);

       // LOG.info("REQUEST : Retrieving the typeId for the type ({})", type);
        Integer result = jdbcTemplate.queryForObject("SELECT idartisttype from artist_type WHERE artisttype=:typename", params, Integer.class);

        //LOG.info("RESPONSE: Returned the typeId ({}) for the type ({})", result, type);
        return result;
    }


}
