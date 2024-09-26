package br.com.kapplanapi.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.kapplanapi.models.Fornecedor;

public class CustomFornecedorDetails implements UserDetails {
	private final Optional<Fornecedor> fornecedor;

	public CustomFornecedorDetails(Optional<Fornecedor> fornecedor) {
		this.fornecedor = fornecedor;
	}

	public Collection<? extends GrantedAuthority> getAuthorities() {
		return new ArrayList<>();
	}

	public Integer getId() {
		return Integer.valueOf(
				((Fornecedor) this.fornecedor.orElse(new Fornecedor())).getId_fornecedor());
	}

	public String getPassword() {
		return ((Fornecedor) this.fornecedor.orElse(new Fornecedor())).getSenha();
	}

	public String getUsername() {
		
		if( ((Fornecedor) this.fornecedor.orElse(new Fornecedor())).getTipo_fornecedor() == 0     )
		 return ((Fornecedor) this.fornecedor.orElse(new Fornecedor())).getCpf();
		else 
			return ((Fornecedor) this.fornecedor.orElse(new Fornecedor())).getCnpj();

	}
	
	
	public boolean isAccountNonExpired() {
		return true;
	}

	public boolean isAccountNonLocked() {
		return true;
	}

	public boolean isCredentialsNonExpired() {
		return true;
	}

	public boolean isEnabled() {
			return true;
	
	}
}