package ir.parliran.session;

import ir.parliran.global.DatePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
import java.util.*;

/**
 * ©hFahimi.com @ 2019/12/22 14:39
 */

@Repository
class SessionInvitationRepositoryImpl implements SessionInvitationRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    SessionInvitationRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<SessionInvitation> findBySessionOid(long fkSessionOid) {
        Optional<SessionInvitation> item = Optional.empty();
        String sql = "select * from sess_invitation where fk_session_oid = ?";
        try {
            item = Optional.ofNullable(
                    jdbcTemplate.queryForObject(sql,
                            new Object[]{fkSessionOid},
                            new BeanPropertyRowMapper<>(SessionInvitation.class)
                    )
            );
        }
        catch (EmptyResultDataAccessException ignore) {
        }
        return item;
    }

    private void fillPreparedStatement(PreparedStatement ps, SessionInvitation item) throws SQLException {
        ps.setTime(1, Time.valueOf(item.getSessionStartTime()));
        ps.setObject(2, Optional.ofNullable(item.getLetterReceivedDate().getGregorianDate()).orElse(null));
        ps.setString(3, item.getLetterNo());
        ps.setLong(4, item.getFkSessionOid());
    }

    @Override
    public SessionInvitation add(SessionInvitation item) {
        jdbcTemplate.update(connection -> {
            final PreparedStatement ps = connection.prepareStatement(
                    "insert into sess_invitation " +
                            "(session_start_time, letter_received_date, letter_no, fk_session_oid) " +
                            "values " +
                            "(?, ?, ?, ?)");
            fillPreparedStatement(ps, item);
            return ps;
        });
        return item;
    }

    public int edit(SessionInvitation item) {
        return jdbcTemplate.update(connection -> {
            final PreparedStatement ps = connection.prepareStatement(
                    "update sess_invitation set " +
                            "session_start_time = ?, letter_received_date = ?, letter_no = ? where fk_session_oid = ? "
            );
            fillPreparedStatement(ps, item);
            return ps;
        });
    }

    @Override
    public int remove(long fkSessionOid) {
        return jdbcTemplate.update("delete from sess_invitation where fk_session_oid = ? ", fkSessionOid);
    }

    @Override
    public int similarCount(SessionInvitation item) {
        Integer result = jdbcTemplate.queryForObject(
                "select count(fk_session_oid) as counter from sess_invitation " +
                        "where " +
                        "fk_session_oid <> ? and letter_received_date = date(?) and letter_no = ? ",
                new Object[]{
                        item.getFkSessionOid(),
                        Optional.ofNullable(item.getLetterReceivedDate().getGregorianDate()).orElse(null),
                        item.getLetterNo()
                },
                Integer.class
        );
        return result != null ? result : -1;
    }

    @Override
    public boolean exists(long fkSessionOid) {
        Integer result = jdbcTemplate.queryForObject(
                "select count(fk_session_oid) as counter from sess_invitation " +
                        "where fk_session_oid = ? ",
                new Object[]{fkSessionOid},
                Integer.class
        );
        return result != null && result >= 1;
    }
}
