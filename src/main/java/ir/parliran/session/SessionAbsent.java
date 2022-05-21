package ir.parliran.session;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SessionAbsent {
    @NotNull(message = "{fk_session_oid}") @Min(1)
    private Long fkSessionOid;

    @Range(min = 1, message = "{fk_board_observer_oid}")
    @NotNull(message = "{fk_board_observer_oid}")
    private Long fkObserverOid;
    private String fkObserverOidTitle;

    private String luAbsPresStatus;
    private String luAbsPresStatusTitle;

}
