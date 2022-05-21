package ir.parliran.global;

import java.util.ArrayList;
import java.util.List;

final public class Page<E> {
    private final int pageNo;
    private final int pageCount;
    private final long rowsCount;
    private List<E> records = new ArrayList<>();

    public Page(int pageNo, int pageCount, long rowsCount) {
        this.pageNo = pageNo;
        this.pageCount = pageCount;
        this.rowsCount = rowsCount;
    }

    public Page(int pageNo, int pageCount, long rowsCount, List<E> records) {
        this.pageNo = pageNo;
        this.pageCount = pageCount;
        this.rowsCount = rowsCount;
        this.records = records;
    }

    public void setRecords(List<E> records) {
        this.records = records;
    }

    public int getPageNo() {
        return pageNo;
    }

    public int getPageCount() {
        return pageCount;
    }

    public List<E> getRecords() {
        return records;
    }

    public long getRowsCount() {
        return rowsCount;
    }
}
