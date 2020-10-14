package br.com.webvirtua.gestaodesinistros.model.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
public class City implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3218645645639481121L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_city;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="state_id", referencedColumnName="id_state", insertable=false)
	private State uf;
	
	@Column(name = "initials")
	private String initials;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "metropolitan")
	private boolean metropolitan;
	
	@Column(name = "code")
	private int code;
	
	@Column(name = "registerDate")
	private Date registerDate;
}
