package ir.parliran.pm;

import ir.parliran.global.Page;
import ir.parliran.global.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * ©hFahimi.com @ 2019/12/16 14:16
 */

@Service("pmService")
public class ParliamentMemberService {

    private final ParliamentMemberRepository repository;

    @Autowired
    ParliamentMemberService(ParliamentMemberRepository repository) {
        this.repository = repository;
    }

    Page<ParliamentMember> findAll(String keyword, int pageNo, int pageSize) {
        return repository.findAll(keyword, pageNo, pageSize);
    }

    Page<ParliamentMember> findAll(SearchCredential searchCredential) {
        return repository.findAll(searchCredential);
    }

    Optional<ParliamentMember> findById(long oid) {
        return repository.findById(oid);
    }

    ParliamentMember add(ParliamentMember parliamentMember) {
        repository.add(parliamentMember);
        if(!Utils.isBlank(parliamentMember.getFilename()))
            editImage(parliamentMember.getOid(), parliamentMember.getFilename());
        return parliamentMember;
    }

    int editImage(long oid, String filename) {
        return repository.editImage(oid, filename);
    }

    int edit(ParliamentMember parliamentMember) {
        int result = repository.edit(parliamentMember);
        if(!Utils.isBlank(parliamentMember.getFilename()))
            editImage(parliamentMember.getOid(), parliamentMember.getFilename());
        return result;
    }

    int remove(long oid) {
        return repository.remove(oid);
    }

    public boolean exists(long oid) {
        return repository.exists(oid);
    }
}
