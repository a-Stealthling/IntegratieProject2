package be.kdg.kandoe.service.declaration;


import be.kdg.kandoe.domain.theme.Theme;
import org.springframework.stereotype.Service;

import java.util.List;


public interface ThemeService {

    Theme addTheme(Theme theme1);
    Theme getThemeByName(String name);
    Theme getThemeById(long id);
    Theme editTheme(Theme theme);
    Theme removeTheme(Theme themeToDelete);
    Theme removeThemeById(long themeId);

    List<Theme> getAllThemes();
}
