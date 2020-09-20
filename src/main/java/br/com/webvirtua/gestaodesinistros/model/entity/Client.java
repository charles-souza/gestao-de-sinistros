package br.com.webvirtua.gestaodesinistros.model.entity;

import java.io.Serializable;
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
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@DiscriminatorValue("client")
@Accessors(chain = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Client extends Person implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7769427416045450081L;
	
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private Long idCliente;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name="phone_id", referencedColumnName="id")
	private List<Phone> phone;
}
