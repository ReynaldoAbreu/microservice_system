package oi.github.reynaldo.msavaliadorcredito.infra.mqueue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import oi.github.reynaldo.msavaliadorcredito.domain.model.DadoSolicitacaoEmissaoCartao;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SolicitacaoEmissaoCartaoPublisher {

    private final RabbitMessagingTemplate rabbitMessagingTemplate;
    private final Queue queueEmissaoCartoes;

    public void solicitarcartao(DadoSolicitacaoEmissaoCartao dados) throws JsonProcessingException {
        var json = convertToJson(dados);
        rabbitMessagingTemplate.convertAndSend(queueEmissaoCartoes.getName(), json);

    }
    private String convertToJson(DadoSolicitacaoEmissaoCartao dados) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        var json = mapper.writeValueAsString(dados);
        return json;
    }

}
