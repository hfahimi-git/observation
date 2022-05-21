package ir.parliran.session;

import ir.parliran.global.DatePair;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SessionBoardReport {
    @NotNull(message = "{fk_session_oid}") @Min(1)
    private Long fkSessionOid;
    private String fkSessionOidTitle;

    private String luHoldStatus;
    private String luHoldStatusTitle;

    private String approval;
    private String filename;
    private MultipartFile file;

    private String letterNo;
    private DatePair letterDate;

    private List<Long> presents;
}
