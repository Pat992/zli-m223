package ch.zli.m223.api19a.crm.controller.rest.user;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ch.zli.m223.api19a.crm.controller.rest.user.dto.ChangePasswordDto;
import ch.zli.m223.api19a.crm.controller.rest.user.dto.RoleInputDto;
import ch.zli.m223.api19a.crm.controller.rest.user.dto.UserDto;
import ch.zli.m223.api19a.crm.controller.rest.user.dto.UserInputDto;
import ch.zli.m223.api19a.crm.model.AppUser;
import ch.zli.m223.api19a.crm.service.UserService;

/**
 * Controller to create endpoints for users.
 * @author Patrick
 *
 */
@CrossOrigin()
@RestController
public class UserController {
	@Autowired UserService userService;

	// CRUD for Users
	
	/**
	 * Get all the existing users
	 * @return
	 */
	@GetMapping("/users")
	public List<UserDto> getAllUsers() {
		return userService.getAllUsers().stream()
			.map((AppUser user) -> {return new UserDto(user);})
			.collect(Collectors.toList());
	}
	
	/**
	 * Gets a user, specified bi its ID
	 * @param id -> ID of the User
	 * @return
	 */
	@GetMapping("/users/{id}")
	public UserDto getUserById(@PathVariable long id) {
		return new UserDto(userService.getUserById(id));
	}
	
	/**
	 * Changes the password of a specified user,
	 * will first check if the user entered the correct old password.
	 * @param id				-> ID of the user
	 * @param changePasswordDto	-> The Dto for the data required
	 */
	@PutMapping("/users/{id}/password")
	public void updatePassword(@PathVariable long id, @RequestBody ChangePasswordDto changePasswordDto) {
		userService.updatePassword(id, changePasswordDto.oldPassword, changePasswordDto.newPassword);
	}
	
	/**
	 * Completely updates the roles of the user, 
	 * old ones will be removed.
	 * @param id	-> ID of the user
	 * @param roles	-> The Dto for the data required
	 * @return
	 */
	@PutMapping("/users/{id}/roles")
	public UserDto updateRoles(@PathVariable long id, @RequestBody RoleInputDto roles) {
		return new UserDto(userService.updateRoles(id, roles.roles));
	}
	
	/**
	 * Creates a new User, 
	 * data must be identically to the UserInputDto
	 * @param user -> The Dto for the data required
	 * @return
	 */
	@PostMapping("/users")
	public UserDto createUser(@RequestBody UserInputDto user) {
		return new UserDto(userService.createUser(user.username, user.password, user.roles));
	}
	
	/**
	 * Deletes a user with the given ID
	 * @param id -> ID of the user
	 */
	@DeleteMapping("/users/{id}")
	public void deleteUser(@PathVariable long id) {
		userService.deleteUser(id);
	}
}
