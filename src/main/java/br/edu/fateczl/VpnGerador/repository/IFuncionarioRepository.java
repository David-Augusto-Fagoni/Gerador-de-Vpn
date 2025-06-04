package br.edu.fateczl.VpnGerador.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.fateczl.VpnGerador.model.Funcionario;


public interface IFuncionarioRepository extends JpaRepository<Funcionario, String>{

}
