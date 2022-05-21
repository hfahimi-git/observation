package ir.parliran.board;

import ir.parliran.global.DatePair;
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
public class BoardObserver {
    private long oid;

    @NotNull @Min(1)
    private Long fkBoardPeriodOid;

    @NotNull @Min(1)
    private Long fkObserverOid;
    private String fkObserverOidTitle;

    @NotNull
    private String luMembershipType;
    private String luMembershipTypeTitle;

    private DatePair votingDate;

    private String statuteLetterNo;

    private DatePair statuteLetterDate;

    private DatePair communiqueIssuanceDate;

    private DatePair observationStartDate;

    private DatePair observationEndDate;

    private String luHowToElect;
    private String luHowToElectTitle;
}
