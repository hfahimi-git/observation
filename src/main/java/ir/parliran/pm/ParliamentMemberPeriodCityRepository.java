package ir.parliran.pm;

import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

interface ParliamentMemberPeriodCityRepository {
    List<Map<String, String>> findAllComplex(long fkPeriodOid);

    List<String> findAll(long fkPeriodOid);

    @Transactional
    int[] add(long fkPeriodOid, @NotNull List<String> cities);

    int remove(long fkPeriodOid);

    @Transactional
    void edit(long fkPeriodOid, @NotNull List<String> cities);
}
