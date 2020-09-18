package br.com.webvirtua.gestaodesinistros.model.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import br.com.webvirtua.gestaodesinistros.api.dto.ClienteDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@DiscriminatorValue("cliente")
@Accessors(chain = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Cliente extends Pessoa implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8390791364675938263L;

//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private Long id_cliente;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name="telefone_id", referencedColumnName="id")
	private List<Telefone> telefone;
	
	private Date dataRegistro;
	
	public ClienteDTO convertCliente() {		
		
		ClienteDTO clienteDTO = new ClienteDTO()
//				.setTelefone(this.telefone)
				.setDataRegistro(this.dataRegistro);
		
		return clienteDTO;		
	}
}
