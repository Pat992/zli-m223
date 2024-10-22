package ch.zli.m223.api19a.crm.model;

import java.util.Set;

public interface AppUser {
	Long getId();
	String getName();
	String getPassword();
	Set<String> getRoles();
	boolean verifyPassword(String oldPassword);
}
