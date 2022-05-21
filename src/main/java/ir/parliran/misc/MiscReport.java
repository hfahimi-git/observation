package ir.parliran.misc;

import ir.parliran.global.DatePair;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MiscReport {
    private long oid;

    @Range(min = 1, message = "{fk_board_period_oid}")
    @NotNull(message = "{fk_board_period_oid}")
    private Long fkBoardPeriodOid;
    private String fkBoardPeriodOidTitle;

    @NotBlank(message = "{title}")
    @Size(min = 3, max = 256, message = "{title}")
    private String title;

    @NotEmpty(message = "{letter_no}")
    private String letterNo;

    @NotNull(message = "{letter_date}")
    private DatePair letterDate;
    private String description;

    @Pattern(regexp = "(^$|.{1,128})", message = "{filename}")
    private String filename;
    private MultipartFile file;

    @NotBlank(message = "{lu_type}")
    @Size(min = 4, max = 32, message = "{lu_type}")
    private String luType;
    private String luTypeTitle;

}
