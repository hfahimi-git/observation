package ir.parliran.board;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardCommission {
    private long oid;

    @NotNull
    @Min(1)
    private Long fkBoardPeriodOid;

    @NotNull @Size(min = 3, message = "{lu_commission}")
    private String luCommission;
    private String luCommissionTitle;

}
