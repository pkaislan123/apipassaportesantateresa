package br.com.kapplanapi.configs;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.kapplanapi.services.UserDetailsServiceImpl;



public class AuthAdminTokenFilter extends OncePerRequestFilter {
  @Autowired
  private JwtUtils jwtUtils;
  
  @Autowired
  private UserDetailsServiceImpl userDetailsService;
  
 // private static final Logger logger = LoggerFactory.getLogger(AuthAdminTokenFilter.class);
  
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    try {
      String jwt = parseJwt(request);
      if (jwt != null && this.jwtUtils.validateJwtToken(jwt)) {
        String username = this.jwtUtils.getUserNameFromJwtToken(jwt);
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        authentication.setDetails((new WebAuthenticationDetailsSource()).buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication((Authentication)authentication);
       // logger.info("Usuario Autenticado");
      } 
    } catch (Exception e) {
    //  logger.error("Cannot set user authentication: {}", e);
    } 
    filterChain.doFilter((ServletRequest)request, (ServletResponse)response);
  }
  
  private String parseJwt(HttpServletRequest request) {
    String headerAuth = request.getHeader("Authorization");
    if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer "))
      return headerAuth.substring(7, headerAuth.length()); 
    return null;
  }

  
  public Authentication getAuthentication(HttpServletRequest request) {
	    String jwt = parseJwt(request);
	    if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
	        String username = jwtUtils.getUserNameFromJwtToken(jwt);
	        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
	        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
	        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
	        return authentication;
	    }
	    return null;
	}



}