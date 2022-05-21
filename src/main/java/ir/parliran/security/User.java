package ir.parliran.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private long oid;

    private Integer fkContactOid;
    private String fkContactOidTitle;

    @NotNull
    @NotBlank
//    @Pattern(regexp = "^[0-9a-z]{3,32}$", message = "{username}")
    @Size(min = 3, max = 32, message = "{username}")
    private String username;

    private String password;

    @Size(min = 6, max = 8, message = "{lu_status}")
    private String luStatus;
    private String luStatusTitle;

    private List<Long> roles;
    private List<Role> rolesComplex;

}
