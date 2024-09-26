package br.com.kapplanapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.kapplanapi.models.Cliente;
import br.com.kapplanapi.models.Fatura;

@Repository
public interface FaturaRepository extends JpaRepository<Fatura,Integer>{

	@Query(value = "select * from fatura where id_fornecedor = :id and mes = :m and ano = :y", nativeQuery = true)
	  List<Fatura> getFaturasPorFornecedorMesAno(int id, int m, int y);
	
	@Query(value = "select * from fatura where id_fornecedor = :id and id_cliente = :id_cli and mes = :m and ano = :y", nativeQuery = true)
	  List<Fatura> getFaturasPorFornecedorClienteMesAno(int id, int id_cli, int m, int y);
	
	@Query(value = "select * from fatura where id_fornecedor = :id and id_cliente = :id_cli and mes = :m and ano = :y", nativeQuery = true)
	Fatura getFaturaPorFornecedorClienteMesAno(int id, int id_cli, int m, int y);
	
	
	@Query(value = "select * from fatura where id_cliente = :id_cli and mes = :m and ano = :y", nativeQuery = true)
	  List<Fatura> getFaturasPorClienteMesAno(int id_cli, int m, int y);
	


}
