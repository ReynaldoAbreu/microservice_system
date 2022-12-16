package oi.github.reynaldo.mscartoes.infra.mqueue;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import oi.github.reynaldo.mscartoes.domain.Cartao;
import oi.github.reynaldo.mscartoes.domain.CartaoCliente;
import oi.github.reynaldo.mscartoes.domain.DadoSolicitacaoEmissaoCartao;
import oi.github.reynaldo.mscartoes.infra.repository.CartaoClienteRepository;
import oi.github.reynaldo.mscartoes.infra.repository.CartaoRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class EmissaoCartaoSubscriber {

    private final CartaoRepository cartaoRepository;
    private  final CartaoClienteRepository cartaoClienteRepository;

    @RabbitListener(queues = "${mq.queues.emissao-cartoes}")
    public void receberSolicitacaoEmissao(@Payload String payload){

        System.out.println(payload);

        try {
            var mapper = new ObjectMapper();
            DadoSolicitacaoEmissaoCartao dados = mapper.readValue(payload, DadoSolicitacaoEmissaoCartao.class);
            Cartao cartao = cartaoRepository.findById(dados.getIdCartao()).orElseThrow();
            CartaoCliente cartaoCliente = new CartaoCliente();
            cartaoCliente.setCartao(cartao);
            cartaoCliente.setCpf(dados.getCpf());
            cartaoCliente.setLimite(dados.getLimiteLiberado());

            cartaoClienteRepository.save(cartaoCliente);

        }catch (Exception e){
            log.info("Erro ao receber solicitacao de emissao de cartoes {} ", e.getMessage());
        }

    }

}
