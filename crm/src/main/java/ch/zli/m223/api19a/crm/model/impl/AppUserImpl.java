package ch.zli.m223.api19a.crm.model.impl;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import ch.zli.m223.api19a.crm.model.AppUser;

@Entity(name="AppUser")
public class AppUserImpl implements AppUser {

	@Id
	@GeneratedValue
	private Long id;
	
	@Column(unique=true)
	private String name;
	private String passwordHash;
	
	@ElementCollection(fetch=FetchType.EAGER)
	private Set<String> roles;
	
	protected AppUserImpl() {} // For Jpa
	
	public AppUserImpl(String name, String password, Set<String> roles) {
		this.name = name;
		this.setPassword(password);
		this.setRoles(roles);
	}

	@Override
	public Long getId() { return this.id; }

	@Override
	public String getName() { return this.name; }

	@Override
	public String getPassword() { return this.passwordHash; }

	@Override
	public Set<String> getRoles() { return this.roles; }

	public AppUserImpl setPassword(String password) {
		// TODO encrypt password
		this.passwordHash = password;
		return this;
	}
	
	public AppUserImpl setRoles(Set<String> roles) {
		this.roles = new HashSet<String>();
		this.roles.clear();
		for(String role : roles) { this.roles.add(role); }
		
		return this;
	}

	@Override
	public boolean verifyPassword(String oldPassword) {
		// TODO should compare hash
		return this.passwordHash.equals(oldPassword);
	}
}
