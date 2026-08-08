package com.example.qrcode.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.net.URI;

@Configuration
public class DatabaseConfig {

    @Bean
    public DataSource dataSource(
            @Value("${DATABASE_URL:}") String databaseUrl,
            @Value("${DATABASE_HOST:localhost}") String databaseHost,
            @Value("${DATABASE_PORT:5432}") String databasePort,
            @Value("${DATABASE_NAME:qrcode_db}") String databaseName,
            @Value("${DATABASE_USER:postgres}") String databaseUser,
            @Value("${DATABASE_PASSWORD:postgres}") String databasePassword) {

        String url;
        String username;
        String password;

        if (databaseUrl != null && !databaseUrl.isBlank()) {
            URI uri = URI.create(databaseUrl);
            url = "jdbc:postgresql://" + uri.getHost() + ":" + (uri.getPort() > 0 ? uri.getPort() : 5432) + uri.getPath();
            String[] userInfo = uri.getUserInfo() == null ? new String[]{} : uri.getUserInfo().split(":", 2);
            username = userInfo.length > 0 ? userInfo[0] : databaseUser;
            password = userInfo.length > 1 ? userInfo[1] : databasePassword;
        } else {
            url = "jdbc:postgresql://" + databaseHost + ":" + databasePort + "/" + databaseName;
            username = databaseUser;
            password = databasePassword;
        }

        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setMaximumPoolSize(5);
        return dataSource;
    }
}
