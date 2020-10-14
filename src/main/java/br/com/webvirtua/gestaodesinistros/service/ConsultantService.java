package br.com.webvirtua.gestaodesinistros.service;

import br.com.webvirtua.gestaodesinistros.api.dto.ConsultantDTO;
import br.com.webvirtua.gestaodesinistros.utils.ReturnRequest;

public interface ConsultantService<C>{

	ReturnRequest save(ConsultantDTO any);

	ReturnRequest getById(Long id);

	ReturnRequest delete(Long id);

	ReturnRequest update(Long id, ConsultantDTO consultantDTO);

	ReturnRequest find();

}
