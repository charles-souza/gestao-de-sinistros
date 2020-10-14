package br.com.webvirtua.gestaodesinistros.model.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "type_person")
@Accessors(chain = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "api_person", uniqueConstraints=@UniqueConstraint(columnNames="id", name="PK_ID_PERSON"))
public class Person implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 989935518526943227L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seq_id_person")
	private Long id;
	
	@NotBlank
	@Size(max = 100)
	@Column(name = "first_name")
	private String firstName;
	
	@NotBlank
	@Size(max = 100)
	@Column(name = "last_name")
	private String lastName;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="address_id", referencedColumnName="id_address")
	private Address address;
	
	@Column(name = "rg")
	private String rg;
	
	@Column(name = "emitter")
	private String emitter;
	
	@Column(name = "expetition")
	private String expedition;
	
	@NotBlank
	@Size(max = 14)
	@Column(name = "cpf")
	private String cpf;
	
	@Column(name = "gender")
	private String gender;
	
	@NotBlank
	@Email(message = "E-mail inválido")
	@Size(max = 120)
	@Column(name = "email")
	private String email;
	
	@Column(name = "marital_status")
	private String maritalStatus;
	
	@Column(name = "birth_day")
	private Date birthDay;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "register_date")
	private Date registerDate;
}
