package ir.parliran.security;

import ir.parliran.global.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.*;

/**
 * ©hFahimi.com @ 2019/12/22 14:39
 */

@Repository
class RoleRepositoryImpl implements RoleRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    RoleRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Role> findAll(String keyword) {
        StringJoiner conditions = new StringJoiner(" AND ", " WHERE ", "").setEmptyValue("");
        List<Object> params = new ArrayList<>();
        if (!Utils.isBlank(keyword)) {
            conditions.add("title like ? )");
            params.add("%" + keyword + "%");
        }

        String sql = "select * from security_role " + conditions + " order by oid desc";

        return jdbcTemplate.query(sql, params.toArray(), new BeanPropertyRowMapper<>(Role.class)
        );
    }

    @Override
    public Optional<Role> findById(long oid) {
        Optional<Role> item = Optional.empty();
        try {
            item = Optional.ofNullable(
                    jdbcTemplate.queryForObject(
                            "select * from security_role where oid = ?",
                            new Object[]{oid},
                            new BeanPropertyRowMapper<>(Role.class)
                    )
            );
        } catch (EmptyResultDataAccessException ignore) {
        }
        return item;

    }

    @Override
    public Optional<Role> findByTitle(String title) {
        Optional<Role> item = Optional.empty();
        try {
            item = Optional.ofNullable(
                    jdbcTemplate.queryForObject(
                            "select * from security_role where title = ?",
                            new Object[]{title},
                            new BeanPropertyRowMapper<>(Role.class)
                    )
            );
        } catch (EmptyResultDataAccessException ignore) {
        }
        return item;
    }

    @Override
    public Role add(Role role) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "insert into security_role (title) values (?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, role.getTitle());
            return ps;
        }, keyHolder);
        role.setOid(Objects.requireNonNull(keyHolder.getKey()).longValue());
        return role;
    }

    @Override
    public int edit(Role role) {
        return jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "update security_role set title = ? where oid = ?"
            );
            ps.setString(1, role.getTitle());
            ps.setLong(2, role.getOid());
            return ps;
        });
    }

    @Override
    public int remove(long oid) {
        return jdbcTemplate.update("delete from security_role where oid = ?", oid);
    }

    @Override
    public int similarCount(Role role) {
        Integer result = jdbcTemplate.queryForObject(
                "select count(oid) as counter from security_role where title = ? and oid <> ?",
                new Object[]{role.getTitle(), role.getOid()},
                Integer.class
        );
        return result != null ? result : -1;
    }

    @Override
    public boolean exists(long oid) {
        Integer result = jdbcTemplate.queryForObject(
                "select count(oid) as counter from security_role where oid = ?",
                new Object[]{oid},
                Integer.class
        );

        return result != null && result >= 1;
    }

    @Override
    public boolean exists(String title) {
        Integer result = jdbcTemplate.queryForObject(
                "select count(oid) as counter from security_role where title = ?",
                new Object[]{title},
                Integer.class
        );

        return result != null && result >= 1;
    }
}
