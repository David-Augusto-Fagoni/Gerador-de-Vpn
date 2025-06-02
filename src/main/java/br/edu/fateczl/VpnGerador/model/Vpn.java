package br.edu.fateczl.VpnGerador.model;

import java.time.LocalDate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "vpn")
@IdClass(VpnId.class)
public class Vpn {
	
	@Id
	@Column(name = "id",length = 10, nullable = false)
	private String id;
	
	@Column(name = "dt_criacao", nullable = false)
	private LocalDate  dt_criacao;
	
	@Column(name = "dt_validade",nullable = false)
	private LocalDate  dt_validade;
	
	@Id
    @ManyToOne(targetEntity = Funcionario.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "funcionarioemail",nullable = false) 
    private Funcionario funcionario;
}
