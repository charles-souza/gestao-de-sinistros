package br.com.webvirtua.gestaodesinistros.api.resource;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.awt.print.Book;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.assertj.core.util.Arrays;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.webvirtua.gestaodesinistros.api.dto.PessoaDTO;
import br.com.webvirtua.gestaodesinistros.exception.BusinessException;
import br.com.webvirtua.gestaodesinistros.model.entity.Pessoa;
import br.com.webvirtua.gestaodesinistros.service.PessoaService;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest
@AutoConfigureMockMvc
public class PessoaControllerTest {

	static String PESSOA_API = "/api/pessoas";

	@Autowired
	MockMvc mvc;

	@MockBean
	PessoaService service;

	@Test
	@DisplayName("Deve criar um livro com sucesso.")
	public void createPessoaTest() throws Exception {

		PessoaDTO dto = createNewPessoa();
		Pessoa savedPessoa = Pessoa.builder().id(1L).nome("Charles").sobrenome("Souza").build();

		BDDMockito.given(service.save(Mockito.any(Pessoa.class))).willReturn(savedPessoa);

		String json = new ObjectMapper().writeValueAsString(dto);

		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(PESSOA_API)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(json);

		mvc.perform(request).andExpect(MockMvcResultMatchers.status().isCreated())
				.andExpect(jsonPath("id").isNotEmpty())
				.andExpect(jsonPath("nome").value(dto.getNome()))
				.andExpect(jsonPath("sobrenome").value(dto.getSobrenome()))
//			.andExpect( MockMvcResultMatchers.jsonPath("rg").value("5000000") )
//			.andExpect( MockMvcResultMatchers.jsonPath("emissor").value("SPTC-GO") )
//			.andExpect( MockMvcResultMatchers.jsonPath("expedicao").value("Goiania") )
//			.andExpect( MockMvcResultMatchers.jsonPath("cpf").value("00000000000") )
//			.andExpect( MockMvcResultMatchers.jsonPath("sexo").value("Masculino") )
//			.andExpect( MockMvcResultMatchers.jsonPath("email").value("charles@gmail.com") )
//			.andExpect( MockMvcResultMatchers.jsonPath("estadoCivil").value("Solteiro") )
//			.andExpect( MockMvcResultMatchers.jsonPath("dataNascimento").value("22-01-1990") )
//			.andExpect( MockMvcResultMatchers.jsonPath("dataRegistro").value("05-07-2020") )
		;
	}

	@Test
	@DisplayName("Deve lançar erro de validação quando não houver dados suficientes para criação da pessoa.")
	public void createInvalidPessoaTest() throws Exception {

		String json = new ObjectMapper().writeValueAsString(new PessoaDTO());

		MockHttpServletRequestBuilder request = MockMvcRequestBuilders
				.post(PESSOA_API)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(json);
		
		mvc.perform(request).andExpect( status().isBadRequest() )
		.andExpect( jsonPath("errors", hasSize(2)))		
		;
	}
	
	@Test
	@DisplayName("Deve lançar erro ao tentar cadastrar uma pessoa com id já utilizado.")
	public void createPessoaWithDuplicatedId() throws Exception {
		
		PessoaDTO dto = createNewPessoa();		
		String json = new ObjectMapper().writeValueAsString(dto);
		String mensagemErro = "Id já cadastrado.";
		BDDMockito.given(service.save(Mockito.any(Pessoa.class))).willThrow(new BusinessException(mensagemErro));

		MockHttpServletRequestBuilder request = MockMvcRequestBuilders
				.post(PESSOA_API)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(json);
		
		mvc.perform(request).andExpect( status().isBadRequest() )
		.andExpect( jsonPath("errors", hasSize(1)))		
		.andExpect( jsonPath( "errors[0]").value(mensagemErro) );
	}
	
	@Test
	@DisplayName("Deve obter informações de uma pessoa.")
	public void getPessoaDetailsTest() throws Exception{
		//cenario (given)
		Long id = 1L;
		
		Pessoa pessoa = Pessoa.builder()
						.id(id)
						.nome(createNewPessoa().getNome())
						.sobrenome(createNewPessoa().getSobrenome())
						.build();
		BDDMockito.given(service.getById(id)).willReturn(Optional.of(pessoa));
		
		//execucao (when)
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders
						.get(PESSOA_API.concat("/"+id))
						.accept(MediaType.APPLICATION_JSON);
		
		mvc
			.perform(request)
			.andExpect(status().isOk())
			.andExpect(jsonPath("id").value(id))
			.andExpect(jsonPath("nome").value(createNewPessoa().getNome()))
			.andExpect(jsonPath("sobrenome").value(createNewPessoa().getSobrenome()))
		;
	}
	
