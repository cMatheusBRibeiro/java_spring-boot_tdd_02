package br.com.fiap.service;

import br.com.fiap.helper.MensagemHelper;
import br.com.fiap.model.Mensagem;
import br.com.fiap.repository.MensagemRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
@RequiredArgsConstructor
public class MensagemServiceImpIT {

    private final MensagemRepository mensagemRepository;
    private final MensagemService mensagemService;

    @Test
    public void devePermitirRegistrarMensagem() {
        var mensagem = MensagemHelper.gerarMensagem();

        var mensagemRegistrada = mensagemService.registrarMensagem(mensagem);

        assertThat(mensagemRegistrada)
                .isInstanceOf(Mensagem.class)
                .isNotNull();

        assertThat(mensagemRegistrada.getConteudo())
                .isEqualTo(mensagem.getConteudo());

        assertThat(mensagemRegistrada.getUsuario())
                .isEqualTo(mensagem.getUsuario());

        assertThat(mensagemRegistrada.getId())
                .isNotNull();
    }

}
