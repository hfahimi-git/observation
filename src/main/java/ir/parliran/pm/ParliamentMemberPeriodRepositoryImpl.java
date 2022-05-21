package ir.parliran.pm;

import ir.parliran.global.LookupGroupKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

/**
 * ©hFahimi.com @ 2019/12/22 14:39
 */

@Repository
class ParliamentMemberPeriodRepositoryImpl implements ParliamentMemberPeriodRepository {
    private final JdbcTemplate jdbcTemplate;
    private final ParliamentMemberPeriodCityRepository parliamentMemberPeriodCityRepository;
    private final ParliamentMemberPeriodLanguageRepository parliamentMemberPeriodLanguageRepository;
    private final ParliamentMemberPeriodCommissionRepository parliamentMemberPeriodCommissionRepository;

    @Autowired
    ParliamentMemberPeriodRepositoryImpl(JdbcTemplate jdbcTemplate,
                                         ParliamentMemberPeriodCityRepository parliamentMemberPeriodCityRepository,
                                         ParliamentMemberPeriodLanguageRepository parliamentMemberPeriodLanguageRepository,
                                         ParliamentMemberPeriodCommissionRepository parliamentMemberPeriodCommissionRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.parliamentMemberPeriodCityRepository = parliamentMemberPeriodCityRepository;
        this.parliamentMemberPeriodLanguageRepository = parliamentMemberPeriodLanguageRepository;
        this.parliamentMemberPeriodCommissionRepository = parliamentMemberPeriodCommissionRepository;
    }

    @Override
    public List<ParliamentMemberPeriod> findAll(long fkParliamentMemberOid) {
        String sql = "select oid, fk_parliament_member_oid, lu_period_no, l1.value as lu_period_no_title, " +
                "lu_province, l2.value as lu_province_title " +
                "from pm_period " +
                "left outer join (select `key`, value from lookup where group_key = '" + LookupGroupKey.parliament_period +
                "' ) l1 on l1.`key` = pm_period.lu_period_no " +
                "left outer join (select `key`, value from lookup where group_key = '" + LookupGroupKey.province +
                "' ) l2 on l2.`key` = pm_period.lu_province " +
                "where fk_parliament_member_oid = ? " +
                "order by lu_period_no desc ";

        return jdbcTemplate.query(sql, new Object[]{fkParliamentMemberOid},
                new BeanPropertyRowMapper<>(ParliamentMemberPeriod.class)
        );
    }

    @Override
    public Optional<ParliamentMemberPeriod> findById(long oid) {
        ParliamentMemberPeriod pmp;
        try {
            pmp = jdbcTemplate.queryForObject(
                    "select oid, fk_parliament_member_oid, lu_inter_period, l1.value as lu_inter_period_title, lu_period_no, " +
                            "l2.value as lu_period_no_title, lu_province, l3.value as lu_province_title, vote_count, vote_percent, " +
                            "description from pm_period " +
                            "left outer join (select `key`, value from lookup where group_key = '" + LookupGroupKey.inter_period +
                            "' ) l1 on l1.`key` = pm_period.lu_inter_period " +
                            "left outer join (select `key`, value from lookup where group_key = '" + LookupGroupKey.parliament_period +
                            "' ) l2 on l2.`key` = pm_period.lu_period_no " +
                            "left outer join (select `key`, value from lookup where group_key = '" + LookupGroupKey.province +
                            "' ) l3 on l3.`key` = pm_period.lu_province " +
                            "where oid = ? ",
                    new Object[]{oid},
                    new BeanPropertyRowMapper<>(ParliamentMemberPeriod.class)
            );
            if(Optional.ofNullable(pmp).isPresent()) {
                pmp.setCitiesComplex(parliamentMemberPeriodCityRepository.findAllComplex(pmp.getOid()));
                pmp.setCities(parliamentMemberPeriodCityRepository.findAll(pmp.getOid()));
//                            .languagesComplex(parliamentMemberPeriodLanguageRepository.findAllComplex(rs.getLong("oid")))
                pmp.setLanguages(parliamentMemberPeriodLanguageRepository.findAll(pmp.getOid()));
            }
        } catch (EmptyResultDataAccessException ex) {
            pmp = null;
        }
        return Optional.ofNullable(pmp);
    }