	@Test
	@DisplayName("Deve retornar resource not found quando a pessoa procurada não existir")
	public void pessoaNotFoundTest() throws Exception{
		
		BDDMockito.given( service.getById( Mockito.anyLong()) ).willReturn( Optional.empty() );
		
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders
				.get(PESSOA_API.concat("/"+ 1))
				.accept(MediaType.APPLICATION_JSON);
		
		mvc
			.perform(request)
			.andExpect( status().isNotFound());
	}
	
	@Test
	@DisplayName("Deve deletar uma pessoa")
	public void deletePessoaTest() throws Exception {
		
		BDDMockito.given( service.getById( Mockito.anyLong()) ).willReturn(Optional.of(Pessoa.builder().id(1l).build()));
		
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders
				.delete(PESSOA_API.concat("/"+ 1));
		
		mvc.perform( request )
			.andExpect( status().isNoContent() );
		
	}
	
	@Test
	@DisplayName("Deve retornar resource not found quando não encontrar a pessoa para deletar")
	public void deleteInexistentPessoaTest() throws Exception {
		
		BDDMockito.given( service.getById( Mockito.anyLong()) ).willReturn(Optional.empty());
		
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders
				.delete(PESSOA_API.concat("/"+ 1));
		
		mvc.perform( request )
			.andExpect( status().isNotFound() );
		
	}
	
	@Test
	@DisplayName("Deve atualizar uma pessoa")
	public void updatePessoaTest() throws Exception {
		Long id = 1l;
		String json = new ObjectMapper().writeValueAsString(createNewPessoa());
		
		Pessoa updatingPessoa = Pessoa.builder().id(1l).nome("maria").sobrenome("jose").build();
		BDDMockito.given( service.getById(id) )
				.willReturn(Optional.of(updatingPessoa));
		
		Pessoa updatedPessoa = Pessoa.builder().id(id).nome("Charles").sobrenome("Souza").build();
		BDDMockito.given(service.update(updatingPessoa)).willReturn(updatedPessoa);
		
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders
				.put(PESSOA_API.concat("/"+ 1))
				.content(json)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);
		
		mvc.perform( request )
			.andExpect( status().isOk() )
			.andExpect(jsonPath("id").value(id))
			.andExpect(jsonPath("nome").value(createNewPessoa().getNome()))
			.andExpect(jsonPath("sobrenome").value(createNewPessoa().getSobrenome()));
	}
	
	@Test
	@DisplayName("Deve retornar 404 ao tentar atualizar uma pessoa inexistente")
	public void updateInexistentPessoaTest() throws Exception {
		
		String json = new ObjectMapper().writeValueAsString(createNewPessoa());
		
		BDDMockito.given( service.getById( Mockito.anyLong() ) )
				.willReturn(Optional.empty());
		
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders
				.put(PESSOA_API.concat("/"+ 1))
				.content(json)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);
		
		mvc.perform( request )
			.andExpect( status().isNotFound() );
	}
	
	@Test
	@DisplayName("Deve filtrar pessoas")
	public void findPessoasTest() throws Exception {
		
		Long id = 1l;
		
		Pessoa pessoa = Pessoa.builder()
						.id(id)
						.nome(createNewPessoa().getNome())
						.sobrenome(createNewPessoa().getSobrenome())
						.build();
		
		List<Pessoa> listPessoas = new ArrayList<Pessoa>();
		
		BDDMockito.given( service.find(Mockito.any(Pessoa.class), Mockito.any(Pageable.class)) )
				.willReturn( new PageImpl<Pessoa>( listPessoas, PageRequest.of(0, 100), 1) );
				
		String queryString = String.format("?nome=%s&sobrenome=%s&page=0&size=100",
				pessoa.getNome(), pessoa.getSobrenome());
		
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders
					.get(PESSOA_API.concat(queryString))
					.accept(MediaType.APPLICATION_JSON);
		
		mvc
			.perform( request )
			.andExpect( status().isOk() )
			.andExpect(jsonPath("content", Matchers.hasSize(1)))
			.andExpect( jsonPath("totalElements").value(1) )
			.andExpect( jsonPath("pageable.pageSize").value(100) )
			.andExpect( jsonPath("pageable.pageNumber").value(0) )
			;
		
	}	
	
	private PessoaDTO createNewPessoa() {
		return PessoaDTO.builder().nome("Charles").sobrenome("Souza").build();
	}

}
