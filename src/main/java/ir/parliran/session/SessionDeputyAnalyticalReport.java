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
public class SessionDeputyAnalyticalReport {
    private long oid;

    @NotNull(message = "{fk_session_oid}") @Min(1)
    private Long fkSessionOid;
    private String fkSessionOidTitle;

    @Range(min = 1, message = "{fk_commission_report_oid}")
    @NotNull(message = "{fk_commission_report_oid}")
    private Long fkCommissionReportOid;
    private String fkCommissionReportOidTitle;

    private String beneficiaryLetterNo;
    private DatePair beneficiaryLetterDate;

    private String evaluationLetterNo;
    private DatePair evaluationLetterDate;

    private String analytical;
    private String luGradeStatus;
    private String luGradeStatusTitle;

    private String filename;
    private MultipartFile file;

}
