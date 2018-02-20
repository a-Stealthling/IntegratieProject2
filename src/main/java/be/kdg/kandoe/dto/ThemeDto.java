package be.kdg.kandoe.dto;

import be.kdg.kandoe.domain.theme.Theme;

import javax.persistence.*;

/**
 * Mediate Object-class to translate JSON-objects to Theme-objects
 */
@Entity
@Table
public class ThemeDto {
    @Column
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long themeId;
    @Column
    private String name;
    @Column
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

    public void setThemeId(long themeId) {
        this.themeId = themeId;
    }


}
