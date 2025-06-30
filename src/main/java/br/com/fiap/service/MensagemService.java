package br.com.fiap.service;

import br.com.fiap.exception.MensagemNotFoundException;
import br.com.fiap.model.Mensagem;

import java.util.List;
import java.util.UUID;

public interface MensagemService {

    Mensagem registrarMensagem(Mensagem mensagem);
    Mensagem obterMensagemPorId(UUID id) throws MensagemNotFoundException;
    List<Mensagem> obterMensagens();
    boolean  removerMensagem(UUID id) throws MensagemNotFoundException;
    Mensagem atualizarMensagem(Mensagem mensagem);

}
