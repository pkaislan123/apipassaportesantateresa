package br.com.kapplanapi.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.kapplanapi.models.Pagamento;

@Repository
public interface PagamentoRepository extends JpaRepository<Pagamento,Integer>{


}
