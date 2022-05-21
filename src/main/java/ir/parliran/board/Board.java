package ir.parliran.board;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Board {
    private long oid;

    @NotBlank(message = "{title}")
    @Size(min=3, max = 256, message = "{title}")
    private String title;

    @NotBlank(message = "{lu_board_type}")
    @Size(max = 64, message = "{lu_board_type}")
    private String luBoardType;
    private String luBoardTypeTitle;

    private Long fkChairmanOid;
    private String fkChairmanOidTitle;

    private Long fkSecretaryOid;
    private String fkSecretaryOidTitle;

    private Integer observationCount;

    private String luSessionPeriod;
    private String luSessionPeriodTitle;

    private String relatedLaw;

    @Size(max = 256, message = "{phone}")
    private String phone;

    @Size(max = 256, message = "{fax}")
    private String fax;

    @Email(message = "{email}")
    private String email;

}
