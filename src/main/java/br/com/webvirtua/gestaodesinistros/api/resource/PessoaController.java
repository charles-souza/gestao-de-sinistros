package br.com.webvirtua.gestaodesinistros.api.resource;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
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

import br.com.webvirtua.gestaodesinistros.api.dto.PessoaDTO;
import br.com.webvirtua.gestaodesinistros.api.exception.ApiErrors;
import br.com.webvirtua.gestaodesinistros.exception.BusinessException;
import br.com.webvirtua.gestaodesinistros.model.entity.Pessoa;
import br.com.webvirtua.gestaodesinistros.service.PessoaService;

@RestController
@RequestMapping("/api/pessoas/v1")
public class PessoaController {
	
	private PessoaService service;
	private ModelMapper modelMapper;
	
	public PessoaController(PessoaService service, ModelMapper mapper) {
		this.service = service;
		this.modelMapper = mapper;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public PessoaDTO create( @RequestBody @Valid PessoaDTO dto ) {
		
//		// Create a TypeMap for your mapping
//		TypeMap<Pessoa, PessoaDTO> typeMap = 
//		    modelMapper.createTypeMap(Pessoa.class, PessoaDTO.class);
//
//		// Define the mappings on the type map
//		typeMap.addMappings(mapper -> {
//		    mapper.map(src -> src.getEndereco().getCep(),
//		    				  PessoaDTO::setCep);
//		    mapper.map(src -> src.getEndereco().getLogradouro(),
//  				  PessoaDTO::setLogradouro);
//		});
		
		Pessoa entity = dto.build();
		
		entity = service.save(entity);
		
		dto = entity.convert();
		
		return dto;
	
		//return modelMapper.map( entity, PessoaDTO.class );
	}
	
	@GetMapping("{id}")
	public PessoaDTO get(@PathVariable Long id) {
		
		return service
				.getById(id)
				.map( pessoa -> modelMapper.map(pessoa, PessoaDTO.class) )
				.orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND) );	
	}
	
	@DeleteMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		Pessoa pessoa = service.getById(id).orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		service.delete(pessoa);
	}
	
	@PutMapping("{id}")
	public PessoaDTO update( @PathVariable Long id, @RequestBody PessoaDTO dto ) {
		return service.getById(id).map( pessoa -> { 
			
			pessoa.setNome(dto.getNome());
			pessoa.setSobrenome(dto.getSobrenome());
			//pessoa.setEndereco(dto.getEndereco());
			pessoa.setRg(dto.getRg());
			pessoa.setEmissor(dto.getEmissor());
			pessoa.setExpedicao(dto.getExpedicao());
			pessoa.setCpf(dto.getCpf());
			pessoa.setSexo(dto.getSexo());
			pessoa.setEmail(dto.getEmail());
			pessoa.setEstadoCivil(dto.getEstadoCivil());
			pessoa.setDataNascimento(dto.getDataNascimento());
			pessoa.setDataRegistro(dto.getDataRegistro());
			pessoa = service.update(pessoa);
			return modelMapper.map(pessoa, PessoaDTO.class);
			
			
		} ).orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND));			
	}
	
	@GetMapping
	public Page<PessoaDTO> find( PessoaDTO dto, Pageable pageRequest ) {
		Pessoa filter = modelMapper.map(dto, Pessoa.class);
		Page<Pessoa> result = service.find(filter, pageRequest);
		List<PessoaDTO> list = result.getContent()
				.stream()
				.map( entity -> modelMapper.map(entity, PessoaDTO.class) )
				.collect( Collectors.toList());
		
		return new PageImpl<PessoaDTO>( list, pageRequest, result.getTotalElements() );
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
