package br.edu.fateczl.VpnGerador.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.fateczl.VpnGerador.model.IpBloqueado;

public interface IIpBloqueado extends JpaRepository<IpBloqueado, String>{
	
}
