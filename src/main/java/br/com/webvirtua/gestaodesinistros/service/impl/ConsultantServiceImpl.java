package br.com.webvirtua.gestaodesinistros.service.impl;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.webvirtua.gestaodesinistros.api.dto.ConsultantDTO;
import br.com.webvirtua.gestaodesinistros.api.dto.PersonDTO;
import br.com.webvirtua.gestaodesinistros.model.entity.Consultant;
import br.com.webvirtua.gestaodesinistros.model.entity.Person;
import br.com.webvirtua.gestaodesinistros.model.repository.ConsultantRepository;
import br.com.webvirtua.gestaodesinistros.model.repository.PersonRepository;
import br.com.webvirtua.gestaodesinistros.service.ConsultantService;
import br.com.webvirtua.gestaodesinistros.service.PersonService;
import br.com.webvirtua.gestaodesinistros.utils.Status;
import br.com.webvirtua.gestaodesinistros.utils.ReturnRequest;

@Service
public class ConsultantServiceImpl implements ConsultantService {

	private ConsultantRepository consultantRepository;
	
	private ModelMapper modelMapper;
	
	@Autowired
	private Status status;

	public ConsultantServiceImpl(ConsultantRepository repository, ModelMapper modelMapper) {
		this.consultantRepository = repository;
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
	public ReturnRequest save(ConsultantDTO consultant) {
//		if( repository.existsById(pessoa.getId()) ) {
//			throw new BusinessException("Id já cadastrado.");
//		}
		
		Consultant entity = this.modelMapper.map(consultant, Consultant.class);
		
		Consultant consultantAdded = consultantRepository.save(entity);
		
		ReturnRequest resultRequest = ReturnRequest.builder()
				.success(1)
				.status(status.getCode201())
				.totalResults(1)
				.successMessage("Usuário inserido com sucesso")
				.data(consultantAdded)
				.build();

		return resultRequest;
	}

	@Override
	public ReturnRequest getById(Long id) {
		
		Optional<Consultant> consultant = consultantRepository.findById(id);
		
		ReturnRequest resultRequest = ReturnRequest.builder()
				.success(1)
				.status(status.getCode200())
				.totalResults(1)
				.successMessage("Resultados Obtidos")
				.data(consultant)
				.build();
		
		return resultRequest;
	}

	@Override
	public ReturnRequest delete(Long id) {
		if(id == null) {
			throw new IllegalArgumentException("Pessoa id cant be null.");
		}
		this.consultantRepository.deleteById(id);
		
		ReturnRequest resultRequest = ReturnRequest.builder()
				.success(1)
				.status(status.getCode200())
				.successMessage("Usuário excluído com sucesso")
				.build();
		
		return resultRequest;
	}

	@Override
	public ReturnRequest update(Long id, ConsultantDTO consultantDTO) {
		if(consultantDTO == null || id == null) {
			throw new IllegalArgumentException("Pessoa id cant be null.");
		}
		Consultant entity = this.modelMapper.map(consultantDTO, Consultant.class);
		entity.setId(id);
		
		Consultant consultantUpdated = consultantRepository.save(entity);
		
		ReturnRequest resultRequest = ReturnRequest.builder()
				.success(1)
				.status(status.getCode200())
				.totalResults(1)
				.successMessage("Usuário alterado com sucesso")
				.data(consultantUpdated)
				.build();
		
		return resultRequest;
	}

	@Override
	public ReturnRequest find() {
		List<Consultant> consultants = consultantRepository.findAll();

		ReturnRequest resultRequest = ReturnRequest.builder()
				.success(1)
				.status(status.getCode200())
				.totalResults(consultants.size())
				.resultsPerPage(0)
				.totalPages(0)
				.page(0)
				.successMessage("Resultados Obtidos")
				.data(consultants)
				.build();
		
		return resultRequest;
	}	

}
