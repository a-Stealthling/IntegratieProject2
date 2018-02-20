package be.kdg.kandoe.unit.theme;

import be.kdg.kandoe.controller.rest.ThemeRestController;
import be.kdg.kandoe.domain.theme.Theme;
import be.kdg.kandoe.dto.ThemeDto;
import be.kdg.kandoe.service.implementation.ThemeServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.core.IsEqual.equalTo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class UnitTestThemeRestController {

    ThemeRestController controller;

    Theme theme1;
    Theme theme2;

    private TestRestTemplate restTemplate = new TestRestTemplate();

    @Before
    public void Setup(){
        controller = new ThemeRestController(new ThemeServiceImpl(new ThemeRepoMock()));
        theme1= new Theme(new ThemeDto(0,"TestTheme School","TestTheme Everything to do with school"));
        theme2= new Theme(new ThemeDto(1, "TestTheme Industry","TestTheme Everything to do with Industry"));
    }

    @Test
    public void TestCreateTheme(){
        ThemeDto themeDto = new ThemeDto(2,"JSONTheme","Theme created via JSON");
        ResponseEntity<ThemeDto> response = restTemplate.postForEntity("http://localhost:9090/api/themes", themeDto, ThemeDto.class);
        //ResponseEntity<ThemeDto> response2 = restTemplate.exchange("http://localhost:9090/api/themes", HttpMethod.POST, new HttpEntity<>(""), ThemeDto.class);
        Assert.assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    }

    @Test
    public void TestGetAllThemes(){
        //mvc.perform(get("api/"))
    }

    @Test
    public void TestGetThemeById(){

    }

    @Test
    public void TestGetThemeByName(){

    }

    @Test
    public void TestEditTheme(){

    }

    @Test
    public void TestRemoveTheme(){

    }

    @Test
    public void TestRemoveThemeById(){

    }
}
