package br.edu.unoesc.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }
     
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
     
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
         
        return authProvider;
    }
 
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .authorizeRequests()
            	.antMatchers("/", "/usuario/cadastro", "/usuario/novo", "/static/**").permitAll()
                .antMatchers("/usuario/**").hasAnyAuthority("ADMIN")
                
                .antMatchers("/curso/new", "/curso/edit/**", "/curso/delete/**").hasAnyAuthority("ADMIN")
                .antMatchers("/curso/**").hasAnyAuthority("ADMIN", "PROFESSOR", "ALUNO")
                
                .antMatchers("/disciplina/new", "/disciplina/edit/**", "/disciplina/delete/**").hasAnyAuthority("ADMIN")
                .antMatchers("/disciplina/**").hasAnyAuthority("ADMIN", "PROFESSOR")
                
                .antMatchers("/inscricao/**").hasAnyAuthority("ADMIN","ALUNO")
                .anyRequest().authenticated()
            .and()
                .formLogin()
                .loginPage("/login")
                .usernameParameter("usuario")
                .passwordParameter("senha")
                .defaultSuccessUrl("/home/index").permitAll()
            .and()
                .logout().permitAll();

    }

}
