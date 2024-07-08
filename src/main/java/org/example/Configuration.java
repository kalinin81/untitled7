package org.example;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

@org.springframework.context.annotation.Configuration
public class Configuration {
    @Bean
    public DataSource dataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:postgresql://localhost:5432/test");
        config.setUsername("postgres");
        config.setPassword("postgres");
        return new HikariDataSource(config);
    }
}
