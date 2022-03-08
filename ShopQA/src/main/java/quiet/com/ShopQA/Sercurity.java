package quiet.com.ShopQA;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class Sercurity extends WebSecurityConfigurerAdapter  {
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
		.antMatchers("/member/**").hasAnyRole("MEMBER").anyRequest().permitAll()
		.and().formLogin().loginPage("/login").failureUrl("/login")
		.defaultSuccessUrl("/dashboard",true).and().logout().logoutSuccessUrl("/login")
		.and().exceptionHandling().accessDeniedPage("/access-deny");
	}
//	public static void main(String[] args) {
//		BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
//		String s = bc.encode("123456");
//		System.out.println(s);
//	}
}
