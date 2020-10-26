package ch.zli.m223.api19a.crm.security.jwt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import ch.zli.m223.api19a.crm.controller.rest.user.dto.UserDto;
import ch.zli.m223.api19a.crm.controller.rest.user.dto.UserInputDto;
import ch.zli.m223.api19a.crm.security.UserDetailsServiceImpl;
import ch.zli.m223.api19a.crm.security.UserDetailsServiceImpl.AppUserDetails;

public class JwtAuthenticationFilter
extends UsernamePasswordAuthenticationFilter
implements JwtSecurityConstants
{
private UserDetailsServiceImpl userDetailsServiceImpl;
private AuthenticationManager authenticationManager;

public JwtAuthenticationFilter(
	AuthenticationManager authenticationManager, 
	UserDetailsServiceImpl userDetailsServiceImpl)
{
  this.authenticationManager = authenticationManager;
  this.userDetailsServiceImpl = userDetailsServiceImpl;
}

@Override
public Authentication attemptAuthentication(
	HttpServletRequest req,
	HttpServletResponse res) throws AuthenticationException
{
	try {
		// Fill in our UserInputDto from the JSON data
		UserInputDto userInputDto = new ObjectMapper()
	            .readValue(req.getInputStream(), UserInputDto.class);
    
    return authenticationManager.authenticate(
    		// Create an authentication token and try to authenticate with it
      new UsernamePasswordAuthenticationToken(
      		userInputDto.username,
      		userInputDto.password,
              new ArrayList<>())
    );
  } catch (IOException e) {
      throw new RuntimeException(e);
  }
}

@Override
protected void successfulAuthentication(
		HttpServletRequest req,
      HttpServletResponse res,
      FilterChain chain,
      Authentication auth
		) throws IOException, ServletException
{
	// Get the AppUser and use it as the token content
	String userName = ((User) auth.getPrincipal()).getUsername();
	AppUserDetails appUserDetails = userDetailsServiceImpl.loadUserByUsername(userName);
	
	UserDto user = new UserDto(appUserDetails.getAppUser());
	String token = JWT.create()
            .withSubject(new ObjectMapper().writeValueAsString(user))
            .withExpiresAt(new Date(System.currentTimeMillis() + JwtSecurityConstants.EXPIRATION_TIME))
            .sign(Algorithm.HMAC512(JwtSecurityConstants.SECRET.getBytes()));
	addJwtToken(token, res);
}

private void addJwtToken(String token, HttpServletResponse response) {
	response.addHeader(AUTHORISATION_HEADER, TOKEN_PREFIX + token);
	try {
		response.getWriter().println(String.format(
				"{\n\t\"%s\": \"%s\"\n}",
				AUTHORISATION_HEADER, TOKEN_PREFIX + token)
		);
	} catch (IOException e) { /* do nothing */ }
}
}
