package be.kdg.kandoe.domain.theme;

import be.kdg.kandoe.domain.GameSession;
import be.kdg.kandoe.dto.ThemeDto;
import be.kdg.kandoe.repository.jpa.ThemeJpa;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Theme {
    private long themeId;
    private String name;
    private String description;
    private List<SubTheme> subThemes;
    private List<GameSession> gameSessions = new ArrayList<>();


    public Theme(){

    }
    public Theme(ThemeDto themeDto){
        this.themeId=themeDto.getThemeId();
        this.name=themeDto.getName();
        this.description=themeDto.getDescription();
        this.subThemes=themeDto.getSubThemes().stream().map(st->st.toSubTheme()).collect(Collectors.toList());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getThemeId() {
        return themeId;
    }
    public void setThemeId(long id){
        this.themeId=id;
    }

    public List<SubTheme> getSubThemes() {
        return subThemes;
    }

    public void setSubThemes(List<SubTheme> subThemes) {
        this.subThemes = subThemes;
    }

    public List<GameSession> getGameSessions() {
        return gameSessions;
    }

    public void setGameSessions(List<GameSession> gameSessions) {
        this.gameSessions = gameSessions;
    }

    public void addGameSession(GameSession gameSession){
        this.gameSessions.add(gameSession);
    }
}
