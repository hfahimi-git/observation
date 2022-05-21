package ir.parliran.pm;

import lombok.*;
import org.hibernate.validator.constraints.Range;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;

/**
 * ©hFahimi.com @ 2019/12/16 10:35
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class ParliamentMember {
    private long oid;

    @NotBlank(message = "{lu_title}")
    @Size(min = 2, max = 3, message = "{lu_title}")
    private String luTitle;

    private String luTitleTitle;

    @NotBlank(message = "{name.error}")
    @Size(min = 2, max = 256, message = "{name.error}")
    private String name;

    @NotBlank(message = "{family.error}")
    @Size(min = 2, max = 256, message = "{family.error}")
    private String family;

    @NotBlank(message = "{lu_gender}")
    @Size(min = 4, max = 6, message = "{lu_gender}")
    private String luGender;

    @Size(max = 256, message = "{father_name.error}")
    private String fatherName;

    @Range(min = 1300, max = 9999, message = "{birth_year}")
    private Integer birthYear;

    @NotBlank(message = "{lu_birth_city}")
    @Size(max = 128, message = "{lu_birth_city}")
    private String luBirthCity;

    @Pattern(regexp = "(^$|.{1,128})", message = "{filename}")
    private String filename;

    private String description;

    private MultipartFile file;

}
