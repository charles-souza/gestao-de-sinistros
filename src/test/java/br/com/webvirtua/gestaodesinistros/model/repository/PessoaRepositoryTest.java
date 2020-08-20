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

import java.util.Optional;

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
		//Long id = 1l;
		String nome = "Charles";
		Pessoa pessoa = createNewPessoa(nome);
		entityManager.persist(pessoa);
		
		//execucao
		boolean exists = repository.existsById(pessoa.getId());
		
		//verificacao
		assertThat(exists).isTrue();
	}

	private Pessoa createNewPessoa(String nome) {
		return Pessoa.builder().nome(nome).sobrenome("Souza").build();
	}
	
	@Test
	@DisplayName("Deve retornar falso quando não existir uma pessoa na base com o id informado")
	public void returnFalseWhenIdDoesntExist() {
		//cenario
		Long id = 1l;
		
		//execucao
		boolean exists = repository.existsById(id);
		
		//verificacao
		assertThat(exists).isFalse();
	}
	
	@Test
	@DisplayName("Deve obter um livro por id.")
	public void findByIdTest() {
		//cenario
		Pessoa pessoa = createNewPessoa("Charles");
		entityManager.persist(pessoa);
		
		//execucao
		Optional<Pessoa> foundPessoa = repository.findById(pessoa.getId());
		
		//verificacao
		assertThat( foundPessoa.isPresent() ).isTrue();
	}
	
	@Test
	@DisplayName("Deve salvar uma pessoa")
	public void savePessoaTest() {
		
		Pessoa pessoa = createNewPessoa("Idalino");
		
		Pessoa savedPessoa = repository.save(pessoa);
		
		assertThat(savedPessoa.getId()).isNotNull();
	}
	
	@Test
	@DisplayName("Deve deletar uma pessoa")
	public void deletePessoaTest() {

		Pessoa pessoa = createNewPessoa("Charles");
		entityManager.persist(pessoa);
		
		Pessoa foundPessoa = entityManager.find( Pessoa.class, pessoa.getId() );
		
		repository.delete(foundPessoa);
		
		Pessoa deletedPessoa = entityManager.find( Pessoa.class, pessoa.getId() );
		assertThat(deletedPessoa).isNull();
	}
}
