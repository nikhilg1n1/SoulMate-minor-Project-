package com.soulmate.config;

import com.soulmate.Services.CustomUserService;
import com.soulmate.Services.LoginUserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final LoginUserService loginUserService;
    @Lazy
    public SecurityConfig( LoginUserService loginUserService) {
        this.loginUserService = loginUserService;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            http.csrf(AbstractHttpConfigurer::disable).cors(CorsConfigurer::disable);
            http.authorizeHttpRequests(auth ->
                    auth.requestMatchers("/css/**", "/image/**", "/video/**", "/js/**", "/**","/login","/register","/home").permitAll()
                            .requestMatchers("/profile").authenticated()
                            .anyRequest().authenticated());
            http.formLogin(form -> form.loginPage("/form").loginProcessingUrl("/login").usernameParameter("email").defaultSuccessUrl("/home").failureUrl("/form?error=true"));
            http.logout(form-> form.logoutUrl("/logout").logoutSuccessUrl("/home"));

            return http.build();
        }
        @Bean
        @Lazy
        public UserDetailsService userDetailsService(){
        return  loginUserService;
        }
        @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider= new DaoAuthenticationProvider();
        provider.setUserDetailsService( loginUserService);
        provider.setPasswordEncoder(passwordEncoder());
        return  provider;
    }


}

