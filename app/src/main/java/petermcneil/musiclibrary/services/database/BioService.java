package petermcneil.musiclibrary.services.database;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;
import petermcneil.domain.Bio;
import petermcneil.musiclibrary.services.CRUDService;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Service
public class BioService implements CRUDService<Bio>{
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private static final Logger LOG = LoggerFactory.getLogger(BioService.class);

    public BioService(final NamedParameterJdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Bio get(Integer bioId){
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("bioId", bioId);
        return jdbcTemplate.query("SELECT * FROM bio WHERE idbio=:bioId", params, new ResultSetExtractor<Bio>() {
            @Override
            public Bio extractData(ResultSet rs) throws SQLException, DataAccessException {
                Bio.Builder bio = Bio.bioBuilder();
                while(rs.next()){
                    bio.bioId(rs.getInt("idbio"));
                    bio.biography(rs.getString("biography"));
                    bio.image(rs.getString("picture"));
                }
                return bio.build();
            }
        });
    }

    @Override
    public List<Bio> getList() {
        return null;
    }

    @Override
    public Integer post(Bio bio) {
        MapSqlParameterSource params = new MapSqlParameterSource();

        params.addValue("biography", bio.getBiography());
        params.addValue("image", bio.getImage());

        KeyHolder key = new GeneratedKeyHolder();

        jdbcTemplate.update("INSERT INTO bio(biography, picture) VALUES (:biography, :image)", params, key);

        Map<String, Object> bioData = key.getKeyList().get(0);
        Integer bioId = Integer.parseInt(bioData.get("idbio").toString());

        LOG.info("RESPONSE: Returned the bio id {}", bioId);
        return bioId;
    }

    @Override
    public void put(Bio bio, Integer bioId) {
        MapSqlParameterSource params = new MapSqlParameterSource();

        params.addValue("bioId", bioId);
        params.addValue("biography", bio.getBiography());
        params.addValue("image", bio.getImage());

        jdbcTemplate.update("UPDATE bio SET biography=:biography, picture=:image WHERE idbio=:bioId", params);
        LOG.info("RESPONSE: PUT the bio ({}) in the database", bioId);
    }

    @Override
    public void delete(Integer objectId) {

    }

}
