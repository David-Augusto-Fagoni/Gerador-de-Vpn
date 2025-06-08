package br.edu.fateczl.VpnGerador.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.edu.fateczl.VpnGerador.model.Funcionario;


public interface IFuncionarioRepository extends JpaRepository<Funcionario, String>{
	@Query(value = "SELECT * FROM fn_procNome(?1)", nativeQuery = true)
	List<Funcionario> fn_procNome(String nome);
}
