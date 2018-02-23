package be.kdg.kandoe.unit.theme;

import be.kdg.kandoe.controller.rest.ThemeRestController;
import be.kdg.kandoe.domain.theme.Theme;
import be.kdg.kandoe.dto.ThemeDto;
import be.kdg.kandoe.service.implementation.ThemeServiceImpl;
import org.junit.After;
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

import java.lang.reflect.Method;
import java.util.List;

import static org.hamcrest.core.IsEqual.equalTo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class UnitTestThemeRestController {

    ThemeRestController controller;

    ThemeDto theme1;
    ThemeDto theme2;

    private TestRestTemplate restTemplate = new TestRestTemplate();

    @Before
    public void Setup(){
        controller = new ThemeRestController(new ThemeServiceImpl(new ThemeRepoMock()));
        theme1= new ThemeDto(0,"School","TestTheme Everything to do with school");
        theme2= new ThemeDto(1, "Industry","TestTheme Everything to do with Industry");
        restTemplate.postForEntity("http://localhost:9090/api/themes",theme1,ThemeDto.class);
        restTemplate.postForEntity("http://localhost:9090/api/themes",theme2,ThemeDto.class);
    }

    @Test
    public void TestCreateTheme(){
        ThemeDto themeDto = new ThemeDto(2,"JSONTheme","Theme created via JSON");
        ResponseEntity<ThemeDto> response = restTemplate.postForEntity("http://localhost:9090/api/themes", themeDto, ThemeDto.class);
        Assert.assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
        Assert.assertThat(response.getBody().getClass(),equalTo(ThemeDto.class));
    }

    @Test
    public void TestGetAllThemes(){
        ResponseEntity<Theme[]> response = restTemplate.getForEntity("http://localhost:9090/api/themes",Theme[].class);
        Assert.assertThat(response.getStatusCode(),equalTo(HttpStatus.OK));
        Assert.assertNotNull(response.getBody());
        Assert.assertThat(response.getBody()[0].getClass(),equalTo(Theme.class));
        Assert.assertThat(response.getBody().length,equalTo(2));
    }

    @Test
    public void TestGetThemeById(){

        System.out.println("Value Analysis: ");
        List<Theme> themes=controller.getAllThemes();
        for (Theme t:themes
             ) {
            System.out.printf("\t%s - %s\n",t.getName(),t.getDescription());
        }
        ResponseEntity<Theme> response1=restTemplate.getForEntity("http://localhost:9090/api/theme/0",Theme.class);
        Assert.assertThat(response1.getStatusCode(),equalTo(HttpStatus.OK));
        Assert.assertThat(response1.getBody().getClass(), equalTo(Theme.class));
        Assert.assertThat(response1.getBody().getName(),equalTo(theme1.getName()));
        ResponseEntity<Theme> response2=restTemplate.getForEntity("http://localhost:9090/api/theme/1",Theme.class);
        Assert.assertThat(response2.getStatusCode(),equalTo(HttpStatus.OK));
        Assert.assertThat(response2.getBody().getClass(), equalTo(Theme.class));
        Assert.assertThat(response2.getBody().getName(),equalTo(theme2.getName()));

    }

    @Test
    public void TestGetThemeByName(){
        ResponseEntity<Theme> response=restTemplate.getForEntity("http://localhost:9090/api/themes/",Theme.class);
        Assert.assertThat(response.getStatusCode(),equalTo(HttpStatus.OK));
        Assert.assertThat(response.getBody().getClass(),equalTo(Theme.class));
        Assert.assertThat(response.getBody().getDescription(),equalTo(theme1.getDescription()));
    }

    @Test
    public void TestEditTheme(){
        Theme newTheme = new Theme(theme1);
        newTheme.setName("School010");
        newTheme.setDescription("Test Theme that has been updated!");
        ResponseEntity<Theme> response = restTemplate.postForEntity("http://localhost:9090/api/theme/1",newTheme,Theme.class);
        Assert.assertThat(response.getStatusCode(),equalTo(HttpStatus.OK));
        Assert.assertThat(response.getBody().getClass(),equalTo(Theme.class));
        Assert.assertThat(response.getBody().getDescription(),equalTo("Test Theme that has been updated!"));
        Assert.assertThat(response.getBody().getName(),equalTo("School010"));
    }

    @Test
    public void TestRemoveTheme(){
        ResponseEntity<Theme> response = restTemplate.exchange("http://localhost:9090/api/theme/1", HttpMethod.DELETE,null,Theme.class);
        Assert.assertNotNull(response.getBody());
        Assert.assertThat(response.getBody().getClass(),equalTo(Theme.class));
        Assert.assertThat(response.getBody().getName(),equalTo("Industry"));
    }

    @Test
    public void TestRemoveThemeById(){
        //deleteForEntity?????
    }
}
