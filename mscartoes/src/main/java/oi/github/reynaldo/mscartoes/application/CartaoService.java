package oi.github.reynaldo.mscartoes.application;

import lombok.RequiredArgsConstructor;
import oi.github.reynaldo.mscartoes.domain.Cartao;
import oi.github.reynaldo.mscartoes.infra.CartaoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartaoService {

    final CartaoRepository cartaoRepository;

    @Transactional
    public Cartao  save(Cartao cartao){

       return cartaoRepository.save(cartao);

    }

    public List<Cartao> getCartoesRendaMenorIgual(Long renda){
        var rendaBigDecimal = BigDecimal.valueOf(renda);
        return cartaoRepository.findByRendaLessThanEqual(rendaBigDecimal);
    }

    public List<Cartao> findAllCartoes(){
        return cartaoRepository.findAll();
    }


}
