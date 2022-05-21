package ir.parliran.session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;

/**
 * ©hFahimi.com @ 2019/12/22 14:39
 */

@Repository
class SessionDeputyJustificationReportRepositoryImpl implements SessionDeputyJustificationReportRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    SessionDeputyJustificationReportRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<SessionDeputyJustificationReport> findBySessionOid(long fkSessionOid) {
        Optional<SessionDeputyJustificationReport> item = Optional.empty();
        String sql = "select * from sess_deputy_justification where fk_session_oid = ?";
        try {
            item = Optional.ofNullable(
                    jdbcTemplate.queryForObject(sql,
                            new Object[]{fkSessionOid},
                            new BeanPropertyRowMapper<>(SessionDeputyJustificationReport.class)
                    )
            );
        }
        catch (EmptyResultDataAccessException ignore) {
        }
        return item;
    }

    private void fillPreparedStatement(PreparedStatement ps, SessionDeputyJustificationReport item) throws SQLException {
        ps.setString(1, item.getDocument());
        ps.setString(2, item.getExpertOpinion());
        ps.setString(3, item.getDescription());
        ps.setString(4, item.getFilename());
        ps.setString(5, item.getSendLetterNo());
        ps.setObject(6, Optional.ofNullable(item.getSendLetterDate().getGregorianDate()).orElse(null));
        ps.setLong(7, item.getFkSessionOid());
    }

    @Override
    @Transactional
    public SessionDeputyJustificationReport add(SessionDeputyJustificationReport item) {
        jdbcTemplate.update(connection -> {
            final PreparedStatement ps = connection.prepareStatement(
                    "insert into sess_deputy_justification " +
                            "(document, expert_opinion, description, filename, send_letter_no, send_letter_date, " +
                            "fk_session_oid) values (?, ?, ?, ?, ?, ?, ?)");
            fillPreparedStatement(ps, item);
            return ps;
        });
        return item;
    }


    @Override
    @Transactional
    public int edit(SessionDeputyJustificationReport item) {
        return jdbcTemplate.update(connection -> {
            final PreparedStatement ps = connection.prepareStatement(
                    "update sess_deputy_justification set " +
                            "document = ?, expert_opinion = ?, description = ?, filename = ?, " +
                            "send_letter_no = ?, send_letter_date = ? where fk_session_oid = ? "
            );
            fillPreparedStatement(ps, item);
            return ps;
        });
    }

    @Override
    @Transactional
    public int remove(long fkSessionOid) {
        return jdbcTemplate.update("delete from sess_deputy_justification where fk_session_oid = ? ", fkSessionOid);
    }

    @Override
    public int similarCount(SessionDeputyJustificationReport item) {
        Integer result = jdbcTemplate.queryForObject(
                "select count(fk_session_oid) as counter from sess_deputy_justification " +
                        "where " +
                        "fk_session_oid <> ? and send_letter_date = date(?) and send_letter_no = ? ",
                new Object[]{
                        item.getFkSessionOid(),
                        Optional.ofNullable(item.getSendLetterDate().getGregorianDate()).orElse(null),
                        item.getSendLetterNo()
                },
                Integer.class
        );
        return result != null ? result : -1;
    }

    @Override
    public boolean exists(long fkSessionOid) {
        Integer result = jdbcTemplate.queryForObject(
                "select count(fk_session_oid) as counter from sess_deputy_justification " +
                        "where fk_session_oid = ? ",
                new Object[]{fkSessionOid},
                Integer.class
        );
        return result != null && result >= 1;
    }

    @Override
    public int removeFile(long fkSessionOid) {
        return jdbcTemplate.update("update sess_deputy_justification set filename = null where fk_session_oid = ? ",
                fkSessionOid);
    }
}
