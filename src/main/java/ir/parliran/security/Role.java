package ir.parliran.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Role {
    private long oid;

    @NotNull @NotBlank
    @Size(min = 1, max=255, message = "{title}")
    private String title;

}
