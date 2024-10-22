package ch.zli.m223.api19a.crm.service;

import java.util.List;
import java.util.Set;

import ch.zli.m223.api19a.crm.model.AppUser;

public interface UserService {

	List<AppUser> getAllUsers();

	AppUser createUser(String name, String password, Set<String> roles);

	void deleteUser(long id);

	AppUser getUserById(long id);

	void updatePassword(long id, String oldPassword, String newPassword);

	AppUser updateRoles(long id, Set<String> roles);
}
