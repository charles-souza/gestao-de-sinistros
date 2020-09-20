package br.com.webvirtua.gestaodesinistros.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.webvirtua.gestaodesinistros.model.entity.Person;

public interface PersonService {

	Person save(Person any);

	Optional<Person> getById(Long id);

	void delete(Person person);

	Person update(Person person);

	Page<Person> find( Person filter, Pageable pageRequest);

}
