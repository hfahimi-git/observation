package ir.parliran.global;

import java.time.LocalDate;
import java.time.ZoneId;


public class DatePair {
    private String jalaliDate;
    private LocalDate gregorianDate;

    public DatePair(String jalaliDate) {
        this.jalaliDate = jalaliDate;
        this.gregorianDate = Utils.j2g(jalaliDate);
    }

    public DatePair(LocalDate date) {
        this.jalaliDate = Utils.g2j(date);
        this.gregorianDate = date;
    }

    public DatePair(java.util.Date date) {
        if(date == null) {
            this.jalaliDate = null;
            this.gregorianDate = null;
            return;
        }
        this.gregorianDate = LocalDate.ofInstant(date.toInstant(), ZoneId.systemDefault());
        this.jalaliDate = Utils.g2j(this.gregorianDate);
    }

    public DatePair(java.sql.Date date) {
        if(date == null) {
            this.jalaliDate = null;
            this.gregorianDate = null;
            return;
        }
        this.gregorianDate = date.toLocalDate();
        this.jalaliDate = Utils.g2j(this.gregorianDate);
    }

    public String getJalaliDate() {
        return jalaliDate;
    }

    public LocalDate getGregorianDate() {
        return gregorianDate;
    }

    @Override
    public String toString() {
        return jalaliDate;
    }
}
