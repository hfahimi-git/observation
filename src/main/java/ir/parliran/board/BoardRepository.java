package ir.parliran.board;

import ir.parliran.contact.Contact;
import ir.parliran.global.Page;

import java.util.List;
import java.util.Optional;

/**
 * ©hFahimi.com @ 2019/12/16 14:14
 */
interface BoardRepository {
    Page<Board> findAll(SearchCredential searchCredential);
    List<Board> findAll();
    Optional<Board> findById(long oid);
    Board add(Board board);
    int edit(Board board);
    int remove(long oid);
}