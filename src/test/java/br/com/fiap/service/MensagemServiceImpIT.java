package br.com.fiap.service;

import br.com.fiap.exception.MensagemNotFoundException;
import br.com.fiap.helper.MensagemHelper;
import br.com.fiap.model.Mensagem;
import br.com.fiap.repository.MensagemRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
@Sql(scripts = {"/db_load.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = {"/db_clean.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class MensagemServiceImpIT {

    @Autowired
    private MensagemRepository mensagemRepository;
    @Autowired
    private MensagemService mensagemService;

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

    @Test
    public void devePermitirObterMensagemPorId() throws MensagemNotFoundException {
        var id = UUID.fromString("9415c37e-08a0-41f7-b81b-5d93181ffc13");

        var mensagemObtida = mensagemService.obterMensagemPorId(id);

        assertThat(mensagemObtida)
                .isInstanceOf(Mensagem.class)
                .isNotNull();

        assertThat(mensagemObtida.getConteudo())
                .isNotNull();

        assertThat(mensagemObtida.getUsuario())
                .isNotNull();

        assertThat(mensagemObtida.getId())
                .isNotNull();
    }

    @Test
    public void devePermitirRemoverMensagemPorId() throws MensagemNotFoundException {
        var id = UUID.fromString("e6aefd1f-55f7-46ad-b178-d77039b73997");

        var mensagemRemovida = mensagemService.removerMensagem(id);

        assertThat(mensagemRemovida)
                .isTrue();
    }

}
