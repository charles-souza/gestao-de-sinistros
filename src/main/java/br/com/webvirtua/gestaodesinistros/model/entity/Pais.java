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
import javax.persistence.OneToMany;
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
public class Pais implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3916329331902034790L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_pais;
	
	private String nm_iniciais;
	private String nm_nome;
	private Date dataRegistro;
}
