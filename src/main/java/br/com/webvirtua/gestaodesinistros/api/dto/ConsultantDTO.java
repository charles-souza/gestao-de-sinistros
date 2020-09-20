package br.com.webvirtua.gestaodesinistros.api.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotEmpty;

import br.com.webvirtua.gestaodesinistros.model.entity.Phone;
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
public class ConsultantDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1244646979381515229L;
	
	@NotEmpty
	private String name;
	
	@NotEmpty
	private String lastName;
	
//	private String phone;
//	private String areaCode;
//	private String whatsapp;
//	private List<Phone> phone;
	
	private String password;
	
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
