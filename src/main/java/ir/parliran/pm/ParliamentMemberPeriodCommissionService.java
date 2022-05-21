package ir.parliran.pm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
class ParliamentMemberPeriodCommissionService {
    private final ParliamentMemberPeriodCommissionRepository repository;

    @Autowired
    ParliamentMemberPeriodCommissionService(ParliamentMemberPeriodCommissionRepository repository) {
        this.repository = repository;
    }

    List<ParliamentMemberPeriodCommission> findAll(long pmOid, int luPeriodNo) {
        return repository.findAll(pmOid, luPeriodNo);
    }

    Optional<ParliamentMemberPeriodCommission> findById(long oid) {
        return repository.findById(oid);
    }

    ParliamentMemberPeriodCommission add(ParliamentMemberPeriodCommission pmpc) throws RuntimeException {
        if(similarCount(pmpc) > 0) {
            throw new RuntimeException("luCommission");
        }
        return repository.add(pmpc);
    }

    private int similarCount (ParliamentMemberPeriodCommission pmpc) {
        return repository.similarCount(pmpc);
    }

    int edit(ParliamentMemberPeriodCommission pmpc) {
        Optional<ParliamentMemberPeriodCommission> pmpcOld = findById(pmpc.getOid());
        if(pmpcOld.isEmpty())
            return 0;
        if(
                pmpcOld.get().getFkParliamentMemberOid() != pmpc.getFkParliamentMemberOid() &&
                !pmpcOld.get().getLuCommission().equalsIgnoreCase(pmpc.getLuCommission()) &&
                pmpcOld.get().getLuPeriodNo() != pmpc.getLuPeriodNo() &&
                pmpcOld.get().getLuYearNo() != pmpc.getLuYearNo() &&
                similarCount(pmpc) > 0
        ) {
            throw new RuntimeException("luCommission");
        }
        return repository.edit(pmpc);
    }

    int remove(long oid) {
        return repository.remove(oid);
    }


}
