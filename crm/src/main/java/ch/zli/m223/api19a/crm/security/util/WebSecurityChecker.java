package ch.zli.m223.api19a.crm.security.util;

import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import ch.zli.m223.api19a.crm.model.AppUser;
import ch.zli.m223.api19a.crm.roles.Role;

public class WebSecurityChecker {

	/**
	 * Check if access is allowed.
	 * @param authentication should have an AppUser in his credentials
	 * @param id the users id in this request
	 * @return true if we have Role.ADMIN or we are a user and our id is equal to the given id
	 */
	public boolean checkForAdminOrUserWithId(Authentication authentication, long id) {
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		if (authorities == null) return false;
		
		// Administrators always allowed
		for (GrantedAuthority authority :authorities) {
			if (authority.getAuthority().equals(Role.ADMIN)) return true;
		}
		
		try { // May not be an AppUser or in may be null
			AppUser appUser = (AppUser)authentication.getCredentials();
			
			// Users allowed if there IDs are equal
			for (GrantedAuthority authority :authorities) {
				if (authority.getAuthority().equals(Role.USER) && appUser.getId() == id) {
					return true;
				}
			}
		} catch(Exception e) { /*do nothing */ }
		return false;
	}
}