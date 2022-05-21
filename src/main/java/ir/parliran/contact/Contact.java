package ir.parliran.contact;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;
import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class Contact {
    private long oid;

    @NotBlank(message = "{lu_contact_type}")
    @Size(max = 64, message = "{lu_contact_type}")
    private String luContactType;
    private String luContactTypeTitle;

    @NotBlank(message = "{lu_title}")
    @Size(max = 64, message = "{lu_title}")
    private String luTitle;
    private String luTitleTitle;

    private String luGender;

    @NotBlank(message = "{name.error}")
    @Size(min=2,  max = 256, message = "{name.error}")
    private String name;

    private String family;

    @Size(max = 256, message = "{father_name.error}")
    private String fatherName;

    @Past(message = "{birth_date}")
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private LocalDate birthDate;

    @Pattern(regexp = "(^$|^[0-9]{9}$)", message = "{national_code}")
    private String nationalCode;

    @Size(max = 256, message = "{phone}")
    private String phone;

    @Size(max = 256, message = "{cell}")
    private String cell;

    @Email(message = "{email}")
    private String email;

    @Size(max = 256, message = "{fax}")
    private String fax;

    @Size(max = 256, message = "{url}")
    private String url;

    @Size(max = 256, message = "{address}")
    private String address;

    private Long fkContactOid;
    private String fkContactOidTitle;

    @Pattern(regexp = "(^$|^.{1,128}$)", message = "{filename}")
    private String filename;

    @Size(max = 1024, message = "{description}")
    private String description;

    private MultipartFile file;


}
