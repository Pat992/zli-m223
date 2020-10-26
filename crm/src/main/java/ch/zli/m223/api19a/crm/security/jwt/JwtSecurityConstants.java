package ch.zli.m223.api19a.crm.security.jwt;

public interface JwtSecurityConstants {
	public static final String SECRET = "SecretKeyToGenJWTs";
  public static final String TOKEN_PREFIX = "Bearer ";
  public static final long   EXPIRATION_TIME =  24*60*60*1000;
  public static final String AUTHORISATION_HEADER = "Authorization";
}