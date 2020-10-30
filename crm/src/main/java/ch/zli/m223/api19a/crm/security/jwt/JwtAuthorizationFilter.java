package ch.zli.m223.api19a.crm.security.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ch.zli.m223.api19a.crm.controller.rest.user.dto.UserInputDto;
import ch.zli.m223.api19a.crm.security.UserDetailsServiceImpl;
import ch.zli.m223.api19a.crm.security.UserDetailsServiceImpl.AppUserDetails;

public class JwtAuthorizationFilter 
extends BasicAuthenticationFilter
implements JwtSecurityConstants
{
UserDetailsServiceImpl userDetailsServiceImpl;

public JwtAuthorizationFilter(AuthenticationManager authManager, UserDetailsServiceImpl userDetailsServiceImp) {
	super(authManager);
	this.userDetailsServiceImpl = userDetailsServiceImp;
}

@Override
protected void doFilterInternal(
		HttpServletRequest req, 
		HttpServletResponse res,
		FilterChain chain)
		throws IOException, ServletException
{
	String jwtString = req.getHeader(AUTHORISATION_HEADER);

	// If there is no JWT header go on with filtering
	if (jwtString == null || !jwtString.startsWith(TOKEN_PREFIX)) {
		chain.doFilter(req, res);
		return;
	}

	// Create an authentication and install it
	SecurityContextHolder.getContext()
		.setAuthentication(getAuthentication(jwtString));
	
	// Go on with filtering
	chain.doFilter(req, res);
}

/**
 * @param jwtString a candidate for a JWT token 
 * @return a UsernamePasswordAuthenticationToken or null on failure
 */
private UsernamePasswordAuthenticationToken getAuthentication(String jwtString)
{
	if (jwtString == null) { return null; }

	try { // Try to decode and verify the token.
		String payload = 
				JWT.require(Algorithm.HMAC512(SECRET.getBytes()))
					.build().verify(jwtString.replace(TOKEN_PREFIX, ""))
					.getSubject();

		if (payload == null) { return null; }
		
		try { // Try to build an UsernamePasswordAuthenticationToken
			
			// The token was build from a UserInputDto. Extract user name from it.
			String username = new ObjectMapper().readValue(payload, UserInputDto.class).username;
			
			// Get the users authorities to verify its roles later
			// Needs a call to the data store, authorities are actual
			AppUserDetails appUserDetails = userDetailsServiceImpl.loadUserByUsername(username);
		
			// Return the user and its authorities
		return new UsernamePasswordAuthenticationToken(
				username,
				appUserDetails.getAppUser(),
				appUserDetails.getAuthorities()
			);
		
		} catch (UsernameNotFoundException | JsonProcessingException e) {
			return null;
		}
		
	} catch (TokenExpiredException e) {
		// TODO: handle expired token
	}
	return null;
}
}
