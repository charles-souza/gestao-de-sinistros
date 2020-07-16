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
				.thenReturn(Pessoa.builder().id((long) 1).nome("Charles").sobrenome("Souza").build());

		// execução
		Pessoa savedPessoa = service.save(pessoa);

		// verificação
		assertThat(savedPessoa.getId()).isNotNull();
		assertThat(savedPessoa.getNome()).isEqualTo("Charles");
		assertThat(savedPessoa.getSobrenome()).isEqualTo("Souza");
	}

	private Pessoa createValidPessoa() {
		return Pessoa.builder().nome("Charles").sobrenome("Souza").build();
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
	
	

}
