package br.com.kapplanapi.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.kapplanapi.models.Fatura;

@Repository
public interface FaturaRepository extends JpaRepository<Fatura,Integer>{


	@Query(value = "select * from fatura where external_reference = ? limit 1", nativeQuery = true)
	Optional<Fatura> buscarPorExternalReference(String external_reference);

}
