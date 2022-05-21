package ir.parliran.pm;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ParliamentMemberPeriodCommission {
    private long oid;

    @NotNull(message = "{fk_parliament_member_oid}")
    @Range(min = 1, message = "{fk_parliament_member_oid}")
    private Long fkParliamentMemberOid;

    @NotNull(message = "{lu_period_no}")
    @Range(min = 1, message = "{lu_period_no}")
    private Integer luPeriodNo;

    private String luPeriodNoTitle;

    @NotNull(message = "{lu_year_no}")
    @Range(min = 1, message = "{lu_year_no}")
    private Integer luYearNo;

    private String luYearNoTitle;

    @NotBlank(message = "{lu_commission}")
    @Size(min=1,  max = 64, message = "{lu_commission.error}")
    private String luCommission;

    private String luCommissionTitle;

    @NotBlank(message = "{lu_commission_role}")
    @Size(min=1,  max = 64, message = "{lu_commission_role.error}")
    private String luCommissionRole;

    private String luCommissionRoleTitle;

}
