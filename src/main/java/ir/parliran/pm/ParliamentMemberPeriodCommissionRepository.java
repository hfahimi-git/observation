package ir.parliran.pm;

import java.util.List;
import java.util.Optional;

interface ParliamentMemberPeriodCommissionRepository {
    List<ParliamentMemberPeriodCommission> findAll(long fkParliamentMemberOid, int luPeriodNo);
    Optional<ParliamentMemberPeriodCommission> findById(long oid);
    ParliamentMemberPeriodCommission add(ParliamentMemberPeriodCommission parliamentMemberPeriodCommission);
    int similarCount(ParliamentMemberPeriodCommission parliamentMemberPeriodCommission);
    int edit(ParliamentMemberPeriodCommission parliamentMemberPeriodCommission);
    int remove(long oid);
    int removeAll(long fkParliamentMemberOid, int luPeriodNo);
}
