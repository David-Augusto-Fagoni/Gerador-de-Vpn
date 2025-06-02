package br.edu.fateczl.VpnGerador.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.fateczl.VpnGerador.model.Vpn;
import br.edu.fateczl.VpnGerador.model.VpnId;


public interface IVpnRepository extends JpaRepository<Vpn, VpnId>{
	
}
