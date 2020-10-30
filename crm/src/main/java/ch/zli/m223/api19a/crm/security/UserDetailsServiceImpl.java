package ch.zli.m223.api19a.crm.security;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ch.zli.m223.api19a.crm.model.AppUser;
import ch.zli.m223.api19a.crm.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired UserRepository userRepository;
	
	@Override
	public AppUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		AppUser appUser = userRepository.findByName(username)
				.orElseThrow(() -> new UsernameNotFoundException(username + " not found."));
		
		return new AppUserDetails(appUser);
	}
	
	@SuppressWarnings("serial")
	public static class AppUserDetails extends User {
		private AppUser appUser;

		public AppUserDetails(AppUser appUser) {
			super(appUser.getName(), appUser.getPassword(), getAuthorities(appUser));			
			this.appUser = appUser;
		}
		
		public AppUser getAppUser() { return this.appUser; }
		
		private static List<GrantedAuthority> getAuthorities(AppUser appUser) {
			return appUser.getRoles().stream()
					.map((role) -> new SimpleGrantedAuthority(role))
					.collect(Collectors.toList());
		}
	}
}
