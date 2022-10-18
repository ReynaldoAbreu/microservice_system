package oi.github.reynaldo.msavaliadorcredito.application;

import lombok.RequiredArgsConstructor;
import oi.github.reynaldo.msavaliadorcredito.domain.model.CartaoCliente;
import oi.github.reynaldo.msavaliadorcredito.domain.model.DadosCliente;
import oi.github.reynaldo.msavaliadorcredito.domain.model.SituacaoCliente;
import oi.github.reynaldo.msavaliadorcredito.infra.CartaoResourceClient;
import oi.github.reynaldo.msavaliadorcredito.infra.ClienteResourceClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AvaliadorCreditoService {

    private final ClienteResourceClient clienteResourceClient;
    private final CartaoResourceClient cartaoResourceClient;

    public SituacaoCliente obterSituacaoCliente(String cpf){
        //objetivo deste metodo:
        //ObterDadosCliente - MSCLIENTES
        //Obter cartao do cliente - MSCARTOES

        ResponseEntity<DadosCliente> dadosClienteResponse = clienteResourceClient.dadosDoCliente(cpf);
        ResponseEntity<List<CartaoCliente>> cartaoClienteResponse = cartaoResourceClient.getCartoesbyCliente(cpf);
        return SituacaoCliente
                .builder()
                .dadosCliente(dadosClienteResponse.getBody())
                .cartaoCliente(cartaoClienteResponse.getBody())
                .build();



    }

}
