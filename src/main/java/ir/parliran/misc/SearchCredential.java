package ir.parliran.misc;

import ir.parliran.global.DatePair;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
class SearchCredential {
    private String keyword;
    private String luType;
    private Long fkBoardOid;
    private Integer luPeriodNo;
    private DatePair fromDate;
    private DatePair toDate;

    private int pageNo;
    private int pageSize;
}
