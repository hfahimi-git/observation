package ir.parliran.session;

import ir.parliran.global.DatePair;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SessionCommissionReport {
    private long oid;

    @NotNull(message = "{fk_session_oid}") @Min(1)
    private Long fkSessionOid;
    private String fkSessionOidTitle;

/*
    @Range(min = 1, message = "{fk_board_observer_oid}")
    @NotNull(message = "{fk_board_observer_oid}")
*/
    private Long fkBoardObserverOid;
    private String fkBoardObserverOidTitle;

    @NotNull(message = "{fk_board_commission_oid}") @Min(1)
    private Long fkBoardCommissionOid;
    private String fkBoardCommissionOidTitle;

    private String toCmsnLetterNo;
    private DatePair toCmsnLetterDate;

    private String toDptLetterNo;
    private DatePair toDptLetterDate;

    private String luHoldStatus;
    private String luHoldStatusTitle;

    private String evaluation;
    private String luGradeStatus;
    private String luGradeStatusTitle;

    private String filename;
    private MultipartFile file;

    @Range(min = 1, message = "{fk_observer_report_oid}")
    @NotNull(message = "{fk_observer_report_oid}")
    private Long fkObserverReportOid;
    private String fkObserverReportOidTitle;

}
