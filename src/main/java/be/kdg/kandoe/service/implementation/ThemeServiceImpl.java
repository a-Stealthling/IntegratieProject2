package be.kdg.kandoe.service.implementation;

import be.kdg.kandoe.domain.theme.Theme;
import be.kdg.kandoe.repository.declaration.ThemeRepository;
import be.kdg.kandoe.service.declaration.ThemeService;
import be.kdg.kandoe.service.exception.InputValidationException;
import org.springframework.beans.factory.annotation.Autowired;

import javax.inject.Named;
import java.util.List;

@Named
public class ThemeServiceImpl implements ThemeService {

    private final ThemeRepository themeRepo;

    public ThemeServiceImpl(ThemeRepository themeRepo){
        this.themeRepo = themeRepo;
    }

    @Override
    public Theme addTheme(Theme theme1) {
        if(checkNameLength(theme1)) {
            themeRepo.createTheme(theme1);
            return theme1;
        } else {
            throw new InputValidationException("Theme name too long");
        }
    }
    @Override
    public Theme getThemeByName(String name) {
        return themeRepo.findThemeByName(name);
    }

    @Override
    public Theme getThemeById(long id) {
        return themeRepo.findThemeById(id);
    }

    @Override
    public Theme editTheme(Theme theme) {
        if (checkNameLength(theme)) return themeRepo.editTheme(theme);
        throw new InputValidationException("Themename too long");
    }
    @Override
    public Theme removeTheme(Theme themeToDelete) {
        return themeRepo.deleteTheme(themeToDelete);
    }

    @Override
    public Theme removeThemeById(long themeId) {
        return themeRepo.deleteThemeById(themeId);
    }

    @Override
    public List<Theme> getAllThemes() {
        return themeRepo.findAllThemes();
    }

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
