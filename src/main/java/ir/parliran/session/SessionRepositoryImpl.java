package ir.parliran.session;

import ir.parliran.board.BoardPeriodRepository;
import ir.parliran.global.LookupGroupKey;
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
 * ©hFahimi.com @ 2019/12/22 14:39
 */

@Repository
class SessionRepositoryImpl implements SessionRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    SessionRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Session> findAll(long fkBoardPeriodOid) {
        String sql = "select sess_session.*, concat(title, ' (', l1.value , ')') as fk_board_period_oid_title " +
                "from sess_session " +
                "left outer join board_period bp on bp.oid = sess_session.fk_board_period_oid " +
                "left outer join board b on b.oid = bp.fk_board_oid " +
                "left outer join (select `key`, value from lookup where group_key = '" + LookupGroupKey.parliament_period +
                "' ) l1 on l1.`key` = bp.lu_period_no " +
                "where fk_board_period_oid = ? " +
                " order by sess_session.oid desc";

        return jdbcTemplate.query(sql, new Object[]{fkBoardPeriodOid},
                new BeanPropertyRowMapper<>(Session.class)
        );
    }

    @Override
    public Page<Session> findAll(SearchCredential searchCredential) {
        StringJoiner conditions = new StringJoiner(" AND ", " WHERE ", "").setEmptyValue("");
        List<Object> params = new ArrayList<>();
        if (!Utils.isBlank(searchCredential.getKeyword())) {
            conditions.add("(b.title like ? )");
            params.add("%" + searchCredential.getKeyword() + "%");
        }

        if (!Utils.isBlank(searchCredential.getLuPeriodNo())) {
            conditions.add("bp.lu_period_no = ?");
            params.add(searchCredential.getLuPeriodNo());
        }

        if (searchCredential.getNo() != null && searchCredential.getNo() > 0) {
            conditions.add("no = ?");
            params.add(searchCredential.getNo());
        }

        if (searchCredential.getFromDate() != null && searchCredential.getFromDate().getGregorianDate() != null) {
            conditions.add("date >= ?");
            params.add(Optional.ofNullable(searchCredential.getFromDate().getGregorianDate()).orElse(null));
        }

        if (searchCredential.getToDate() != null && searchCredential.getToDate().getGregorianDate() != null) {
            conditions.add("date <= ?");
            params.add(searchCredential.getToDate().getGregorianDate());
        }

        String sql = "select sess_session.*, concat(title, ' (', l1.value , ')') as fk_board_period_oid_title " +
                "from sess_session " +
                "left outer join board_period bp on bp.oid = sess_session.fk_board_period_oid " +
                "left outer join board b on b.oid = bp.fk_board_oid " +
                "left outer join (select `key`, value from lookup where group_key = '" + LookupGroupKey.parliament_period +
                "' ) l1 on l1.`key` = bp.lu_period_no " +
                conditions +
                " order by sess_session.oid desc";

        return new PagingHelper<Session>().fetch(jdbcTemplate, sql, params.toArray(), searchCredential.getPageNo(),
                searchCredential.getPageSize(),
                new BeanPropertyRowMapper<>(Session.class)
        );
    }

    @Override
    public Optional<Session> findById(long oid) {
        Optional<Session> item = Optional.empty();
        String sql = "select sess_session.*, concat(title, ' (', l1.value , ')') as fk_board_period_oid_title " +
                "from sess_session " +
                "left outer join board_period bp on bp.oid = sess_session.fk_board_period_oid " +
                "left outer join board b on b.oid = bp.fk_board_oid " +
                "left outer join (select `key`, value from lookup where group_key = '" + LookupGroupKey.parliament_period +
                "' ) l1 on l1.`key` = bp.lu_period_no where sess_session.oid = ?";
        try {
            item = Optional.ofNullable(
                    jdbcTemplate.queryForObject(sql,
                            new Object[]{oid},
                            new BeanPropertyRowMapper<>(Session.class)
                    )
            );
        } catch (EmptyResultDataAccessException ignore) {
        }
        return item;

    }

    private void fillPreparedStatement(PreparedStatement ps, Session item) throws SQLException {
        ps.setInt(1, item.getNo());
        ps.setObject(2, Optional.ofNullable(item.getDate().getGregorianDate()).orElse(null));
        ps.setLong(3, item.getFkBoardPeriodOid());
    }

    @Override
    public Session add(Session item) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            final PreparedStatement ps = connection.prepareStatement(
                    "insert into sess_session " +
                            "(no, date, fk_board_period_oid) " +
                            "values " +
                            "(?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            fillPreparedStatement(ps, item);
            return ps;
        }, keyHolder);
        item.setOid(Objects.requireNonNull(keyHolder.getKey()).intValue());
        return item;
    }

    public int edit(Session item) {
        return jdbcTemplate.update(connection -> {
            final PreparedStatement ps = connection.prepareStatement(
                    "update sess_session set no = ?, date = ? where oid = ?"
            );
            fillPreparedStatement(ps, item);
            ps.setLong(3, item.getOid());
            return ps;
        });
    }

    @Override
    public int remove(long oid) {
        return jdbcTemplate.update("delete from sess_session where oid = ? ", oid);
    }

    @Override
    public int similarCount(Session item) {
        Integer result = jdbcTemplate.queryForObject(
                "select count(oid) as counter from sess_session " +
                        "where " +
                        "oid <> ? and  fk_board_period_oid = ? and  no = ? and date = ? ",
                new Object[]{
                        item.getOid(),
                        item.getFkBoardPeriodOid(), item.getNo(),
                        Optional.ofNullable(item.getDate().getGregorianDate()).orElse(null)
                },
                Integer.class
        );
        return result != null ? result : -1;
    }

    @Override
    public boolean exists(long oid) {
        Integer result = jdbcTemplate.queryForObject(
                "select count(oid) as counter from sess_session where oid = ? ",
                new Object[]{oid},Integer.class
        );
        return result != null && result >= 1;
    }
}
