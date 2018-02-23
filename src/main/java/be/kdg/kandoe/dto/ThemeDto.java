package be.kdg.kandoe.dto;

import be.kdg.kandoe.domain.theme.Theme;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;

/**
 * Mediate Object-class to translate JSON-objects to Theme-objects
 */
@Entity
@Table(name = "THEME")
public class ThemeDto {
    @Column(name = "theme_id")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long themeId;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;

    public ThemeDto(){
        this.themeId=0;
        this.name="default";
        this.description="default";
    }

    public ThemeDto(long themeId, String name, String description) {
        this.themeId=themeId;
        this.name = name;
        this.description = description;
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



}
