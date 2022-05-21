package ir.parliran.lookup;

import ir.parliran.global.Page;
import ir.parliran.global.PagingHelper;
import ir.parliran.global.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

/**
 * ©hFahimi.com @ 2019/12/23 14:34
 */

@Repository
public class LookupRepositoryImpl implements LookupRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public LookupRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<String> findAllGroupKeys() {
        return jdbcTemplate.queryForList(
                "select distinct group_key from lookup order by group_key", String.class
        );
    }

    @Override
    public Page<Lookup> findAll(String keyword, String groupKey, int pageNo, int pageSize) {
        StringJoiner conditions = new StringJoiner(" AND ", " WHERE ", "").setEmptyValue("");
        List<Object> params = new ArrayList<>();
        if (!Utils.isBlank(keyword)) {
            conditions.add("(`key` like ? or value like ? or extra like ? or group_key like ?)");
            params.add("%" + keyword  + "%");
            params.add("%" + keyword  + "%");
            params.add("%" + keyword  + "%");
            params.add("%" + keyword  + "%");
        }

        if (!Utils.isBlank(groupKey)) {
            conditions.add("group_key = ?");
            params.add(groupKey);
        }

        String sql = "select * from lookup " + conditions + " order by oid desc";

        return new PagingHelper<Lookup>().fetch(jdbcTemplate, sql, params.toArray(), pageNo, pageSize,
                new BeanPropertyRowMapper<>(Lookup.class) );

    }

    @Override
    public Optional<Lookup> findByGroupKeyAndKey(String groupKey, String key) {
        Optional<Lookup> pm = Optional.empty();
        try {
            pm = Optional.ofNullable(
                    jdbcTemplate.queryForObject(
                            "select * from lookup where lookup.group_key = ? and `key` = ?",
                            new Object[]{groupKey, key},
                            new BeanPropertyRowMapper<>(Lookup.class)
                    )
            );
        } catch (EmptyResultDataAccessException ignore) {}
        return pm;
    }

    @Override
    public List<Lookup> findByGroupKey(String groupKey) {
        return jdbcTemplate.query(
                "select * from lookup where group_key = ? order by orderby, oid",
                new Object[]{groupKey},
                new BeanPropertyRowMapper<>(Lookup.class)
        );
    }

    public List<Lookup> findByGroupKeyOrderBy(String groupKey, String orderBy) {
        return jdbcTemplate.query(
                "select * from lookup where group_key = ? order by " + orderBy,
                new Object[]{groupKey},
                new BeanPropertyRowMapper<>(Lookup.class)
        );
    }

    @Override
    public Optional<Lookup> findById(long oid) {
        Optional<Lookup> pm = Optional.empty();
        try {
            pm = Optional.ofNullable(
                    jdbcTemplate.queryForObject(
                            "select * from lookup where lookup.oid = ?",
                            new Object[]{oid},
                            new BeanPropertyRowMapper<>(Lookup.class)
                    )
            );
        } catch (EmptyResultDataAccessException ignore) {}
        return pm;

    }

    private void fillPreparedStatement(PreparedStatement ps, Lookup pm) throws SQLException {
        ps.setString(1, pm.getGroupKey());
        ps.setString(2, pm.getKey());
        ps.setString(3, pm.getValue());
        ps.setString(4, pm.getExtra());
        ps.setObject(5, pm.getOrderby());
    }

    @Override
    public Lookup add(Lookup pm) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "insert into lookup " +
                            "(group_key, `key`, value, extra, orderby) " +
                            "values " +
                            "(?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            fillPreparedStatement(ps, pm);
            return ps;
        }, keyHolder);
        pm.setOid(Objects.requireNonNull(keyHolder.getKey()).longValue());
        return pm;
    }

    @Override
    public int edit(Lookup pm) {
        return jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "update lookup set " +
                            "group_key = ?, `key` = ?, value = ?, extra = ?, orderby = ? " +
                            "where oid = ?"
            );
            fillPreparedStatement(ps, pm);
            ps.setLong(6, pm.getOid());
            return ps;
        });
    }

    @Override
    public int remove(long oid) {
        return jdbcTemplate.update("delete from lookup where oid = ?", oid);
    }

}