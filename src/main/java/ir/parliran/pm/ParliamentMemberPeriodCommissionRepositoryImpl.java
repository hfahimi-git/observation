package ir.parliran.pm;

import ir.parliran.global.LookupGroupKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

@Repository
class ParliamentMemberPeriodCommissionRepositoryImpl implements ParliamentMemberPeriodCommissionRepository {
    private static final String _SELECT_QUERY_ =
            "select p.*, l0.value as lu_commission_title, l1.value as lu_commission_role_title, " +
                    "l2.value as lu_period_no_title, l3.value as lu_year_no_title " +
                    "from pm_period_commission p " +
                    "left join (select `key`, value from lookup where group_key = '" + LookupGroupKey.commission +
                    "') l0 on l0.`key` = p.lu_commission " +
                    "left join (select `key`, value from lookup where group_key = '" + LookupGroupKey.commission_role +
                    "') l1 on l1.`key` = p.lu_commission_role " +
                    "left join (select `key`, value from lookup where group_key = '" + LookupGroupKey.parliament_period +
                    "') l2 on l2.`key` = p.lu_period_no " +
                    "left join (select `key`, value from lookup where group_key = '" + LookupGroupKey.parliament_year +
                    "') l3 on l3.`key` = p.lu_year_no";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ParliamentMemberPeriodCommissionRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<ParliamentMemberPeriodCommission> findAll(long fkParliamentMemberOid, int luPeriodNo) {
        String sql = _SELECT_QUERY_ +
                " where fk_parliament_member_oid = ? and lu_period_no = ? " +
                "order by lu_period_no, lu_year_no ";

        return jdbcTemplate.query(sql,
                new Object[]{fkParliamentMemberOid, luPeriodNo},
                new BeanPropertyRowMapper<>(ParliamentMemberPeriodCommission.class)
        );

    }

    public Optional<ParliamentMemberPeriodCommission> findById(long oid) {
        return Optional.ofNullable(
                jdbcTemplate.queryForObject(
                        _SELECT_QUERY_ + " where oid = ? ",
                        new Object[]{oid},
                        new BeanPropertyRowMapper<>(ParliamentMemberPeriodCommission.class)
                )
        );
    }

    private void fillPreparedStatement(@NotNull PreparedStatement ps, @NotNull ParliamentMemberPeriodCommission pmpc) throws SQLException {
        ps.setLong(1, pmpc.getFkParliamentMemberOid());
        ps.setString(2, pmpc.getLuCommission());
        ps.setString(3, pmpc.getLuCommissionRole());
        ps.setInt(4, pmpc.getLuPeriodNo());
        ps.setInt(5, pmpc.getLuYearNo());
    }

    public ParliamentMemberPeriodCommission add(@NotNull ParliamentMemberPeriodCommission pmpc) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "insert into pm_period_commission " +
                            "(fk_parliament_member_oid, lu_commission, lu_commission_role, lu_period_no, lu_year_no) " +
                            "values (?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            fillPreparedStatement(ps, pmpc);
            return ps;
        }, keyHolder);
        pmpc.setOid(Objects.requireNonNull(keyHolder.getKey()).longValue());
        return pmpc;

    }

    @Override
    public int similarCount(ParliamentMemberPeriodCommission pmpc) {
        Integer result = jdbcTemplate.queryForObject(
                "select count(oid) as counter from pm_period_commission " +
                        "where fk_parliament_member_oid = ?  and lu_commission = ? and lu_period_no = ? and lu_year_no = ?",
                new Object[]{pmpc.getFkParliamentMemberOid(), pmpc.getLuCommission(), pmpc.getLuPeriodNo(), pmpc.getLuYearNo()},
                Integer.class
        );
        if (result == null)
            result = -1;
        return result;
    }


    public int edit(ParliamentMemberPeriodCommission pmpc) {
        return jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "update pm_period_commission " +
                            "set fk_parliament_member_oid = ?, lu_commission = ?, lu_commission_role = ?, lu_period_no = ?, " +
                            "lu_year_no = ? where oid = ?"
            );
            fillPreparedStatement(ps, pmpc);
            ps.setLong(6, pmpc.getOid());
            return ps;
        });
    }

    public int remove(long oid) {
        return jdbcTemplate.update("delete from pm_period_commission where oid = ?", oid);
    }

    @Override
    public int removeAll(long fkParliamentMemberOid, int luPeriodNo) {
        return jdbcTemplate.update(
                "delete from pm_period_commission where fk_parliament_member_oid = ? and lu_period_no = ?",
                fkParliamentMemberOid, luPeriodNo);
    }
}
