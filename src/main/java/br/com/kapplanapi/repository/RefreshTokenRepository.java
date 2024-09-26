package br.com.kapplanapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.kapplanapi.models.RefreshToken;
import br.com.kapplanapi.models.User;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

	  @Override
	    Optional<RefreshToken> findById(Long id);
	    Optional<RefreshToken> findByToken(String token);
	    
	    
		int deleteByUser(User user);
	    
}
