package ir.parliran.pm;

import java.util.List;
import java.util.Optional;

interface ParliamentMemberPeriodRepository {
    List<ParliamentMemberPeriod> findAll(long fkParliamentMemberOid);
    Optional<ParliamentMemberPeriod> findById(long oid);
    ParliamentMemberPeriod add(ParliamentMemberPeriod parliamentMemberPeriod);
    int similarCount(long fkParliamentMemberOid, int luPeriodNo);
    int edit(ParliamentMemberPeriod parliamentMemberPeriod);
    int remove(long oid);
}
