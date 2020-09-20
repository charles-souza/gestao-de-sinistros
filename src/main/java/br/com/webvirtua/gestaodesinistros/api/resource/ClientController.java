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
import br.com.webvirtua.gestaodesinistros.api.dto.PersonDTO;
import br.com.webvirtua.gestaodesinistros.api.exception.ApiErrors;
import br.com.webvirtua.gestaodesinistros.exception.BusinessException;
import br.com.webvirtua.gestaodesinistros.model.entity.City;
import br.com.webvirtua.gestaodesinistros.model.entity.Client;
import br.com.webvirtua.gestaodesinistros.model.entity.Address;
import br.com.webvirtua.gestaodesinistros.model.entity.State;
import br.com.webvirtua.gestaodesinistros.model.entity.Person;
import br.com.webvirtua.gestaodesinistros.service.ClientService;
import br.com.webvirtua.gestaodesinistros.service.PersonService;

@RestController
@RequestMapping("/api/clientes/v1")
public class ClientController {

	private ClientService service;
	private ModelMapper modelMapper;

	public ClientController(ClientService service, ModelMapper mapper) {
		this.service = service;
		this.modelMapper = mapper;
		
		PropertyMap<Client, ClientDTO> clientMap = new PropertyMap<Client, ClientDTO>() {
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
		modelMapper.addMappings(clientMap);		
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ClientDTO create(@RequestBody @Valid ClientDTO dto) {		

		Client entity = modelMapper.map( dto, Client.class );
		
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

		return modelMapper.map(entity, ClientDTO.class);
	}

	@GetMapping("{id}")
	public ClientDTO get(@PathVariable Long id) {		

		return service
				.getById(id)
				.map( client -> modelMapper.map(client, ClientDTO.class) )
				.orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND) );
	}

	@DeleteMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		Client client = service.getById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		service.delete(client);
	}

	@PutMapping("{id}")
	public ClientDTO update(@PathVariable Long id, @RequestBody ClientDTO dto) {

		return service.getById(id).map(client -> {
			
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

			client.setName(dto.getName());
			client.setLastName(dto.getLastName());
			client.setPhone(dto.getPhone());
			client.setAddress(address);
			client.setRg(dto.getRg());
			client.setEmitter(dto.getEmitter());
			client.setExpedition(dto.getExpedition());
			client.setCpf(dto.getCpf());
			client.setGender(dto.getGender());
			client.setEmail(dto.getEmail());
			client.setMaritalStatus(dto.getMaritalStatus());
			client.setBirth(dto.getBirth());
			client.setRegisterDate(new Date(System.currentTimeMillis()));
			
			client = service.update(client);
			return modelMapper.map(client, ClientDTO.class);

		}).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}

	@GetMapping
	public Page<ClientDTO> find(ClientDTO dto, Pageable pageRequest) {
		Client filter = modelMapper.map(dto, Client.class);
		Page<Client> result = service.find(filter, pageRequest);
		List<ClientDTO> list = result.getContent().stream().map(entity -> modelMapper.map(entity, ClientDTO.class))
				.collect(Collectors.toList());

		return new PageImpl<ClientDTO>(list, pageRequest, result.getTotalElements());
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
