package ir.parliran.session;

import java.util.Optional;

public interface SessionInvitationRepository {
    Optional<SessionInvitation> findBySessionOid(long fkSessionOid);
    SessionInvitation add(SessionInvitation sessionInvitation);
    int edit(SessionInvitation sessionInvitation);
    int remove(long oid);
    int similarCount(SessionInvitation sessionInvitation);

    boolean exists(long fkSessionOid);
}
