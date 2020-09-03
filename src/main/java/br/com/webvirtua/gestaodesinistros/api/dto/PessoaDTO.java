package br.com.webvirtua.gestaodesinistros.api.dto;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import br.com.webvirtua.gestaodesinistros.model.entity.Cidade;
import br.com.webvirtua.gestaodesinistros.model.entity.Endereco;
import br.com.webvirtua.gestaodesinistros.model.entity.Estado;
import br.com.webvirtua.gestaodesinistros.model.entity.Pessoa;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class PessoaDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1244646979381515229L;

//	private Long id;
	
	@NotEmpty
	private String nome;
	
	@NotEmpty
	private String sobrenome;
	
	private String cep;
	private String logradouro;
	private String complemento;
	private String bairro;
	
	private String localidade;
	
	private String uf;
	
	@NotEmpty
	private String rg;
	private String emissor;
	private String expedicao;
	private String cpf;
	private String sexo;
	private String email;
	private String estadoCivil;
	private Date dataNascimento = new Date();
	private Date dataRegistro;
	
	
	public String formatDate() {
		SimpleDateFormat dataFormatada = new SimpleDateFormat("dd/MM/yyyy");
		return dataFormatada.format(this.dataNascimento);
	}
	
	public Pessoa build() {
		Estado estado = new Estado()
				.setNm_nome(this.uf);
		
		Cidade cidade = new Cidade()
				.setNm_nome(this.localidade);
		
		Endereco endereco = new Endereco()
				.setCep(this.cep)
				.setLogradouro(this.logradouro)
				.setComplemento(this.complemento)
				.setBairro(this.bairro)
				.setCidade(cidade)
				.setUf(estado);
		
		Pessoa pessoa = new Pessoa()
				.setNome(this.nome)
				.setSobrenome(this.sobrenome)
				.setEndereco(endereco)
				.setRg(this.rg)
				.setEmissor(this.emissor)
				.setExpedicao(this.expedicao)
				.setCpf(this.cpf)
				.setSexo(this.sexo)
				.setEmail(this.email)
				.setEstadoCivil(this.estadoCivil)
				.setDataNascimento(this.dataNascimento)
				.setDataRegistro(new Date(System.currentTimeMillis()));
		
		return pessoa;
	}

}
