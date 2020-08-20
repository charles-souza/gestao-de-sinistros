package br.com.webvirtua.gestaodesinistros.api.dto;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import br.com.webvirtua.gestaodesinistros.model.entity.Endereco;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PessoaDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1244646979381515229L;

	private Long id;
	
	@NotEmpty
	private String nome;
	
	@NotEmpty
	private String sobrenome;
	
	private Endereco endereco;
	
	@NotEmpty
	private String rg;
	private String emissor;
	private String expedicao;
	private String cpf;
	private String sexo;
	private String email;
	private String estadoCivil;
	private Date dataNascimento;
	private Date dataRegistro;

}
