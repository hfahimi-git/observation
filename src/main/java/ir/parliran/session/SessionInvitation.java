package ir.parliran.session;

import ir.parliran.global.DatePair;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SessionInvitation {
    @NotNull(message = "{fk_session_oid}") @Min(1)
    private Long fkSessionOid;
    private String fkSessionOidTitle;

    private LocalTime sessionStartTime;

    private DatePair letterReceivedDate;

    private String letterNo;
}
