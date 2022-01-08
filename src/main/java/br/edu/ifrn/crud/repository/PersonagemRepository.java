package br.edu.ifrn.crud.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.edu.ifrn.crud.dominio.Personagem;

public interface PersonagemRepository extends JpaRepository<Personagem, Integer>{

	@Query("select p from Personagem p where p.nome like %:nome%") 
	List<Personagem> findByNome(@Param("nome") String nome);
}
