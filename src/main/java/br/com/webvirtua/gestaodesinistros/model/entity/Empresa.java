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
public class Empresa implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4154108861251292342L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String razaoSocial;
	
	private String nomeFantasia;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="endereco_id", referencedColumnName="id")
	private Endereco endereco;
	
	private String cnpj;
	
}
