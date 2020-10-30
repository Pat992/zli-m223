package ch.zli.m223.api19a.crm.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.zli.m223.api19a.crm.exception.InvalidPasswordException;
import ch.zli.m223.api19a.crm.exception.NoPasswordException;
import ch.zli.m223.api19a.crm.exception.NoUserNameException;
import ch.zli.m223.api19a.crm.exception.UserAlreadyExistsException;
import ch.zli.m223.api19a.crm.exception.UserNotFoundException;
import ch.zli.m223.api19a.crm.model.AppUser;
import ch.zli.m223.api19a.crm.repository.UserRepository;
import ch.zli.m223.api19a.crm.service.UserService;

/**
 * Implements the UserService
 * @author Patrick Hettich
 *
 */
@Service
public class UserServiceImpl implements UserService{
	@Autowired private UserRepository userRepository;
	
	@Override
	public List<AppUser> getAllUsers() { 
		return new ArrayList<>(userRepository.findAll()); 
	}

	@Override
	public AppUser createUser(String name, String password, Set<String> roles) {
		
		if(name == null || name.trim().isEmpty()) { throw new NoUserNameException(); }
		
		if(password == null || password.trim().isEmpty()) {throw new NoPasswordException(); }
		
		if (roles == null) { roles = new HashSet<String>(); }
		
		this.userRepository.findByName(name).ifPresent((AppUser user) -> { throw new UserAlreadyExistsException(); });

		return this.userRepository.createUser(name, password, roles);
	}
	
	@Override
	public AppUser getUserById(long id) {
		return this.userRepository.findById(id).orElseThrow(UserNotFoundException::new);
	}
	
	@Override
	public void updatePassword(long id, String oldPassword, String newPassword) {
	 	AppUser user = getUserById(id);
	 	
	 	if(!user.verifyPassword(oldPassword)) {
	 		throw new InvalidPasswordException();
	 	}
	 	
	 	this.userRepository.changePassword(user, newPassword);
	}
	
	@Override
	public AppUser updateRoles(long id, Set<String> roles) {
		AppUser user = getUserById(id);
		if (roles == null) { roles = new HashSet<String>(); }

		return this.userRepository.changeRoles(user, roles);
	}

	@Override
	public void deleteUser(long id) {
		this.getUserById(id);
		this.userRepository.deleteById(id);
	}
}
