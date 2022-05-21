package ir.parliran.lookup;

import ir.parliran.global.ValidInteger;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * ©hFahimi.com @ 2019/12/18 09:44
 */

@Data @Builder @AllArgsConstructor @NoArgsConstructor

public class Lookup {

    private long oid;

    @NotBlank @Size(min=2,  max = 255, message = "{group_key.error}")
    private String groupKey;

    private String groupKeyTitle;

    @Size(max = 255, message = "{key.error}")
    private String key;

    @Size(max = 1024, message = "{value.error}")
    private String value;

    @Size(max = 255, message = "{extra.error}")
    private String extra;

    private Integer orderby;

    private List<Lookup> children;
}
