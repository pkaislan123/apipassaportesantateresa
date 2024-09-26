package br.com.kapplanapi.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.kapplanapi.models.Cupom;

@Repository
public interface CupomRepository extends JpaRepository<Cupom, Integer> {

	

}
