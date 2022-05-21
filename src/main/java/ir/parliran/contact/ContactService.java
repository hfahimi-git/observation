package ir.parliran.contact;

import ir.parliran.global.Page;
import ir.parliran.global.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * ©hFahimi.com @ 2019/12/16 14:16
 */

@Service()
class ContactService {

    private final ContactRepository repository;

    @Autowired
    ContactService(ContactRepository repository) {
        this.repository = repository;
    }

    Page<Contact> findAll(String keyword, String luContactType, int pageNo, int pageSize) {
        return repository.findAll(keyword, luContactType, pageNo, pageSize);
    }

    Optional<Contact> findById(long oid) {
        return repository.findById(oid);
    }

    Contact add(Contact contact) {
        repository.add(contact);
        if(!Utils.isBlank(contact.getFilename()))
            editImage(contact.getOid(), contact.getFilename());
        return contact;
    }

    int editImage(long oid, String filename) {
        return repository.editImage(oid, filename);
    }

    int edit(Contact contact) {
        int result = repository.edit(contact);
        if(!Utils.isBlank(contact.getFilename()))
            editImage(contact.getOid(), contact.getFilename());
        return result;
    }

    int remove(long oid) {
        return repository.remove(oid);
    }
}
