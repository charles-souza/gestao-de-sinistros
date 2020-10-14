package br.com.webvirtua.gestaodesinistros.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.webvirtua.gestaodesinistros.model.entity.Custumer;
import br.com.webvirtua.gestaodesinistros.model.entity.Consultant;

public interface ConsultantRepository extends JpaRepository<Consultant, Long>{
	
}
