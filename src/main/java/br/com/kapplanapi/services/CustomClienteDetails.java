package br.com.kapplanapi.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.kapplanapi.models.Cliente;


public class CustomClienteDetails implements UserDetails {
	private final Optional<Cliente> cliente;

	public CustomClienteDetails(Optional<Cliente> cliente) {
		this.cliente = cliente;
	}

	@Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Aqui, você pode dividir as roles em uma lista e criar um GrantedAuthority para cada uma
        return Arrays.stream(cliente.get().getRoles().split(",")) // Supondo que as roles sejam separadas por vírgula
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());
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
