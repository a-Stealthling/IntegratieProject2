package be.kdg.kandoe.repository.declaration;

import be.kdg.kandoe.domain.theme.Theme;
import be.kdg.kandoe.dto.ThemeDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ThemeRepository {
    Theme findThemeByName(String name);
    Theme findThemeById(Long id);
    Theme createTheme(Theme theme);

    Theme deleteThemeByName(String name);
    Theme deleteThemeByThemeId(Long themeId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE THEME SET name = :name, description = :description WHERE themeId= :themeId")
    Theme editTheme(@Param("themeId")Long themeId, @Param("name")String name, @Param("description")String description);

    @Query("SELECT t FROM THEME")
    List<Theme> findAllThemes();
}
