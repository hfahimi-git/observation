package ir.parliran.board;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardPeriod {
    private long oid;

    @NotNull @Min(1)
    private Long fkBoardOid;
    private String fkBoardOidTitle;

    @NotNull @Min(1)
    private Integer luPeriodNo;
    private String luPeriodNoTitle;
}
