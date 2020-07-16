package br.com.webvirtua.gestaodesinistros.model.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.webvirtua.gestaodesinistros.model.entity.Pessoa;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
public class PessoaRepositoryTest {
	
	@Autowired
	TestEntityManager entityManager;
	
	@Autowired
	PessoaRepository repository;
	
	@Test
	@DisplayName("Deve retornar verdadeiro quando existir uma pessoa na base com o id informado")
	public void returnTrueWhenIdExists() {
		//cenario
		Long id = (long) 1;
		Pessoa pessoa = Pessoa.builder().id(id).nome("Charles").sobrenome("Souza").build();
		entityManager.persist(pessoa);
		
		//execucao
		boolean exists = repository.existsById(id);
		
		//verificacao
		assertThat(exists).isTrue();
	}
	
	@Test
	@DisplayName("Deve retornar falso quando não existir uma pessoa na base com o id informado")
	public void returnFalseWhenIdDoesntExist() {
		//cenario
		Long id = (long) 1;
		
		//execucao
		boolean exists = repository.existsById(id);
		
		//verificacao
		assertThat(exists).isFalse();
	}
}
