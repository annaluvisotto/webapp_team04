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

        //autorizzazione
        http.authorizeHttpRequests(c ->
                c.requestMatchers("/dashboard", "/logout").hasAnyRole("ADMIN", "USER_PROVA", "USER_BASIC", "USER_PRO")
                        .requestMatchers("/profilo", "/cambio_pw", "/gestione_cambiopw", "/inserimento_recensione", "/training/completed").hasAnyRole("USER_PROVA", "USER_BASIC", "USER_PRO")
                        .requestMatchers("/dashboard_admin", "/lista_utenti", "/rimuovi_utenti", "/elimina_disabilitati","/statistiche_admin").hasRole("ADMIN")
                        .requestMatchers("/dashboard_pro", "/inserisci_programma", "/addPersTraining").hasRole("USER_PRO")
                        .requestMatchers("/statistiche_user").hasAnyRole("USER_BASIC", "USER_PRO")
                        .requestMatchers("/gestione_update").hasAnyRole("USER_PROVA", "USER_BASIC")
                        .requestMatchers("/dashboard_basic", "/upgrade_basic").hasRole("USER_BASIC")
                        .requestMatchers("/dashboard_prova", "/upgrade_prova").hasRole("USER_PROVA")
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
