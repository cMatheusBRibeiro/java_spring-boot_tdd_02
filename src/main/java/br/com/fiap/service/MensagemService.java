package br.com.fiap.service;

import br.com.fiap.exception.MensagemNotFoundException;
import br.com.fiap.model.Mensagem;

import java.util.List;
import java.util.UUID;

public interface MensagemService {

    public Mensagem registrarMensagem(Mensagem mensagem);
    public Mensagem obterMensagemPorId(UUID id) throws MensagemNotFoundException;
    public List<Mensagem> obterMensagens();
    public void removerMensagem(UUID id);
    public Mensagem atualizarMensagem(Mensagem mensagem);

}
