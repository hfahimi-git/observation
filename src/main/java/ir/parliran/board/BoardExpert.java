package ir.parliran.board;
import ir.parliran.global.DatePair;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardExpert {
    private int oid;

    @NotNull @Min(1)
    private Integer fkBoardPeriodOid;

    @NotNull @Min(1)
    private Integer fkUserOid;

    private String fkUserOidTitle;

    private DatePair startDate;

    private DatePair endDate;
}
