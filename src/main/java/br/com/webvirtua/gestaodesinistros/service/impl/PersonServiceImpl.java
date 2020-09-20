package br.com.webvirtua.gestaodesinistros.service.impl;

import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Service;

import br.com.webvirtua.gestaodesinistros.exception.BusinessException;
import br.com.webvirtua.gestaodesinistros.model.entity.Person;
import br.com.webvirtua.gestaodesinistros.model.repository.PersonRepository;
import br.com.webvirtua.gestaodesinistros.service.PersonService;

@Service
public class PersonServiceImpl implements PersonService {

	private PersonRepository repository;

	public PersonServiceImpl(PersonRepository repository) {
		this.repository = repository;
	}

	@Override
	public Person save(Person person) {
//		if( repository.existsById(pessoa.getId()) ) {
//			throw new BusinessException("Id já cadastrado.");
//		}
		return repository.save(person);
	}

	@Override
	public Optional<Person> getById(Long id) {
		return this.repository.findById(id);
	}

	@Override
	public void delete(Person person) {
		if(person == null || person.getId() == null) {
			throw new IllegalArgumentException("Pessoa id cant be null.");
		}
		this.repository.delete(person);		
	}

	@Override
	public Person update(Person person) {
		if(person == null || person.getId() == null) {
			throw new IllegalArgumentException("Pessoa id cant be null.");
		}
		return this.repository.save(person);
	}

	@Override
	public Page<Person> find(Person filter, Pageable pageRequest) {
		Example<Person> example = Example.of(filter,
					ExampleMatcher
							.matching()
							.withIgnoreCase()
							.withIgnoreNullValues()
							.withStringMatcher( StringMatcher.CONTAINING )
							
				);
		return repository.findAll(example , pageRequest);
	}	

}
