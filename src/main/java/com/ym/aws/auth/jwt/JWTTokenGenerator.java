/**
 * 
 */
package com.ym.aws.auth.jwt;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

import javax.crypto.spec.SecretKeySpec;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.SignatureException;

/**
 * @author Pranit.Mhatre
 *
 */
public final class JWTTokenGenerator {

	private static final String SECRET = "asdfSFS34wfsdfsdfSDSD32dfsddDDerQSNCK34SOWEK5354fdgdf4";
	private static final String JWT_CLAIM_NAME = "name";

	private static Key hmacKey = new SecretKeySpec(Base64.getDecoder().decode(SECRET),
			SignatureAlgorithm.HS256.getJcaName());

	private JWTTokenGenerator() {
		throw new IllegalStateException("Cannot instantiate private constructor");
	}

	public static final String createToken(String userName) {
		Instant now = Instant.now();
		String jwtToken = Jwts.builder().claim(JWT_CLAIM_NAME, userName).setSubject(userName)
				.setId(UUID.randomUUID().toString()).setIssuedAt(Date.from(now))
				.setExpiration(Date.from(now.plus(1, ChronoUnit.HOURS))).signWith(hmacKey).compact();
		return jwtToken;
	}

	private static Claims parseJwt(String jwtString) throws ExpiredJwtException, SignatureException {
		Jws<Claims> jwt = Jwts.parserBuilder().setSigningKey(hmacKey).build().parseClaimsJws(jwtString);
		return jwt.getBody();
	}

	public static String getUser(String token) {
		return (String) parseJwt(token).get(JWT_CLAIM_NAME);
	}
}
