package br.com.webvirtua.gestaodesinistros.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.webvirtua.gestaodesinistros.model.entity.Address;

public interface AddressRepository extends JpaRepository<Address, Long>{
	@Modifying
    @Query("UPDATE Address a SET a.zipcode=:zipcode WHERE a.idAddress=:id")
    public void updateCity(@Param("id") Long id, @Param("zipcode") String zipcode);
}
