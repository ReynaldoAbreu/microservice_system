package oi.github.reynaldo.mscartoes.application;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import oi.github.reynaldo.mscartoes.domain.CartaoCliente;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartoesPorClientesResponse {
    private String nome;
    private String bandeira;
    private BigDecimal limiteLiberado;

    public static CartoesPorClientesResponse fromModel(CartaoCliente model){
        return  new CartoesPorClientesResponse(
                model.getCartao().getName(),
                model.getCartao().getBandeira().toString(),
                model.getLimite()
        );
    }

}
