package br.com.webvirtua.gestaodesinistros.model.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Seguradora implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2244800428123985474L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nome;
	
	private String nomeComercial;
	
	private String ramoDeAtividade;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="telefone_id", referencedColumnName="id")
	private Telefone telefone;
	
	private Date dataRegistro;
}
