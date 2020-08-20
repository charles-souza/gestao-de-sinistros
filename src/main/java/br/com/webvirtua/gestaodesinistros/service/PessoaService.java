package br.com.webvirtua.gestaodesinistros.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.webvirtua.gestaodesinistros.model.entity.Pessoa;

public interface PessoaService {

	Pessoa save(Pessoa any);

	Optional<Pessoa> getById(Long id);

	void delete(Pessoa pessoa);

	Pessoa update(Pessoa pessoa);

	Page<Pessoa> find( Pessoa filter, Pageable pageRequest);

}
