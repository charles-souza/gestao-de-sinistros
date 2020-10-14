package br.com.webvirtua.gestaodesinistros.api.dto;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotEmpty;

import br.com.webvirtua.gestaodesinistros.model.entity.Address;
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
	
	private String firstName;	
	private String lastName;	
	private Address address;	
	private Long city_id;	
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
