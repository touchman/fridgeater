package com.zhdanovich.fridgeater.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                //.antMatchers("/login").permitAll()
                //.antMatchers("/product/*").authenticated()
                //.antMatchers("/recipe/*").authenticated()
                .anyRequest().authenticated()
                .and()
                .logout()
                .permitAll()
                .and().httpBasic();
    }

    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        final UserDetails user =
                User.withDefaultPasswordEncoder()
                        .username("u")
                        .password("p")
                        .roles("USER")
                        .build();

        return new InMemoryUserDetailsManager(user);
    }
}