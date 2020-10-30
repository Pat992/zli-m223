package ch.zli.m223.api19a.crm.repository;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import ch.zli.m223.api19a.crm.model.AppUser;
import ch.zli.m223.api19a.crm.model.impl.AppUserImpl;

/**
 * Repository for the User, extends the JPA-repository.
 * @author Patrick Hettich
 *
 */
public interface UserRepository extends JpaRepository<AppUserImpl, Long> {

	/**
	 * Creates a new AppUser
	 * @param name
	 * @param password
	 * @param roles
	 * @return
	 */
	default AppUser createUser(String name, String password, Set<String> roles) {
		AppUserImpl user = new AppUserImpl(name, password, roles);
		return save(user);
	}

	/**
	 * Possibility to find a user by its name.
	 * @param name
	 * @return
	 */
	Optional<AppUser> findByName(String name);

	/**
	 * Changes the password of an existing user
	 * @param user
	 * @param newPassword
	 */
	default void changePassword(AppUser user, String newPassword) {
		save(((AppUserImpl)user).setPassword(newPassword));
	}

	/**
	 * Changes the roles of an existing user.
	 * @param user
	 * @param roles
	 * @return
	 */
	default AppUser changeRoles(AppUser user, Set<String> roles) {
		return save(((AppUserImpl)user).setRoles(roles));
	}
}
