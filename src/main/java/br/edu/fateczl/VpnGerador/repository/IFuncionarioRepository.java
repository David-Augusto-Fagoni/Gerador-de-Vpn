package br.edu.fateczl.VpnGerador.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.edu.fateczl.VpnGerador.model.Funcionario;


public interface IFuncionarioRepository extends JpaRepository<Funcionario, String>{
	@Query("SELECT f FROM Funcionario f WHERE f.nome LIKE %?1%")
	List<Funcionario> fn_procNome(String nome);
}
