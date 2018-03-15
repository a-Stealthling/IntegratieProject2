package be.kdg.kandoe.service.implementation;

import be.kdg.kandoe.domain.theme.SubTheme;
import be.kdg.kandoe.domain.theme.Theme;
import be.kdg.kandoe.repository.declaration.ThemeRepository;
import be.kdg.kandoe.repository.jpa.SubThemeJpa;
import be.kdg.kandoe.repository.jpa.ThemeJpa;
import be.kdg.kandoe.service.declaration.ThemeService;
import be.kdg.kandoe.service.exception.InputValidationException;
import be.kdg.kandoe.service.exception.ThemeServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Primary
public class ThemeServiceImpl implements ThemeService {

    private ThemeRepository themeRepo;

    @Autowired
    public ThemeServiceImpl(ThemeRepository repository){
        this.themeRepo=repository;
    }
    //ADD-METHODS
    @Override
    public Theme addTheme(Theme theme) {
        if(checkNameLength(theme)) {
           return themeRepo.createTheme(theme);
        } else {
            throw new InputValidationException("Theme name too long");
        }
    }

    @Override
    public ThemeJpa addThemeNoConvert(ThemeJpa theme) {
       return themeRepo.createThemeNoConvert(theme);
    }

    @Override
    public SubTheme addSubThemeByThemeId(SubTheme subTheme,long themeId) {
        Theme themeToAdd=themeRepo.findThemeById(themeId);
        subTheme.setTheme(themeToAdd);
        return themeRepo.createSubTheme(subTheme);
    }

    @Override
    public SubThemeJpa addSubThemeByIdNoConvert(SubThemeJpa subThemeJpa, long themeId) {
        //entityManager.getReference(..., ...); --> Eigenlijk beter want dan moet er geen call naar de db gemaakt worden
        return themeRepo.createSubThemeNoConvert(subThemeJpa);
    }

    //ADD-METHODS
    //GET-METHODS
    @Override
    public Theme getThemeByName(String name) {
        Theme themeToFind = themeRepo.findThemeByName(name);
        if(themeToFind==null){
            throw new ThemeServiceException("No theme found for name: "+name);
        }
        return themeToFind;
    }




    @Override
    public Theme getThemeById(long id) {
        Theme foundTheme = themeRepo.findThemeById(id);
        if(foundTheme==null){
            throw new ThemeServiceException("No theme found by id: "+id);
        }
        return themeRepo.findThemeById(id);
    }

    @Override
    public SubTheme getSubThemeById(long subThemeId){
        SubTheme foundSubTheme = themeRepo.findSubThemeById(subThemeId);
        if(foundSubTheme==null){
            throw new ThemeServiceException("No subtheme found by id: "+subThemeId);
        }
        return foundSubTheme;
    }

    @Override
    public List<Theme> getAllThemes() {
        return themeRepo.findAllThemes();
    }

    @Override
    public List<SubTheme> getAllSubThemes() {
        return themeRepo.findAllSubThemes();
    }

    @Override
    public List<SubTheme> getSubThemesByThemeId(long id){
        return themeRepo.findSubThemesByThemeId(id);
    }
    //GET-METHODS
    //EDIT-METHODS
    @Override
    public Theme editTheme(Theme theme) {
        if (checkNameLength(theme)) return themeRepo.editTheme(theme);
        throw new InputValidationException("Themename too long");
    }

    @Override
    public SubTheme editSubtheme(SubTheme subTheme) {
        return themeRepo.editSubTheme(subTheme);
    }

    //EDIT-METHODS
    //REMOVE-METHODS
    public void removeAllThemes(){
        themeRepo.deleteAll();
    }

    @Override
    public Theme removeThemeById(long themeId) {
        Theme themeToDelete = themeRepo.findThemeById(themeId);
        if(themeToDelete==null){
            throw new ThemeServiceException("No Theme found for ID: "+themeId);
        }
        return themeRepo.deleteTheme(themeToDelete);
    }

    @Override
    public SubTheme removeSubThemeById(long subThemeId){
        SubTheme subThemeToDelete = themeRepo.findSubThemeById(subThemeId);
        if(subThemeToDelete==null){
            throw new ThemeServiceException("No SubTheme found for ID: "+subThemeId);
        }
        return themeRepo.deleteSubTheme(subThemeToDelete);
    }

    @Override
    public List<SubTheme> removeSubThemesByThemeId(long themeId) {
        Theme theme = themeRepo.findThemeById(themeId);
        if(theme==null){
            throw new ThemeServiceException("No theme found for id: "+themeId);
        }
        List<SubTheme> subThemes = themeRepo.findAllSubThemes();
        List<SubTheme> deletedSubThemes= new ArrayList<>();
        for (SubTheme st:subThemes
             ) {
            if(st.getTheme().getThemeId()==themeId){
                deletedSubThemes.add(st);
                themeRepo.deleteSubTheme(st);
            }
        }
        return deletedSubThemes;
    }
    //REMOVE-METHODS

    /**
     * Checks if the themeName is not longer than 50 characters
     * @param theme
     * @return boolean
     */
    private boolean checkNameLength(Theme theme) {
        if(theme.getName().length()<=50){
            return true;
        }
        return false;
    }
}
