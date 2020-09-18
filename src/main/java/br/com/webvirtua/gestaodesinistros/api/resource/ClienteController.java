package br.com.webvirtua.gestaodesinistros.api.resource;

import java.util.List;

import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
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

import br.com.webvirtua.gestaodesinistros.api.dto.ClienteDTO;
import br.com.webvirtua.gestaodesinistros.api.exception.ApiErrors;
import br.com.webvirtua.gestaodesinistros.exception.BusinessException;
import br.com.webvirtua.gestaodesinistros.model.entity.Cliente;
import br.com.webvirtua.gestaodesinistros.service.ClienteService;

@RestController
@RequestMapping("/v1/clientes")
public class ClienteController {
	
	private ClienteService service;
	private ModelMapper modelMapper;
	
	public ClienteController(ClienteService service, ModelMapper mapper) {
		this.service = service;
		this.modelMapper = mapper;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ClienteDTO create( @RequestBody @Valid ClienteDTO dto ) {
		
		Cliente entity = dto.build();
		
		entity = service.save(entity);
		
		dto = entity.convertCliente();
		
		return dto;
	
		//return modelMapper.map( entity, ClienteDTO.class );
	}
	
	@GetMapping("{id}")
	public ClienteDTO get(@PathVariable Long id) {
		
		Cliente entity = service.getById(id).orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND) );
		
		ClienteDTO dto = entity.convertCliente();
		
		return dto;
	}
	
	@DeleteMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		Cliente cliente = service.getById(id).orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		service.delete(cliente);
	}
	
	@PutMapping("{id}")
	public ClienteDTO update( @PathVariable Long id, @RequestBody ClienteDTO dto ) {
		
		return service.getById(id).map( cliente -> { 
			
//			pessoa.setNome(dto.getNome());
//			pessoa.setSobrenome(dto.getSobrenome());
//			pessoa.setEndereco(endereco.setCep(dto.getCep()));
//			pessoa.setRg(dto.getRg());
//			pessoa.setEmissor(dto.getEmissor());
//			pessoa.setExpedicao(dto.getExpedicao());
//			pessoa.setCpf(dto.getCpf());
//			pessoa.setSexo(dto.getSexo());
//			pessoa.setEmail(dto.getEmail());
//			pessoa.setEstadoCivil(dto.getEstadoCivil());
//			pessoa.setDataNascimento(dto.getDataNascimento());
//			pessoa.setDataRegistro(dto.getDataRegistro());
			dto.build();
			cliente = service.update(cliente);
			return modelMapper.map(cliente, ClienteDTO.class);
			
			
		} ).orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND));			
	}
	
	@GetMapping
	public Page<ClienteDTO> find( ClienteDTO dto, Pageable pageRequest ) {
		Cliente filter = modelMapper.map(dto, Cliente.class);
		Page<Cliente> result = service.find(filter, pageRequest);
		List<ClienteDTO> list = result.getContent()
				.stream()
				.map( entity -> modelMapper.map(entity, ClienteDTO.class) )
				.collect( Collectors.toList());
		
		return new PageImpl<ClienteDTO>( list, pageRequest, result.getTotalElements() );
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
