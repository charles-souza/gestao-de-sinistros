package br.com.webvirtua.gestaodesinistros.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.webvirtua.gestaodesinistros.model.entity.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Long>{
	
}
