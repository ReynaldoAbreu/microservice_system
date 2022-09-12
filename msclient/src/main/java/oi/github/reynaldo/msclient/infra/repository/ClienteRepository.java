package oi.github.reynaldo.msclient.infra.repository;

import oi.github.reynaldo.msclient.domain.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long>{

    Optional<Cliente> findByCpf(String cpf);

}
