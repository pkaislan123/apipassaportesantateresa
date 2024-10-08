package br.com.kapplanapi.configs;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import java.util.Date;

import br.com.kapplanapi.services.CustomClienteDetails;
import br.com.kapplanapi.services.CustomFornecedorDetails;
import br.com.kapplanapi.services.UserDetailsImpl;

@Component
public class JwtUtils {
	private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

	@Value("${bezkoder.app.jwtSecret}")
	private String jwtSecret;

	@Value("${bezkoder.app.jwtExpirationMs}")
	private int jwtExpirationMs;

	public String generateJwtToken(Authentication authentication) {
		try {
			UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
			return Jwts.builder().setSubject(userPrincipal.getUsername()).setIssuedAt(new Date())
					.setExpiration(new Date((new Date()).getTime() + this.jwtExpirationMs))
					.signWith(SignatureAlgorithm.HS512, this.jwtSecret).compact();
		} catch (Exception e) {

			try {

				CustomClienteDetails userPrincipal = (CustomClienteDetails) authentication.getPrincipal();
				return Jwts.builder().setSubject(userPrincipal.getUsername()).setIssuedAt(new Date())
						.setExpiration(new Date((new Date()).getTime() + this.jwtExpirationMs))
						.signWith(SignatureAlgorithm.HS512, this.jwtSecret).compact();

			} catch (Exception f) {
				CustomFornecedorDetails userPrincipal = (CustomFornecedorDetails) authentication.getPrincipal();
				return Jwts.builder().setSubject(userPrincipal.getUsername()).setIssuedAt(new Date())
						.setExpiration(new Date((new Date()).getTime() + this.jwtExpirationMs))
						.signWith(SignatureAlgorithm.HS512, this.jwtSecret).compact();

			}
		}
	}

	public String generateTokenFromUsername(String username) {
		return Jwts.builder().setSubject(username).setIssuedAt(new Date())
				.setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
				.signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
	}
	

	public String getUserNameFromJwtToken(String token) {
		return ((Claims) Jwts.parser().setSigningKey(this.jwtSecret).parseClaimsJws(token).getBody()).getSubject();
	}

	public boolean validateJwtToken(String authToken) {
		try {
			Jwts.parser().setSigningKey(this.jwtSecret).parseClaimsJws(authToken);
			return true;
		} catch (SignatureException e) {
			logger.error("Invalid JWT signature: {}", e.getMessage());
		} catch (MalformedJwtException e) {
			logger.error("Invalid JWT token: {}", e.getMessage());
		} catch (ExpiredJwtException e) {
			logger.error("JWT token is expired: {}", e.getMessage());
		} catch (UnsupportedJwtException e) {
			logger.error("JWT token is unsupported: {}", e.getMessage());
		} catch (IllegalArgumentException e) {
			logger.error("JWT claims string is empty: {}", e.getMessage());
		}
		return false;
	}
}