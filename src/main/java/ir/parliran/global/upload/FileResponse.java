package ir.parliran.global.upload;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * ©hFahimi.com @ 2019/12/21 14:37
 */
@Data @AllArgsConstructor
public class FileResponse {
    private final String name;
    private final String uri;
    private final String type;
    private final long size;
}
