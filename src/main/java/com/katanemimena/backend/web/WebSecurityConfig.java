package com.katanemimena.backend.web;

import com.katanemimena.backend.authorized.AuthorizedUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthorizedUserService authorizedUserService;
    @Autowired
    private LoginHandler loginHandler;

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
        http
                .csrf().disable()
                .authorizeRequests()
                .anyRequest()
                .permitAll();


//                .csrf().disable()
//                .authorizeRequests()
//                .antMatchers("/admin.html").hasRole("ADMIN")
//                .antMatchers("/secretary.html").hasRole("SECRETARY")
//                .antMatchers("/officer.html").hasRole("OFFICER")
//                .antMatchers("/citizen.html").permitAll()
//                .antMatchers("/api/v1/citizen/**").permitAll()
//                .antMatchers("/api/v1/form/**").permitAll()
//                .and()
//                .formLogin()
//                .loginPage("/login")
//                .successHandler(loginHandler)
//                .permitAll();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**", "/icon/**");
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
