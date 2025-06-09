package br.edu.fateczl.VpnGerador.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.edu.fateczl.VpnGerador.model.Login;

public interface ILoginRepository extends JpaRepository<Login, String>{
	@Query(value = "SELECT fn_login(?1, ?2)", nativeQuery = true)
	String fn_login(String usuario, String senha);

    @Query(value = "SELECT fn_procUsuario(?1)", nativeQuery = true)
    String fn_procUsuario(String usuario);
}
