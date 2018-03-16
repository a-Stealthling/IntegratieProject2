package be.kdg.kandoe.domain.theme;

import be.kdg.kandoe.dto.SubThemeDto;

import java.util.ArrayList;
import java.util.List;

public class SubTheme {

    private long subThemeId;
    private Theme theme;
    private String subThemeName;
    private String subThemeDescription;

    //TEST
    private List<CardSubTheme> cardSubThemes = new ArrayList<>();

    public SubTheme(){

    }

    public SubTheme(SubThemeDto dto){
        this.subThemeId=dto.getSubThemeId();
        this.subThemeName=dto.getSubThemeName();
        this.subThemeDescription=dto.getSubThemeDescription();
        if(dto.getTheme()!=null){
            this.theme=dto.getTheme().toTheme();
        }else{
            this.theme=null;
        }
    }

    public Theme getTheme() {
        return theme;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
    }

    public long getSubThemeId() {
        return subThemeId;
    }

    public void setSubThemeId(long subThemeId) {
        this.subThemeId = subThemeId;
    }

    public String getSubThemeName() {
        return subThemeName;
    }

    public void setSubThemeName(String subThemeName) {
        this.subThemeName = subThemeName;
    }

    public String getSubThemeDescription() {
        return subThemeDescription;
    }

    public void setSubThemeDescription(String subThemeDescription) {
        this.subThemeDescription = subThemeDescription;
    }

    public List<CardSubTheme> getCardSubThemes() {
        return cardSubThemes;
    }
}
