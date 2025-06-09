package br.edu.fateczl.VpnGerador.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.NamedStoredProcedureQuery;
import jakarta.persistence.OneToOne;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureParameter;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "token_redefinicao")
@NamedStoredProcedureQuery(
	    name = "TokenRedefinicao.sp_del_token",
	    procedureName = "sp_del_token",
	    parameters = {
	        @StoredProcedureParameter(mode = ParameterMode.IN, name = "email", type = String.class)
	    }
	)
public class TokenRedefinicao {

    @Id
    @Column(name = "token", nullable = false, unique = true)
    private String token;

    @OneToOne
    @JoinColumn(name = "login_email", referencedColumnName = "funcionario_email")
    private Login login;

    @Column(name = "expiracao", nullable = false)
    private LocalDateTime expiracao;
}
