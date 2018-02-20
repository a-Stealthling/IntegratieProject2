package be.kdg.kandoe.controller.rest;

import be.kdg.kandoe.domain.theme.Theme;
import be.kdg.kandoe.dto.ThemeDto;
import be.kdg.kandoe.service.declaration.ThemeService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
public class ThemeRestController {
    private final Logger logger = Logger.getLogger(ThemeRestController.class);
    private final ThemeService themeService;

    @Autowired
    public ThemeRestController(ThemeService themeService){
        this.themeService=themeService;
    }
    @GetMapping("api/themes")
    public List<Theme> getAllThemes(){
        return themeService.getAllThemes();
    }

    @GetMapping("api/theme/{themeId}")
    public ResponseEntity<Theme> getThemeById(@PathVariable Long themeId){
        Theme theme = themeService.getThemeById(themeId);
        if(theme !=null){
            return ResponseEntity.ok().body(theme);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("api/themes")
    public Theme CreateTheme(@Valid @RequestBody ThemeDto theme){
        return themeService.addTheme(new Theme(theme));
    }

    @PostMapping("api/theme/{themeId}")
    public ResponseEntity<Theme> updateTheme(@PathVariable Long id, @Valid @RequestBody Theme theme){
        Theme foundTheme = themeService.getThemeById(id);
        if(foundTheme == null){
            return ResponseEntity.notFound().build();
        }
        foundTheme.setDescription(theme.getDescription());
        foundTheme.setName(theme.getName());
        Theme updatedTheme=themeService.editTheme(foundTheme);
        return ResponseEntity.ok().body(updatedTheme);
    }
}
