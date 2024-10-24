package com.example.demo.configs;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.data.mongodb.core.MongoClientFactoryBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.example.demo.security.SecureFilter;
import com.mongodb.ConnectionString;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class AppConfig {

    @Autowired
    private DeniedHandler deniedHandler;

    @Autowired
    private SecureFilter secureFilter;

    @Autowired
    private UserDetailsService userDetailsService;
    
    @Bean
    public MongoClientFactoryBean mongo(@Value("${spring.data.mongodb.uri}") String uri) {
        MongoClientFactoryBean mongo = new MongoClientFactoryBean();
        ConnectionString conn = new ConnectionString(uri);
        mongo.setConnectionString(conn);
        return mongo;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) 
    throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        return authenticationProvider;
    }

    // @Bean
    // public SecurityFilterChain permit(HttpSecurity http) throws Exception {
    //     http
    //         .csrf(c -> c.disable())
    //         .cors( cors -> cors.disable())
    //         .authorizeHttpRequests(aut -> 
    //             aut.anyRequest().permitAll()
    //         )
    //         .sessionManagement(session -> 
    //             session
    //                 .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
    //         );
            
    //     return http.build();
    // }

    @Bean
    public SecurityFilterChain apiSecurityFilterChain(HttpSecurity http) throws Exception {
        http
            .securityMatcher("/api/**")
            .csrf(c -> c.disable())
            .cors( cors -> cors.disable())
            .authorizeHttpRequests(aut -> 
                aut.requestMatchers("/api/login","/api/ws/* *", "/api/ws").permitAll()
                    .anyRequest().authenticated()
            )
            .sessionManagement(session -> 
                session
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .addFilterBefore(secureFilter, UsernamePasswordAuthenticationFilter.class);
            
        return http.build();
    }
    @Bean
	public FilterRegistrationBean<?> simpleCorsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		// * URL below needs to match the Vue client URL and port *s
		config.setAllowedOriginPatterns(Collections.singletonList("*"));
		config.setAllowedMethods(Collections.singletonList("*"));
		config.setAllowedHeaders(Collections.singletonList("*"));
		source.registerCorsConfiguration("/**", config);
		FilterRegistrationBean<?> bean = new FilterRegistrationBean<>(new CorsFilter(source));
		bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
		return bean;
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
