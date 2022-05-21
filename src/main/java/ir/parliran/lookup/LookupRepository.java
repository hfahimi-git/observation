package ir.parliran.lookup;

import ir.parliran.global.Page;

import java.util.List;
import java.util.Optional;

/**
 * ©hFahimi.com @ 2019/12/18 10:20
 */
public interface LookupRepository{
    List<Lookup> findByGroupKey(String groupKey);
    List<Lookup> findByGroupKeyOrderBy(String groupKey, String orderBy);
    Optional<Lookup> findById(long oid);
    Lookup add(Lookup lookup);
    int edit(Lookup lookup);
    int remove(long oid);

    List<String> findAllGroupKeys();

    Page<Lookup> findAll(String keyword, String groupKey, int pageNo, int pageSize);

    Optional<Lookup> findByGroupKeyAndKey(String groupKey, String key);
}
