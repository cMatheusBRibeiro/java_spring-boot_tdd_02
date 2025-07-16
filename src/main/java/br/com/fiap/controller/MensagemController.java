package br.com.fiap.controller;

import br.com.fiap.exception.MensagemNotFoundException;
import br.com.fiap.model.Mensagem;
import br.com.fiap.service.MensagemService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/mensagens")
@AllArgsConstructor
public class MensagemController {

    private final MensagemService mensagemService;

    @GetMapping(
            value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity obterMensagemPorId(
            @PathVariable
            String id
    ) {
        try {
            var uuid = UUID.fromString(id);

            var mensagem = mensagemService.obterMensagemPorId(uuid);

            return new ResponseEntity<>(mensagem, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("ID inválido", HttpStatus.BAD_REQUEST);
        } catch (MensagemNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Mensagem> registrarMensagem(
            @Valid
            @RequestBody
            Mensagem mensagem
    ) {
        var mensagemRegistrada = mensagemService.registrarMensagem(mensagem);

        return new ResponseEntity<>(mensagemRegistrada, HttpStatus.CREATED);
    }

}
