package be.kdg.kandoe.controller.rest;

import be.kdg.kandoe.domain.GameSession;
import be.kdg.kandoe.domain.theme.SubTheme;
import be.kdg.kandoe.domain.theme.Theme;
import be.kdg.kandoe.domain.user.User;
import be.kdg.kandoe.dto.DtoConverter;
import be.kdg.kandoe.dto.SubThemeDto;
import be.kdg.kandoe.dto.ThemeDto;
import be.kdg.kandoe.repository.implementation.ThemeRepositoryImpl;
import be.kdg.kandoe.repository.jpa.ThemeJpa;
import be.kdg.kandoe.service.declaration.AuthenticationHelperService;
import be.kdg.kandoe.service.declaration.GameSessionService;
import be.kdg.kandoe.service.declaration.ThemeService;
import be.kdg.kandoe.service.declaration.UserService;
import be.kdg.kandoe.service.exception.ThemeServiceException;
import be.kdg.kandoe.service.implementation.ThemeServiceImpl;
import com.sun.org.apache.regexp.internal.RE;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.method.P;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.xml.ws.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "https://angularkandoe.herokuapp.com")
public class ThemeRestController {
    private final Logger logger = Logger.getLogger(ThemeRestController.class);

    private ThemeService themeService;

    @Autowired
    private AuthenticationHelperService authenticationHelperService;

    @Autowired
    private UserService userService;

    @Autowired
    private GameSessionService gameSessionService;

    public ThemeRestController(ThemeService themeService){
        this.themeService=themeService;
    }

    @RequestMapping(value = "api/public/themes", method = RequestMethod.GET)
 //   @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<List<ThemeDto>> getAllThemes(){
        System.out.println("CALL RECEIVED: getAllThemes");
        List<Theme> allThemes =themeService.getAllThemes();
        return ResponseEntity.ok().body(allThemes.stream().map(t-> DtoConverter.toThemeDto(t)).collect(Collectors.toList()));
    }

    //GET-METHODS

    @RequestMapping(value= "api/public/theme/{themeId}", method = RequestMethod.GET)
//    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<ThemeDto> getThemeById(@PathVariable Long themeId){
        System.out.println("CALLED RECEIVED: getThemeById: "+themeId);
        Theme theme;
        try{
            theme = themeService.getThemeById(themeId);
        }catch (ThemeServiceException e){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(ThemeDto.fromTheme(theme));
    }

    @RequestMapping(value = "api/public/subthemes",method = RequestMethod.GET)
  //  @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<List<SubThemeDto>> getAllSubThemes(){
        System.out.println("CALL RECEIVED: getAllSubThemes");
        List<SubTheme> subThemes = themeService.getAllSubThemes();
        return ResponseEntity.ok().body(subThemes.stream().map(st->SubThemeDto.fromSubTheme(st)).collect(Collectors.toList()));
    }

    @RequestMapping(value= "api/public/theme", method = RequestMethod.GET)
//    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<ThemeDto> getThemeByName(@RequestParam(name = "name") String name){
        System.out.println("CALL RECEIVED: getThemeByName: "+name);
        Theme theme = themeService.getThemeByName(name);
        if(theme !=null){
            return ResponseEntity.ok().body(ThemeDto.fromTheme(theme));
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(value = "api/public/subtheme/{subThemeId}",method = RequestMethod.GET)
   // @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<SubThemeDto> getSubThemeById(@PathVariable(name = "subThemeId")Long subThemeId){
        System.out.println("CALL RECEIVED: getSubThemeById: "+subThemeId);
        SubTheme subTheme = themeService.getSubThemeById(subThemeId);
        if(subTheme==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(SubThemeDto.fromSubTheme(subTheme));
    }

    @RequestMapping(value = "api/public/theme/{themeId}/subthemes", method= RequestMethod.GET)
  //  @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<List<SubThemeDto>> getSubThemesByThemeId(@RequestParam(name="themeId")Long themeId){
        System.out.println("CALL RECEIVED: getSubThemesByThemeId: "+themeId);
        List<SubTheme> subThemes = themeService.getSubThemesByThemeId(themeId);
        if(subThemes==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(subThemes.stream().map(s->SubThemeDto.fromSubTheme(s)).collect(Collectors.toList()));
    }

    //GET-METHODS
    //POST-METHODS
    @RequestMapping(value = "api/public/themes", method = RequestMethod.POST)
 //   @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<ThemeDto> CreateTheme(@Valid @RequestBody ThemeDto themeDto){
        System.out.println("CALL RECEIVED: CreateTheme");
        logger.log(Priority.INFO,"API CALL: CreateTheme");
        Theme createdTheme = themeService.addTheme(themeDto.toTheme());
        if(createdTheme==null){
            logger.log(Priority.ERROR,"ThemeService.addTheme returns NULL..");
            return ResponseEntity.badRequest().build();
        }else{
            logger.log(Priority.INFO,"Successfully created new Theme");
            return ResponseEntity.ok().body(ThemeDto.fromTheme(createdTheme));
        }
    }

    @RequestMapping(value = "api/public/subthemes/{themeId}",method = RequestMethod.POST)
