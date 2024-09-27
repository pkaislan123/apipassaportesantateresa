package br.com.kapplanapi.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.kapplanapi.models.Fornecedor;

public class CustomFornecedorDetails implements UserDetails {
	private final Optional<Fornecedor> fornecedor;

	public CustomFornecedorDetails(Optional<Fornecedor> fornecedor) {
		this.fornecedor = fornecedor;
	}

	 
	@Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Aqui, você pode dividir as roles em uma lista e criar um GrantedAuthority para cada uma
        return Arrays.stream(fornecedor.get().getRoles().split(",")) // Supondo que as roles sejam separadas por vírgula
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());
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