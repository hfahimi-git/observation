package ir.parliran.pm;

import ir.parliran.global.Page;

import java.util.List;
import java.util.Optional;

/**
 * ©hFahimi.com @ 2019/12/16 14:14
 */
interface ParliamentMemberRepository{
    Page<ParliamentMember> findAll(String keyword, int pageNo, int pageSize);
    Page<ParliamentMember> findAll(SearchCredential searchCredential);
    Optional<ParliamentMember> findById(long oid);
    ParliamentMember add(ParliamentMember parliamentMember);
    int edit(ParliamentMember parliamentMember);
    int editImage(long oid, String filename);
    int remove(long oid);

    boolean exists(long oid);
}