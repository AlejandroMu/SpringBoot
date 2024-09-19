package com.example.demo.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoClientFactoryBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import com.mongodb.ConnectionString;

@Configuration
@EnableWebSecurity
public class AppConfig {
    
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

    // @Bean
    // public SecurityFilterChain securityFilterChain(HttpSecurity security)throws Exception{
    //     security.
    //         csrf(c -> c.disable()).
    //         authorizeHttpRequests(aut -> 
    //         aut.requestMatchers("/login", "/signUp").permitAll()
    //         .anyRequest().authenticated()
    //     ).formLogin(cus -> 
    //             cus.loginPage("/login")
    //             .successForwardUrl("/")
    //             .permitAll()
    //     );
    //     return security.build();
    // }

    // public AuthenticationManager authenticationManager(
	// 		UserDetailsService userDetailsService,
	// 		PasswordEncoder passwordEncoder) {
	// 	DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
	// 	authenticationProvider.setUserDetailsService(userDetailsService);
	// 	authenticationProvider.setPasswordEncoder(passwordEncoder);

	// 	ProviderManager providerManager = new ProviderManager(authenticationProvider);
	// 	providerManager.setEraseCredentialsAfterAuthentication(false);

	// 	return providerManager;
	// }

}
