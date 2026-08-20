package it.unitn.disi.webapp_team04.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@ComponentScan("it.unitn.disi.webapp_team04")
public class SecurityConfig{

    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource) {
        return new JdbcUserDetailsManager(dataSource);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http)
            throws Exception {

        //autenticazione
        http.formLogin(c ->
                c.loginPage("/login")
                        .defaultSuccessUrl("/dashboard")
                        .failureForwardUrl("/login_failure")
        );

        //autorizzazione --> da modificare man mano, questo è l'esempio della prof
        http.authorizeHttpRequests(c ->
                c.requestMatchers("/dashboard").hasAnyRole("ADMIN", "USER_PROVA", "USER_BASIC", "USER_PRO")
                        .requestMatchers("/userDashboard").hasRole( "USER")
                        .requestMatchers("/compute").hasRole("USER")
                        .requestMatchers("/datetime").hasRole("USER")
                        .requestMatchers("/externalDateTime").hasRole( "USER")
                        .requestMatchers("/adminDashboard").hasRole("ADMIN")
                        .requestMatchers("/getUsers").hasRole("ADMIN")
                        .requestMatchers("/getSequence").hasRole("USER")
                        .anyRequest().permitAll()
        );

        //logout
        http.logout(c ->
                c.logoutUrl("/perform_logout") //default
                        .logoutSuccessUrl("/logout") //usa una get mapping nel controller relativo
        );

        http.csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }
}
