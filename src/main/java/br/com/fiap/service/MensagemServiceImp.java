package br.com.fiap.service;

import br.com.fiap.exception.MensagemNotFoundException;
import br.com.fiap.model.Mensagem;
import br.com.fiap.repository.MensagemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MensagemServiceImp implements MensagemService {

    private final MensagemRepository mensagemRepository;

    @Override
    public Mensagem registrarMensagem(Mensagem mensagem) {
        mensagem.setId(UUID.randomUUID());
        return mensagemRepository.save(mensagem);
    }

    @Override
    public Mensagem obterMensagemPorId(UUID id) throws MensagemNotFoundException {
        return mensagemRepository.findById(id)
                .orElseThrow(() -> new MensagemNotFoundException("Mensagem não encontrada"));
    }

    @Override
    public List<Mensagem> obterMensagens() {
        return mensagemRepository.findAll();
    }

    @Override
    public boolean removerMensagem(UUID id) throws MensagemNotFoundException {
        if (mensagemRepository.existsById(id)) {
            mensagemRepository.deleteById(id);
            return true;
        }
        throw new MensagemNotFoundException("Mensagem não encontrada");
    }

    @Override
    public Mensagem atualizarMensagem(Mensagem mensagem) {
        return mensagemRepository.save(mensagem);
    }

}
