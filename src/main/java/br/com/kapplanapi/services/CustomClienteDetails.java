package br.com.kapplanapi.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.kapplanapi.models.Cliente;


public class CustomClienteDetails implements UserDetails {
	private final Optional<Cliente> cliente;

	public CustomClienteDetails(Optional<Cliente> cliente) {
		this.cliente = cliente;
	}

	public Collection<? extends GrantedAuthority> getAuthorities() {
		return new ArrayList<>();
	}

	public Integer getId() {
		return Integer.valueOf(
				((Cliente) this.cliente.orElse(new Cliente())).getId_cliente());
	}

	public String getPassword() {
		return ((Cliente) this.cliente.orElse(new Cliente())).getSenha();
	}

	public String getUsername() {
		
		 return ((Cliente) this.cliente.orElse(new Cliente())).getCpf();

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
