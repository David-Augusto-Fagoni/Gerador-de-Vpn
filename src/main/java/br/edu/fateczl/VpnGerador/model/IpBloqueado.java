package br.edu.fateczl.VpnGerador.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "ip_bloqueado")
public class IpBloqueado {

    @Id
    private String ip;
}
