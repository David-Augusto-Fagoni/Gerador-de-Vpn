package br.edu.fateczl.VpnGerador.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.fateczl.VpnGerador.model.IpBloqueado;
import br.edu.fateczl.VpnGerador.repository.IIpBloqueado;

@Service
public class LoginAttemptService {

    private static final int MAX_ATTEMPTS = 10;

    @Autowired
    private IIpBloqueado ipBloqueadoRepository;

    private Map<String, Integer> attempts = new ConcurrentHashMap<>();

    public void loginFailed(String ip) {
        if (isBlocked(ip)) {return;}

        int count = attempts.getOrDefault(ip, 0) + 1;
        attempts.put(ip, count);

        if (count >= MAX_ATTEMPTS) {
            IpBloqueado bloqueado = new IpBloqueado();
            bloqueado.setIp(ip);
            ipBloqueadoRepository.save(bloqueado);
        }
    }

    public void loginSucceeded(String ip) {
        attempts.remove(ip); // limpa contador
    }

    public boolean isBlocked(String ip) {
        return ipBloqueadoRepository.existsById(ip);
    }
}