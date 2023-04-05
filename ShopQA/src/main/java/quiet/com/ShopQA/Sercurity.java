package quiet.com.ShopQA;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import quiet.com.ShopQA.service.impl.CustomOAuth2User;
import quiet.com.ShopQA.service.impl.CustomOAuth2UserService;
import quiet.com.ShopQA.service.impl.UserOAuth2Service;
import quiet.com.ShopQA.sercurity.JwtAuthenticationFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
public class Sercurity extends WebSecurityConfigurerAdapter  {

	@Bean
	public JwtAuthenticationFilter jwtAuthenticationFilter() {

		return new JwtAuthenticationFilter();
	}

	@Bean(BeanIds.AUTHENTICATION_MANAGER)
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		// Get AuthenticationManager bean
		return super.authenticationManagerBean();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}


	@Autowired
	private CustomOAuth2UserService oauth2UserService;

	@Autowired
	private UserOAuth2Service userOAuth2Service;


	@Autowired
	UserDetailsService userDetailsService;
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
	}
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests()
		.antMatchers("/admin/**").hasAnyRole("ADMIN")
		.antMatchers("/manager/**").hasAnyRole("MANAGER")
		.antMatchers("/member/**").hasAnyRole("MEMBER")
				.anyRequest().permitAll()
		.and().formLogin().loginPage("/login").failureUrl("/login")
		.defaultSuccessUrl("/dashboard",true)
				.and().logout().logoutSuccessUrl("/login")
		.and().exceptionHandling().accessDeniedPage("/access-deny")
				.and().headers().frameOptions().disable();



		http.oauth2Login()
				.loginPage("/login")
				.userInfoEndpoint()
				.userService(oauth2UserService)
				.and()
				.successHandler(new AuthenticationSuccessHandler() {

					@Override
					public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
						CustomOAuth2User oauthUser = (CustomOAuth2User) authentication.getPrincipal();
						if(((CustomOAuth2User) authentication.getPrincipal()).getOauth2ClientName().equalsIgnoreCase("Facebook")) {
							userOAuth2Service.processOAuthPostLoginFaceBook(oauthUser.getEmail(), oauthUser.getName());
						}
						if(((CustomOAuth2User) authentication.getPrincipal()).getOauth2ClientName().equalsIgnoreCase("Google")) {
							userOAuth2Service.processOAuthPostLoginGoogle(oauthUser.getEmail(), oauthUser.getName());
						}

						response.sendRedirect("/trangchu");
					}


				});
	}
//	public static void main(String[] args) {
//		BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
//		String s = bc.encode("123456");
//		System.out.println(s);
//	}
}
