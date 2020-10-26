package ch.zli.m223.api19a.crm.security;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import ch.zli.m223.api19a.crm.roles.Role;
import ch.zli.m223.api19a.crm.security.util.WebSecurityChecker;

@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
	private UserDetailsService userDetailsService;
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public WebSecurityConfiguration(
			UserDetailsServiceImpl userDetailsService,
			BCryptPasswordEncoder bCryptPasswordEncoder) 
	{
		this.userDetailsService = userDetailsService;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable()
			.authorizeRequests()
			.antMatchers("/users").hasAnyAuthority(Role.ADMIN)
			.antMatchers("/users/*/roles").hasAnyAuthority(Role.ADMIN)
			.antMatchers("/customers/**").permitAll() 
			.anyRequest().authenticated()
		
			.and()
			//TODO: .addFilter(new JwtAuthenticationFilter())
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); 
	}
	
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception{
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
	}
	
	  @Bean
	  CorsConfigurationSource corsConfigurationSource() {
	    final CorsConfiguration corsConfiguration = new CorsConfiguration();
	    corsConfiguration.applyPermitDefaultValues(); // GET & POST
	    corsConfiguration.addAllowedMethod(HttpMethod.PUT);
	    corsConfiguration.addAllowedMethod(HttpMethod.PATCH);
	    corsConfiguration.addAllowedMethod(HttpMethod.DELETE);
	    
	    final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	    source.registerCorsConfiguration("/**", corsConfiguration);
	    return source;
	  }
	  
	  @Bean
	  WebSecurityChecker webSecurityChecker() {
		  return new WebSecurityChecker();
	  }
}
