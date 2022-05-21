package ir.parliran.pm;

import ir.parliran.global.Page;
import ir.parliran.global.PagingHelper;
import ir.parliran.global.Utils;
import ir.parliran.lookup.Lookup;
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
 * ©hFahimi.com @ 2019/12/22 14:39
 */

@Repository
class ParliamentMemberRepositoryImpl implements ParliamentMemberRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    ParliamentMemberRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Page<ParliamentMember> findAll(String keyword, int pageNo, int pageSize) {
        StringJoiner conditions = new StringJoiner(" AND ", " WHERE ", "").setEmptyValue("");
        List<Object> params = new ArrayList<>();
        if (!Utils.isBlank(keyword)) {
            conditions.add("(name like ? or family like ?)");
            params.add("%" + keyword + "%");
            params.add("%" + keyword + "%");
        }
        String sql = "select oid, name, family, father_name, filename from parliament_member " +
                conditions +
                " order by oid desc";

        return new PagingHelper<ParliamentMember>().fetch(jdbcTemplate, sql, params.toArray(), pageNo, pageSize,
                new BeanPropertyRowMapper<>(ParliamentMember.class)
        );
    }

    @Override
    public Page<ParliamentMember> findAll(SearchCredential searchCredential) {
        StringJoiner conditions = new StringJoiner(" AND ", " WHERE ", "").setEmptyValue("");
        List<Object> params = new ArrayList<>();
        if (!Utils.isBlank(searchCredential.getKeyword())) {
            conditions.add("(name like ? or family like ?)");
            params.add("%" + searchCredential.getKeyword() + "%");
            params.add("%" + searchCredential.getKeyword() + "%");
        }

        if (searchCredential.getLuPeriodNo() != null && searchCredential.getLuPeriodNo() > 0 ) {
            conditions.add("oid in (select fk_parliament_member_oid from pm_period where lu_period_no = ?)");
            params.add(searchCredential.getLuPeriodNo());
        }

        String sql = "select oid, name, family, father_name, filename from parliament_member " +
                conditions +
                " order by oid desc";

        return new PagingHelper<ParliamentMember>().fetch(jdbcTemplate, sql, params.toArray(),
                searchCredential.getPageNo(), searchCredential.getPageSize(),
                new BeanPropertyRowMapper<>(ParliamentMember.class)
        );
    }

    @Override
    public Optional<ParliamentMember> findById(long oid) {
        Optional<ParliamentMember> pm = Optional.empty();
        try {
            pm = Optional.ofNullable(
                    jdbcTemplate.queryForObject(
                            "select * from parliament_member where oid = ?",
                            new Object[]{oid},
                            new BeanPropertyRowMapper<>(ParliamentMember.class)
                    )
            );
        } catch (EmptyResultDataAccessException ignore) {}
        return pm;

    }

    private void fillPreparedStatement(PreparedStatement ps, ParliamentMember pm) throws SQLException {
        ps.setString(1, pm.getLuTitle());
        ps.setString(2, pm.getName());
        ps.setString(3, pm.getFamily());
        ps.setString(4, pm.getLuGender());
        ps.setString(5, pm.getFatherName());
        ps.setInt(6, pm.getBirthYear());
        ps.setString(7, pm.getLuBirthCity());
        ps.setString(8, pm.getDescription());
    }


    @Override
    public ParliamentMember add(ParliamentMember pm) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "insert into parliament_member " +
                            "(lu_title, name, family, lu_gender, father_name, birth_year, lu_birth_city, description) " +
                            "values " +
                            "(?, ?, ?, ?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            fillPreparedStatement(ps, pm);
            return ps;
        }, keyHolder);
        pm.setOid(Objects.requireNonNull(keyHolder.getKey()).longValue());
        return pm;
    }

    @Override
    public int editImage(long oid, String filename) {
        return jdbcTemplate.update("update parliament_member set filename = ? where oid = ?",
                filename, oid);
    }

    @Override
    public int edit(ParliamentMember pm) {
        return jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "update parliament_member " +
                            "set lu_title = ?, name = ?, family = ?, lu_gender = ?, father_name = ?, birth_year = ?, " +
                            "lu_birth_city = ?, description = ? " +
                            "where oid = ?"
            );
            fillPreparedStatement(ps, pm);
            ps.setLong(9, pm.getOid());
            return ps;
        });
    }

    @Override
    public int remove(long oid) {
        return jdbcTemplate.update("delete from parliament_member where oid = ?", oid);
    }

    @Override
    public boolean exists(long oid) {
        Integer result = jdbcTemplate.queryForObject(
                "select count(oid) as counter from parliament_member where oid = ?",
                new Object[]{oid},
                Integer.class
        );

        return result != null && result >= 1;
    }
}
