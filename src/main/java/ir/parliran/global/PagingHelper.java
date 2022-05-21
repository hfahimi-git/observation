package ir.parliran.global;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;

public class PagingHelper<E> {

    private long getRowCount(final JdbcTemplate jt, final String sql, final Object[] args) {
        return jt.queryForObject("select count(1) as counter from (" + sql + ") t ", args, Integer.class);
    }

    public Page<E> fetch(final JdbcTemplate jt, final String sql, final Object[] args,
                         final int pageNo, final int pageSize, final RowMapper<E> rowMapper) {
        final long rowCount = getRowCount(jt, sql, args);
        int pageCount = (int) (rowCount / pageSize);
        if (rowCount > pageSize * pageCount || pageCount == 0) {
            pageCount++;
        }

        int finalPageNo = pageNo;
        if(pageNo > pageCount)
            finalPageNo = pageCount;

        final long startRow = (finalPageNo - 1) * pageSize;
        final Page<E> page = new Page<E>(finalPageNo, pageCount, rowCount);
        final String finalSql = sql + " limit " + startRow + ", " + pageSize;
        jt.query (
                finalSql,
                args,
                (ResultSetExtractor<Object>) rs -> {
                    final List<E> pageItems = page.getRecords();
                    int currentRow = 0;
                    while (rs.next()) {
                        pageItems.add(rowMapper.mapRow(rs, currentRow));
                        currentRow++;
                    }
                    return page;
                });
        return page;
    }

    public Page<E> fetch(final JdbcTemplate jt, final String sql, final Object[] args,
                         final RowMapper<E> rowMapper) {
        final long rowCount = getRowCount(jt, sql, args);
        final Page<E> page = new Page<E>(1, 1, rowCount);
        jt.query (
                sql,
                args,
                (ResultSetExtractor<Object>) rs -> {
                    final List<E> pageItems = page.getRecords();
                    int currentRow = 0;
                    while (rs.next()) {
                        pageItems.add(rowMapper.mapRow(rs, currentRow));
                        currentRow++;
                    }
                    return page;
                });
        return page;
    }
}
