package ir.parliran.session;

import ir.parliran.global.LookupGroupKey;
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
class SessionCommissionReportRepositoryImpl implements SessionCommissionReportRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    SessionCommissionReportRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<SessionCommissionReport> findAll(long fkSessionOid) {
        String sql = "select b.*, l1.value as lu_grade_status_title " +
                "from sess_commission_report b " +
                "left outer join (select `key`, value from lookup where group_key = '" + LookupGroupKey.grade_status +
                "' ) l1 on l1.`key` = b.lu_grade_status " +
                "where b.fk_session_oid = ?";

        return jdbcTemplate.query(sql, new Object[]{fkSessionOid},
                new BeanPropertyRowMapper<>(SessionCommissionReport.class)
        );
    }

    @Override
    public Optional<SessionCommissionReport> findById(long oid) {
        Optional<SessionCommissionReport> item;
        String sql = "select b.*, l1.value as lu_grade_status_title " +
                "from sess_commission_report b " +
                "left outer join (select `key`, value from lookup where group_key = '" + LookupGroupKey.grade_status +
                "' ) l1 on l1.`key` = b.lu_grade_status " +
                "where b.oid = ?";
        item = Optional.ofNullable(
                jdbcTemplate.queryForObject(sql,
                        new Object[]{oid},
                        new BeanPropertyRowMapper<>(SessionCommissionReport.class)
                )
        );
        return item;
    }

    private void fillPreparedStatement(PreparedStatement ps, SessionCommissionReport item) throws SQLException {
        ps.setLong(1, item.getFkBoardCommissionOid());
        ps.setString(2, item.getToCmsnLetterNo());
        ps.setObject(3, Optional.ofNullable(item.getToCmsnLetterDate().getGregorianDate()).orElse(null));
        ps.setString(4, item.getToDptLetterNo());
        ps.setObject(5, Optional.ofNullable(item.getToDptLetterDate().getGregorianDate()).orElse(null));
        ps.setString(6, item.getEvaluation());
        ps.setString(7, item.getFilename());
        ps.setString(8, item.getLuGradeStatus());
        ps.setLong(9, item.getFkSessionOid());
        ps.setLong(10, Optional.ofNullable(item.getFkBoardObserverOid()).orElse(0L));
        ps.setLong(11, item.getFkObserverReportOid());
    }

    @Override
    public SessionCommissionReport add(SessionCommissionReport item) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            final PreparedStatement ps = connection.prepareStatement(
                    "insert into sess_commission_report " +
                            "(fk_board_commission_oid, to_cmsn_letter_no, to_cmsn_letter_date, to_dpt_letter_no, " +
                            "to_dpt_letter_date, evaluation, filename, lu_grade_status, fk_session_oid, " +
                            "fk_board_observer_oid, fk_observer_report_oid) " +
                            "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            fillPreparedStatement(ps, item);
            return ps;
        }, keyHolder);
        item.setOid(Objects.requireNonNull(keyHolder.getKey()).longValue());
        return item;
    }


    @Override
    public int edit(SessionCommissionReport item) {
        return jdbcTemplate.update(connection -> {
            final PreparedStatement ps = connection.prepareStatement(
                    "update sess_commission_report set " +
                            "fk_board_commission_oid = ?, to_cmsn_letter_no = ?, to_cmsn_letter_date = ?, " +
                            "to_dpt_letter_no = ?, to_dpt_letter_date = ?, evaluation = ?, filename = ?, " +
                            "lu_grade_status = ?, fk_session_oid = ?, fk_board_observer_oid = ?, " +
                            "fk_observer_report_oid = ? " +
                            "where oid = ? "
            );
            fillPreparedStatement(ps, item);
            ps.setLong(12, item.getOid());
            return ps;
        });
    }

    @Override
    public int remove(long oid) {
        return jdbcTemplate.update("delete from sess_commission_report where oid = ? ", oid);
    }

    @Override
    public int similarCount(SessionCommissionReport item) {
        Integer result = jdbcTemplate.queryForObject(
                "select count(fk_session_oid) as counter from sess_commission_report " +
                        "where " +
                        "oid <> ? and fk_observer_report_oid = ?",
                /*fk_session_oid = ? and fk_board_commission_oid = ? and to_dpt_letter_date = date(?) and " +
                        "to_dpt_letter_no = ? and fk_board_observer_oid = ?",*/
                new Object[]{item.getOid(),
                        item.getFkObserverReportOid()
                        /*
                        item.getOid(),
                        item.getFkSessionOid(),
                        item.getFkBoardCommissionOid(),
                        Optional.ofNullable(item.getToDptLetterDate().getGregorianDate()).orElse(null),
                        item.getToDptLetterNo(),
                        item.getFkBoardObserverOid()*/
                },
                Integer.class
        );
        return result != null ? result : -1;
    }

    @Override
    public boolean exists(SessionCommissionReport item) {
        Integer result = jdbcTemplate.queryForObject(
                "select count(fk_session_oid) as counter from sess_commission_report " +
                        "where fk_session_oid = ? and fk_observer_report_oid = ? ",
                        /*fk_board_observer_oid = ? and fk_board_commission_oid = ? ",*/
                new Object[]{item.getFkSessionOid(), item.getFkObserverReportOid()
                        /*item.getFkBoardObserverOid(), item.getFkBoardCommissionOid()*/},
                Integer.class
        );
        return result != null && result >= 1;
    }

    @Override
    public int removeFile(long oid) {
        return jdbcTemplate.update("update sess_commission_report set filename = null where oid = ?", oid);
    }

    @Override
    public boolean exists(long oid) {
        Integer result = jdbcTemplate.queryForObject(
                "select count(oid) as counter from sess_commission_report where oid = ? ",
                new Object[]{oid},
                Integer.class
        );
        return result != null && result >= 1;
    }
}
