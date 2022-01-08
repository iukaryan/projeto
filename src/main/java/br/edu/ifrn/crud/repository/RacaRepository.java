package br.edu.ifrn.crud.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.edu.ifrn.crud.dominio.Raca;

public interface RacaRepository extends JpaRepository<Raca, Integer> {
	
	@Query("select r from Raca r where r.nome like %:nome%") 
	List<Raca> findByNome(@Param("nome") String nome);
}
