package oi.github.reynaldo.msclient.application;

import lombok.RequiredArgsConstructor;
import oi.github.reynaldo.msclient.domain.Cliente;
import oi.github.reynaldo.msclient.infra.repository.ClienteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClienteServece {

    private final ClienteRepository clienteRepository;

    @Transactional
    public Cliente save(Cliente cliente){

        return clienteRepository.save(cliente);

    }

    public Optional<Cliente> getByCpf(String cpf){

        return clienteRepository.findByCpf(cpf);
    }
}
