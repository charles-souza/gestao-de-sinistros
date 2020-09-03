package br.com.webvirtua.gestaodesinistros.model.entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import br.com.webvirtua.gestaodesinistros.api.dto.PessoaDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Pessoa implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 989935518526943227L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nome;
	
	private String sobrenome;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="endereco_id", referencedColumnName="id")
	private Endereco endereco;
	
	private String rg;
	private String emissor;
	private String expedicao;
	private String cpf;
	private String sexo;
	private String email;
	private String estadoCivil;
	private Date dataNascimento;
	private Date dataRegistro;
	
	public String formatDate() {
		SimpleDateFormat dataFormatada = new SimpleDateFormat("dd/MM/yyyy");
		return dataFormatada.format(this.dataNascimento);
	}
	
	public PessoaDTO convert() {		
		
		PessoaDTO pessoaDTO = new PessoaDTO()
				.setNome(this.nome)
				.setSobrenome(this.sobrenome)
				.setCep(this.endereco.getCep())
				.setLogradouro(this.endereco.getLogradouro())
				.setComplemento(this.endereco.getComplemento())
				.setBairro(this.endereco.getBairro())
				.setLocalidade(this.endereco.getCidade().getNm_nome())
				.setUf(this.endereco.getUf().getNm_nome())
				.setRg(this.rg)
				.setEmissor(this.emissor)
				.setExpedicao(this.expedicao)
				.setCpf(this.cpf)
				.setSexo(this.sexo)
				.setEmail(this.email)
				.setEstadoCivil(this.estadoCivil)
				.setDataNascimento(this.dataNascimento)
				.setDataRegistro(this.dataRegistro);
		
		return pessoaDTO;		
	}
}
