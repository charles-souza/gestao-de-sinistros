package br.com.webvirtua.gestaodesinistros.service;

import br.com.webvirtua.gestaodesinistros.api.dto.CustumerDTO;
import br.com.webvirtua.gestaodesinistros.utils.ReturnRequest;

public interface CustumerService<C>{

	ReturnRequest save(CustumerDTO any);

	ReturnRequest getById(Long id);

	ReturnRequest delete(Long id);

	ReturnRequest update(Long id, CustumerDTO personDTO);

	ReturnRequest find();

}
