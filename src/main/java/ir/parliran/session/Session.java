package ir.parliran.session;

import ir.parliran.global.DatePair;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Session {
    private long oid;

    @NotNull @Min(1)
    private Long fkBoardPeriodOid;
    private String fkBoardPeriodOidTitle;

    @NotNull
    private Integer no;

    @NotNull
    private DatePair date;
}
