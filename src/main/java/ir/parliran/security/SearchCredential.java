package ir.parliran.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
class SearchCredential {
    private String keyword;
    private String luStatus;
    private int pageNo;
    private int pageSize;
}
