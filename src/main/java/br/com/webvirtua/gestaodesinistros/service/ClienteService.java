package br.com.webvirtua.gestaodesinistros.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.webvirtua.gestaodesinistros.model.entity.Cliente;

public interface ClienteService {

	Cliente save(Cliente any);

	Optional<Cliente> getById(Long id);

	void delete(Cliente cliente);

	Cliente update(Cliente cliente);

	Page<Cliente> find( Cliente filter, Pageable pageRequest);

}
