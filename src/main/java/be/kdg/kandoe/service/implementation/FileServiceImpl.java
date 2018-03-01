package be.kdg.kandoe.service.implementation;

import be.kdg.kandoe.repository.declaration.FileRepository;
import be.kdg.kandoe.service.declaration.FileService;
import be.kdg.kandoe.service.exception.FileServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

@Service
public class FileServiceImpl implements FileService {
    private static final String ORIGIN_PATH = "";
    private FileRepository fileRepository;

    @Autowired
    public FileServiceImpl(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    @Override
    public String upload(MultipartFile image, Class type) throws IOException, FileServiceException {
        if(image.isEmpty()){
            throw new FileServiceException();
        }
        byte[] bytes = image.getBytes();
        String imageId = Arrays.hashCode(bytes)+"."+image.getOriginalFilename().split("\\.")[1];
        fileRepository.addnewFile(bytes,type.getSimpleName()+File.pathSeparator+imageId);
        return imageId;
    }
}
