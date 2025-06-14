package br.edu.fateczl.VpnGerador.model;

import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "funcionario")
public class Funcionario {

	@Id
	@Column(name = "email",length = 100, nullable = false)
	private String email;

	@Column(name = "nome", nullable = false)
	private String nome;

	@Column(name = "permissao", nullable = false)
	private String permissao;

	@Column(name = "ativo", nullable = false)
	private boolean ativo;

	@OneToMany(mappedBy = "funcionario")
	private Set<Vpn> vpn;

	@OneToOne(mappedBy = "funcionario", cascade = CascadeType.ALL)
	@PrimaryKeyJoinColumn
	private Login login;

	public Funcionario(String email, String nome, String permissao, Boolean ativo) {
		super();
		this.email = email;
		this.nome = nome;
		this.permissao = permissao;
		this.ativo = ativo;
	}

}
