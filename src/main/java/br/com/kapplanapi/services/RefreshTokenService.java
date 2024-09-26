package br.com.kapplanapi.services;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.com.kapplanapi.exceptions.TokenRefreshException;
import br.com.kapplanapi.models.RefreshToken;
import br.com.kapplanapi.repository.ClienteRepository;
import br.com.kapplanapi.repository.FornecedorRepository;
import br.com.kapplanapi.repository.RefreshTokenRepository;
import br.com.kapplanapi.repository.UserRepository;

@Service
public class RefreshTokenService {

	@Value("${bezkoder.app.jwtRefreshExpirationMs}")
	private Long refreshTokenDurationMs;
	
	@Autowired
	private RefreshTokenRepository refreshTokenRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	ClienteRepository clienteRepository;
	
	
	@Autowired
	FornecedorRepository fornecedorRepository;

	public Optional<RefreshToken> findByToken(String token) {
		return refreshTokenRepository.findByToken(token);
	}

	public RefreshToken createRefreshToken(int id, int tipo) {
		
		//0 cliente
		//1 usuario
		//2 fornecedor
		
		RefreshToken refreshToken = new RefreshToken();
		
		if(tipo == 0) {
			refreshToken.setCliente(clienteRepository.findById(id).get());
		}else if(tipo == 1) {
			refreshToken.setUser(userRepository.findByIdUsuario(id).get());

		}else {
			refreshToken.setFornecedor(fornecedorRepository.findById(id).get());

		}
		
		refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
		refreshToken.setToken(UUID.randomUUID().toString());
		refreshToken = refreshTokenRepository.save(refreshToken);
		return refreshToken;
	}

	public RefreshToken verifyExpiration(RefreshToken token) {
		if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
			refreshTokenRepository.delete(token);
			throw new TokenRefreshException(token.getToken(),
					"Refresh token was expired. Please make a new signin request");
		}
		return token;
	}

	
}
