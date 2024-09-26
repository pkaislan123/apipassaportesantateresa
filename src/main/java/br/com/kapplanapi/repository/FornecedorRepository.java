package br.com.kapplanapi.repository;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.kapplanapi.models.Fornecedor;


@Repository

public interface FornecedorRepository extends JpaRepository<Fornecedor, Integer>{

	@Query(value = "select * from fornecedor where cpf = ? or cnpj = ? limit 1", nativeQuery = true)
	  Optional<Fornecedor> findByCpfCnpj(String cpf, String cnpj);
	
	@Query(value = "select * from fornecedor where email = ? limit 1", nativeQuery = true)
	  Optional<Fornecedor> findByEmail(String email);
	
	
		Boolean existsByCpf(String cpf);

		Boolean existsByCnpj(String cnpj);

		Boolean existsByEmail(String email);
		

		
		
		
		
		
}
