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
class ParliamentMemberPeriodCityRepositoryImpl implements ParliamentMemberPeriodCityRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    ParliamentMemberPeriodCityRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Map<String, String>> findAllComplex(long fkPeriodOid) {
        String sql = "select lu_city, l1.value as lu_city_title " +
                "from pm_period_city " +
                "left outer join (select `key`, value from lookup where extra = '" + LookupGroupKey.city + "' ) l1 on l1.`key` = lu_city " +
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
                "select lu_city from pm_period_city where fk_pm_period_oid = ? ",
                new Object[]{fkPeriodOid},
                String.class);

    }

    @Transactional
    @Override
    public int[] add(long fkPeriodOid, @NotNull List<String> cities) {
        return jdbcTemplate.batchUpdate(
                "insert into pm_period_city(fk_pm_period_oid, lu_city) values(?, ?)",
                new BatchPreparedStatementSetter() {

                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setLong(1, fkPeriodOid);
                        ps.setString(2, cities.get(i));
                    }

                    @Override
                    public int getBatchSize() {
                        return cities.size();
                    }
                });
    }

    @Override
    public int remove(long fkPeriodOid) {
        return jdbcTemplate.update("delete from pm_period_city where fk_pm_period_oid= ?", fkPeriodOid);
    }

    @Transactional
    @Override
    public void edit(long fkPeriodOid, @NotNull List<String> cities) {
        remove(fkPeriodOid);
        add(fkPeriodOid, cities);

    }

}
