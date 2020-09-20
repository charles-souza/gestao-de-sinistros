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
import br.com.webvirtua.gestaodesinistros.model.entity.Consultant;
import br.com.webvirtua.gestaodesinistros.model.repository.ClientRepository;
import br.com.webvirtua.gestaodesinistros.model.repository.ConsultantRepository;
import br.com.webvirtua.gestaodesinistros.service.ClientService;
import br.com.webvirtua.gestaodesinistros.service.ConsultantService;

@Service
public class ConsultantServiceImpl implements ConsultantService {

	private ConsultantRepository repository;

	public ConsultantServiceImpl(ConsultantRepository repository) {
		this.repository = repository;
	}

	@Override
	public Consultant save(Consultant consultant) {
//		if( repository.existsById(pessoa.getId()) ) {
//			throw new BusinessException("Id já cadastrado.");
//		}
		return repository.save(consultant);
	}

	@Override
	public Optional<Consultant> getById(Long id) {
		return this.repository.findById(id);
	}

	@Override
	public void delete(Consultant consultant) {
		if(consultant == null || consultant.getId() == null) {
			throw new IllegalArgumentException("Client id cant be null.");
		}
		this.repository.delete(consultant);		
	}

	@Override
	public Consultant update(Consultant consultant) {
		if(consultant == null || consultant.getId() == null) {
			throw new IllegalArgumentException("Client id cant be null.");
		}
		return this.repository.save(consultant);
	}

	@Override
	public Page<Consultant> find(Consultant filter, Pageable pageRequest) {
		Example<Consultant> example = Example.of(filter,
					ExampleMatcher
							.matching()
							.withIgnoreCase()
							.withIgnoreNullValues()
							.withStringMatcher( StringMatcher.CONTAINING )
							
				);
		return repository.findAll(example , pageRequest);
	}	

}
