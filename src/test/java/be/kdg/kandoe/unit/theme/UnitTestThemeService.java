package be.kdg.kandoe.unit.theme;

import be.kdg.kandoe.domain.theme.Theme;
import be.kdg.kandoe.dto.ThemeDto;
import be.kdg.kandoe.service.declaration.ThemeService;
import be.kdg.kandoe.service.exception.InputValidationException;
import be.kdg.kandoe.service.exception.ThemeServiceException;
import be.kdg.kandoe.service.implementation.ThemeServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class UnitTestThemeService {

    private ThemeService themeService;
    private Theme theme1;
    private Theme theme2;

    @Before
    public void Setup(){
        themeService = new ThemeServiceImpl(new ThemeRepoMock());
        ThemeDto theme1DTO=new ThemeDto(0,"Jeugd","Acties voor de jeugd");
        ThemeDto theme2DTO= new ThemeDto(1,"Sport","Acties voor sport");
        theme1 = new Theme(theme1DTO);
        theme2 = new Theme(theme2DTO);
        themeService.addTheme(theme1);
        themeService.addTheme(theme2);
    }

    @Test
    public void TestAddTheme(){
        ThemeDto DTOToAdd = new ThemeDto(2,"ThemeToAdd","Test Theme, if this exists success");
        Theme themeToAdd = new Theme(DTOToAdd);
        themeService.addTheme(themeToAdd);
        Theme returningTheme = themeService.getThemeByName(themeToAdd.getName());
        assertEquals("Theme returned should be equal to ThemeToAdd",returningTheme,themeToAdd);
    }

    /**
     * ThemeName can only contain 50 characters, if longer InputValidationException should be thrown
     */
    @Test(expected = InputValidationException.class)
    public void TestAddInvalidTheme(){
        ThemeDto DTOToAdd = new ThemeDto(2,"ThemeToAdd123456789123456789123456789123456789123456789123456789123456789123456789123456789123456789123456789123456789123456789","Theme with a name that is too long");
        Theme ThemeToAdd = new Theme(DTOToAdd);
        themeService.addTheme(ThemeToAdd);
        Theme returningTheme = themeService.getThemeById(ThemeToAdd.getThemeId());
        assertEquals("Theme returned should be null, name too long", returningTheme,null);
    }

    @Test
    public void TestGetThemeById() {
        Theme theme = themeService.getThemeById(0);
        assertEquals("Returned theme should be equal", theme, theme1);
    }

    @Test
    public void TestGetAllThemes(){
        Theme theme = new Theme(new ThemeDto(2,"Schoonheid","Thema ivm met schoonheid"));
        themeService.addTheme(theme);
        assertEquals("Length of themes should be 3",themeService.getAllThemes().size(),3);
        assertEquals("Values of themes should match",themeService.getThemeById(0).getName(),"Jeugd");
        assertEquals("Values of themes should match",themeService.getThemeById(1).getName(),"Sport");
        assertEquals("Values of themes should match",themeService.getThemeById(2).getName(),"Schoonheid");

    }

    @Test
    public void TestGetThemeByName(){
        Theme theme = themeService.getThemeByName(theme1.getName());
        assertEquals("Returned theme should be equal", theme,theme1);
    }

    @Test
    public void TestEditTheme(){
        ThemeDto updatedDTO = new ThemeDto(theme1.getThemeId(),theme1.getName(),theme1.getDescription());
        Theme newTheme = new Theme(updatedDTO);
        Theme oldTheme = new Theme(new ThemeDto(newTheme.getThemeId(),newTheme.getName(),newTheme.getDescription()));
        newTheme.setDescription("Theme has been updated");
        newTheme.setName("Updated Theme");
        Theme updatedTheme = themeService.editTheme(newTheme);
        Assert.assertNotSame("Themes should not be the same",oldTheme,updatedTheme);
    }
    /**
     * ThemeName can only contain 50 characters, if longer InputValidationException should be thrown
     */
    @Test(expected = InputValidationException.class)
    public void TestEditInvalidTheme(){
        Theme theme = theme1;
        theme.setName("Updated Theme 1234567891324684316846312316846132035461.065846464165468784513221341654631616316541321654131");
        theme.setDescription("Theme has been updated");
        themeService.editTheme(theme);
    }

    @Test
    public void TestDeleteTheme(){
        Theme themeToDelete = theme2;
        themeService.removeTheme(themeToDelete);
        assertEquals("Returned theme should be NULL",themeService.getThemeByName(themeToDelete.getName()),null);
    }
    @Test
    public void TestDeleteThemeById(){
        Theme themeToDelete = theme1;
        themeService.removeThemeById(themeToDelete.getThemeId());
        assertEquals("Returned theme should be NULL",themeService.getThemeById(themeToDelete.getThemeId()),null);
    }

    /**
     * If the themeToDelete is not found or doesn't exist, ThemeServiceException should be thrown
     */
    @Test(expected = ThemeServiceException.class)
    public void TestDeleteByNonExistingThemeId(){
        Theme unknownTheme = new Theme(new ThemeDto(3,"School","Thema ivm school"));
        themeService.removeThemeById(unknownTheme.getThemeId());
    }
    /**
     * If the themeToDelete is not found or doesn't exist, ThemeServiceException should be thrown
     */
    @Test(expected = ThemeServiceException.class)
    public void TestDeleteNonExistingTheme(){
        Theme unknownTheme = new Theme(new ThemeDto(3,"Armoede", "Thema ivm armoeden enzo"));
        themeService.removeTheme(unknownTheme);
    }
}
