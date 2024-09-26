package br.com.kapplanapi.utils;

public class JwtResponse {
	
	private String token;
	private String refreshToken;
	private Integer id;
	private String type = "Bearer";
	private String role;
	
	
	

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setRole(String role) {
		this.role = role;
	}


	public JwtResponse(String token,String refreshToken, String type, Integer id, String role) {
		this.token = token;
		this.refreshToken = refreshToken;
		this.type = type;
		this.id = id;
		this.role = role;
	}

	public String getToken() {
		return this.token;
	}

	

	public String getType() {
		return this.type;
	}

	public Integer getId() {
		return this.id;
	}

	public String getRole() {
		return this.role;
	}

	public JwtResponse(int flag, String accessToken, String refreshToken, Integer id) {
		this.token = accessToken;
		this.refreshToken = refreshToken;

		this.id = id;
		if (flag == 0)
			this.role = "ROLE_ADMIN";
		else if (flag == 1)
			this.role = "ROLE_CLIENTE";
		else 
			this.role = "ROLE_FORNECEDOR";
	}

	
	public JwtResponse(String roles, String accessToken, String refreshToken, Integer id) {
		this.token = accessToken;
		this.refreshToken = refreshToken;

		this.id = id;
		this.role = roles;
	}
	
	
	public JwtResponse() {
	}
}
