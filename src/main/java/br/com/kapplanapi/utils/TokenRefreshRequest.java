package br.com.kapplanapi.utils;

import javax.validation.constraints.NotBlank;

public class TokenRefreshRequest {

	@NotBlank
	private String refreshToken;

	@NotBlank
	private String regra;
	
	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public String getRegra() {
		return regra;
	}

	public void setRegra(String regra) {
		this.regra = regra;
	}
	
	

}
