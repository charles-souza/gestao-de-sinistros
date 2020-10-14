package br.com.webvirtua.gestaodesinistros.service.impl;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.webvirtua.gestaodesinistros.api.dto.CustumerDTO;
import br.com.webvirtua.gestaodesinistros.api.dto.PersonDTO;
import br.com.webvirtua.gestaodesinistros.model.entity.Custumer;
import br.com.webvirtua.gestaodesinistros.model.entity.Person;
import br.com.webvirtua.gestaodesinistros.model.repository.CustumerRepository;
import br.com.webvirtua.gestaodesinistros.model.repository.PersonRepository;
import br.com.webvirtua.gestaodesinistros.service.CustumerService;
import br.com.webvirtua.gestaodesinistros.service.PersonService;
import br.com.webvirtua.gestaodesinistros.utils.Status;
import br.com.webvirtua.gestaodesinistros.utils.ReturnRequest;

@Service
public class CustumerServiceImpl implements CustumerService {

	private CustumerRepository custumerRepository;
	
	private ModelMapper modelMapper;
	
	@Autowired
	private Status status;

	public CustumerServiceImpl(CustumerRepository repository, ModelMapper modelMapper) {
		this.custumerRepository = repository;
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
	public ReturnRequest save(CustumerDTO custumer) {
//		if( repository.existsById(pessoa.getId()) ) {
//			throw new BusinessException("Id já cadastrado.");
//		}
		
		Custumer entity = this.modelMapper.map(custumer, Custumer.class);
		
		Custumer personAdded = custumerRepository.save(entity);
		
		ReturnRequest resultRequest = ReturnRequest.builder()
				.success(1)
				.status(status.getCode201())
				.totalResults(1)
				.successMessage("Cliente inserido com sucesso")
				.data(personAdded)
				.build();

		return resultRequest;
	}

	@Override
	public ReturnRequest getById(Long id) {
		
		Optional<Custumer> person = custumerRepository.findById(id);
		
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
			throw new IllegalArgumentException("Custumer id cant be null.");
		}
		this.custumerRepository.deleteById(id);
		
		ReturnRequest resultRequest = ReturnRequest.builder()
				.success(1)
				.status(status.getCode200())
				.successMessage("Cliente excluído com sucesso")
				.build();
		
		return resultRequest;
	}

	@Override
	public ReturnRequest update(Long id, CustumerDTO custumerDTO) {
		if(custumerDTO == null || id == null) {
			throw new IllegalArgumentException("Pessoa id cant be null.");
		}
		Custumer entity = this.modelMapper.map(custumerDTO, Custumer.class);
		entity.setId(id);
		
		Custumer personUpdated = custumerRepository.save(entity);
		
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
		List<Custumer> custumer = custumerRepository.findAll();

		ReturnRequest resultRequest = ReturnRequest.builder()
				.success(1)
				.status(status.getCode200())
				.totalResults(custumer.size())
				.resultsPerPage(0)
				.totalPages(0)
				.page(0)
				.successMessage("Resultados Obtidos")
				.data(custumer)
				.build();
		
		return resultRequest;
	}	

}
