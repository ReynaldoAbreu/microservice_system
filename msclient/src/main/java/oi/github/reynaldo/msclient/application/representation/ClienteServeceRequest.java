package oi.github.reynaldo.msclient.application.representation;

import lombok.Data;
import oi.github.reynaldo.msclient.domain.Cliente;

@Data
public class ClienteServeceRequest {

    private String cpf;
    private String nome;
    private Integer idade;

    public Cliente toModel(){
        return new Cliente(cpf, nome, idade);
    }


}
