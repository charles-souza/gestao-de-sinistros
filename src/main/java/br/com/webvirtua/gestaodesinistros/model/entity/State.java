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
public class State implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2731473028954230434L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_state;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="state_id", referencedColumnName="id_country")
	private Country country;
	
	private String initials;
	private String name;
	private Date registerDate;
}
