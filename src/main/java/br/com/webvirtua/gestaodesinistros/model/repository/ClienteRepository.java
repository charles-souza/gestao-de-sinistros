package br.com.webvirtua.gestaodesinistros.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.webvirtua.gestaodesinistros.model.entity.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long>{
	
}
