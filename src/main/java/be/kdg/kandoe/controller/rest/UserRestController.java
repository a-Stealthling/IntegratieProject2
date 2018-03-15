package be.kdg.kandoe.controller.rest;

import be.kdg.kandoe.domain.user.User;
import be.kdg.kandoe.dto.RequestUserDto;
import be.kdg.kandoe.dto.UserDto;
import be.kdg.kandoe.service.declaration.AuthenticationHelperService;
import be.kdg.kandoe.service.declaration.StorageService;
import be.kdg.kandoe.service.declaration.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "https://angularkandoe.herokuapp.com")
public class UserRestController {
    private final Logger logger = Logger.getLogger(UserRestController.class);
    private final UserService userService;
    private final StorageService storageService;
    private final AuthenticationHelperService authenticationHelperService;


    @Autowired
    public UserRestController(UserService userService, StorageService storageService, AuthenticationHelperService authenticationHelperService) {
        this.userService = userService;
        this.storageService = storageService;
        this.authenticationHelperService = authenticationHelperService;
    }


    //GET ALL USERS
    @GetMapping("/api/private/users")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<User> getUsers(){
        return userService.findUsers();
    }



    //GET ALL INFO ABOUT ALL USERS (LIMITED)
    @GetMapping("/api/private/users/limited")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity getAllUsersLimited(HttpServletRequest request){
        List<User> users = userService.findUsers();

        List<RequestUserDto> userDtos = new ArrayList<>();
        for(User user : users){
            userDtos.add(new RequestUserDto(user.getUsername(), user.getFirstName(), user.getLastName(), user.getEmail()));
        }

        return ResponseEntity.ok(userDtos);
    }


    //GET ONE USER ON USERID
    @GetMapping("/api/private/userid/{userId}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<RequestUserDto> getUser(HttpServletRequest request, @PathVariable long userId){
        User requestUser = userService.findUserById(userId);
        String username = "";

        if (requestUser != null) username = requestUser.getUsername();

        if (!authenticationHelperService.userIsAllowedToAccessResource(request, username)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        };

        if(requestUser == null){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        RequestUserDto requestUserDto = new RequestUserDto(requestUser.getUsername(), requestUser.getFirstName(), requestUser.getLastName(), requestUser.getEmail(), requestUser.getBirthday(), requestUser.getGender());

        return ResponseEntity.ok().body(requestUserDto);
    }


    //GET ONE USER ON USERNAME
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @GetMapping("/api/private/users/username/{username}")
    public ResponseEntity<RequestUserDto> getUser(@PathVariable String username, HttpServletRequest request){
        User requestUser = userService.findUserByUsername(username);

        if (!authenticationHelperService.userIsAllowedToAccessResource(request, username)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        };

        if(requestUser == null){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        RequestUserDto requestUserDto = new RequestUserDto(requestUser.getUsername(), requestUser.getFirstName(), requestUser.getLastName(), requestUser.getEmail(), requestUser.getBirthday(), requestUser.getGender());

        return ResponseEntity.ok().body(requestUserDto);
    }

    //CREATE USER
//    @PostMapping("/api/private/users")
//    public User createUser(@Valid @RequestBody User user){
//        return userService.addUser(user);
//    }


    //UPDATE USER
    @PutMapping("/api/private/users/{username}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<RequestUserDto> updateUser(@PathVariable String username, @Valid @RequestBody UserDto changedUser, HttpServletRequest request){
        String usernameFromToken = (String) request.getAttribute("username");
        User requestUser = userService.findUserByUsername(username);

        if (!authenticationHelperService.userIsAllowedToAccessResource(request, username)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        };

        if(requestUser == null){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        if(!requestUser.getUsername().equalsIgnoreCase(changedUser.getUsername()) ||
                !requestUser.getUsername().equalsIgnoreCase(usernameFromToken) ||
                !changedUser.getUsername().equalsIgnoreCase(usernameFromToken)){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }


        requestUser.setFirstName(changedUser.getFirstName());
        requestUser.setLastName(changedUser.getLastName());
        requestUser.setGender(changedUser.getGender());
        requestUser.setYear(changedUser.getYear());
        requestUser.setMonth(changedUser.getMonth());
        requestUser.setDay(changedUser.getDay());

        User savedUser = userService.updateUserNoPassword(requestUser);
        RequestUserDto requestUserDto = new RequestUserDto(requestUser.getUsername(), requestUser.getFirstName(), requestUser.getLastName(), requestUser.getEmail());
        return ResponseEntity.ok(requestUserDto);
    }

    @PostMapping("/api/private/users/{username}/updatepassword")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity updatePasswordOfUser(@PathVariable String username, @RequestBody UserDto changedUser,  HttpServletRequest request){
        User requestUser = userService.findUserByUsername(username);

        if (!authenticationHelperService.userIsAllowedToAccessResource(request, username)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        };

        if(requestUser == null){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        requestUser.setEncryptedPassword(changedUser.getPassword());

        userService.updateUser(requestUser.getUserId(), requestUser);

        return ResponseEntity.ok().build();
    }



    //DELETE
//    @DeleteMapping("api/private/users/{userId}")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    public ResponseEntity<User> deleteUser(@PathVariable Long userId){
//        User user = userService.findUserById(userId);
//
//        if(user == null){
//            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
//        }
//
//        userService.deleteUser(userId);
//        return ResponseEntity.ok().build();
//    }



}
