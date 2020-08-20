package br.com.webvirtua.gestaodesinistros.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.webvirtua.gestaodesinistros.model.entity.Empresa;
import br.com.webvirtua.gestaodesinistros.model.entity.Pessoa;

public interface EmpresaService {

	Empresa save(Empresa any);

	Optional<Empresa> getById(Long id);

	void delete(Empresa empresa);

	Empresa update(Empresa empresa);

	Page<Empresa> find( Empresa filter, Pageable pageRequest);

}
