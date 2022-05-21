package ir.parliran.session;

import ir.parliran.global.DatePair;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SessionDeputyJustificationReport {
    @NotNull(message = "{fk_session_oid}") @Min(1)
    private Long fkSessionOid;
    private String fkSessionOidTitle;

    private String document;
    private String expertOpinion;
    private String description;
    private String filename;
    private MultipartFile file;

    private String sendLetterNo;
    private DatePair sendLetterDate;
}
