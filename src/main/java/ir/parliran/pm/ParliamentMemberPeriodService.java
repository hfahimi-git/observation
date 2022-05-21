package ir.parliran.pm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
class ParliamentMemberPeriodService {
    private final ParliamentMemberPeriodRepository repository;

    @Autowired
    ParliamentMemberPeriodService(ParliamentMemberPeriodRepository repository) {
        this.repository = repository;
    }

    List<ParliamentMemberPeriod> findAll(long pmOid) {
        return repository.findAll(pmOid);
    }

    Optional<ParliamentMemberPeriod> findById(long oid) {
        return repository.findById(oid);
    }

    ParliamentMemberPeriod add(ParliamentMemberPeriod parliamentMemberPeriod) throws RuntimeException {
        if(similarCount(parliamentMemberPeriod.getFkParliamentMemberOid(), parliamentMemberPeriod.getLuPeriodNo()) > 0) {
            throw new RuntimeException("luPeriodNo");
        }
        return repository.add(parliamentMemberPeriod);
    }

    private int similarCount (long fkParliamentMemberOid, int luPeriodNo) {
        return repository.similarCount(fkParliamentMemberOid, luPeriodNo);
    }

    int edit(ParliamentMemberPeriod parliamentMemberPeriod) {
        Optional<ParliamentMemberPeriod> pmp = findById(parliamentMemberPeriod.getOid());
        if(pmp.isEmpty())
            return 0;
        if(
                !pmp.get().getLuPeriodNo().equals(parliamentMemberPeriod.getLuPeriodNo()) &&
                similarCount(parliamentMemberPeriod.getFkParliamentMemberOid(), parliamentMemberPeriod.getLuPeriodNo()) > 0
        ) {
            throw new RuntimeException("luPeriodNo");
        }
        return repository.edit(parliamentMemberPeriod);
    }

    int remove(long oid) {
        return repository.remove(oid);
    }


}
