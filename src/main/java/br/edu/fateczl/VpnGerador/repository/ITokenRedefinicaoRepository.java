package br.edu.fateczl.VpnGerador.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.edu.fateczl.VpnGerador.model.TokenRedefinicao;

@Repository
public interface ITokenRedefinicaoRepository extends JpaRepository<TokenRedefinicao, String> {
	@Procedure(name = "dbo.sp_del_token")
	void sp_del_token(@Param("email") String email);
}
