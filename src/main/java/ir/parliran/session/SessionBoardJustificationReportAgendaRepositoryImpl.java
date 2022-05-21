package ir.parliran.session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.StringJoiner;

/**
 * ©hFahimi.com @ 2019/12/22 14:39
 */

@Repository
class SessionBoardJustificationReportAgendaRepositoryImpl implements SessionBoardJustificationReportAgendaRepository {
    private final JdbcTemplate jdbcTemplate;
    private final SessionAgendaRepository sessionAgendaRepository;

    @Autowired
    SessionBoardJustificationReportAgendaRepositoryImpl(JdbcTemplate jdbcTemplate, SessionAgendaRepository sessionAgendaRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.sessionAgendaRepository = sessionAgendaRepository;
    }

    @Override
    public List<Long> findAllBySessionOid(long fkSessionOid) {
        String sql = "select agenda_no from sess_board_justification_agenda where fk_session_oid = ?";
        return jdbcTemplate.query(sql, new Object[]{fkSessionOid},
                new BeanPropertyRowMapper<>(Long.class)
        );
    }

    @Override
    public int add(long fkSessionOid, List<Integer> agendaNumbers) {
        if(agendaNumbers == null){
            return 0;
        }
        StringJoiner sql = new StringJoiner(", ",
                " insert into sess_board_justification_agenda(fk_session_oid, agenda_no) values ", "; ")
                .setEmptyValue("");

        for (Integer agenda : agendaNumbers) {
            if (agenda != null &&
                    sessionAgendaRepository.exists(fkSessionOid, agenda) &&
                    !exists(fkSessionOid, agenda)
            ) {
                sql.add("(" + fkSessionOid + ", " + agenda + ")");
            } else {
                agendaNumbers.remove(agenda);
            }
        }
        if (sql.length() < 1) {
            return 0;
        }
        return jdbcTemplate.update(sql.toString());
    }

    public void edit(long fkSessionOid, List<Integer> agendaNumbers) {
        remove(fkSessionOid);
        add(fkSessionOid, agendaNumbers);
    }

    @Override
    public int remove(long fkSessionOid) {
        return jdbcTemplate.update("delete from sess_board_justification_agenda where fk_session_oid = ? ", fkSessionOid);
    }

    @Override
    public boolean exists(long fkSessionOid, int orderby) {
        Integer result = jdbcTemplate.queryForObject(
                "select count(fk_session_oid) as counter from sess_board_justification_agenda " +
                        "where fk_session_oid = ? ",
                new Object[]{fkSessionOid},
                Integer.class
        );
        return result != null && result >= 1;
    }
}
