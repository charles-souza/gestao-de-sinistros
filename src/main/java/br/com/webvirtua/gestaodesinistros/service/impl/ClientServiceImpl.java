package br.com.webvirtua.gestaodesinistros.service.impl;

import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Service;

import br.com.webvirtua.gestaodesinistros.exception.BusinessException;
import br.com.webvirtua.gestaodesinistros.model.entity.Client;
import br.com.webvirtua.gestaodesinistros.model.repository.ClientRepository;
import br.com.webvirtua.gestaodesinistros.service.ClientService;

@Service
public class ClientServiceImpl implements ClientService {

	private ClientRepository repository;

	public ClientServiceImpl(ClientRepository repository) {
		this.repository = repository;
	}

	@Override
	public Client save(Client client) {
//		if( repository.existsById(pessoa.getId()) ) {
//			throw new BusinessException("Id já cadastrado.");
//		}
		return repository.save(client);
	}

	@Override
	public Optional<Client> getById(Long id) {
		return this.repository.findById(id);
	}

	@Override
	public void delete(Client client) {
		if(client == null || client.getId() == null) {
			throw new IllegalArgumentException("Client id cant be null.");
		}
		this.repository.delete(client);		
	}

	@Override
	public Client update(Client client) {
		if(client == null || client.getId() == null) {
			throw new IllegalArgumentException("Client id cant be null.");
		}
		return this.repository.save(client);
	}

	@Override
	public Page<Client> find(Client filter, Pageable pageRequest) {
		Example<Client> example = Example.of(filter,
					ExampleMatcher
							.matching()
							.withIgnoreCase()
							.withIgnoreNullValues()
							.withStringMatcher( StringMatcher.CONTAINING )
							
				);
		return repository.findAll(example , pageRequest);
	}	

}
