package br.com.webvirtua.gestaodesinistros.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.webvirtua.gestaodesinistros.model.entity.Empresa;

public interface EmpresaRepository extends JpaRepository<Empresa, Long>{
	
}
