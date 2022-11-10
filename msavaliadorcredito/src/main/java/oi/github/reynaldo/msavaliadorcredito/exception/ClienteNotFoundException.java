package oi.github.reynaldo.msavaliadorcredito.exception;

public class ClienteNotFoundException extends Exception{

    public ClienteNotFoundException() {
        super("Cliente não encontrado para o CPF informado");
    }
}
