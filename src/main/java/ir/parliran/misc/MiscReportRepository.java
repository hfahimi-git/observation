package ir.parliran.misc;

import ir.parliran.global.Page;

import java.util.Optional;

public interface MiscReportRepository {
    Page<MiscReport> findAll(SearchCredential searchCredential);

    Optional<MiscReport> findById(long oid);

    MiscReport add(MiscReport miscReport);

    int edit(MiscReport miscReport);

    boolean exists(long oid);

    int similarCount(MiscReport item);

    int remove(long oid);

    int removeFile(long oid);
}
