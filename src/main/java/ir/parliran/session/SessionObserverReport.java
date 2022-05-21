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
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SessionObserverReport {
    private long oid;

    @NotNull(message = "{fk_session_oid}") @Min(1)
    private Long fkSessionOid;
    private String fkSessionOidTitle;

    @Range(min = 1, message = "{fk_board_observer_oid}")
    @NotNull(message = "{fk_board_observer_oid}")
    private Long fkBoardObserverOid;
    private String fkBoardObserverOidTitle;

    private String luAbsPresStatus;
    private String luAbsPresStatusTitle;

    private String luInvitationStatus;
    private String luInvitationStatusTitle;

    private String luCoordinationStatus;
    private String luCoordinationStatusTitle;

    private String luHoldStatus;
    private String luHoldStatusTitle;

    private String luMemberAbsPresStatus;
    private String luMemberAbsPresStatusTitle;

    private String topic;
    private String suggestion;
    private String contrary;
    private String description;
    private String filename;
    private MultipartFile file;

    private String letterNo;
    private DatePair letterDate;
}
