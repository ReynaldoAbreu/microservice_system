package oi.github.reynaldo.msavaliadorcredito.application;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import oi.github.reynaldo.msavaliadorcredito.domain.model.*;
import oi.github.reynaldo.msavaliadorcredito.exception.ClienteNotFoundException;
import oi.github.reynaldo.msavaliadorcredito.exception.ErroComunicacaoMicroserviceException;
import oi.github.reynaldo.msavaliadorcredito.exception.ErroSolicitacaoCartaoException;
import oi.github.reynaldo.msavaliadorcredito.infra.CartaoResourceClient;
import oi.github.reynaldo.msavaliadorcredito.infra.ClienteResourceClient;
import oi.github.reynaldo.msavaliadorcredito.infra.mqueue.SolicitacaoEmissaoCartaoPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AvaliadorCreditoService {

    private final ClienteResourceClient clienteResourceClient;
    private final CartaoResourceClient cartaoResourceClient;
    private final SolicitacaoEmissaoCartaoPublisher solicitarCartaoPublisher;

    public SituacaoCliente obterSituacaoCliente(String cpf) throws ClienteNotFoundException, ErroComunicacaoMicroserviceException {
        //objetivo deste metodo:
        //ObterDadosCliente - MSCLIENTES
        //Obter cartao do cliente - MSCARTOES

        try {
            ResponseEntity<DadosCliente> dadosClienteResponse = clienteResourceClient.dadosDoCliente(cpf);
            ResponseEntity<List<CartaoCliente>> cartaoClienteResponse = cartaoResourceClient.getCartoesbyCliente(cpf);
            return SituacaoCliente
                    .builder()
                    .cliente(dadosClienteResponse.getBody())
                    .cartoes(cartaoClienteResponse.getBody())
                    .build();
        }catch (FeignException.FeignClientException e){
            int status = e.status();
            if (HttpStatus.NOT_FOUND.value() == status){
                throw new ClienteNotFoundException();
            }
            throw new ErroComunicacaoMicroserviceException(e.getMessage(), e.status());

        }

    }

    public RetornoAvaliacaoCliente realizarAvaliacao(String cpf, Long renda)
            throws ClienteNotFoundException, ErroComunicacaoMicroserviceException{
        try {

            ResponseEntity<DadosCliente> dadosClienteResponse = clienteResourceClient.dadosDoCliente(cpf);
            ResponseEntity<List<Cartao>> cartaoClienteResponse = cartaoResourceClient.getCartoesRendaThan(renda);

            List<Cartao> cartoes = cartaoClienteResponse.getBody();

            var listacartoesAprovado = cartoes.stream().map(cartao -> {
                DadosCliente dadosCliente = dadosClienteResponse.getBody();

                BigDecimal limiteBasico = cartao.getLimiteBasico();
                BigDecimal idadeBD = BigDecimal.valueOf(
                dadosCliente.getIdade());

                var fator = idadeBD.divide(BigDecimal.valueOf(10));
                BigDecimal limiteAprovado = fator.multiply(limiteBasico);

                CartaoAprovado cartaoAprovado = new CartaoAprovado();
                cartaoAprovado.setCartao(cartao.getName());
                cartaoAprovado.setBandeira(cartao.getBandeira());
                cartaoAprovado.setLimeteAprovado(limiteAprovado);

                return cartaoAprovado;

            }).collect(Collectors.toList());
            return new RetornoAvaliacaoCliente(listacartoesAprovado);

        }catch (FeignException.FeignClientException e){
            int status = e.status();
            if (HttpStatus.NOT_FOUND.value() == status){
                throw new ClienteNotFoundException();
            }
            throw new ErroComunicacaoMicroserviceException(e.getMessage(), e.status());

        }

    }

    public ProtocoloSolicitacaoCartao solicitarEmissaoCartao(DadoSolicitacaoEmissaoCartao dados){
        try {
            solicitarCartaoPublisher.solicitarcartao(dados);
            var protocolo = UUID.randomUUID().toString();
            return new ProtocoloSolicitacaoCartao(protocolo);
        }catch (Exception e){
            throw new ErroSolicitacaoCartaoException(e.getMessage());

        }
    }

}
