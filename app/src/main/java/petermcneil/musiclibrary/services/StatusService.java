package petermcneil.musiclibrary.services;

import org.slf4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import static org.slf4j.LoggerFactory.getLogger;

@Service
public class StatusService {
    private static final Logger LOG = getLogger(StatusService.class);

    private static final Integer EXPECTED_JDBC_RESULT = 1;
    private static final String SIMPLE_SQL_SELECT = "SELECT 1";

    private final JdbcTemplate jdbcTemplate;

    public StatusService(final JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean getStatus(){
        Integer jdbcResult = this.jdbcTemplate.queryForObject(SIMPLE_SQL_SELECT, Integer.class);
        LOG.info("RESPONSE: Retrieved the status from the server - {}", jdbcResult);
        if (!EXPECTED_JDBC_RESULT.equals(jdbcResult)){
            return false;
        }
        return true;
    }

}
