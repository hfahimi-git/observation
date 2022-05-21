package ir.parliran.contact;

import ir.parliran.global.LookupGroupKey;
import ir.parliran.global.Page;
import ir.parliran.global.PagingHelper;
import ir.parliran.global.Utils;
import ir.parliran.pm.ParliamentMemberPeriodCommission;
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
import java.util.*;

/**
 * ©hFahimi.com @ 2019/12/22 14:39
 */

@Repository
class ContactRepositoryImpl implements ContactRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    ContactRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Page<Contact> findAll(String keyword, String luContactType,int pageNo, int pageSize) {
        StringJoiner conditions = new StringJoiner(" AND ", " WHERE ", "").setEmptyValue("");
        List<Object> params = new ArrayList<>();
        if (!Utils.isBlank(keyword)) {
            conditions.add("(name like ? or family like ? or national_code like ? or phone like ? or cell like ? " +
                    "or email like ? or fax like ? or url like ? or address like ? )");
            params.add("%" + keyword + "%");
            params.add("%" + keyword + "%");
            params.add("%" + keyword + "%");
            params.add("%" + keyword + "%");
            params.add("%" + keyword + "%");
            params.add("%" + keyword + "%");
            params.add("%" + keyword + "%");
            params.add("%" + keyword + "%");
            params.add("%" + keyword + "%");
        }

        if (!Utils.isBlank(luContactType)) {
            conditions.add("lu_contact_type = ?");
            params.add(luContactType);
        }

        String sql = "select oid, lu_contact_type, l1.value as lu_contact_type_title, lu_title, l2.value as lu_title_title, " +
                "name, family, father_name, filename " +
                "from contact " +
                "left outer join (select `key`, value from lookup where group_key = '" + LookupGroupKey.contact_type +
                "' ) l1 on l1.`key` = lu_contact_type " +
                "left outer join (select `key`, value from lookup where group_key in ('" + LookupGroupKey.company_contact_title +
                "', '" + LookupGroupKey.person_contact_title + "') ) l2 on l2.`key` = lu_title " +
                conditions +
                " order by oid desc";

        return new PagingHelper<Contact>().fetch(
                jdbcTemplate,
                sql,
                params.toArray(),
                pageNo,
                pageSize,
                new BeanPropertyRowMapper<>(Contact.class)
        );
    }

    @Override
    public Optional<Contact> findById(long oid) {
        Optional<Contact> pm = Optional.empty();
        try {
            pm = Optional.ofNullable(
                    jdbcTemplate.queryForObject(
                            "select contact.*, l1.value as lu_contact_type_title, l2.value as lu_title_title, " +
                                    "concat(l3.name, ' ', l3.family) as fk_contact_oid_title from contact " +
                                    "left outer join (select `key`, value from lookup where group_key = '" +
                                    LookupGroupKey.contact_type + "' ) l1 on l1.`key` = lu_contact_type " +
                                    "left outer join (select `key`, value from lookup where group_key in ('" +
                                    LookupGroupKey.company_contact_title + "', '" + LookupGroupKey.person_contact_title +
                                    "') ) l2 on l2.`key` = lu_title " +
                                    "left outer join contact l3 on l3.oid = contact.fk_contact_oid " +
                                    "where contact.oid = ?",
                            new Object[]{oid},
                            new BeanPropertyRowMapper<>(Contact.class)
                    )
            );
        } catch (EmptyResultDataAccessException ignore) {}
        return pm;

    }

    private void fillPreparedStatement(PreparedStatement ps, Contact pm) throws SQLException {
        ps.setString(1, pm.getLuContactType());
        ps.setString(2, pm.getLuTitle());
        ps.setString(3, pm.getLuGender());
        ps.setString(4, pm.getName());
        ps.setString(5, pm.getFamily());
        ps.setString(6, pm.getFatherName());
        ps.setObject(7, pm.getBirthDate());
        ps.setString(8, pm.getNationalCode());
        ps.setString(9, pm.getPhone());
        ps.setString(10, pm.getCell());
        ps.setString(11, pm.getEmail());
        ps.setString(12, pm.getFax());
        ps.setString(13, pm.getUrl());
        ps.setString(14, pm.getAddress());
        ps.setLong(15, Optional.ofNullable(pm.getFkContactOid()).orElse(0L));
        ps.setString(16, pm.getDescription());
    }

    @Override
    public Contact add(Contact pm) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "insert into contact " +
                            "(lu_contact_type, lu_title, lu_gender, name, family, father_name, birth_date, " +
                            "national_code, phone, cell, email, fax, url, address, fk_contact_oid, description) " +
                            "values " +
                            "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            fillPreparedStatement(ps, pm);
            return ps;
        }, keyHolder);
        pm.setOid(Objects.requireNonNull(keyHolder.getKey()).longValue());
        return pm;
    }

    @Override
    public int editImage(long oid, String filename) {
        return jdbcTemplate.update("update contact set filename = ? where oid = ?",
                filename, oid);
    }

    @Override
    public int edit(Contact pm) {
        return jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "update contact set " +
                            "lu_contact_type = ?, lu_title = ?, lu_gender = ?, name = ?, family = ?, father_name = ?, " +
                            "birth_date = ?, national_code = ?, phone = ?, cell = ?, email = ?, fax = ?, url = ?, " +
                            "address = ?, fk_contact_oid = ?, description = ? " +
                            "where oid = ?"
            );
            fillPreparedStatement(ps, pm);
            ps.setLong(17, pm.getOid());
            return ps;
        });
    }

    @Override
    public int remove(long oid) {
        return jdbcTemplate.update("delete from contact where oid = ?", oid);
    }
}
