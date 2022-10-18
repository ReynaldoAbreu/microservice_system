package oi.github.reynaldo.msavaliadorcredito.application;

import lombok.RequiredArgsConstructor;
import oi.github.reynaldo.msavaliadorcredito.domain.model.DadosCliente;
import oi.github.reynaldo.msavaliadorcredito.domain.model.SituacaoCliente;
import oi.github.reynaldo.msavaliadorcredito.infra.ClienteResourceClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("avaliador-credito")
public class AvaliadorCreditoController {

    private final AvaliadorCreditoService avaliadorcreditoService;

    @GetMapping
    public String status(){
        return "ok";
    }

    @GetMapping(value = "situacao-cliente", params = "cpf")
    public ResponseEntity<SituacaoCliente> consultaSituacaoDoCliente(@RequestParam("cpf") String cpf){

        SituacaoCliente situacaoCliente = avaliadorcreditoService.obterSituacaoCliente(cpf);
        return ResponseEntity.ok(situacaoCliente);


    }


}
