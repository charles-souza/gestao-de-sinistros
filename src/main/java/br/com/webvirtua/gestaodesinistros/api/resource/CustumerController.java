package br.com.webvirtua.gestaodesinistros.api.resource;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import br.com.webvirtua.gestaodesinistros.api.dto.CustumerDTO;
import br.com.webvirtua.gestaodesinistros.api.dto.PersonDTO;
import br.com.webvirtua.gestaodesinistros.api.exception.ApiErrors;
import br.com.webvirtua.gestaodesinistros.exception.BusinessException;
import br.com.webvirtua.gestaodesinistros.model.entity.Custumer;
import br.com.webvirtua.gestaodesinistros.model.entity.Person;
import br.com.webvirtua.gestaodesinistros.service.CustumerService;
import br.com.webvirtua.gestaodesinistros.service.PersonService;
import br.com.webvirtua.gestaodesinistros.utils.Status;
import br.com.webvirtua.gestaodesinistros.utils.ReturnRequest;

@RestController
@RequestMapping("/v1/custumers")
public class CustumerController {

	private CustumerService<Custumer> service;
	
	@Autowired
	private Status status;
	
	@Autowired
	private HttpServletResponse response;

	public CustumerController(CustumerService<Custumer> service) {
		this.service = service;	
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ReturnRequest create(@RequestBody @Valid CustumerDTO dto) {		
		
		try {
		
			ReturnRequest result = service.save(dto);
			
			ResponseEntity.ok(result.getData());
			response.setStatus(result.getStatus());
			
			return result;
		} catch (Exception e) {
			ReturnRequest resultRequest = ReturnRequest.builder()
					.success(0)
					.status(status.getCode400())
					.errorMessage(e.getMessage())
					.build();
			
			ResponseEntity.badRequest().build();
			response.setStatus(status.getCode400());
			
			return resultRequest;
		}		
	}

	@GetMapping("{id}")
	public ReturnRequest get(@PathVariable Long id) {	
		
			try {
				ReturnRequest result = service.getById(id);
				
				ResponseEntity.ok(result.getData());
				response.setStatus(result.getStatus());
				
				return result;
			} catch (Exception e) {
				ReturnRequest resultRequest = ReturnRequest.builder()
						.success(0)
						.status(status.getCode400())
						.errorMessage(e.getMessage())
						.build();
				
				ResponseEntity.badRequest().build();
				response.setStatus(status.getCode400());
				
				return resultRequest;
			}
	}

	@DeleteMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ReturnRequest delete(@PathVariable Long id) {
		try {
			
			ReturnRequest result = service.delete(id);
			
			ResponseEntity.ok();
			response.setStatus(result.getStatus());
			
			return result;
		} catch (Exception e) {
			ReturnRequest resultRequest = ReturnRequest.builder()
					.success(0)
					.status(status.getCode400())
					.errorMessage(e.getMessage())
					.build();
			
			ResponseEntity.badRequest().build();
			response.setStatus(status.getCode400());
			
			return resultRequest;
		}
	}

	@PutMapping("{id}")
	public ReturnRequest update(@Valid @PathVariable Long id, @RequestBody CustumerDTO dto) {
		try {
//			this.verifyName(user.getName());
//			this.verifyEmail(user.getName());
//			this.verifyPassword(user.getName());
			
			ReturnRequest result = service.update(id, dto);
			
			ResponseEntity.ok(result.getData());
			response.setStatus(result.getStatus());
			
			return result;
		} catch (Exception e) {
			ReturnRequest resultRequest = ReturnRequest.builder()
					.success(0)
					.status(status.getCode400())
					.errorMessage(e.getMessage())
					.build();
			
			ResponseEntity.badRequest().build();
			response.setStatus(status.getCode400());
			
			return resultRequest;
		}
	}

	@GetMapping
	public ReturnRequest find() {
		try {
			ReturnRequest result = service.find();
			
			ResponseEntity.ok(result.getData());
			response.setStatus(result.getStatus());
			
			return result;
		} catch (Exception e) {
			ReturnRequest resultRequest = ReturnRequest.builder()
					.success(0)
					.status(status.getCode400())
					.errorMessage(e.getMessage())
					.build();
		
			ResponseEntity.badRequest().build();
			response.setStatus(status.getCode400());
			
			return resultRequest;
		}
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
