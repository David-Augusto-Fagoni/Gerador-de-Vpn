package br.edu.fateczl.VpnGerador.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.edu.fateczl.VpnGerador.model.Vpn;
import br.edu.fateczl.VpnGerador.model.VpnId;


public interface IVpnRepository extends JpaRepository<Vpn, VpnId>{
    @Query(value = "CALL sp_procVpn(:id, :email)", nativeQuery = true)
    List<Vpn> buscarVpn(@Param("id") String id, @Param("email") String email);
}
