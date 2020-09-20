package br.com.webvirtua.gestaodesinistros.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.webvirtua.gestaodesinistros.model.entity.Client;
import br.com.webvirtua.gestaodesinistros.model.entity.Consultant;

public interface ConsultantService {

	Consultant save(Consultant any);

	Optional<Consultant> getById(Long id);

	void delete(Consultant consultant);

	Consultant update(Consultant consultant);

	Page<Consultant> find( Consultant filter, Pageable pageRequest);

}
