package com.squad.cadastro.controller;

import com.squad.cadastro.controller.dto.Cliente;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping
@CrossOrigin("https://frontend-register-app-squad-cadastro.vercel.app/")
public class CadastroController {
    DocumentoValidatorInterface documentoValidatorInterface;

    public CadastroController(DocumentoValidatorInterface documentoValidatorInterface) {
        this.documentoValidatorInterface = documentoValidatorInterface;
    }

    @GetMapping("/cep/{cep}")
    public EnderecoResponse buscarEndereco(@PathVariable String cep){
        return new RestTemplate().getForEntity("https://viacep.com.br/ws/"+ cep +"/json/", EnderecoResponse.class).getBody();
    }

    @PostMapping("/sum/{a}/{b}")
    public String sum(@PathVariable Integer a, @PathVariable Integer b) {
        return String.valueOf(a+b);
    }

    @PostMapping("/clientes")
    public Cliente create(@RequestBody Cliente cliente){
        cliente.setId(String.valueOf(UUID.randomUUID()));
        var result = documentoValidatorInterface.validarCPF(cliente.getDocumento());
        if (!result){
            System.out.println("Documento invalido");
        }
        return cliente;
    }
}
