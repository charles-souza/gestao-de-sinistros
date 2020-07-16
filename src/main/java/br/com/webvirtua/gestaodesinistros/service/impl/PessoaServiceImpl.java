package br.com.webvirtua.gestaodesinistros.service.impl;

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
		if( repository.existsById(pessoa.getId()) ) {
			throw new BusinessException("Id já cadastrado.");
		}
		return repository.save(pessoa);
	}

}
