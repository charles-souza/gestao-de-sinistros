package br.com.webvirtua.gestaodesinistros.api.dto;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class PersonDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1244646979381515229L;
	
	@NotEmpty
	private String name;
	
	@NotEmpty
	private String lastName;
	
//	private Enum<?> type;
	private String street;
	private int number;
	private String complement;
	private String district;
	private String zipcode;
	
	private String city;
	
	private String state;
	
	@NotEmpty
	private String rg;
	private String emitter;
	private String expedition;
	private String cpf;
	private String gender;
	private String email;
	private String maritalStatus;
	private Date birth;
	private Date registerDate;

}
