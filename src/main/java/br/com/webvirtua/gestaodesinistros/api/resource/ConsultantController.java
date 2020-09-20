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

import br.com.webvirtua.gestaodesinistros.api.dto.ClientDTO;
import br.com.webvirtua.gestaodesinistros.api.dto.ConsultantDTO;
import br.com.webvirtua.gestaodesinistros.api.dto.PersonDTO;
import br.com.webvirtua.gestaodesinistros.api.exception.ApiErrors;
import br.com.webvirtua.gestaodesinistros.exception.BusinessException;
import br.com.webvirtua.gestaodesinistros.model.entity.City;
import br.com.webvirtua.gestaodesinistros.model.entity.Client;
import br.com.webvirtua.gestaodesinistros.model.entity.Consultant;
import br.com.webvirtua.gestaodesinistros.model.entity.Address;
import br.com.webvirtua.gestaodesinistros.model.entity.State;
import br.com.webvirtua.gestaodesinistros.model.entity.Person;
import br.com.webvirtua.gestaodesinistros.service.ClientService;
import br.com.webvirtua.gestaodesinistros.service.ConsultantService;
import br.com.webvirtua.gestaodesinistros.service.PersonService;

@RestController
@RequestMapping("/api/consultores/v1")
public class ConsultantController {

	private ConsultantService service;
	private ModelMapper modelMapper;

	public ConsultantController(ConsultantService service, ModelMapper mapper) {
		this.service = service;
		this.modelMapper = mapper;
		
		PropertyMap<Consultant, ConsultantDTO> consultantMap = new PropertyMap<Consultant, ConsultantDTO>() {
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
		modelMapper.addMappings(consultantMap);		
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ConsultantDTO create(@RequestBody @Valid ConsultantDTO dto) {		

		Consultant entity = modelMapper.map( dto, Consultant.class );
		
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

		return modelMapper.map(entity, ConsultantDTO.class);
	}

	@GetMapping("{id}")
	public ConsultantDTO get(@PathVariable Long id) {		

		return service
				.getById(id)
				.map( consultant -> modelMapper.map(consultant, ConsultantDTO.class) )
				.orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND) );
	}

	@DeleteMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		Consultant consultant = service.getById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		service.delete(consultant);
	}

	@PutMapping("{id}")
	public ConsultantDTO update(@PathVariable Long id, @RequestBody ConsultantDTO dto) {

		return service.getById(id).map(consultant -> {
			
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

			consultant.setName(dto.getName());
			consultant.setLastName(dto.getLastName());
//			consultant.setPhone(dto.getPhone());
			consultant.setPassword(dto.getPassword());
			consultant.setAddress(address);
			consultant.setRg(dto.getRg());
			consultant.setEmitter(dto.getEmitter());
			consultant.setExpedition(dto.getExpedition());
			consultant.setCpf(dto.getCpf());
			consultant.setGender(dto.getGender());
			consultant.setEmail(dto.getEmail());
			consultant.setMaritalStatus(dto.getMaritalStatus());
			consultant.setBirth(dto.getBirth());
			consultant.setRegisterDate(new Date(System.currentTimeMillis()));
			
			consultant = service.update(consultant);
			return modelMapper.map(consultant, ConsultantDTO.class);

		}).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}

	@GetMapping
	public Page<ConsultantDTO> find(ConsultantDTO dto, Pageable pageRequest) {
		Consultant filter = modelMapper.map(dto, Consultant.class);
		Page<Consultant> result = service.find(filter, pageRequest);
		List<ConsultantDTO> list = result.getContent().stream().map(entity -> modelMapper.map(entity, ConsultantDTO.class))
				.collect(Collectors.toList());

		return new PageImpl<ConsultantDTO>(list, pageRequest, result.getTotalElements());
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
