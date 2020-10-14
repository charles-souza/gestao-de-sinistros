package br.com.webvirtua.gestaodesinistros.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.webvirtua.gestaodesinistros.model.entity.City;

public interface CityRepository extends JpaRepository<City, Long>{
	
}
