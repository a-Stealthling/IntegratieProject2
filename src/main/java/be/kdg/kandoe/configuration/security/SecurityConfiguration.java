package be.kdg.kandoe.configuration.security;

import be.kdg.kandoe.security.TokenHelper;
import be.kdg.kandoe.security.auth.RestAuthenticationEntryPoint;
import be.kdg.kandoe.security.auth.TokenAuthenticationFilter;
import be.kdg.kandoe.service.implementation.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {


    @Autowired
    private CustomUserDetailsService jwtUserDetailsService;

    @Autowired
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;


    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.
                userDetailsService(jwtUserDetailsService).
                passwordEncoder(passwordEncoder());
    }

    @Autowired
    private TokenHelper tokenHelper;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http

                .cors().and()
                .sessionManagement().sessionCreationPolicy( SessionCreationPolicy.STATELESS).and()
                .exceptionHandling().authenticationEntryPoint( restAuthenticationEntryPoint).and()
                .authorizeRequests()
                .antMatchers("/api/public/**").permitAll()
                .antMatchers("/api/private/**").authenticated()
                .anyRequest().authenticated().and()
                //.addFilterBefore(new be.kdg.kandoe.configuration.cors.CorsFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new TokenAuthenticationFilter(tokenHelper, jwtUserDetailsService), UsernamePasswordAuthenticationFilter.class)
                .csrf().disable();

    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // TokenAuthenticationFilter will ignore the below paths
        web.ignoring().antMatchers(
                HttpMethod.POST,
                "/api/public/**"
        );
        web.ignoring().antMatchers(
                HttpMethod.GET, "/api/public/**"
        );
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource(){
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }




//    public static class ApiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
//
//        protected void configure(HttpSecurity http) throws Exception {
//
//            //OLD
////                http.authorizeRequests()
////                        .antMatchers("/api/public/**").permitAll()
//////                        .antMatchers("/api/private/**").authenticated()
//////                        .antMatchers("/api/admin/**").authenticated()
////                            .and()
////                            .csrf()
////                            .disable();
//        }
//    }
}
