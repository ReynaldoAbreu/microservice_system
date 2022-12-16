package oi.github.reynaldo.mscartoes.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DadoSolicitacaoEmissaoCartao {
    
    private Long idCartao;
    private String cpf;
    private String endereco;
    private BigDecimal limiteLiberado;
    
}