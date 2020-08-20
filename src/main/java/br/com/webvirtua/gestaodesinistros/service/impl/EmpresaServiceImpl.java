package br.com.webvirtua.gestaodesinistros.service.impl;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.webvirtua.gestaodesinistros.exception.BusinessException;
import br.com.webvirtua.gestaodesinistros.model.entity.Empresa;
import br.com.webvirtua.gestaodesinistros.model.repository.EmpresaRepository;
import br.com.webvirtua.gestaodesinistros.service.EmpresaService;

@Service
public class EmpresaServiceImpl implements EmpresaService {

	private EmpresaRepository repository;

	public EmpresaServiceImpl(EmpresaRepository repository) {
		this.repository = repository;
	}

	@Override
	public Empresa save(Empresa empresa) {
		if( repository.existsById(empresa.getId()) ) {
			throw new BusinessException("Id já cadastrado.");
		}
		return repository.save(empresa);
	}

	@Override
	public Optional<Empresa> getById(Long id) {
		return this.repository.findById(id);
	}

	@Override
	public void delete(Empresa empresa) {
		if(empresa == null || empresa.getId() == null) {
			throw new IllegalArgumentException("Empresa id cant be null.");
		}
		this.repository.delete(empresa);		
	}

	@Override
	public Empresa update(Empresa empresa) {
		if(empresa == null || empresa.getId() == null) {
			throw new IllegalArgumentException("Empresa id cant be null.");
		}
		return this.repository.save(empresa);
	}

	@Override
	public Page<Empresa> find(Empresa filter, Pageable pageRequest) {
		// TODO Auto-generated method stub
		return null;
	}	

}
