package be.kdg.kandoe.repository.implementation;

import be.kdg.kandoe.domain.theme.Theme;
import be.kdg.kandoe.dto.DtoConverter;
import be.kdg.kandoe.dto.ThemeDto;
import be.kdg.kandoe.repository.declaration.ThemeRepository;
import org.hibernate.Query;
import org.hibernate.jpa.internal.EntityManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class ThemeRepositoryImpl implements ThemeRepository {
    private EntityManager em;

    @Autowired
    public ThemeRepositoryImpl(EntityManager entityManager){
        em = entityManager;
    }

    @Override
    public Theme findThemeByName(String name) {
        throw new NotImplementedException();
    }

    @Override
    public Theme findThemeById(Long id) {
        throw new NotImplementedException();
    }

    @Override
    @Transactional
    public Theme createTheme(Theme theme) throws DataAccessException {
        ThemeDto dto = DtoConverter.toThemeDto(theme);
        em.persist(dto);
        return theme;
    }

    @Override
    public Theme editTheme(Theme theme) {
        throw new NotImplementedException();
    }

    @Override
    public Theme deleteTheme(Theme themeToDelete) {
        throw new NotImplementedException();
    }

    @Override
    public Theme deleteThemeById(long themeId) {
        throw new NotImplementedException();
    }

    @Override
    public List<Theme> findAllThemes() {
        throw new NotImplementedException();
    }
}
