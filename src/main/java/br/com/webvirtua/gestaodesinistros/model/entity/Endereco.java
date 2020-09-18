package br.com.webvirtua.gestaodesinistros.model.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;


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
public class Endereco implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7223131625370505593L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String cep;
	private String logradouro;
	private String complemento;
	private String bairro;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="endereco_id", referencedColumnName="id_cidade")
	private Cidade cidade;
	
	private Estado uf;
}
