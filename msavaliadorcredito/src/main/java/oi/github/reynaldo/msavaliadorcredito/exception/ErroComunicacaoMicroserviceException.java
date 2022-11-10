package oi.github.reynaldo.msavaliadorcredito.exception;

import lombok.Getter;

public class ErroComunicacaoMicroserviceException extends Exception{

    @Getter
    private final Integer ststus;

    public ErroComunicacaoMicroserviceException(String msg, Integer ststus) {
        super(msg);
        this.ststus = ststus;
    }

}
