package br.com.webvirtua.gestaodesinistros.api.dto;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import br.com.webvirtua.gestaodesinistros.model.entity.Cidade;
import br.com.webvirtua.gestaodesinistros.model.entity.Cliente;
import br.com.webvirtua.gestaodesinistros.model.entity.Endereco;
import br.com.webvirtua.gestaodesinistros.model.entity.Estado;
import br.com.webvirtua.gestaodesinistros.model.entity.Pessoa;
import br.com.webvirtua.gestaodesinistros.model.entity.Telefone;
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
public class ClienteDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1244646979381515229L;
	
	private String telefone;

	private Date dataRegistro;
	
	
	
	public Cliente build() {
		Telefone telefone = new Telefone()
				.setTelefone(this.telefone);
		List<Telefone> telefones = new ArrayList<Telefone>();
		telefones.add(telefone);
		

		
		Cliente cliente = new Cliente()
				.setTelefone(telefones)
				.setDataRegistro(new Date(System.currentTimeMillis()));
		
		return cliente;
	}

}
