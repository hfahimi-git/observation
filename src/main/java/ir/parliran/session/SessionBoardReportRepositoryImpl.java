package ir.parliran.session;

import ir.parliran.global.LookupGroupKey;
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
class SessionBoardReportRepositoryImpl implements SessionBoardReportRepository {
    private final JdbcTemplate jdbcTemplate;
    private final SessionPresentService presentService;

    @Autowired
    SessionBoardReportRepositoryImpl(JdbcTemplate jdbcTemplate, SessionPresentService presentService) {
        this.jdbcTemplate = jdbcTemplate;
        this.presentService = presentService;
    }

    @Override
    public Optional<SessionBoardReport> findBySessionOid(long fkSessionOid) {
        Optional<SessionBoardReport> item = Optional.empty();
        String sql = "select b.*, l1.value as lu_hold_status_title " +
                "from sess_board_report b " +
                "left outer join (select `key`, value from lookup where group_key = '" + LookupGroupKey.yes_no +
                "' ) l1 on l1.`key` = b.lu_hold_status " +
                "where b.fk_session_oid = ?";
        try {
            item = Optional.ofNullable(
                    jdbcTemplate.queryForObject(sql,
                            new Object[]{fkSessionOid},
                            new BeanPropertyRowMapper<>(SessionBoardReport.class)
                    )
            );
        }
        catch (EmptyResultDataAccessException ignore) {
        }
        item.ifPresent(e -> e.setPresents(presentService.findAll(e.getFkSessionOid())));
        return item;
    }

    private void fillPreparedStatement(PreparedStatement ps, SessionBoardReport item) throws SQLException {
        ps.setString(1, item.getLetterNo());
        ps.setObject(2, Optional.ofNullable(item.getLetterDate().getGregorianDate()).orElse(null));
        ps.setString(3, item.getLuHoldStatus());
        ps.setString(4, item.getApproval());
        ps.setString(5, item.getFilename());
        ps.setLong(6, item.getFkSessionOid());
    }

    @Override
    @Transactional
    public SessionBoardReport add(SessionBoardReport item) {
        int result = jdbcTemplate.update(connection -> {
            final PreparedStatement ps = connection.prepareStatement(
                    "insert into sess_board_report " +
                            "(letter_no, letter_date, lu_hold_status, approval, filename, fk_session_oid) " +
                            "values (?, ?, ?, ?, ?, ?)");
            fillPreparedStatement(ps, item);
            return ps;
        });
        if(result > 0) {
            presentService.add(item.getFkSessionOid(), item.getPresents());
        }
        return item;
    }


    @Override
    @Transactional
    public int edit(SessionBoardReport item) {
        int result = jdbcTemplate.update(connection -> {
            final PreparedStatement ps = connection.prepareStatement(
                    "update sess_board_report set " +
                            "letter_no = ?, letter_date = ?, lu_hold_status = ?, approval = ?, filename = ?" +
                            " where fk_session_oid = ? "
            );
            fillPreparedStatement(ps, item);
            return ps;
        });
        if(result > 0) {
            presentService.edit(item.getFkSessionOid(), item.getPresents());
        }
        return result;
    }

    @Override
    @Transactional
    public int remove(long fkSessionOid) {
        int result = jdbcTemplate.update("delete from sess_board_report where fk_session_oid = ? ", fkSessionOid);
        if(result > 0) {
            presentService.remove(fkSessionOid);
        }
        return result;
    }

    @Override
    public int similarCount(SessionBoardReport item) {
        Integer result = jdbcTemplate.queryForObject(
                "select count(fk_session_oid) as counter from sess_board_report " +
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
                "select count(fk_session_oid) as counter from sess_board_report " +
                        "where fk_session_oid = ? ",
                new Object[]{fkSessionOid},
                Integer.class
        );
        return result != null && result >= 1;
    }

    @Override
    public int removeFile(long fkSessionOid) {
        return jdbcTemplate.update("update sess_board_report set filename = null where fk_session_oid = ? ",
                fkSessionOid);
    }
}
