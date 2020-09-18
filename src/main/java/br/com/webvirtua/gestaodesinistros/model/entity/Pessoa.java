package br.com.webvirtua.gestaodesinistros.model.entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import br.com.webvirtua.gestaodesinistros.api.dto.PessoaDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_pessoa")
@Accessors(chain = true)
@SuperBuilder
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
