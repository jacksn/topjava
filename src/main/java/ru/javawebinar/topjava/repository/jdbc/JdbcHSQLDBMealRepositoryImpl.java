package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@Profile("db-hsqldb")
public class JdbcHSQLDBMealRepositoryImpl extends AbstractJdbcMealRepositoryImpl {

    public JdbcHSQLDBMealRepositoryImpl(DataSource dataSource) {
        super(dataSource);
    }

//    @Override
//    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
//        return jdbcTemplate.query(
//                "SELECT * FROM meals WHERE user_id=?  AND date_time BETWEEN  ? AND ? ORDER BY date_time DESC",
//                ROW_MAPPER, userId, Timestamp.valueOf(startDate), Timestamp.valueOf(endDate));
//    }
//
//    @Override
//    protected MapSqlParameterSource getMapSqlParameterSource(Meal meal, int userId) {
//        return super.getMapSqlParameterSource(meal, userId).addValue("date_time", Timestamp.valueOf(meal.getDateTime()));
//    }
}