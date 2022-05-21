package ir.parliran.global.upload;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.stream.Stream;

/**
 * ©hFahimi.com @ 2019/12/21 14:38
 */
public interface StorageService {
    void init();

    String store(MultipartFile file);

    Stream<Path> loadAll();

    Path load(String filename);

    Resource loadAsResource(String filename) throws FileNotFoundException;

    void deleteAll();
}
