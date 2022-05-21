package ir.parliran.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Login {
    @NotNull
    @NotBlank
    @Pattern(regexp = "^[0-9]{10}$", message = "{username}")
    private String username;

    private String password;
}
