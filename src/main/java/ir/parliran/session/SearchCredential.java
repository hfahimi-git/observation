package ir.parliran.session;

import ir.parliran.global.DatePair;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.time.DatePrinter;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
class SearchCredential {
    private String keyword;
    private String luPeriodNo;
    private Integer no;
    private DatePair fromDate;
    private DatePair toDate;
    private int pageNo;
    private int pageSize;
}
