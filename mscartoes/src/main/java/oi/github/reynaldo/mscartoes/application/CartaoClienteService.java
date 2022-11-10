package oi.github.reynaldo.mscartoes.application;

import lombok.RequiredArgsConstructor;
import oi.github.reynaldo.mscartoes.domain.CartaoCliente;
import oi.github.reynaldo.mscartoes.infra.repository.CartaoClienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartaoClienteService {

    private final CartaoClienteRepository cartaoClienteRepository;

    public List<CartaoCliente> listcartoesByCpf(String cpf){
        return cartaoClienteRepository.findByCpf(cpf);
    }
}
