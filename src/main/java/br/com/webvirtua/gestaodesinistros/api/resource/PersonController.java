package br.com.webvirtua.gestaodesinistros.api.resource;

import java.util.Date;
import java.util.List;

import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.boot.context.properties.PropertyMapper.Source;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.webvirtua.gestaodesinistros.api.dto.PersonDTO;
import br.com.webvirtua.gestaodesinistros.api.exception.ApiErrors;
import br.com.webvirtua.gestaodesinistros.exception.BusinessException;
import br.com.webvirtua.gestaodesinistros.model.entity.City;
import br.com.webvirtua.gestaodesinistros.model.entity.Address;
import br.com.webvirtua.gestaodesinistros.model.entity.State;
import br.com.webvirtua.gestaodesinistros.model.entity.Person;
import br.com.webvirtua.gestaodesinistros.service.PersonService;

@RestController
@RequestMapping("/api/pessoas/v1")
public class PersonController {

	private PersonService service;
	private ModelMapper modelMapper;

	public PersonController(PersonService service, ModelMapper mapper) {
		this.service = service;
		this.modelMapper = mapper;
		
		PropertyMap<Person, PersonDTO> personMap = new PropertyMap<Person, PersonDTO>() {
			protected void configure() {
				map().setZipcode(source.getAddress().getZipcode());
				map().setStreet(source.getAddress().getStreet());
				map().setNumber(source.getAddress().getNumber());
				map().setComplement(source.getAddress().getComplement());
				map().setDistrict(source.getAddress().getDistrict());
				map().setCity(source.getAddress().getCity().getName());
				map().setState(source.getAddress().getState().getName());
			}
		};		
		modelMapper.addMappings(personMap);		
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public PersonDTO create(@RequestBody @Valid PersonDTO dto) {		

		Person entity = modelMapper.map( dto, Person.class );
		
		State state = new State();
		state.setName(dto.getState());
		
		City city = new City();
		city.setName(dto.getCity());
		city.setUf(state);
		
		Address address = new Address();
		address.setZipcode(dto.getZipcode());
		address.setStreet(dto.getStreet());
		address.setNumber(dto.getNumber());
		address.setComplement(dto.getComplement());
		address.setDistrict(dto.getDistrict());
		address.setCity(city);
		address.setState(state);
		
		entity.setAddress(address);
		entity.setRegisterDate(new Date(System.currentTimeMillis()));
		entity = service.save(entity);

		return modelMapper.map(entity, PersonDTO.class);
	}

	@GetMapping("{id}")
	public PersonDTO get(@PathVariable Long id) {		

		return service
				.getById(id)
				.map( pessoa -> modelMapper.map(pessoa, PersonDTO.class) )
				.orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND) );
	}

	@DeleteMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		Person pessoa = service.getById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		service.delete(pessoa);
	}

	@PutMapping("{id}")
	public PersonDTO update(@PathVariable Long id, @RequestBody PersonDTO dto) {

		return service.getById(id).map(person -> {
			
			State state = new State();
			state.setName(dto.getState());
			
			City city = new City();
			city.setName(dto.getCity());
			city.setUf(state);
			
			Address address = new Address();
			address.setZipcode(dto.getZipcode());
			address.setStreet(dto.getStreet());
			address.setNumber(dto.getNumber());
			address.setComplement(dto.getComplement());
			address.setDistrict(dto.getDistrict());
			address.setCity(city);
			address.setState(state);

			person.setName(dto.getName());
			person.setLastName(dto.getLastName());
			person.setAddress(address);
			person.setRg(dto.getRg());
			person.setEmitter(dto.getEmitter());
			person.setExpedition(dto.getExpedition());
			person.setCpf(dto.getCpf());
			person.setGender(dto.getGender());
			person.setEmail(dto.getEmail());
			person.setMaritalStatus(dto.getMaritalStatus());
			person.setBirth(dto.getBirth());
			person.setRegisterDate(new Date(System.currentTimeMillis()));
			
			person = service.update(person);
			return modelMapper.map(person, PersonDTO.class);

		}).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}

	@GetMapping
	public Page<PersonDTO> find(PersonDTO dto, Pageable pageRequest) {
		Person filter = modelMapper.map(dto, Person.class);
		Page<Person> result = service.find(filter, pageRequest);
		List<PersonDTO> list = result.getContent().stream().map(entity -> modelMapper.map(entity, PersonDTO.class))
				.collect(Collectors.toList());

		return new PageImpl<PersonDTO>(list, pageRequest, result.getTotalElements());
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ApiErrors handleValidationExceptions(MethodArgumentNotValidException ex) {
		BindingResult bindingResult = ex.getBindingResult();

		return new ApiErrors(bindingResult);
	}

	@ExceptionHandler(BusinessException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ApiErrors handleBusinessException(BusinessException ex) {
		return new ApiErrors(ex);

	}

}
