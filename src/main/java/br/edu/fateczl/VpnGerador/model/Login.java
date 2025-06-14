package br.edu.fateczl.VpnGerador.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "login")
public class Login {

	@Id
    @Column(name = "funcionario_email", nullable = false, length = 100)
    private String email;

	@Column(name = "usuario",length = 80, nullable = false, unique = true)
	private String usuario;

	@Column(name = "senha", length = 30, nullable = false)
	private String senha;

    @OneToOne(mappedBy = "login", cascade = CascadeType.ALL)
    private TokenRedefinicao tokenRedefinicao;

	@OneToOne
	@JoinColumn(name = "funcionario_email")
	@MapsId
    private Funcionario funcionario;

	public Login(String usuario, String senha, Funcionario funcionario) {
		super();
		this.usuario = usuario;
		this.senha = senha;
		this.funcionario = funcionario;
	}

}
