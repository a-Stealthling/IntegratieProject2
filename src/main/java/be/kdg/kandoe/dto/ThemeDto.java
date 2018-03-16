package be.kdg.kandoe.dto;

import be.kdg.kandoe.domain.theme.Theme;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Mediate Object-class to translate JSON-objects to Theme-objects
 */

public class ThemeDto {

    private long themeId;
    private String name;
    private String description;
    private List<SubThemeDto> subThemes;

    public ThemeDto(){


    }

    public ThemeDto(long themeId, String name, String description) {
        this.themeId=themeId;
        this.name = name;
        this.description = description;
        subThemes = new ArrayList<>();
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

    public void setThemeId(long themeId){
        this.themeId=themeId;
    }

    public List<SubThemeDto> getSubThemes() {
        return subThemes;
    }

    public void setSubThemes(List<SubThemeDto> subThemes) {
        this.subThemes = subThemes;
    }

    public String toJsonString(){
        String JSON = new Gson().toJson(this);
        return JSON;
    }
    public Theme toTheme(){
        Theme theme = new Theme();
        theme.setDescription(this.description);
        theme.setThemeId(this.themeId);

        theme.setName(this.getName());
        return theme;
    }

    public static ThemeDto fromTheme(Theme theme){
        ThemeDto newThemeDto =  new ThemeDto(theme.getThemeId(),theme.getName(),theme.getDescription());
        if(theme.getSubThemes()!=null){
            newThemeDto.setSubThemes(theme.getSubThemes().stream().map(st-> SubThemeDto.fromSubTheme(st)).collect(Collectors.toList()));
        }
        return newThemeDto;
    }
}
