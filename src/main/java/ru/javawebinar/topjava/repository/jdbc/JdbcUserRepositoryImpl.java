package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.util.UserUtil;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

@Repository
public class JdbcUserRepositoryImpl implements UserRepository {

    private static final ResultSetExtractor<List<User>> USER_RESULT_SET_EXTRACTOR = rs -> {
        Map<Integer, User> map = new HashMap<>();
        Set<Role> roles;
        while (rs.next()) {
            Integer id = rs.getInt("id");
            User user = map.get(id);
            if (user == null) {
                user = new User();
                user.setId(id);
                roles = new HashSet<>();
                user.setRoles(roles);
                user.setCaloriesPerDay(rs.getInt("calories_per_day"));
                user.setEmail(rs.getString("email"));
                user.setName(rs.getString("name"));
                user.setPassword(rs.getString("password"));
                user.setEnabled(rs.getBoolean("enabled"));
                user.setRegistered(rs.getDate("registered"));
                map.put(id, user);
            } else {
                roles = user.getRoles();
            }
            roles.add(Role.valueOf(rs.getString("role")));
        }
        List<User> result = new ArrayList<>(map.values());
        result.sort(UserUtil.SORT_BY_NAME_EMAIL);
        return result;
    };

    private static final RowMapper<Role> ROLE_ROW_MAPPER = (rs, rowNum) -> Role.valueOf(rs.getString("role"));

    private static final String INSERT_ROLES = "INSERT INTO user_roles (role, user_id) VALUES (?, ?)";
    private static final String DELETE_ROLES = "DELETE FROM user_roles WHERE role=? AND user_id=?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private SimpleJdbcInsert insertUser;

    @Autowired
    public JdbcUserRepositoryImpl(DataSource dataSource) {
        this.insertUser = new SimpleJdbcInsert(dataSource)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");
    }

    @Transactional
    @Override
    public User save(User user) {
        Integer id = user.getId();
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("id", id)
                .addValue("name", user.getName())
                .addValue("email", user.getEmail())
                .addValue("password", user.getPassword())
                .addValue("registered", user.getRegistered())
                .addValue("enabled", user.isEnabled())
                .addValue("caloriesPerDay", user.getCaloriesPerDay());

        if (user.isNew()) {
            Number newKey = insertUser.executeAndReturnKey(map);
            id = newKey.intValue();
            user.setId(id);
            List<Role> roles = new ArrayList<>(user.getRoles());
            updateRoles(INSERT_ROLES, id, roles);
        } else {
            namedParameterJdbcTemplate.update(
                    "UPDATE users SET name=:name, email=:email, password=:password, " +
                            "registered=:registered, enabled=:enabled, calories_per_day=:caloriesPerDay WHERE id=:id", map);
            List<Role> oldRoles = getRoles(id);
            Set<Role> newRoles = user.getRoles();
            if (!oldRoles.equals(newRoles)) {
                List<Role> rolesToDelete = new ArrayList<>(oldRoles);
                rolesToDelete.removeAll(newRoles);
                List<Role> rolesToInsert = new ArrayList<>(newRoles);
                rolesToInsert.removeAll(oldRoles);
                updateRoles(INSERT_ROLES, id, rolesToInsert);
                updateRoles(DELETE_ROLES, id, rolesToDelete);
            }
        }
        return user;
    }

    private void updateRoles(String query, final int id, final List<Role> roles) {
        jdbcTemplate.batchUpdate(query, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setString(1, roles.get(i).toString());
                ps.setInt(2, id);
            }

            @Override
            public int getBatchSize() {
                return roles.size();
            }
        });
    }

    @Transactional
    @Override
    public boolean delete(int id) {
        return jdbcTemplate.update("DELETE FROM users WHERE id=?", id) != 0;
    }

    @Override
    public User get(int id) {
        List<User> users = jdbcTemplate.query(
                "SELECT * FROM users LEFT JOIN user_roles ON users.id = user_roles.user_id WHERE id=?",
                USER_RESULT_SET_EXTRACTOR,
                id);
        return DataAccessUtils.singleResult(users);
    }

    @Override
    public User getByEmail(String email) {
        List<User> users = jdbcTemplate.query(
                "SELECT * FROM users LEFT JOIN user_roles ON users.id = user_roles.user_id WHERE email=?",
                USER_RESULT_SET_EXTRACTOR,
                email);
        return DataAccessUtils.singleResult(users);
    }

    @Override
    public List<User> getAll() {
        return jdbcTemplate.query(
                "SELECT * FROM users LEFT JOIN user_roles ON users.id = user_roles.user_id ORDER BY name, email",
                USER_RESULT_SET_EXTRACTOR);
    }

    private List<Role> getRoles(int id) {
        return jdbcTemplate.query(
                "SELECT * FROM user_roles WHERE user_id=?",
                ROLE_ROW_MAPPER,
                id);
    }
}
