package br.com.webvirtua.gestaodesinistros.api.resource;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.webvirtua.gestaodesinistros.api.dto.PessoaDTO;
import br.com.webvirtua.gestaodesinistros.api.exception.ApiErrors;
import br.com.webvirtua.gestaodesinistros.exception.BusinessException;
import br.com.webvirtua.gestaodesinistros.model.entity.Pessoa;
import br.com.webvirtua.gestaodesinistros.service.PessoaService;

@RestController
@RequestMapping("/api/pessoas")
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
		Pessoa entity = modelMapper.map( dto, Pessoa.class );
		
		entity = service.save(entity);
		
		return modelMapper.map( entity, PessoaDTO.class );
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
