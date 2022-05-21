package ir.parliran.board;

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
    private String luBoardType;
    private String luSessionPeriod;
    private int pageNo;
    private int pageSize;
}
