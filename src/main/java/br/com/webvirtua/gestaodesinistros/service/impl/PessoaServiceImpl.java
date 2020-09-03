package br.com.webvirtua.gestaodesinistros.service.impl;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.webvirtua.gestaodesinistros.exception.BusinessException;
import br.com.webvirtua.gestaodesinistros.model.entity.Pessoa;
import br.com.webvirtua.gestaodesinistros.model.repository.PessoaRepository;
import br.com.webvirtua.gestaodesinistros.service.PessoaService;

@Service
public class PessoaServiceImpl implements PessoaService {

	private PessoaRepository repository;

	public PessoaServiceImpl(PessoaRepository repository) {
		this.repository = repository;
	}

	@Override
	public Pessoa save(Pessoa pessoa) {
//		if( repository.existsById(pessoa.getId()) ) {
//			throw new BusinessException("Id já cadastrado.");
//		}
		return repository.save(pessoa);
	}

	@Override
	public Optional<Pessoa> getById(Long id) {
		return this.repository.findById(id);
	}

	@Override
	public void delete(Pessoa pessoa) {
		if(pessoa == null || pessoa.getId() == null) {
			throw new IllegalArgumentException("Pessoa id cant be null.");
		}
		this.repository.delete(pessoa);		
	}

	@Override
	public Pessoa update(Pessoa pessoa) {
		if(pessoa == null || pessoa.getId() == null) {
			throw new IllegalArgumentException("Pessoa id cant be null.");
		}
		return this.repository.save(pessoa);
	}

	@Override
	public Page<Pessoa> find(Pessoa filter, Pageable pageRequest) {
		// TODO Auto-generated method stub
		return null;
	}	

}
