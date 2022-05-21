package ir.parliran.board;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
class BoardPeriodSearchCredential {
    private String keyword;
    private Integer luPeriodNo;
    private int pageNo;
    private int pageSize;
}
