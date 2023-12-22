package pl.kargolek.data.jdbc;

import lombok.Getter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

/**
 * @author Karol Kuta-Orlowicz
 */
@Getter
public class MySqlDbConnector {

    private final JdbcTemplate jdbcTemplate;

    private MySqlDbConnector(DbConnectorBuilder dbConnectorBuilder) {
        this.jdbcTemplate = new JdbcTemplate(dbConnectorBuilder.dataSource);
    }

    public static DbConnectorBuilder builder() {
        return new DbConnectorBuilder();
    }

    public static class DbConnectorBuilder {
        private String dataSourceURL;
        private String username;
        private String password;
        private DataSource dataSource;

        public DbConnectorBuilder setDataSourceURL(String dataSourceURL) {
            this.dataSourceURL = dataSourceURL;
            return this;
        }

        public DbConnectorBuilder setUsername(String username) {
            this.username = username;
            return this;
        }

        public DbConnectorBuilder setPassword(String password) {
            this.password = password;
            return this;
        }

        public MySqlDbConnector build() {
            DriverManagerDataSource dataSource = new DriverManagerDataSource();
            dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
            dataSource.setUrl(this.dataSourceURL);
            dataSource.setUsername(this.username);
            dataSource.setPassword(this.password);
            this.dataSource = dataSource;
            return new MySqlDbConnector(this);
        }
    }
}
