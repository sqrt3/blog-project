package com.estsoft.springproject.user.config;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

import com.estsoft.springproject.user.service.UserDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurityConfiguration {

    @Bean
    public WebSecurityCustomizer ignore() {
        return webSecurity -> webSecurity.ignoring()
                //.requestMatchers(toH2Console())
                .requestMatchers("/static/**")
                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger.-ui.html");
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.authorizeHttpRequests(
                    custom -> //custom.requestMatchers("/login", "/signup", "/user").permitAll()
                            //.requestMatchers("/articles/**").hasRole("ADMIN")
                            // No Prefix
                            //.requestMatchers("/articles/**").hasAuthority("ROLE_USER")
                            custom.anyRequest().permitAll()
                )
                .formLogin(custom -> custom.loginPage("/login").defaultSuccessUrl("/articles"))
                .logout(custom -> custom.logoutSuccessUrl("/login").invalidateHttpSession(true))
//                .csrf(AbstractHttpConfigurer::disable)
                .csrf(custom -> custom.disable())
                .build();
    }

//    @Bean
//    public AuthenticationManager authenticationManager(HttpSecurity httpSecurity, BCryptPasswordEncoder bCryptPasswordEncoder, UserDetailService userDetailService) {
//        return httpSecurity.getSharedObject(AuthenticationManagerBuilder.class)
//                .userDetailsService(userDetailService)  // 8) 사용자 정보 서비스 설정
//                .passwordEncoder(bCryptPasswordEncoder)
//                .and()
//                .build();
//    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}