//    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<SubThemeDto> CreateSubTheme(@Valid @RequestBody SubThemeDto subThemeDto,@PathVariable(name = "themeId") Long themeId){
        if(themeId==new Long(0)){
            themeId=null;
        }
        System.out.println("CALL RECEICED: CreateSubTheme");
        logger.log(Priority.INFO,"API CALL: CreateSubTheme");

        SubTheme createdSubTheme = themeService.addSubThemeByThemeId(subThemeDto.toSubTheme(),themeId);

        if(createdSubTheme!=null){
            return ResponseEntity.ok().body(SubThemeDto.fromSubTheme(createdSubTheme));
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    //POST-METHODS
    //PUT-METHODS
    @RequestMapping(value = "api/public/theme/{themeId}", method = RequestMethod.PUT)
//    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<ThemeDto> updateTheme(@PathVariable Long themeId, @Valid @RequestBody ThemeDto theme){
        System.out.println("CALL RECEIVED: updateTheme: "+themeId);
        logger.log(Priority.INFO,"API CALL: updateTheme: "+themeId);

        Theme foundTheme = themeService.getThemeById(themeId);
        if(foundTheme == null){
            logger.log(Priority.ERROR,"No theme found for Id: "+themeId);
            return ResponseEntity.notFound().build();
        }
        foundTheme.setDescription(theme.getDescription());
        foundTheme.setName(theme.getName());
        Theme updatedTheme=themeService.editTheme(foundTheme);

        logger.log(Priority.INFO,"Updated Theme for id: "+themeId);
        return ResponseEntity.ok().body(ThemeDto.fromTheme(updatedTheme));
    }

    @RequestMapping(value = "api/public/subtheme/{subThemeId}",method = RequestMethod.PUT)
//    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<SubThemeDto> updateSubTheme(@PathVariable Long subThemeId, @Valid @RequestBody SubThemeDto dto){
        System.out.println("CALL RECEIVED: updateSubTheme: "+subThemeId);
        logger.log(Priority.INFO, "API CALL: UpdateSubTheme: "+subThemeId);
        SubTheme foundSubTheme = themeService.getSubThemeById(subThemeId);
        if(foundSubTheme==null){
            logger.log(Priority.WARN,"No Theme found for Id: "+subThemeId);
            return ResponseEntity.notFound().build();
        }
        foundSubTheme.setSubThemeName(dto.getSubThemeName());
        foundSubTheme.setSubThemeDescription(dto.getSubThemeDescription());
        foundSubTheme.setTheme(dto.getTheme().toTheme());
        SubTheme updatedTheme = themeService.editSubtheme(foundSubTheme);
        if(updatedTheme==null){
            logger.log(Priority.ERROR,"Updated Theme is NULL");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok().body(SubThemeDto.fromSubTheme(updatedTheme));
    }
    //PUT-METHODS
    //DELET-METHODS
    @RequestMapping(value = "api/public/theme/{themeId}",method = RequestMethod.DELETE)
 //   @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<ThemeDto> deleteThemeByThemeId(@PathVariable Long themeId){
        System.out.println("CALLED deleteTheByThemeId: "+themeId);
        try{
            Theme deletedTheme=themeService.removeThemeById(themeId);
            return ResponseEntity.ok().body(ThemeDto.fromTheme(deletedTheme));
        }catch (ThemeServiceException e){
            return ResponseEntity.notFound().build();
        }

    }

    @RequestMapping(value = "api/public/theme", method = RequestMethod.DELETE)
  //  @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<ThemeDto> deleteThemeByName(@RequestParam(value = "name") String name){
        Theme foundTheme = themeService.getThemeByName(name);
        if(foundTheme==null){
            return ResponseEntity.notFound().build();
        }
        themeService.removeThemeById(foundTheme.getThemeId());
        return ResponseEntity.ok().body(ThemeDto.fromTheme(foundTheme));
    }

    @RequestMapping(value = "api/public/themes", method = RequestMethod.DELETE)
//    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<String> deleteThemes(){
        themeService.removeAllThemes();
        return ResponseEntity.ok().body("All good!");
    }

    @RequestMapping(value = "api/public/subthemes/{themeId}",method = RequestMethod.DELETE)
 //   @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<List<SubThemeDto>> deleteSubThemesByThemeId(@PathVariable(name = "themeId")Long themeId){
        if(themeId==new Long(0)){
            themeId=null;
        }
        List<SubTheme> deletedSubThemes=null;
        try{
            deletedSubThemes = themeService.removeSubThemesByThemeId(themeId);
        }catch (ThemeServiceException tse){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(deletedSubThemes.stream().map(st->SubThemeDto.fromSubTheme(st)).collect(Collectors.toList()));
    }
    //DELETE-METHODS
    /**
     * Temporary Testing Method to ensure clean database data.
     * @return
     */



    @GetMapping(value = "/api/private/users/{username}/connectedthemes")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity getConnectedThemes(@PathVariable String username, HttpServletRequest request){
        User requestUser = userService.findUserByUsername(username);

        if (!authenticationHelperService.userIsAllowedToAccessResource(request, username)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        };

        if(requestUser == null){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        List<ThemeJpa> themeJpas = new ArrayList<>();
        for(GameSession gameSession : gameSessionService.getUserGameSessions(username)){
            themeJpas.add(gameSession.getThemeForSession());
        }

        return ResponseEntity.ok(themeJpas);
    }

}
