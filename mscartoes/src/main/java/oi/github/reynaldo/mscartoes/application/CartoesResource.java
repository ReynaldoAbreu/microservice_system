package oi.github.reynaldo.mscartoes.application;


import lombok.RequiredArgsConstructor;
import oi.github.reynaldo.mscartoes.application.representation.CartaoSaveRequest;
import oi.github.reynaldo.mscartoes.domain.Cartao;
import oi.github.reynaldo.mscartoes.domain.CartaoCliente;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cartoes")
@RequiredArgsConstructor
public class CartoesResource {

    private final CartaoService cartaoService;
    private final CartaoClienteService cartaoClienteService;

    @GetMapping
    public String status(){

        return "ok";
    }

    @PostMapping
    public ResponseEntity cadastra( @RequestBody CartaoSaveRequest cartaoSaveRequest){

        //Cartao cartao = cartaoSaveRequest.toModel();
        cartaoService.save(cartaoSaveRequest.toModel());
        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    @GetMapping(params = ("renda"))
    public ResponseEntity<List<Cartao>> getCartoesRendaThan(@RequestParam("renda") Long renda){

        List<Cartao> cartaoList = cartaoService.getCartoesRendaMenorIgual(renda);
        return ResponseEntity.ok(cartaoList);
    }

    @GetMapping(params = "cpf")
    public ResponseEntity<List<CartoesPorClientesResponse>> getCartoesbyCliente(@RequestParam ("cpf") String cpf){
        List<CartaoCliente> cartaoClienteList = cartaoClienteService.listcartoesByCpf(cpf);
        List<CartoesPorClientesResponse> resultList = cartaoClienteList.stream()
                .map(CartoesPorClientesResponse::fromModel)
                .collect(Collectors.toList());

        return ResponseEntity.ok(resultList);

    }



}
