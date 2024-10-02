package dio.webapi.config;

import dio.webapi.service.SecurityDatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)

public class WebSecurityConfig {

   @Autowired
   SecurityDatabaseService securityDatabaseService;

//   @Autowired
//   public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {
//      auth.userDetailsService(securityDatabaseService).passwordEncoder(passwordEncoder());
//   }

   @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
       http
               .csrf(AbstractHttpConfigurer::disable)
               .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
               .authorizeHttpRequests(auth ->
           auth
             .requestMatchers("/").permitAll()
             .requestMatchers("/login").permitAll()
             .requestMatchers("/wellcome").permitAll()
             .requestMatchers("/wellcome/home").permitAll()
             .requestMatchers("/wellcome/admin").hasRole("MANAGER")
             .requestMatchers("/wellcome/user").hasAnyRole("USER", "MANAGER")
             .requestMatchers("/users/**").hasRole("MANAGER")
             .requestMatchers("/h2/**").permitAll()
             .anyRequest().authenticated())
             .formLogin(fl -> fl.defaultSuccessUrl("/wellcome/home", true))
             .userDetailsService(securityDatabaseService)
             .httpBasic(Customizer.withDefaults());
/* configuração para exibir a tela de login
           .formLogin(Customizer.withDefaults())
               .formLogin(fl -> fl.defaultSuccessUrl("/wellcome/home", true));
*/
       return http.build();
   }


/* Configuração para criar usuários em memória */
   /*
   @Bean
    UserDetailsService userDetailsService() {
       InMemoryUserDetailsManager usersManager = new InMemoryUserDetailsManager();
       usersManager.createUser(User.withUsername("user")
               .password(passwordEncoder().encode("user123"))
               .roles("USERS")
               .build()
       );
       usersManager.createUser(User.withUsername("admin")
               .password(passwordEncoder().encode("admin123"))
               .roles("ADMINS")
               .build()
       );

       return usersManager;
   }*/

   @Bean
    public PasswordEncoder passwordEncoder() {
       return new BCryptPasswordEncoder();
   }
}
