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
public class EmpresaDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1244646979381515229L;

	private Long id;
	
	@NotEmpty
	private String razaoSocial;
	
	@NotEmpty
	private String nomeFantasia;
	
	@NotNull
	private Endereco endereco;
	
	@NotEmpty
	private String cnpj;

}
