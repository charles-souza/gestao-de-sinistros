package br.com.webvirtua.gestaodesinistros.model.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
public class Cidade implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3218645645639481121L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_cidade;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="cidade_id", referencedColumnName="id_estado")
	private Estado uf;
	
	private String nm_iniciais;
	private String nm_nome;
	private boolean nu_metropolitana;
	private int codigo;
	private Date dataRegistro;
}
