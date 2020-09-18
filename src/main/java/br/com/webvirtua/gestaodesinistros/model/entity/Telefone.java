package br.com.webvirtua.gestaodesinistros.model.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
public class Telefone implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2550929378449149238L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String telefone;
}
