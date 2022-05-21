package ir.parliran.pm;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ParliamentMemberPeriod {

    private long oid;

    @NotNull(message = "{fk_parliament_member_oid}")
    @Range(min = 1, message = "{fk_parliament_member_oid}")
    private Long fkParliamentMemberOid;

    @NotBlank(message = "{lu_inter_period}")
    @Size(min = 2, message = "{lu_inter_period}")
    private String luInterPeriod;
    private String luInterPeriodTitle;

    @NotNull(message = "{lu_period_no}")
    @Range(min = 1, max = 64, message = "{lu_period_no}")
    private Integer luPeriodNo;

    private String luPeriodNoTitle;

    @NotBlank(message = "{lu_province}")
    @Size(min = 2, max = 128, message = "{lu_province}")
    private String luProvince;

    private String luProvinceTitle;

    private int voteCount;

    private float votePercent;

    private String description;

    @NotNull(message = "{cities_of_realm}")
        private List<String> cities;
    private List<Map<String, String>> citiesComplex;

    private List<ParliamentMemberPeriodCommission> commissions;

    private List<Map<String, String>> languagesComplex;
    private List< String> languages;

}
