package petermcneil.musiclibrary.ioc;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.PooledDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;


@Configuration
public class AppConfig {
    private static final Logger LOG = LoggerFactory.getLogger(AppConfig.class);

    @Bean(destroyMethod = "close")
    public PooledDataSource dataSource(@Value("${db.driverClassName}") String driverClassName,
                                       @Value("${db.jdbcSchemaUrl}") String jdbcSchemaUrl,
                                       @Value("${db.user}") String user,
                                       @Value("${db.password}") String password
    ) throws PropertyVetoException {
        final ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();
        comboPooledDataSource.setDriverClass(driverClassName);
        comboPooledDataSource.setJdbcUrl(jdbcSchemaUrl);
        comboPooledDataSource.setUser(user);
        comboPooledDataSource.setPassword(password);

        //  Sensible defaults
        comboPooledDataSource.setCheckoutTimeout(Long.valueOf(TimeUnit.SECONDS.toMillis(10)).intValue());
        comboPooledDataSource.setMaxConnectionAge(Long.valueOf(TimeUnit.MINUTES.toSeconds(30)).intValue());
        comboPooledDataSource.setMaxIdleTime(Long.valueOf(TimeUnit.MINUTES.toSeconds(30)).intValue());
        comboPooledDataSource.setInitialPoolSize(5);
        comboPooledDataSource.setMinPoolSize(5);
        comboPooledDataSource.setMaxPoolSize(20);
        comboPooledDataSource.setNumHelperThreads(16);

        LOG.info("Connecting db with following Jdbc Url '{}'", jdbcSchemaUrl);
        return comboPooledDataSource;
    }

    @Bean
    public DataSourceTransactionManager transactionManager(final DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate(final DataSourceTransactionManager transactionManager) {
        return new NamedParameterJdbcTemplate(transactionManager.getDataSource());
    }
}
