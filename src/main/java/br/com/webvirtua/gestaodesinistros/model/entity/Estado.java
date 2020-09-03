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
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Estado implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2731473028954230434L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_estado;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="estado_id", referencedColumnName="id_pais")
	private Pais pais;
	
	private String nm_iniciais;
	private String nm_nome;
	private Date dataRegistro;
}
