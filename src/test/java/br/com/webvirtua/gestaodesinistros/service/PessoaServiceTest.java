package br.com.webvirtua.gestaodesinistros.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.webvirtua.gestaodesinistros.exception.BusinessException;
import br.com.webvirtua.gestaodesinistros.model.entity.Pessoa;
import br.com.webvirtua.gestaodesinistros.model.repository.PessoaRepository;
import br.com.webvirtua.gestaodesinistros.service.impl.PessoaServiceImpl;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.assertj.core.api.Assertions;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class PessoaServiceTest {

	PessoaService service;

	@MockBean
	PessoaRepository repository;
	
	@BeforeEach
	public void setUp() {
		this.service = new PessoaServiceImpl(repository);
	}

	@Test
	@DisplayName("Deve salvar uma pessoa.")
	public void savePessoaTest() {
		// cenário
		Pessoa pessoa = createValidPessoa();
		Mockito.when( repository.existsById(Mockito.anyLong()) ).thenReturn(false);
		Mockito.when(repository.save(pessoa))
				.thenReturn(Pessoa.builder().id(1l).nome("Charles").sobrenome("Souza").build());

		// execução
		Pessoa savedPessoa = service.save(pessoa);

		// verificação
		assertThat(savedPessoa.getId()).isNotNull();
		assertThat(savedPessoa.getNome()).isEqualTo("Charles");
		assertThat(savedPessoa.getSobrenome()).isEqualTo("Souza");
	}

	private Pessoa createValidPessoa() {
		return Pessoa.builder().id(1l).nome("Charles").sobrenome("Souza").build();
	}
	
	@Test
	@DisplayName("Deve lançar erro de negocio ao tentar salvar uma pessoa com id duplicado.")
	public void shouldNotSaveAPessoaWithDuplicated() {
		//cenario
		Pessoa pessoa = createValidPessoa();
		Mockito.when( repository.existsById(Mockito.anyLong()) ).thenReturn(true);
		
		//execucao
		Throwable exception = Assertions.catchThrowable( () -> service.save(pessoa) );
		
		//verificacoes
		assertThat(exception)
				.isInstanceOf(BusinessException.class)
				.hasMessage("Id já cadastrado.");
		
		Mockito.verify(repository, Mockito.never()).save(pessoa);
	}
	
	@Test
	@DisplayName("Deve obter uma pessoa por Id")
	public void getByIdTest() {
		Long id = 1l;
		
		Pessoa pessoa = createValidPessoa();
		pessoa.setId(id);
		Mockito.when(repository.findById(id)).thenReturn(Optional.of(pessoa));
		
		//execucao
		Optional<Pessoa> foundPessoa = service.getById(id);
		
		//verificacoes
		assertThat( foundPessoa.isPresent() ).isTrue();
		assertThat( foundPessoa.get().getId() ).isEqualTo(id);
		assertThat( foundPessoa.get().getNome() ).isEqualTo(pessoa.getNome());
		assertThat( foundPessoa.get().getSobrenome() ).isEqualTo(pessoa.getSobrenome());
	}
	
	@Test
	@DisplayName("Deve retornar vazio ao obter uma pessoa por Id quando ele não existe na base.")
	public void pessoaNotFoundByIdTest() {
		Long id = 1l;
		
		Mockito.when(repository.findById(id)).thenReturn(Optional.empty());
		
		//execucao
		Optional<Pessoa> foundPessoa = service.getById(id);
		
		//verificacoes
		assertThat( foundPessoa.isPresent() ).isFalse();
	}
	
	@Test
	@DisplayName("Deve deletar uma pessoa.")
	public void deletePessoaTest() {		
		Pessoa pessoa = Pessoa.builder().id(1l).build();
		
		//execucao
		org.junit.jupiter.api.Assertions.assertDoesNotThrow( () -> service.delete(pessoa) ); 
		
		//verificacoes
		Mockito.verify(repository, Mockito.times(1)).delete(pessoa);		
	}
	
	@Test
	@DisplayName("Deve ocorrer erro ao tentar deletar uma pessoa inexistente")
	public void deleteInvalidPessoaTest() {		
		Pessoa pessoa = new Pessoa();
		
		org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class, () -> service.delete(pessoa));
		
		Mockito.verify(repository, Mockito.never()).delete(pessoa);
	}
	
	@Test
	@DisplayName("Deve ocorrer erro ao tentar atualizar uma pessoa inexistente")
	public void updateInvalidPessoaTest() {		
		Pessoa pessoa = new Pessoa();
		
		org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class, () -> service.update(pessoa));
		
		Mockito.verify(repository, Mockito.never()).save(pessoa);
	}
	
	@Test
	@DisplayName("Deve atualizar uma pessoa.")
	public void updatePessoaTest() {		
		long id = 1l;
		
		//pessoa a atualizar
		Pessoa updatingPessoa = Pessoa.builder().id(id).build();
		
		//simulacao
		Pessoa updatedPessoa = createValidPessoa();
		updatedPessoa.setId(id);
		
		Mockito.when(repository.save(updatingPessoa)).thenReturn(updatedPessoa);
		
		//execucao
		Pessoa pessoa = service.update(updatingPessoa);
		
		//verificacoes
		assertThat(pessoa.getId()).isEqualTo(updatedPessoa.getId());
		assertThat(pessoa.getNome()).isEqualTo(updatedPessoa.getNome());
		assertThat(pessoa.getSobrenome()).isEqualTo(updatedPessoa.getSobrenome());
	}

}
