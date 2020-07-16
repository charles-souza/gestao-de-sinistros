package br.com.webvirtua.gestaodesinistros.api.dto;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PessoaDTO {
	
	private Long id;
	
	@NotEmpty
	private String nome;
	
	@NotEmpty
	private String sobrenome;
//	private String rg;
//	private String emissor;
//	private String expedicao;
//	private String cpf;
//	private String sexo;
//	private String email;
//	private String estadoCivil;
//	private Date dataNascimento;
//	private Date dataRegistro;

}
