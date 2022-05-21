package ir.parliran.session;

import ir.parliran.global.LookupGroupKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
class SessionObserverReportRepositoryImpl implements SessionObserverReportRepository {
    private final JdbcTemplate jdbcTemplate;
    private final String JOIN_FIELDS =
            "l1.value as lu_abs_pres_status_title, " +
                    "l2.value as lu_invitation_status_title, " +
                    "l3.value as lu_coordination_status_title, " +
                    "l4.value as lu_hold_status_title, " +
                    "l5.value as lu_member_abs_pres_status_title ";

    private final String JOIN_STATEMENTS =
            "left outer join (select `key`, value from lookup where group_key = '" + LookupGroupKey.abs_present +
                    "' ) l1 on l1.`key` = o.lu_abs_pres_status " +

                    "left outer join (select `key`, value from lookup where group_key = '" + LookupGroupKey.yes_no +
                    "' ) l2 on l2.`key` = o.lu_invitation_status " +

                    "left outer join (select `key`, value from lookup where group_key = '" + LookupGroupKey.yes_no +
                    "' ) l3 on l3.`key` = o.lu_coordination_status " +

                    "left outer join (select `key`, value from lookup where group_key = '" + LookupGroupKey.yes_no +
                    "' ) l4 on l4.`key` = o.lu_hold_status " +

                    "left outer join (select `key`, value from lookup where group_key = '" + LookupGroupKey.member_abs_pres +
                    "' ) l5 on l5.`key` = o.lu_member_abs_pres_status ";


    @Autowired
    SessionObserverReportRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<SessionObserverReport> findAll(long fkSessionOid) {
        String sql = "select o.*, " +
                JOIN_FIELDS +
                "from sess_observer_report o " +
                JOIN_STATEMENTS +
                "where o.fk_session_oid = ?";

        return jdbcTemplate.query(sql, new Object[]{fkSessionOid},
                new BeanPropertyRowMapper<>(SessionObserverReport.class)
        );
    }

    @Override
    public Optional<SessionObserverReport> findById(long oid) {
            return Optional.ofNullable(
                    jdbcTemplate.queryForObject(
                            "select o.*, " +
                                    JOIN_FIELDS +
                                    "from sess_observer_report o " +
                                    JOIN_STATEMENTS +
                                    "where o.oid = ? ",
                            new Object[]{oid},
                            new BeanPropertyRowMapper<>(SessionObserverReport.class)
                    )
            );
    }

    private void fillPreparedStatement(PreparedStatement ps, SessionObserverReport item) throws SQLException {
        ps.setString(1, item.getLetterNo());
        ps.setObject(2, Optional.ofNullable(item.getLetterDate().getGregorianDate()).orElse(null));
        ps.setString(3, item.getLuAbsPresStatus());
        ps.setString(4, item.getLuInvitationStatus());
        ps.setString(5, item.getLuCoordinationStatus());
        ps.setString(6, item.getLuHoldStatus());
        ps.setString(7, item.getLuMemberAbsPresStatus());
        ps.setString(8, item.getTopic());
        ps.setString(9, item.getSuggestion());
        ps.setString(10, item.getContrary());
        ps.setString(11, item.getDescription());
        ps.setString(12, item.getFilename());
        ps.setLong(13, item.getFkSessionOid());
        ps.setLong(14, item.getFkBoardObserverOid());
    }

    @Override
    public SessionObserverReport add(SessionObserverReport item) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            final PreparedStatement ps = connection.prepareStatement(
                    "insert into sess_observer_report " +
                            "(letter_no, letter_date, lu_abs_pres_status, lu_invitation_status, lu_coordination_status, " +
                            "lu_hold_status, lu_member_abs_pres_status, topic, suggestion, contrary, description, filename, " +
                            "fk_session_oid, fk_board_observer_oid) " +
                            "values " +
                            "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            fillPreparedStatement(ps, item);
            return ps;
        }, keyHolder);
        item.setOid(Objects.requireNonNull(keyHolder.getKey()).intValue());
        return item;
    }

    @Override
    @Transactional
    public int edit(SessionObserverReport item) {
        return jdbcTemplate.update(connection -> {
            final PreparedStatement ps = connection.prepareStatement(
                    "update sess_observer_report set " +
                            "letter_no = ?, letter_date = ?, lu_abs_pres_status = ?, lu_invitation_status = ?, " +
                            "lu_coordination_status = ?, lu_hold_status = ?, lu_member_abs_pres_status = ?, topic = ?, " +
                            "suggestion = ?, contrary = ?, description = ?, filename = ?, fk_session_oid = ?, " +
                            "fk_board_observer_oid = ? where oid = ? "
            );
            fillPreparedStatement(ps, item);
            ps.setLong(15, item.getOid());
            return ps;
        });
    }

    @Override
    public int remove(long oid) {
        return jdbcTemplate.update("delete from sess_observer_report where oid = ? ", oid);
    }

    @Override
    public int similarCount(SessionObserverReport item) {
        Integer result = jdbcTemplate.queryForObject(
                "select count(fk_session_oid) as counter from sess_observer_report " +
                        "where " +
                        "oid <> ? and fk_session_oid = ? and fk_board_observer_oid = ? and letter_date = date(?) and " +
                        "letter_no = ? ",
                new Object[]{
                        item.getOid(),
                        item.getFkSessionOid(),
                        item.getFkBoardObserverOid(),
                        Optional.ofNullable(item.getLetterDate().getGregorianDate()).orElse(null), item.getLetterNo()
                },
                Integer.class
        );
        return result != null ? result : -1;
    }

    @Override
    public boolean exists(SessionObserverReport sessionObserverReport) {
        Integer result = jdbcTemplate.queryForObject(
                "select count(oid) as counter from sess_observer_report " +
                        "where fk_session_oid = ? and fk_board_observer_oid = ?",
                new Object[]{sessionObserverReport.getFkBoardObserverOid(), sessionObserverReport.getFkBoardObserverOid()},
                Integer.class
        );
        return result != null && result >= 1;
    }

    @Override
    public int removeFile(long oid) {
        return jdbcTemplate.update(
                "update sess_observer_report " +
                        "set filename = null " +
                        "where oid = ? ",
                oid);
    }

    @Override
    public boolean exists(long oid) {
        Integer result = jdbcTemplate.queryForObject(
                "select count(oid) as counter from sess_observer_report where oid = ?",
                new Object[]{oid},
                Integer.class
        );
        return result != null && result >= 1;
    }
}
