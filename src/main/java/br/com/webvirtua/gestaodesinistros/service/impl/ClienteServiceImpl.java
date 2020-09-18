package br.com.webvirtua.gestaodesinistros.service.impl;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.webvirtua.gestaodesinistros.model.entity.Cliente;
import br.com.webvirtua.gestaodesinistros.model.repository.ClienteRepository;
import br.com.webvirtua.gestaodesinistros.service.ClienteService;

@Service
public class ClienteServiceImpl implements ClienteService {

	private ClienteRepository repository;

	public ClienteServiceImpl(ClienteRepository repository) {
		this.repository = repository;
	}

	@Override
	public Cliente save(Cliente cliente) {
//		if( repository.existsById(pessoa.getId()) ) {
//			throw new BusinessException("Id já cadastrado.");
//		}
		return repository.save(cliente);
	}

	@Override
	public Optional<Cliente> getById(Long id) {
		return this.repository.findById(id);
	}

	@Override
	public void delete(Cliente cliente) {
		if(cliente == null || cliente.getId() == null) {
			throw new IllegalArgumentException("Pessoa id cant be null.");
		}
		this.repository.delete(cliente);		
	}

	@Override
	public Cliente update(Cliente cliente) {
		if(cliente == null || cliente.getId() == null) {
			throw new IllegalArgumentException("Pessoa id cant be null.");
		}
		return this.repository.save(cliente);
	}

	@Override
	public Page<Cliente> find(Cliente filter, Pageable pageRequest) {
		// TODO Auto-generated method stub
		return null;
	}	

}
