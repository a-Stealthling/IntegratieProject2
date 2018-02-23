package be.kdg.kandoe.repository.implementation;

import be.kdg.kandoe.domain.theme.Theme;
import be.kdg.kandoe.dto.DtoConverter;
import be.kdg.kandoe.dto.ThemeDto;
import be.kdg.kandoe.repository.declaration.ThemeRepository;
import be.kdg.kandoe.service.exception.ThemeRepositoryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class ThemeRepositoryImpl implements ThemeRepository {

 //   @PersistenceContext
    private final EntityManager em;

    @Autowired
    public ThemeRepositoryImpl(EntityManager entityManager) {
        em = entityManager;
    }

    @Transactional
    @Override
    public Theme findThemeByName(@Param("name")String name) {
        Query query = em.createQuery("SELECT themeId, name, description from ThemeDto WHERE name = :name",ThemeDto.class).setParameter("name",name);
        if(query.getResultList()==null || query.getResultList().get(0)==null){
            throw new ThemeRepositoryException("Query for findThemeByName: "+name+" threw an exception: ResultList empty");
        }
        return new Theme((ThemeDto)query.getResultList().get(0));
    }

    @Transactional
    @Override
    public Theme findThemeById(Long themeId) {
        Query query= em.createQuery("SELECT theme FROM ThemeDto theme WHERE themeId=:themeId",ThemeDto.class).setParameter("themeId",themeId);
        System.out.println(query.getResultList().get(0).getClass());
        if(query.getResultList()==null || query.getResultList().get(0)==null){
            throw new ThemeRepositoryException("Query for findThemeById: "+themeId+" threw an exception: ResultList empty");
        }
        return new Theme((ThemeDto)query.getResultList().get(0));
    }

    @Transactional
    @Override
    public Theme createTheme(Theme theme) {
        em.persist(DtoConverter.toThemeDto(theme));
        return theme;
    }
    @Transactional
    @Override
    public Theme editTheme(Long themeId, String name, String description) {
        return null;
    }

    @Transactional
    @Override
    public Theme deleteThemeByName(String name) {
        return null;
    }

    @Transactional
    @Override
    public Theme deleteThemeByThemeId(Long themeId) {
        return null;
    }

    @Transactional
    @Override
    public List<Theme> findAllThemes() {
        return null;
    }
}

