package be.kdg.kandoe.domain.user;

import be.kdg.kandoe.domain.UserGameSessionInfo;
import be.kdg.kandoe.dto.UpdateuserDto;
import be.kdg.kandoe.dto.UserDto;
import org.hibernate.annotations.Fetch;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "USER_ENTITY")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "user_id")
    private long userId;

    @Column(length = 50, nullable = false)
    private String firstName;

    @Column(length = 50, nullable = false)
    private String lastName;

    @Column(length = 50, nullable = false)
    private String username;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private int day;

    @Column(nullable = false)
    private int month;

    @Column(nullable = false)
    private int year;


    @Column(nullable = false)
    private String encryptedPassword;

    @Column(nullable = false)
    private Gender gender;

    @Column
    @OneToMany(mappedBy = "user",targetEntity = Authority.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(org.hibernate.annotations.FetchMode.SELECT)
    private List<Authority> authorities = new ArrayList<>();


    @Column
    @OneToMany(targetEntity = UserGameSessionInfo.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "user", orphanRemoval = true)
    @Fetch(org.hibernate.annotations.FetchMode.SELECT)
    private List<UserGameSessionInfo> gameSessionInfos = new ArrayList<>();

    @Column
    private String profilePictureFileName = "default-profile.png";



    public User() {
    }

    public User(UserDto userDto){
        this.firstName = userDto.getFirstName();
        this.lastName = userDto.getLastName();
        this.username = userDto.getUsername();
        this.email = userDto.getEmail();
        this.year = userDto.getYear();
        this.month = userDto.getMonth();
        this.day = userDto.getDay();
        this.gender = userDto.getGender();
        this.encryptedPassword = userDto.getPassword();
    }

    public User(UpdateuserDto updateuserDto){
        this.firstName = updateuserDto.getFirstName();
        this.lastName = updateuserDto.getLastName();
        this.encryptedPassword = updateuserDto.getPassword();
        this.gender = updateuserDto.getGender();
        this.year = updateuserDto.getYear();
        this.month = updateuserDto.getMonth();
        this.day = updateuserDto.getDay();
    }

    public User(String firstName, String lastName, String username, String email, int day, int month, int year, String encryptedPassword, Gender gender, List<Authority> authorities) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.day = day;
        this.month = month;
        this.year = year;
        this.encryptedPassword = encryptedPassword;
        this.gender = gender;
        this.authorities = authorities;
    }

    public void addGameSessionInfo(UserGameSessionInfo userGameSessionInfo){
        this.gameSessionInfos.add(userGameSessionInfo);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getPassword() {
        return this.encryptedPassword;
    }

    public String getEncryptedPassword() {
        return this.encryptedPassword;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setEncryptedPassword(String password) {
        this.encryptedPassword = password;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setAuthorities(List<Authority> authorities) {
        this.authorities = authorities;
    }

    public List<Authority> getUserRoles(){
        return this.authorities;
    }

    public Calendar getBirthday(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(getYear(), getMonth() - 1, getDay());
        return calendar;
    }

    public List<UserGameSessionInfo> getGameSessionInfos() {
        return gameSessionInfos;
    }

    public void setGameSessionInfos(List<UserGameSessionInfo> gameSessionInfos) {
        this.gameSessionInfos = gameSessionInfos;
    }

    public String getProfilePictureFileName() {
        return profilePictureFileName;
    }

    public void setProfilePictureFileName(String profilePictureFileName) {
        this.profilePictureFileName = profilePictureFileName;
    }

    public void removeUserGameSessionInfo(UserGameSessionInfo userGameSessionInfo){
        this.getGameSessionInfos().remove(userGameSessionInfo);
    }
}
