package oi.github.reynaldo.msavaliadorcredito.application;

import lombok.RequiredArgsConstructor;
import oi.github.reynaldo.msavaliadorcredito.domain.model.*;
import oi.github.reynaldo.msavaliadorcredito.exception.ClienteNotFoundException;
import oi.github.reynaldo.msavaliadorcredito.exception.ErroComunicacaoMicroserviceException;
import oi.github.reynaldo.msavaliadorcredito.exception.ErroSolicitacaoCartaoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<?> consultarSituacaoDoCliente(@RequestParam("cpf") String cpf){

        try {

            SituacaoCliente situacaoCliente = avaliadorcreditoService.obterSituacaoCliente(cpf);
            return ResponseEntity.ok(situacaoCliente);

        }catch (ClienteNotFoundException e){
            return ResponseEntity.notFound().build();
        }catch (ErroComunicacaoMicroserviceException e){
            assert HttpStatus.resolve(e.getStstus()) != null;
            return ResponseEntity.status(HttpStatus.resolve(e.getStstus())).body(e.getMessage());
        }

    }

    @PostMapping
    public ResponseEntity realizarAvaliacao(@RequestBody DadosAvaliacao dados){

        try {

            RetornoAvaliacaoCliente retornoAvaliacaoCliente = avaliadorcreditoService
                    .realizarAvaliacao(dados.getCpf(), dados.getRenda());
            return ResponseEntity.ok(retornoAvaliacaoCliente);

        }catch (ClienteNotFoundException e){
            return ResponseEntity.notFound().build();
        }catch (ErroComunicacaoMicroserviceException e){
            assert HttpStatus.resolve(e.getStstus()) != null;
            return ResponseEntity.status(HttpStatus.resolve(e.getStstus())).body(e.getMessage());
        }

    }

    @PostMapping("solicitacoes-cartao")
    public ResponseEntity solicitarCartao(@RequestBody DadoSolicitacaoEmissaoCartao dados){

        try {

            ProtocoloSolicitacaoCartao protocolo = avaliadorcreditoService.solicitarEmissaoCartao(dados);
            return ResponseEntity.ok(protocolo);

        }catch (ErroSolicitacaoCartaoException e){

            return ResponseEntity.internalServerError().body(e.getMessage());
        }

    }

}
