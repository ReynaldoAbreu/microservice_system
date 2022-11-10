package oi.github.reynaldo.mscartoes.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class DadoSolicitacaoEmissaoCartao {
    
    private Long idCartao;
    private String cpf;
    private String endereco;
    private BigDecimal limiteLiberado;
    
}