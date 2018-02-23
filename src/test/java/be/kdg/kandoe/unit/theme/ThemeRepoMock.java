package be.kdg.kandoe.unit.theme;

import be.kdg.kandoe.domain.theme.Theme;
import be.kdg.kandoe.dto.ThemeDto;
import be.kdg.kandoe.repository.declaration.ThemeRepository;
import be.kdg.kandoe.service.declaration.ThemeService;
import be.kdg.kandoe.service.exception.ThemeServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;

import javax.validation.constraints.Null;
import java.util.ArrayList;
import java.util.List;

public class ThemeRepoMock implements ThemeRepository {
    @Autowired
    ThemeRepository repository;
    List<Theme> themes = new ArrayList<>();

    public Theme findThemeByName(String name) {
        for (Theme t: themes
             ) {
           if(t.getName().equals(name)) {
               return t;
           }
        }
        return null;
    }

    public Theme findThemeById(Long id) {
        for (Theme t: themes
             ) {
            if(t.getThemeId() == id) {
                return t;
            }
        }
        return null;
    }

    public Theme createTheme(Theme theme) {
        themes.add(theme);
        return themes.get(themes.indexOf(theme));
    }

    @Query("UPDATE THEME SET name = :name, description = :description WHERE themeId= :themeId")
    public Theme editTheme(Long themeId, String name, String description) {
        Theme themeToFind =null;
        for (Theme t:themes
             ) {
            if(t.getThemeId()==themeId){
                t.setName(name);
                t.setDescription(description);
                themeToFind=t;
            }
        }
        if (themeToFind==null){
            throw new NullPointerException("No theme found for ID: "+themeId);
        }
        return themeToFind;
    }

    public Theme deleteThemeByName(String name) {
        Theme themeToFind =null;
        for (Theme t: themes
             ) {
            if(t.getName().equals(name)){
                themeToFind=t;
            }
        }
        if(themeToFind==null){
            throw new ThemeServiceException("No theme found for name: "+name);
        }
        themes.remove(themeToFind);
        return themeToFind;
    }

    @Override
    public Theme deleteThemeByThemeId(Long themeId) {
        Theme themeToFind =null;
        for (Theme t: themes
                ) {
            if(t.getThemeId()==themeId){
                themeToFind=t;
            }
        }
        if(themeToFind==null){
            throw new ThemeServiceException("No theme found for ID: "+themeId);
        }
        themes.remove(themeToFind);
        return themeToFind;
    }


    public List<Theme> findAllThemes() {
        if(themes!=null){
            return themes;
        }
        else throw new NullPointerException("Arraylist 'themes' equals null");
    }
}
