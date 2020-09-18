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

import br.com.webvirtua.gestaodesinistros.api.dto.EmpresaDTO;
import br.com.webvirtua.gestaodesinistros.api.exception.ApiErrors;
import br.com.webvirtua.gestaodesinistros.exception.BusinessException;
import br.com.webvirtua.gestaodesinistros.model.entity.Empresa;
import br.com.webvirtua.gestaodesinistros.service.EmpresaService;

@RestController
@RequestMapping("/api/empresas/v1")
public class EmpresaController {
	
	private EmpresaService service;
	private ModelMapper modelMapper;
	
	public EmpresaController(EmpresaService service, ModelMapper mapper) {
		this.service = service;
		this.modelMapper = mapper;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public EmpresaDTO create( @RequestBody @Valid EmpresaDTO dto ) {
		Empresa entity = modelMapper.map( dto, Empresa.class );
		
		entity = service.save(entity);
	
		return modelMapper.map( entity, EmpresaDTO.class );
	}
	
	@GetMapping("{id}")
	public EmpresaDTO get(@PathVariable Long id) {
		
		return service
				.getById(id)
				.map( empresa -> modelMapper.map(empresa, EmpresaDTO.class) )
				.orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND) );	
	}
	
	@DeleteMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		Empresa empresa = service.getById(id).orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		service.delete(empresa);
	}
	
	@PutMapping("{id}")
	public EmpresaDTO update( @PathVariable Long id, @RequestBody EmpresaDTO dto ) {
		return service.getById(id).map( empresa -> { 
			
			empresa.setRazaoSocial(dto.getRazaoSocial());
			empresa.setNomeFantasia(dto.getNomeFantasia());
			empresa.setEndereco(dto.getEndereco());
			empresa.setCnpj(dto.getCnpj());
			empresa = service.update(empresa);
			return modelMapper.map(empresa, EmpresaDTO.class);
			
			
		} ).orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND));			
	}
	
	@GetMapping
	public Page<EmpresaDTO> find( EmpresaDTO dto, Pageable pageRequest ) {
		Empresa filter = modelMapper.map(dto, Empresa.class);
		Page<Empresa> result = service.find(filter, pageRequest);
		List<EmpresaDTO> list = result.getContent()
				.stream()
				.map( entity -> modelMapper.map(entity, EmpresaDTO.class) )
				.collect( Collectors.toList());
		
		return new PageImpl<EmpresaDTO>( list, pageRequest, result.getTotalElements() );
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
