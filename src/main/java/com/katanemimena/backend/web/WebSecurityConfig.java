package com.katanemimena.backend.web;

import com.katanemimena.backend.authorized.AuthorizedUserRepository;
import com.katanemimena.backend.authorized.AuthorizedUserService;
import com.katanemimena.backend.jwt.CustomAuthenticationFilter;
import com.katanemimena.backend.jwt.CustomAuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import javax.sql.DataSource;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    DataSource dataSource;
    @Autowired
    private AuthorizedUserService authorizedUserService;
    @Autowired
    private AuthorizedUserRepository authorizedUserRepository;



    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
         auth.userDetailsService(authorizedUserService).passwordEncoder(passwordEncoder());
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable();
        http.httpBasic().disable()
                .cors().configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues());
        http.authorizeRequests().antMatchers("/login").permitAll();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests().antMatchers("/api/v1/admin/**").hasAuthority("ROLE_ADMIN");
        http.authorizeRequests().antMatchers("/api/v1/form/secretary/**").hasAuthority("ROLE_SECRETARY");
        http.authorizeRequests().antMatchers("/api/v1/form/officer").hasAuthority("ROLE_OFFICER");
        http.authorizeRequests().antMatchers("/api/v1/form/addForm").permitAll();
        http.authorizeRequests().antMatchers("/api/v1/form/**").hasAnyAuthority("ROLE_OFFICER", "ROLE_SECRETARY");
        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);

        http.addFilter(new CustomAuthenticationFilter(authenticationManagerBean(), authorizedUserRepository));

    }

    @Override
    public void configure(WebSecurity web) {
        web
                .ignoring()
                .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**", "/icon/**");
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
