package br.com.webvirtua.gestaodesinistros.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.webvirtua.gestaodesinistros.model.entity.Custumer;

public interface CustumerRepository extends JpaRepository<Custumer, Long>{
	
}
