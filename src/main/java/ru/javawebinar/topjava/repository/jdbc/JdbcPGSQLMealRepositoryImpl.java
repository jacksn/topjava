package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
@Profile("db-postgres")
public class JdbcPGSQLMealRepositoryImpl extends AbstractJdbcMealRepositoryImpl {

    public JdbcPGSQLMealRepositoryImpl(DataSource dataSource) {
        super(dataSource);
    }
}
