package br.com.webvirtua.gestaodesinistros.service;

import br.com.webvirtua.gestaodesinistros.api.dto.PersonDTO;
import br.com.webvirtua.gestaodesinistros.utils.ReturnRequest;

public interface PersonService<C>{

	ReturnRequest save(PersonDTO any);

	ReturnRequest getById(Long id);

	ReturnRequest delete(Long id);

	ReturnRequest update(Long id, PersonDTO personDTO);

	ReturnRequest find();

}
