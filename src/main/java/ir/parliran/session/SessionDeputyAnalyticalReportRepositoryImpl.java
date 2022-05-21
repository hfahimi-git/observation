package ir.parliran.session;

import ir.parliran.global.LookupGroupKey;
import org.springframework.beans.factory.annotation.Autowired;
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
class SessionDeputyAnalyticalReportRepositoryImpl implements SessionDeputyAnalyticalReportRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    SessionDeputyAnalyticalReportRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<SessionDeputyAnalyticalReport> findAll(long fkSessionOid) {
        String sql = "select da.*, l1.value as lu_grade_status_title " +
                "from sess_deputy_analytical da " +
                "left outer join (select `key`, value from lookup where group_key = '" + LookupGroupKey.grade_status +
                "' ) l1 on l1.`key` = da.lu_grade_status " +
                "where da.fk_session_oid = ?";

        return jdbcTemplate.query(sql,
                new Object[]{fkSessionOid},
                new BeanPropertyRowMapper<>(SessionDeputyAnalyticalReport.class)
        );
    }

    @Override
    public Optional<SessionDeputyAnalyticalReport> findById(long oid) {
        Optional<SessionDeputyAnalyticalReport> item;
        String sql = "select da.*, l1.value as lu_grade_status_title " +
                "from sess_deputy_analytical da " +
                "left outer join (select `key`, value from lookup where group_key = '" + LookupGroupKey.grade_status +
                "' ) l1 on l1.`key` = da.lu_grade_status " +
                "where da.oid = ?";

        return Optional.ofNullable(
                jdbcTemplate.queryForObject(sql,
                        new Object[]{oid},
                        new BeanPropertyRowMapper<>(SessionDeputyAnalyticalReport.class)
                )
        );
    }

    private void fillPreparedStatement(PreparedStatement ps, SessionDeputyAnalyticalReport item) throws SQLException {
        ps.setString(1, item.getBeneficiaryLetterNo());
        ps.setObject(2, Optional.ofNullable(item.getBeneficiaryLetterDate().getGregorianDate()).orElse(null));
        ps.setString(3, item.getEvaluationLetterNo());
        ps.setObject(4, Optional.ofNullable(item.getEvaluationLetterDate().getGregorianDate()).orElse(null));
        ps.setString(5, item.getLuGradeStatus());
        ps.setString(6, item.getAnalytical());
        ps.setString(7, item.getFilename());
        ps.setLong(8, item.getFkSessionOid());
        ps.setLong(9, item.getFkCommissionReportOid());

    }

    @Override
    public SessionDeputyAnalyticalReport add(SessionDeputyAnalyticalReport item) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            final PreparedStatement ps = connection.prepareStatement(
                    "insert into sess_deputy_analytical " +
                            "(beneficiary_letter_no, beneficiary_letter_date, evaluation_letter_no, evaluation_letter_date, " +
                            "lu_grade_status, analytical, filename, fk_session_oid, fk_commission_report_oid) " +
                            "values (?, ?, ?, ?, ?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            fillPreparedStatement(ps, item);
            return ps;
        }, keyHolder);
        item.setOid(Objects.requireNonNull(keyHolder.getKey()).longValue());

        return item;
    }


    @Override
    public int edit(SessionDeputyAnalyticalReport item) {
        return jdbcTemplate.update(connection -> {
            final PreparedStatement ps = connection.prepareStatement(
                    "update sess_deputy_analytical set " +
                            "beneficiary_letter_no = ?, beneficiary_letter_date = ?, evaluation_letter_no = ?, " +
                            "evaluation_letter_date = ?, lu_grade_status = ?, analytical = ?, filename = ?, " +
                            "fk_session_oid = ?, fk_commission_report_oid = ?  " +
                            "where oid = ? "
            );
            fillPreparedStatement(ps, item);
            ps.setLong(10, item.getOid());
            return ps;
        });
    }

    @Override
    public int remove(long oid) {
        return jdbcTemplate.update("delete from sess_deputy_analytical where oid = ? ", oid);
    }

    @Override
    public int similarCount(SessionDeputyAnalyticalReport item) {
        Integer result = jdbcTemplate.queryForObject(
                "select count(oid) as counter from sess_deputy_analytical " +
                        "where " +
                        "oid <> ? and fk_session_oid = ? and fk_commission_report_oid = ? and " +
                        "beneficiary_letter_date = date(?) and beneficiary_letter_no = ? ",
                new Object[]{
                        item.getOid(),
                        item.getFkSessionOid(),
                        item.getFkCommissionReportOid(),
                        Optional.ofNullable(item.getBeneficiaryLetterDate().getGregorianDate()).orElse(null),
                        item.getBeneficiaryLetterNo()
                },
                Integer.class
        );
        return result != null ? result : -1;
    }

    @Override
    public boolean exists(SessionDeputyAnalyticalReport item) {
        Integer result = jdbcTemplate.queryForObject(
                "select count(fk_session_oid) as counter from sess_deputy_analytical " +
                        "where fk_session_oid = ? and fk_commission_report_oid = ? ",
                new Object[]{item.getFkSessionOid(), item.getFkCommissionReportOid()},
                Integer.class
        );
        return result != null && result >= 1;
    }

    @Override
    public int removeFile(long oid) {
        return jdbcTemplate.update("update sess_deputy_analytical set filename = null where oid = ? ", oid);
    }

    @Override
    public boolean exists(long oid) {
        Integer result = jdbcTemplate.queryForObject(
                "select count(oid) as counter from sess_deputy_analytical where oid = ? ",
                new Object[]{oid},
                Integer.class
        );
        return result != null && result >= 1;
    }
}
