package com.chhatrola.springsecurity.securitybasicssolution.securityconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.DigestAuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.DigestAuthenticationFilter;

/**
 * Created by niv214 on 13/5/20.
 */
@Configuration
@Order(1)
public class AdminSecurityConfiguration extends WebSecurityConfiguration{

    private DigestAuthenticationEntryPoint getDigentEnrtyPoint(){
        DigestAuthenticationEntryPoint digestAuthenticationEntryPoint = new DigestAuthenticationEntryPoint();
        digestAuthenticationEntryPoint.setRealmName("admin-digest-realm");  // realm will be set here.
        digestAuthenticationEntryPoint.setKey("somedigestkey");  // nonce will be generated using this key.
        return digestAuthenticationEntryPoint;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("NiravC").password("CNirav").roles("USER")
                .and()
                .withUser("admin").password("adminp").roles("ADMIN");
    }

    @Bean
    public UserDetailsService userDetailsServiceBean() throws Exception {
        return super.userDetailsServiceBean();
    }

    private DigestAuthenticationFilter getDigestFilter(){
        DigestAuthenticationFilter digestAuthenticationFilter = new DigestAuthenticationFilter();
        digestAuthenticationFilter.setUserDetailsService(userDetailsService());
        digestAuthenticationFilter.setAuthenticationEntryPoint(getDigentEnrtyPoint());
        return digestAuthenticationFilter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.antMatcher("/admin/**")
                .addFilter(getDigestFilter())
            .exceptionHandling()
                .authenticationEntryPoint(getDigentEnrtyPoint())
            .and()
                .authorizeRequests()
                .antMatchers("/admin/**")
                .hasRole("ADMIN");
    }
}
