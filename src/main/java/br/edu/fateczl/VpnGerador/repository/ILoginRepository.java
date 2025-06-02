package br.edu.fateczl.VpnGerador.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.fateczl.VpnGerador.model.Login;

public interface ILoginRepository extends JpaRepository<Login, String>{

}
