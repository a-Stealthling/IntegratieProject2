package be.kdg.kandoe.controller.rest;

import be.kdg.kandoe.domain.card.Card;
import be.kdg.kandoe.service.declaration.FileService;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FileRestController {

    private final Logger logger = Logger.getLogger(ThemeRestController.class);

    private FileService fileService;

    public FileRestController(FileService fileService) {
        this.fileService = fileService;
    }

    @RequestMapping(value="api/public/image",method = RequestMethod.POST)
    public ResponseEntity<String> uploadCardImage(@RequestParam("file")MultipartFile image){
        if(!image.isEmpty()){
            try{
                String fileName = fileService.upload(image, Card.class);
                return new ResponseEntity<>(fileName, HttpStatus.OK);
            }
            catch (Exception e){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }
}
