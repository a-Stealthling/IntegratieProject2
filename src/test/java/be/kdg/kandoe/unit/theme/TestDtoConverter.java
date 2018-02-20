package be.kdg.kandoe.unit.theme;

import be.kdg.kandoe.domain.theme.Theme;
import be.kdg.kandoe.dto.DtoConverter;
import be.kdg.kandoe.dto.ThemeDto;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class TestDtoConverter {

    @Test
    public void TestThemeDtoConverter(){
        Theme theme = new Theme(new ThemeDto(0,"TestTheme","TestTheme to convert into DTO"));
        ThemeDto themeDto = DtoConverter.toThemeDto(theme);
        Assert.assertEquals("themeId should be the same",theme.getThemeId(),themeDto.getThemeId());
        Assert.assertEquals("themeName should be the same",theme.getName(),themeDto.getName());
        Assert.assertEquals("themeDescription should be the same",theme.getDescription(),themeDto.getDescription());
    }
}
