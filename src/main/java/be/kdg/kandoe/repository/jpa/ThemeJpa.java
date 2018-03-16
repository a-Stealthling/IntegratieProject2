package be.kdg.kandoe.repository.jpa;

import be.kdg.kandoe.domain.GameSession;
import be.kdg.kandoe.domain.theme.SubTheme;
import be.kdg.kandoe.domain.theme.Theme;
import be.kdg.kandoe.dto.ThemeDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
    @Table(name = "THEME")
    public class ThemeJpa {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Generated(GenerationTime.INSERT)
        @Column(name="themeId",nullable = false)
        private long themeId;

        @Column(length = 50,nullable = false)
        private String name;

        @Column(nullable = false)
        private String description;


        @Column(nullable = false)
        @OneToMany(targetEntity=SubThemeJpa.class,fetch = FetchType.EAGER,mappedBy = "theme",cascade = CascadeType.REMOVE)
        @OnDelete(action = OnDeleteAction.CASCADE)
        @Fetch(FetchMode.SELECT)
//        @JsonIgnore
        private List<SubThemeJpa> subThemes = new ArrayList<>();

        @OneToMany(targetEntity = GameSession.class,mappedBy = "themeForSession")
        @Column
//        @JsonIgnore
        private List<GameSession> gameSessions = new ArrayList<>();

        public ThemeJpa(){

        }
        public ThemeJpa(Theme theme){
            this.name=theme.getName();
            this.description=theme.getDescription();
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

        /**public List<SubThemeJpa> getSubThemes() {
            return subThemes;
        }**/

        public Theme toTheme(){
            Theme theme = new Theme();
            theme.setThemeId(this.themeId);
            theme.setDescription(this.description);
            theme.setName(this.name);
            if(theme.getSubThemes()!=null){
                theme.setSubThemes(this.subThemes.stream().map(st->st.toSubTheme()).collect(Collectors.toList()));
            }
            return theme;
        }

         public static ThemeJpa fromTheme(Theme theme){
            ThemeJpa jpa = new ThemeJpa();
            jpa.name=theme.getName();
            jpa.description=(theme.getDescription());
            jpa.themeId=theme.getThemeId();
            if(theme.getSubThemes()!=null){
                jpa.subThemes = theme.getSubThemes().stream().map(st->SubThemeJpa.fromSubTheme(st)).collect(Collectors.toList());
            }
            jpa.gameSessions = theme.getGameSessions();
            return jpa;
        }

    public void setGameSessions(List<GameSession> gameSessions) {
        this.gameSessions = gameSessions;
    }

    public void setSubThemes(List<SubThemeJpa> subThemes) {
        this.subThemes = subThemes;
    }

    public List<SubThemeJpa> getSubThemes() {
        return subThemes;
    }
}
