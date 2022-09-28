package oi.github.reynaldo.mscartoes.application;


import lombok.RequiredArgsConstructor;
import oi.github.reynaldo.mscartoes.application.representation.CartaoSaveRequest;
import oi.github.reynaldo.mscartoes.domain.Cartao;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cartoes")
@RequiredArgsConstructor
public class CartoesResource {

    private final CartaoService cartaoService;

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



}
