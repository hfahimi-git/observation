package ir.parliran.misc;

import ir.parliran.global.LookupGroupKey;
import ir.parliran.global.Page;
import ir.parliran.global.PagingHelper;
import ir.parliran.global.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;

/**
 * ©hFahimi.com @ 2019/12/22 14:39
 */

@Repository
class MiscRepositoryImpl implements MiscReportRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    MiscRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<MiscReport> findById(long oid) {
        Optional<MiscReport> item = Optional.empty();
        String sql = "select m.*, concat(m.title, ' (', l1.value , ')') as fk_board_period_oid_title, l2.value as lu_type_title " +
                "from misc_report m " +
                "left outer join board_period bp on bp.oid = m.fk_board_period_oid " +
                "left outer join board b on b.oid = bp.fk_board_oid " +
                "left outer join (select `key`, value from lookup where group_key = '" + LookupGroupKey.parliament_period +
                "' ) l1 on l1.`key` = bp.lu_period_no " +
                "left outer join (select `key`, value from lookup where group_key = '" + LookupGroupKey.misc_report_type +
                "' ) l2 on l2.`key` = m.lu_type where m.oid = ? " +
                " order by m.oid desc";
        try {
            item = Optional.ofNullable(
                    jdbcTemplate.queryForObject(sql,
                            new Object[]{oid},
                            new BeanPropertyRowMapper<>(MiscReport.class)
                    )
            );
        } catch (EmptyResultDataAccessException ignore) {
        }
        return item;

    }

    public Page<MiscReport> findAll(SearchCredential searchCredential) {
        StringJoiner conditions = new StringJoiner(" AND ", " WHERE ", "").setEmptyValue("");
        List<Object> params = new ArrayList<>();
        if (!Utils.isBlank(searchCredential.getKeyword())) {
            conditions.add("(title like ? or letter_no like ? or description like ?)");
            params.add("%" + searchCredential.getKeyword() + "%");
            params.add("%" + searchCredential.getKeyword() + "%");
            params.add("%" + searchCredential.getKeyword() + "%");
        }

        if (searchCredential.getLuType() != null && searchCredential.getLuType().trim().length() > 0) {
            conditions.add("lu_type = ?");
            params.add(searchCredential.getLuType().trim());
        }

        if (searchCredential.getFkBoardOid() != null && searchCredential.getFkBoardOid() > 0 ) {
            conditions.add("bp.fk_board_oid = ?");
            params.add(searchCredential.getFkBoardOid());
        }

        if (searchCredential.getLuPeriodNo() != null && searchCredential.getLuPeriodNo() > 0 ) {
            conditions.add("bp.lu_period_no = ?");
            params.add(searchCredential.getLuPeriodNo());
        }

        if (searchCredential.getFromDate() != null && searchCredential.getFromDate().getGregorianDate() != null) {
            conditions.add("letter_date >= ?");
            params.add(Optional.ofNullable(searchCredential.getFromDate().getGregorianDate()).orElse(null));
        }

        if (searchCredential.getToDate() != null && searchCredential.getToDate().getGregorianDate() != null) {
            conditions.add("letter_date <= ?");
            params.add(Optional.ofNullable(searchCredential.getToDate().getGregorianDate()).orElse(null));
        }

        String sql = "select m.*, concat(b.title, ' (', l1.value , ')') as fk_board_period_oid_title, l2.value as lu_type_title " +
                "from misc_report m " +
                "left outer join board_period bp on bp.oid = m.fk_board_period_oid " +
                "left outer join board b on b.oid = bp.fk_board_oid " +
                "left outer join (select `key`, value from lookup where group_key = '" + LookupGroupKey.parliament_period +
                "' ) l1 on l1.`key` = bp.lu_period_no " +
                "left outer join (select `key`, value from lookup where group_key = '" + LookupGroupKey.misc_report_type +
                "' ) l2 on l2.`key` = m.lu_type " +
                conditions +
                " order by m.oid desc";

        return new PagingHelper<MiscReport>().fetch(jdbcTemplate, sql, params.toArray(), searchCredential.getPageNo(),
                searchCredential.getPageSize(),
                new BeanPropertyRowMapper<>(MiscReport.class)
        );
    }

    private void fillPreparedStatement(PreparedStatement ps, MiscReport item) throws SQLException {
        ps.setString(1, item.getTitle());
        ps.setString(2, item.getLetterNo());
        ps.setObject(3, Optional.ofNullable(item.getLetterDate().getGregorianDate()).orElse(null));
        ps.setString(4, item.getDescription());
        ps.setString(5, item.getFilename());
        ps.setString(6, item.getLuType());
        ps.setLong(7, item.getFkBoardPeriodOid());
    }

    @Override
    @Transactional
    public MiscReport add(MiscReport item) {
        int result = jdbcTemplate.update(connection -> {
            final PreparedStatement ps = connection.prepareStatement(
                    "insert into misc_report " +
                            "(title, letter_no, letter_date, description, filename, lu_type, fk_board_period_oid) " +
                            "values (?, ?, ?, ?, ?, ?, ?)");
            fillPreparedStatement(ps, item);
            return ps;
        });
        return item;
    }


    @Override
    @Transactional
    public int edit(MiscReport item) {
        return jdbcTemplate.update(connection -> {
            final PreparedStatement ps = connection.prepareStatement(
                    "update misc_report set " +
                            "title = ?, letter_no = ?, letter_date = ?, description = ?, filename = ?, lu_type = ? " +
                            " where oid = ? "
            );
            fillPreparedStatement(ps, item);
            return ps;
        });
    }

    @Override
    public int similarCount(MiscReport item) {
        Integer result = jdbcTemplate.queryForObject(
                "select count(oid) as counter from misc_report " +
                        "where " +
                        "oid <> ? and  fk_board_period_oid = ? and  letter_no = ? and letter_date = ? ",
                new Object[]{
                        item.getOid(),
                        item.getFkBoardPeriodOid(), item.getLetterNo(),
                        Optional.ofNullable(item.getLetterDate().getGregorianDate()).orElse(null)
                },
                Integer.class
        );
        return result != null ? result : -1;
    }

    @Override
    public boolean exists(long oid) {
        Integer result = jdbcTemplate.queryForObject(
                "select count(oid) as counter from misc_report where oid = ? ",
                new Object[]{oid},
                Integer.class
        );
        return result != null && result >= 1;
    }

    @Override
    public int remove(long oid) {
        return jdbcTemplate.update("delete from misc_report where oid = ? ", oid);
    }

    @Override
    public int removeFile(long oid) {
        return jdbcTemplate.update("update misc_report set filename = null where oid = ? ", oid);
    }
}
