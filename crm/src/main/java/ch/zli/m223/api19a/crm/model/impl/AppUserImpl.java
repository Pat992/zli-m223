package ch.zli.m223.api19a.crm.model.impl;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import ch.zli.m223.api19a.crm.model.AppUser;

/**
 * Implementation for AppUser
 * @author Patrick Hettich
 *
 */
@Entity(name="AppUser")
public class AppUserImpl implements AppUser {

	/**
	 * private Attributes for AppUserImpl
	 */
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(unique=true)
	private String name;
	private String passwordHash;
	
	@ElementCollection(fetch=FetchType.EAGER)
	private Set<String> roles;
	
	protected AppUserImpl() {} // For Jpa
	
	/**
	 * ctor
	 * @param name
	 * @param password
	 * @param roles
	 */
	public AppUserImpl(String name, String password, Set<String> roles) {
		this.name = name;
		this.setPassword(password);
		this.setRoles(roles);
	}

	/**
	 * Getter
	 */
	@Override
	public Long getId() { return this.id; }

	@Override
	public String getName() { return this.name; }

	@Override
	public String getPassword() { return this.passwordHash; }

	@Override
	public Set<String> getRoles() { return this.roles; }

	/**
	 * Creates a password-hash
	 * @param password
	 * @return
	 */
	public AppUserImpl setPassword(String password) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		this.passwordHash = encoder.encode(password);
		return this;
	}
	
	/**
	 * Replaces the roles of the AppUser with new ones
	 * @param roles
	 * @return
	 */
	public AppUserImpl setRoles(Set<String> roles) {
		this.roles = new HashSet<String>();
		this.roles.clear();
		for(String role : roles) { this.roles.add(role); }
		
		return this;
	}

	/**
	 * Check if user entered the correct Password.
	 * Compares the two hashes
	 */
	@Override
	public boolean verifyPassword(String oldPassword) {
		return new BCryptPasswordEncoder().matches(oldPassword, this.passwordHash);
	}
}
