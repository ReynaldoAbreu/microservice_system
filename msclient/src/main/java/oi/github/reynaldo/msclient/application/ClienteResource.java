package oi.github.reynaldo.msclient.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import oi.github.reynaldo.msclient.application.representation.ClienteServeceRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;

@RestController
@RequestMapping("clientes")
@RequiredArgsConstructor
@Slf4j
public class ClienteResource {

    private final ClienteServece clienteServece;

    @GetMapping
    public String status(){

        log.info("Obtendo status do microservice de cliente");
        return "ok";
    }

    @PostMapping
    public ResponseEntity save(@RequestBody ClienteServeceRequest request){

        var cliente = request.toModel();
        clienteServece.save(cliente);
        URI headerLocation = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .query("cpf={cpf}")
                .buildAndExpand(cliente.getCpf())
                .toUri();
        return ResponseEntity.created(headerLocation).build();

    }

    @GetMapping(params = "cpf")
    public ResponseEntity dadosDoCliente(@RequestParam("cpf") String cpf){

        var cliente = clienteServece.getByCpf(cpf);
        if (cliente.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cliente);

    }



}
