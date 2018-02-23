package be.kdg.kandoe.controller.rest;

import be.kdg.kandoe.domain.theme.Theme;
import be.kdg.kandoe.dto.ThemeDto;
import be.kdg.kandoe.repository.implementation.ThemeRepositoryImpl;
import be.kdg.kandoe.service.declaration.ThemeService;
import be.kdg.kandoe.service.implementation.ThemeServiceImpl;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.method.P;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class ThemeRestController {
    private final Logger logger = Logger.getLogger(ThemeRestController.class);

    private ThemeService themeService;

    public ThemeRestController(ThemeService themeService){
        this.themeService=themeService;
    }
    @GetMapping("api/themes")
    public List<Theme> getAllThemes(){
        return themeService.getAllThemes();
    }

    @RequestMapping(value= "api/theme/{themeId}", method = RequestMethod.GET)
    public ResponseEntity<Theme> getThemeById(@PathVariable Long themeId){
        System.out.println("CALLED RECEIVED: getThemeById: "+themeId);
        Theme theme = themeService.getThemeById(themeId);
        if(theme !=null){
            return ResponseEntity.ok().body(theme);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(value= "api/theme/", method = RequestMethod.GET)
    public ResponseEntity<Theme> getThemeByName(@RequestParam(name = "name") String name){
        System.out.println("CALLED RECEIVED: getThemeByName: "+name);
        Theme theme = themeService.getThemeByName(name);
        if(theme !=null){
            return ResponseEntity.ok().body(theme);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(value = "api/themes", method = RequestMethod.POST)
    public ResponseEntity<Theme> CreateTheme(@Valid @RequestBody ThemeDto theme){
        System.out.println("CALL RECEIVED: CreateTheme");
        logger.log(Priority.INFO,"API CALL: CreateTheme");
        Theme createdTheme = themeService.addTheme(new Theme(theme));
        if(createdTheme==null){
            logger.log(Priority.ERROR,"ThemeService.addTheme returns NULL..");
            return ResponseEntity.badRequest().build();
        }else{
            logger.log(Priority.INFO,"Successfully created new Theme");
            return ResponseEntity.ok().body(createdTheme);

        }

    }

    @RequestMapping(value = "api/theme/{themeId}", method = RequestMethod.POST)
    public ResponseEntity<Theme> updateTheme(@PathVariable Long id, @Valid @RequestBody Theme theme){
        logger.log(Priority.INFO,"API CALL: updateTheme: "+id);
        Theme foundTheme = themeService.getThemeById(id);
        if(foundTheme == null){
            logger.log(Priority.ERROR,"No theme found for Id: "+id);
            return ResponseEntity.notFound().build();
        }
        foundTheme.setDescription(theme.getDescription());
        foundTheme.setName(theme.getName());
        Theme updatedTheme=themeService.editTheme(foundTheme);
        logger.log(Priority.INFO,"Updated Theme for id: "+id);
        return ResponseEntity.ok().body(updatedTheme);
    }
    @RequestMapping(value = "api/theme/{themeId}",method = RequestMethod.DELETE)
    public ResponseEntity<Theme> deleteThemeByThemeId(@PathVariable Long id){
        Theme foundTheme =themeService.getThemeById(id);
        if(foundTheme==null){
            return ResponseEntity.notFound().build();
        }
        themeService.removeThemeById(foundTheme.getThemeId());
        return ResponseEntity.ok().body(foundTheme);
    }
    @RequestMapping(value = "api/theme", method = RequestMethod.DELETE)
    public ResponseEntity<Theme> deleteThemeByName(@RequestParam(value = "name") String name){
        Theme foundTheme = themeService.getThemeByName(name);
        if(foundTheme==null){
            return ResponseEntity.notFound().build();
        }
        themeService.removeThemeById(foundTheme.getThemeId());
        return ResponseEntity.ok().body(foundTheme);
    }
}
