package oi.github.reynaldo.mscartoes.infra.repository;

import oi.github.reynaldo.mscartoes.domain.CartaoCliente;
import org.apache.catalina.LifecycleState;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartaoClienteRepository extends JpaRepository<CartaoCliente, Long> {

    List<CartaoCliente> findByCpf(String cpf);

}
