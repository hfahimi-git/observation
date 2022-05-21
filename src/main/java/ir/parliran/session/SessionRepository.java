package ir.parliran.session;

import ir.parliran.global.Page;

import java.util.List;
import java.util.Optional;

public interface SessionRepository {
    List<Session> findAll(long fkBoardOid);
    Page<Session> findAll(SearchCredential searchCredential);
    Optional<Session> findById(long oid);
    Session add(Session session);
    int edit(Session session);
    int remove(long oid);

    int similarCount(Session session);

    boolean exists(long oid);
}
