package com.algaworks.cobranca.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.algaworks.cobranca.model.Titulo;

public interface Titulos extends JpaRepository<Titulo, Long> {

	  //List<Titulo> descricaoTipo(String descricaoTipo);
	  List<Titulo> findByDescricaoContaining(String descricao);

	 // Titulos findByEmailAddress(String emailAddress);
	
}
 