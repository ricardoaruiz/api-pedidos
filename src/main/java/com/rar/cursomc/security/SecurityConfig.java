package com.rar.cursomc.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.rar.cursomc.security.filter.JWTAuthenticationFilter;
import com.rar.cursomc.security.filter.JWTAuthorizationFilter;
import com.rar.cursomc.security.utils.JWTUtil;

/**
 * Spring Security generate a end point http://host/login that receive Credentials.class on pay load *
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private Environment env;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private JWTUtil jwtUtil;
	
	public static final String[] PUBLIC_MATCHERS = { "/h2-console/**" };

	public static final String[] PUBLIC_MATCHERS_GET = { "/produtos/**", "/categorias/**" };
	
	public static final String[] PUBLIC_MATCHERS_POST = { "/clientes/**", "/auth/forgot/**" };

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		// For H2 console access
		if (Arrays.asList(env.getActiveProfiles()).contains("test")) {
			http.headers().frameOptions().disable();
		}

		/**
		 * Config CORS and CSRF
		 */
		http.cors()
			.and()
			.csrf().disable();

		/**
		 * Set urls in security control
		 */
		http.authorizeRequests()
			.antMatchers(HttpMethod.GET, PUBLIC_MATCHERS_GET).permitAll()
			.antMatchers(HttpMethod.POST, PUBLIC_MATCHERS_POST).permitAll()
			.antMatchers(PUBLIC_MATCHERS).permitAll()
			.anyRequest().authenticated();

		// Add filter for authentication
		http.addFilter(new JWTAuthenticationFilter(authenticationManager(), jwtUtil));
		
		// Add filter for authorization
		http.addFilter(new JWTAuthorizationFilter(authenticationManager(), jwtUtil, userDetailsService));
		
		// Add policy for stateless session
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}
	
	/**
	 * Config Spring Security (UserDetailService and Password encoder) for authentication
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(this.bCryptPasswordEncoder());
	}

	/**
	 * Configure Cors origin
	 * @return CorsConfigurationSource
	 */
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
		return source;
	}

	/**
	 * Password encoder
	 * @return BCryptPasswordEncoder
	 */
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
