package be.kdg.kandoe.service.declaration;

import be.kdg.kandoe.domain.card.Card;
import be.kdg.kandoe.service.exception.FileServiceException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public interface FileService {
    String upload(MultipartFile image, Class type) throws IOException, FileServiceException;
}
