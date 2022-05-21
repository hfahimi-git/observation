package ir.parliran.session;

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
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * ©hFahimi.com @ 2019/12/22 14:39
 */

@Repository
class SessionAgendaRepositoryImpl implements SessionAgendaRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    SessionAgendaRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<SessionAgenda> findAll(long fkSessionOid) {
        String sql = "select * " +
                "from sess_agenda " +
                "where fk_session_oid = ? " +
                " order by orderby desc";

        return jdbcTemplate.query(sql, new Object[]{fkSessionOid},
                new BeanPropertyRowMapper<>(SessionAgenda.class)
        );
    }

    @Override
    public Optional<SessionAgenda> findById(long oid) {
        Optional<SessionAgenda> item = Optional.empty();
        try {
            item = Optional.ofNullable(
                    jdbcTemplate.queryForObject(
                            "select * from sess_agenda where oid = ? ",
                            new Object[]{oid},
                            new BeanPropertyRowMapper<>(SessionAgenda.class)
                    )
            );
        } catch (EmptyResultDataAccessException ignore) {}
        return item;

    }

    private void fillPreparedStatement(PreparedStatement ps, SessionAgenda item) throws SQLException {
        ps.setObject(1, item.getOrderby());
        ps.setObject(2, item.getDescription());
        ps.setLong(3, item.getFkSessionOid());
    }

    @Override
    public SessionAgenda add(SessionAgenda item) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            final PreparedStatement ps = connection.prepareStatement(
                    "insert into sess_agenda " +
                            "(orderby, description, fk_session_oid) " +
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

    public int edit(SessionAgenda item) {
        return jdbcTemplate.update(connection -> {
            final PreparedStatement ps = connection.prepareStatement(
                    "update sess_agenda set orderby = ?, description = ? where oid = ?"
            );
            fillPreparedStatement(ps, item);
            ps.setLong(3, item.getOid());
            return ps;
        });
    }

    @Override
    public int remove(long oid) {
        return jdbcTemplate.update("delete from sess_agenda where oid = ? ", oid);
    }

    @Override
    public int similarCount(SessionAgenda item) {
        Integer result = jdbcTemplate.queryForObject(
                "select count(oid) as counter from sess_agenda " +
                        "where oid <> ? and fk_session_oid = ? and orderby = ?",
                new Object[]{item.getOid(), item.getFkSessionOid(), item.getOrderby() },
                Integer.class
        );
        return result != null? result: -1;
    }

    @Override
    public boolean exists(SessionAgenda item) {
        Integer result = jdbcTemplate.queryForObject(
                "select count(oid) as counter from sess_agenda " +
                        "where fk_session_oid = ? and orderby = ?",
                new Object[]{item.getFkSessionOid(), item.getOrderby() },
                Integer.class
        );
        return result != null && result >= 1;
    }

    @Override
    public boolean exists(long fkSessionOid, int orderby) {
        return exists(SessionAgenda.builder().fkSessionOid(fkSessionOid).orderby(orderby).build());
    }
}
