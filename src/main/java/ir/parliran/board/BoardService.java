package ir.parliran.board;

import ir.parliran.global.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * ©hFahimi.com @ 2019/12/16 14:16
 */

@Service()
public class BoardService {

    private final BoardRepository repository;

    @Autowired
    BoardService(BoardRepository repository) {
        this.repository = repository;
    }

    Page<Board> findAll(SearchCredential searchCredential) {
        return repository.findAll(searchCredential);
    }

    public List<Board> findAll() {
        return repository.findAll();
    }

    Optional<Board> findById(long oid) {
        return repository.findById(oid);
    }

    Board add(Board board) {
        return repository.add(board);
    }

    int edit(Board board) {
        return repository.edit(board);
    }

    int remove(long oid) {
        return repository.remove(oid);
    }
}
