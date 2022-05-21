package ir.parliran.session;

import ir.parliran.global.DatePair;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SessionAgenda {
    private long oid;

    @NotNull @Min(1)
    private Long fkSessionOid;

    @NotNull @Min(1)
    private Integer orderby;

    private String description;
}
