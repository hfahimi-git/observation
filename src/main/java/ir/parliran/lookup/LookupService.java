package ir.parliran.lookup;

import ir.parliran.global.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

/**
 * ©hFahimi.com @ 2019/12/18 10:24
 */

@Service("lookupService")
public class LookupService {
    private final LookupRepository repository;

    @Autowired
    public LookupService(LookupRepository repository) {
        this.repository = repository;
    }

    public List<Lookup> findByGroupKey(String groupKey) {
        return repository.findByGroupKey(groupKey);
    }

    public Optional<Lookup> findByGroupKeyAndKey(String groupKey, String key) {
        return repository.findByGroupKeyAndKey(groupKey, key);
    }

    public List<Lookup> findByGroupKeyOrderBy(String groupKey, String orderBy) {
        return repository.findByGroupKeyOrderBy(groupKey, orderBy);
    }

    public List<Lookup> findByGroupKey2Level(String groupKey) {
        List<Lookup> lookups = repository.findByGroupKey(groupKey);
        for(Lookup lookup: lookups) {
            lookup.setChildren(repository.findByGroupKeyOrderBy(lookup.getKey(), "value"));
        }
        return lookups;
    }

    Optional<Lookup> findById(long oid) {
        return repository.findById(oid);
    }

    Lookup add(Lookup lookup) {
        return repository.add(lookup);
    }

    int edit(Lookup lookup) {
        return repository.edit(lookup);
    }

    int remove(long oid) {
        return repository.remove(oid);
    }

    public List<String> findAllGroupKeys() {
        return repository.findAllGroupKeys();
    }

    public Page<Lookup> findAll(String keyword, String groupKey, int pageNo, int pageSize) {
        return repository.findAll(keyword, groupKey, pageNo, pageSize);

    }
}
