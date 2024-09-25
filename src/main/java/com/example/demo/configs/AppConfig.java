package com.example.demo.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoClientFactoryBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import com.mongodb.ConnectionString;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class AppConfig {

    @Autowired
    private DeniedHandler deniedHandler;
    
    @Bean
    public MongoClientFactoryBean mongo(@Value("${spring.data.mongodb.uri}") String uri) {
        MongoClientFactoryBean mongo = new MongoClientFactoryBean();
        ConnectionString conn = new ConnectionString(uri);
        mongo.setConnectionString(conn);
        return mongo;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity security)throws Exception{
        security.
            authorizeHttpRequests(aut -> 
                aut
                    .requestMatchers("/**.css").permitAll()
                    .anyRequest().authenticated()
        ).formLogin(cus -> 
                cus
                    .loginPage("/auth/login")
                    .successForwardUrl("/auth/success")
                    .permitAll()
        ).logout(logout -> 
                logout
                    .logoutUrl("/auth/logout")
                    .logoutSuccessUrl("/auth/login")
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID")
                    .permitAll()
        ).exceptionHandling(ex -> 
                    ex
                        .accessDeniedHandler(deniedHandler)
        );
        return security.build();
    }


}
