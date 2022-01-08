package br.edu.ifrn.crud.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.edu.ifrn.crud.dominio.Jogo;

public interface JogoRepository extends JpaRepository<Jogo, Integer> {
	
	@Query("select j from Jogo j where j.nome like %:nome%") 
	List<Jogo> findByNome(@Param("nome") String nome);
}
