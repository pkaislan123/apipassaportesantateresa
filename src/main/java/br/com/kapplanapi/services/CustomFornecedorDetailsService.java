package br.com.kapplanapi.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;

import br.com.kapplanapi.models.Fornecedor;
import br.com.kapplanapi.repository.FornecedorRepository;

@Component
@CrossOrigin
public class CustomFornecedorDetailsService implements UserDetailsService {
	  private final FornecedorRepository fornecedorRepository;
	  
	  @Autowired
	  public CustomFornecedorDetailsService(FornecedorRepository fornecedorRepository) {
	    this.fornecedorRepository = fornecedorRepository;
	  }
	  
	  @CrossOrigin
	  public UserDetails loadUserByUsername(String cpf) throws UsernameNotFoundException {
	    Optional<Fornecedor> func = this.fornecedorRepository.findByCpfCnpj(cpf, cpf);
	    if (func.isEmpty()) {
	     // throw new UsernameNotFoundException("Fornecedor [" + func + "] não encontrado!"); 
	   //  System.out.println("Fornecedor [" + func + "] por cpf/cnpj não encontrado!");
	     
	     func = this.fornecedorRepository.findByEmail(cpf);
		    if (func.isEmpty()) {
		     // throw new UsernameNotFoundException("Fornecedor [" + func + "] não encontrado!"); 
		 //    System.out.println("Fornecedor [" + func + "] por  email não encontrado!");
		    }
	    }
	      return (UserDetails)new CustomFornecedorDetails(func);
	  }
	}
