package br.edu.ifrn.crud.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.edu.ifrn.crud.dominio.Usuario;
import br.edu.ifrn.crud.service.UsuarioService;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private UsuarioService service;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		//http.authorizeRequests().antMatchers("/").permitAll();
		
		http.authorizeRequests()
			.antMatchers("/css/**,/imagens/**,/js/**").permitAll()
			.antMatchers("/publico/**").permitAll()
			
			.antMatchers("/usuarios/salvar","/usuarios/editar/**",
					"/usuaios/remover/**").hasAuthority(Usuario.ADMIN)
			
			.anyRequest().authenticated() 
			.and() 
				.formLogin()
				.loginPage("/login")
				.defaultSuccessUrl("/",true) 
				.failureUrl("/login-error") 
				.permitAll()
			.and()
				.logout()
				.logoutUrl("/logout") 
				.logoutSuccessUrl("/") 
			.and()
				.rememberMe(); 
				
		
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		auth.userDetailsService(service).passwordEncoder(new BCryptPasswordEncoder());
	}
	
}

