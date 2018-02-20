package be.kdg.kandoe.repository.declaration;

import be.kdg.kandoe.domain.theme.Theme;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface ThemeRepository {
    Theme findThemeByName(String name);
    Theme findThemeById(Long id);
    Theme createTheme(Theme theme);


    Theme editTheme(Theme theme);

    Theme deleteTheme(Theme themeToDelete);

    Theme deleteThemeById(long themeId);

    List<Theme> findAllThemes();
}
