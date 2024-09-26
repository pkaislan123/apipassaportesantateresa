package br.com.kapplanapi.services;



import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;

import br.com.kapplanapi.models.Cliente;
import br.com.kapplanapi.repository.ClienteRepository;



@Component
@CrossOrigin
public class CustomClienteDetailsService implements UserDetailsService {
  private final ClienteRepository clienteRepository;
  
  @Autowired
  public CustomClienteDetailsService(ClienteRepository clienteRepository) {
    this.clienteRepository = clienteRepository;
  }
  
  @CrossOrigin
  public UserDetails loadUserByUsername(String cpf) throws UsernameNotFoundException {
    Optional<Cliente> func = this.clienteRepository.findByCpf(cpf);
    if (func.isEmpty()) {
        //throw new UsernameNotFoundException("Cliente [" + func + "] não encontrato!"); 
       //    System.out.println("Cliente [" + func + "] por cpf/cnpj não encontrado!");
           func = this.clienteRepository.findByEmail(cpf);
           if (func.isEmpty()) {
               //throw new UsernameNotFoundException("Cliente [" + func + "] não encontrato!"); 
          //        System.out.println("Cliente [" + func + "] por email não encontrado!");

           }
    }
    return (UserDetails)new CustomClienteDetails(func);
  }
  
  
  @CrossOrigin
  public UserDetails loadUserByEmail(String email) throws UsernameNotFoundException {
    Optional<Cliente> func = this.clienteRepository.findByEmail(email);
    if (func.isEmpty()) {
        //throw new UsernameNotFoundException("Cliente [" + func + "] não encontrato!"); 
        //   System.out.println("Cliente [" + func + "] por email não encontrado!");

    }
    return (UserDetails)new CustomClienteDetails(func);
  }
  
}