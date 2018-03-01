package be.kdg.kandoe.repository.implementation;

import be.kdg.kandoe.repository.declaration.FileRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Repository
public class FileRepositoryImpl implements FileRepository {

    private static final String ORIGIN_PATH = System.getProperty("user.home");

    @Override
    @Async
    public void addnewFile(byte[] bytes, String filePath) {
        Path path = Paths.get(ORIGIN_PATH+ File.pathSeparator+filePath);
        try {
            Files.write(path, bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
