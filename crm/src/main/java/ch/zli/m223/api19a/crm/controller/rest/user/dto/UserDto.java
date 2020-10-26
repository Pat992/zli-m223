package ch.zli.m223.api19a.crm.controller.rest.user.dto;

import java.util.Set;

import ch.zli.m223.api19a.crm.model.AppUser;

public class UserDto {
	public Long id;
	public String username;
	public Set<String> roles;
	
	public UserDto() {}
	
	public UserDto(AppUser user) {
		id = user.getId();
		username = user.getName();
		roles = user.getRoles();
	}
}
