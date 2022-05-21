package ir.parliran.pm;

import ir.parliran.global.LookupGroupKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
class ParliamentMemberPeriodLanguageRepositoryImpl implements ParliamentMemberPeriodLanguageRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    ParliamentMemberPeriodLanguageRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Map<String, String>> findAllComplex(long fkPeriodOid) {
        String sql = "select lu_lang, l1.value as lu_lang_title " +
                "from pm_period_lang " +
                "left outer join (select `key`, value from lookup where group_key = '" +
                LookupGroupKey.site_langs + "' ) l1 on l1.`key` = lu_lang " +
                "where fk_pm_period_oid = ? ";

        List<Map<String, String>> result = new ArrayList<>();
        SqlRowSet rows = jdbcTemplate.queryForRowSet(sql, fkPeriodOid);
        while (rows.next()) {
            Map<String, String> pair = new HashMap<>();
            pair.put(rows.getString(1), rows.getString(2));
            result.add(pair);
        }
        return result;

    }

    @Override
    public List<String> findAll(long fkPeriodOid) {
        return jdbcTemplate.queryForList(
                "select lu_lang from pm_period_lang where fk_pm_period_oid = ? ",
                new Object[]{fkPeriodOid},
                String.class);

    }

    @Transactional
    @Override
    public int[] add(long fkPeriodOid, @NotNull List<String> languages) {
        return jdbcTemplate.batchUpdate(
                "insert into pm_period_lang(fk_pm_period_oid, lu_lang) values(?, ?)",
                new BatchPreparedStatementSetter() {

                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setLong(1, fkPeriodOid);
                        ps.setString(2, languages.get(i));
                    }

                    @Override
                    public int getBatchSize() {
                        return languages.size();
                    }
                });
    }

    @Override
    public int remove(long fkPeriodOid) {
        return jdbcTemplate.update("delete from pm_period_lang where fk_pm_period_oid= ?", fkPeriodOid);
    }

    @Transactional
    @Override
    public void edit(long fkPeriodOid, @NotNull List<String> cities) {
        remove(fkPeriodOid);
        add(fkPeriodOid, cities);

    }


}
