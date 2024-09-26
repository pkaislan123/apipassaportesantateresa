package br.com.kapplanapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.kapplanapi.models.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

	@Query(value = "select * from cliente where cpf = ?", nativeQuery = true)
	Optional<Cliente> findByCpf(String cpf);

	@Query(value = "select * from cliente where email = ? limit 1", nativeQuery = true)
	Optional<Cliente> findByEmail(String email);

	Boolean existsByCpf(String cpf);

	Boolean existsByEmail(String email);

}
