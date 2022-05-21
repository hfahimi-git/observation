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
class SessionBoardJustificationReportRepositoryImpl implements SessionBoardJustificationReportRepository {
    private final JdbcTemplate jdbcTemplate;
    private final SessionBoardJustificationReportAgendaRepository agenda;

    @Autowired
    SessionBoardJustificationReportRepositoryImpl(JdbcTemplate jdbcTemplate, SessionBoardJustificationReportAgendaRepository agenda) {
        this.jdbcTemplate = jdbcTemplate;
        this.agenda = agenda;
    }

    @Override
    public Optional<SessionBoardJustificationReport> findBySessionOid(long fkSessionOid) {
        Optional<SessionBoardJustificationReport> item = Optional.empty();
        String sql = "select * from sess_board_justification where fk_session_oid = ?";
        try {
            item = Optional.ofNullable(
                    jdbcTemplate.queryForObject(sql,
                            new Object[]{fkSessionOid},
                            new BeanPropertyRowMapper<>(SessionBoardJustificationReport.class)
                    )
            );
        }
        catch (EmptyResultDataAccessException ignore) {
        }
        return item;
    }

    private void fillPreparedStatement(PreparedStatement ps, SessionBoardJustificationReport item) throws SQLException {
        ps.setString(1, item.getLocation());
        ps.setString(2, item.getDocument());
        ps.setString(3, item.getAgendaHistory());
        ps.setString(4, item.getDescription());
        ps.setString(5, item.getFilename());
        ps.setString(6, item.getLetterNo());
        ps.setObject(7, Optional.ofNullable(item.getLetterDate().getGregorianDate()).orElse(null));
        ps.setLong(8, item.getFkSessionOid());
    }

    @Override
    @Transactional
    public SessionBoardJustificationReport add(SessionBoardJustificationReport item) {
        jdbcTemplate.update(connection -> {
            final PreparedStatement ps = connection.prepareStatement(
                    "insert into sess_board_justification " +
                            "(location, document, agenda_history, description, filename, letter_no, letter_date, fk_session_oid) " +
                            "values " +
                            "(?, ?, ?, ?, ?, ?, ?, ?)");
            fillPreparedStatement(ps, item);
            return ps;
        });

        agenda.add(item.getFkSessionOid(), item.getAgenda());
        return item;
    }


    @Override
    @Transactional
    public int edit(SessionBoardJustificationReport item) {
        int result =  jdbcTemplate.update(connection -> {
            final PreparedStatement ps = connection.prepareStatement(
                    "update sess_board_justification set " +
                            "location = ?, document = ?, agenda_history = ?, description = ?, filename = ?, " +
                            "letter_no = ?, letter_date = ? where fk_session_oid = ? "
            );
            fillPreparedStatement(ps, item);
            return ps;
        });
        if (result > 0) {
            agenda.edit(item.getFkSessionOid(), item.getAgenda());
        }
        return result;
    }

    @Override
    @Transactional
    public int remove(long fkSessionOid) {
        int result = jdbcTemplate.update("delete from sess_board_justification where fk_session_oid = ? ", fkSessionOid);
        agenda.remove(fkSessionOid);
        return result;
    }

    @Override
    public int similarCount(SessionBoardJustificationReport item) {
        Integer result = jdbcTemplate.queryForObject(
                "select count(fk_session_oid) as counter from sess_board_justification " +
                        "where " +
                        "fk_session_oid <> ? and letter_date = date(?) and letter_no = ? ",
                new Object[]{
                        item.getFkSessionOid(),
                        Optional.ofNullable(item.getLetterDate().getGregorianDate()).orElse(null), item.getLetterNo()
                },
                Integer.class
        );
        return result != null ? result : -1;
    }

    @Override
    public boolean exists(long fkSessionOid) {
        Integer result = jdbcTemplate.queryForObject(
                "select count(fk_session_oid) as counter from sess_board_justification " +
                        "where fk_session_oid = ? ",
                new Object[]{fkSessionOid},
                Integer.class
        );
        return result != null && result >= 1;
    }

    @Override
    public int removeFile(long fkSessionOid) {
        return jdbcTemplate.update("update sess_board_justification set filename = null where fk_session_oid = ? ",
                fkSessionOid);
    }
}
