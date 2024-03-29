package br.com.webvirtua.gestaodesinistros.service.impl;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.webvirtua.gestaodesinistros.api.dto.PersonDTO;
import br.com.webvirtua.gestaodesinistros.model.entity.Person;
import br.com.webvirtua.gestaodesinistros.model.repository.PersonRepository;
import br.com.webvirtua.gestaodesinistros.service.PersonService;
import br.com.webvirtua.gestaodesinistros.utils.Status;
import br.com.webvirtua.gestaodesinistros.utils.ReturnRequest;

@Service
public class PersonServiceImpl implements PersonService {

	private PersonRepository personRepository;
	
	private ModelMapper modelMapper;
	
	@Autowired
	private Status status;

	public PersonServiceImpl(PersonRepository repository, ModelMapper modelMapper) {
		this.personRepository = repository;
		this.modelMapper = modelMapper;
		
//		PropertyMap<Person, PersonDTO> personMap = new PropertyMap<Person, PersonDTO>() {
//			protected void configure() {
//				map().setZipcode(source.getAddress().getZipcode());
//				map().setStreet(source.getAddress().getStreet());
//				map().setNumber(source.getAddress().getNumber());
//				map().setComplement(source.getAddress().getComplement());
//				map().setDistrict(source.getAddress().getDistrict());
//				map().setCity(source.getAddress().getCity().getName());
//				map().setState(source.getAddress().getState().getName());
//				
//				map().setId_city(source.getAddress().getCity().getId_city());
				
//				map().getAddress().getCity().setId_city(source.getAddress().getCity().getId_city());
//			}
//		};		
//		modelMapper.addMappings(personMap);	
	}

	@Override
	@Transactional
	public ReturnRequest save(PersonDTO person) {
//		if( repository.existsById(pessoa.getId()) ) {
//			throw new BusinessException("Id já cadastrado.");
//		}
		
		Person entity = this.modelMapper.map(person, Person.class);
		
		Person personAdded = personRepository.save(entity);
		
		ReturnRequest resultRequest = ReturnRequest.builder()
				.success(1)
				.status(status.getCode201())
				.totalResults(1)
				.successMessage("Usuário inserido com sucesso")
				.data(personAdded)
				.build();

		return resultRequest;
	}

	@Override
	public ReturnRequest getById(Long id) {
		
		Optional<Person> person = personRepository.findById(id);
		
		ReturnRequest resultRequest = ReturnRequest.builder()
				.success(1)
				.status(status.getCode200())
				.totalResults(1)
				.successMessage("Resultados Obtidos")
				.data(person)
				.build();
		
		return resultRequest;
	}

	@Override
	public ReturnRequest delete(Long id) {
		if(id == null) {
			throw new IllegalArgumentException("Pessoa id cant be null.");
		}
		this.personRepository.deleteById(id);
		
		ReturnRequest resultRequest = ReturnRequest.builder()
				.success(1)
				.status(status.getCode200())
				.successMessage("Usuário excluído com sucesso")
				.build();
		
		return resultRequest;
	}

	@Override
	public ReturnRequest update(Long id, PersonDTO personDTO) {
		if(personDTO == null || id == null) {
			throw new IllegalArgumentException("Pessoa id cant be null.");
		}
		Person entity = this.modelMapper.map(personDTO, Person.class);
		entity.setId(id);
		
		Person personUpdated = personRepository.save(entity);
		
		ReturnRequest resultRequest = ReturnRequest.builder()
				.success(1)
				.status(status.getCode200())
				.totalResults(1)
				.successMessage("Usuário alterado com sucesso")
				.data(personUpdated)
				.build();
		
		return resultRequest;
	}

	@Override
	public ReturnRequest find() {
		List<Person> persons = personRepository.findAll();

		ReturnRequest resultRequest = ReturnRequest.builder()
				.success(1)
				.status(status.getCode200())
				.totalResults(persons.size())
				.resultsPerPage(0)
				.totalPages(0)
				.page(0)
				.successMessage("Resultados Obtidos")
				.data(persons)
				.build();
		
		return resultRequest;
	}	

}
