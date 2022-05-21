package ir.parliran.contact;

import ir.parliran.global.Page;
import ir.parliran.pm.ParliamentMember;

import java.util.Optional;

/**
 * ©hFahimi.com @ 2019/12/16 14:14
 */
interface ContactRepository {
    Page<Contact> findAll(String keyword, String luContactType, int pageNo, int pageSize);
    Optional<Contact> findById(long oid);
    Contact add(Contact contact);
    int edit(Contact contact);
    int editImage(long oid, String filename);
    int remove(long oid);
}