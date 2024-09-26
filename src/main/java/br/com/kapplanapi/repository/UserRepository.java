package br.com.kapplanapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.kapplanapi.models.User;


@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
	  Optional<User> findByLogin(String paramString);

	  @Query(value = "select * from usuarios where id_usuario = ? ", nativeQuery = true)
	  Optional<User> findByIdUsuario(int id);
	 

		 boolean existsByLogin(String login);
		
		
		
}
