package oi.github.reynaldo.mscartoes.application.representation;

import lombok.Data;
import oi.github.reynaldo.mscartoes.domain.BandeiraCartao;
import oi.github.reynaldo.mscartoes.domain.Cartao;

import java.math.BigDecimal;

@Data
public class CartaoSaveRequest {

    private String name;
    private BandeiraCartao bandeira;
    private BigDecimal renda;
    private BigDecimal limiteBasico;

    public Cartao toModel(){

        return new Cartao(name, bandeira, renda, limiteBasico);

    }

}
