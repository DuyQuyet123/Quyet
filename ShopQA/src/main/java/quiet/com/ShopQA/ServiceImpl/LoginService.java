package quiet.com.ShopQA.ServiceImpl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import quiet.com.ShopQA.DTO.UserPrincipal;
import quiet.com.ShopQA.Entity.UserEntity;
import quiet.com.ShopQA.Repostory.UserRepository;

@Service
@Transactional
public class LoginService implements UserDetailsService {
	@Autowired
	private UserRepository userRepository;

	// @Override
	// public UserDetails loadUserByUsername(String username) throws
	// UsernameNotFoundException {
	// UserEntity u = userRepository.search(username);
	// if(u == null) {
	// throw new UsernameNotFoundException("Not found");
	// }
	// convert role sang security
	// String role = u.getRole();//vai tro

	// List<SimpleGrantedAuthority> authorities = new
	// ArrayList<SimpleGrantedAuthority>();

	// authorities.add(new SimpleGrantedAuthority(role));

	/// covnert user model cua security
	// User currentUser = new User(username, u.getPassword(), authorities);

	// return (UserDetails) currentUser;
	// }
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity user = userRepository.search(username);
		if (user == null) {
			throw new UsernameNotFoundException("not found");
		}

		String role = user.getRole();
		System.out.println(role);
		List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority(user.getRole()));

		UserPrincipal userPrincipal = new UserPrincipal(user.getUsername(), user.getPassword(), user.getEnabled(), true,
				true, true, authorities);

		userPrincipal.setId(user.getId());
		userPrincipal.setName(user.getName());
		userPrincipal.setRole(user.getRole());
		userPrincipal.setPhone(user.getPhone());
		userPrincipal.setEmail(user.getEmail());

		return userPrincipal;
	}

}
