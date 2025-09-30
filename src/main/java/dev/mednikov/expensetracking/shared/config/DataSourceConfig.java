package dev.mednikov.expensetracking.shared.config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;
import java.net.URI;

@Configuration
@Profile("prod")
public class DataSourceConfig {

    private final static Logger logger = LoggerFactory.getLogger(DataSourceConfig.class);

    private final String databaseUser;
    private final String databasePassword;
    private final String databaseJdbcUrl;

    public DataSourceConfig(@Value("${DATABASE_URL}") String databaseUrl) throws Exception{
        final URI url = new URI(databaseUrl);
        this.databaseUser = url.getUserInfo().split(":")[0];
        this.databasePassword = url.getUserInfo().split(":")[1];
        String databaseHost = url.getHost();
        int databasePort = url.getPort();
        String databaseName = url.getPath();
        this.databaseJdbcUrl = "jdbc:postgresql://" + databaseHost + ":" + Integer.toString(databasePort) + databaseName;
    }

    @Bean
    public DataSource dataSource(){
        final DataSource dataSource = DataSourceBuilder.create()
                .url(databaseJdbcUrl)
                .username(databaseUser)
                .password(databasePassword)
                .driverClassName("org.postgresql.Driver")
                .build();
        logger.info("Datasource was created");
        return dataSource;
    }

}