    private Optional<ParliamentMemberPeriod> findLiteById(long oid) {
        ParliamentMemberPeriod pmp;
        try {
            pmp = jdbcTemplate.queryForObject(
                    "select oid, fk_parliament_member_oid, lu_period_no " +
                            "from pm_period " +
                            "where oid = ? ",
                    new Object[]{oid},
                    new BeanPropertyRowMapper<>(ParliamentMemberPeriod.class)
            );
        } catch (EmptyResultDataAccessException ex) {
            pmp = null;
        }
        return Optional.ofNullable(pmp);
    }

    private void fillPreparedStatement(@NotNull PreparedStatement ps, @NotNull ParliamentMemberPeriod pmp) throws SQLException {
        ps.setLong(1, pmp.getFkParliamentMemberOid());
        ps.setString(2, pmp.getLuInterPeriod());
        ps.setInt(3, pmp.getLuPeriodNo());
        ps.setString(4, pmp.getLuProvince());
        ps.setInt(5, pmp.getVoteCount());
        ps.setFloat(6, pmp.getVotePercent());
        ps.setString(7, pmp.getDescription());
    }

    @Transactional
    @Override
    public ParliamentMemberPeriod add(@NotNull ParliamentMemberPeriod pmp) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "insert into pm_period " +
                            "(fk_parliament_member_oid, lu_inter_period, lu_period_no, lu_province, vote_count, " +
                            "vote_percent, description) " +
                            "values (?, ?, ?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            fillPreparedStatement(ps, pmp);
            return ps;
        }, keyHolder);
        pmp.setOid(Objects.requireNonNull(keyHolder.getKey()).longValue());
        if (pmp.getOid() > 0) {
            parliamentMemberPeriodCityRepository.add(pmp.getOid(), pmp.getCities());
            parliamentMemberPeriodLanguageRepository.add(pmp.getOid(), pmp.getLanguages());
        } else {
            throw new RuntimeException("insertion pm_period failed!");
        }
        return pmp;
    }

    @Override
    public int similarCount(long fkParliamentMemberOid, int luPeriodNo) {
        Integer result = jdbcTemplate.queryForObject(
                "select count(oid) as counter from pm_period where fk_parliament_member_oid = ?  and lu_period_no = ?",
                new Object[]{fkParliamentMemberOid, luPeriodNo},
                Integer.class
        );
        if (result == null)
            result = -1;
        return result;
    }

    @Transactional
    @Override
    public int edit(@NotNull ParliamentMemberPeriod pmp) {
        int result = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "update pm_period " +
                            "set fk_parliament_member_oid = ?, lu_inter_period = ?, lu_period_no = ?, lu_province = ?, " +
                            "vote_count = ?, vote_percent = ?, description = ? " +
                            "where oid = ?"
            );
            fillPreparedStatement(ps, pmp);
            ps.setLong(8, pmp.getOid());
            return ps;
        });
        if (result > 0) {
            parliamentMemberPeriodCityRepository.edit(pmp.getOid(), pmp.getCities());
            parliamentMemberPeriodLanguageRepository.edit(pmp.getOid(), pmp.getLanguages());
        } else {
            throw new RuntimeException("update pm_period failed!");
        }
        return result;
    }

    @Transactional
    @Override
    public int remove(long oid) {
        Optional<ParliamentMemberPeriod> pmp = findLiteById(oid);
        pmp.ifPresent(parliamentMemberPeriod ->
                parliamentMemberPeriodCommissionRepository.removeAll(
                        parliamentMemberPeriod.getFkParliamentMemberOid(), parliamentMemberPeriod.getLuPeriodNo()
                )
        );
        return jdbcTemplate.update("delete from pm_period where oid = ?", oid);
    }
}
