package br.com.fiap.service;

import br.com.fiap.exception.MensagemNotFoundException;
import br.com.fiap.helper.MensagemHelper;
import br.com.fiap.model.Mensagem;
import br.com.fiap.repository.MensagemRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class MensagemServiceImpTest {

    @Mock
    private MensagemRepository mensagemRepository;
    private MensagemService mensagemService;

    private AutoCloseable mock;

    @BeforeEach
    public void setup() {
        mock = MockitoAnnotations.openMocks(this);
        mensagemService = new MensagemServiceImp(mensagemRepository);
    }

    @AfterEach
    public void tearDown() throws Exception {
        mock.close();
    }

    @Test
    public void devePermitirRegistrarMensagem() {
        var mensagem = MensagemHelper.gerarMensagem();
        when(mensagemRepository.save(any(Mensagem.class)))
                .thenAnswer(i -> i.getArgument(0));

        var mensagemRegistrada = mensagemService.registrarMensagem(mensagem);

        verify(mensagemRepository, times(1))
                .save(any(Mensagem.class));
    }

    @Test
    public void deveRetornarUmObjetoNaoNuloAoRegistrarMensagem() {
        var mensagem = MensagemHelper.gerarMensagem();
        when(mensagemRepository.save(any(Mensagem.class)))
                .thenAnswer(i -> i.getArgument(0));

        var mensagemRegistrada = mensagemService.registrarMensagem(mensagem);

        assertThat(mensagemRegistrada).isNotNull();
    }

    @Test
    public void deveRetornarUmObjetoMensagemAoRegistrarMensagem() {
        var mensagem = MensagemHelper.gerarMensagem();
        when(mensagemRepository.save(any(Mensagem.class)))
                .thenAnswer(i -> i.getArgument(0));

        var mensagemRegistrada = mensagemService.registrarMensagem(mensagem);

        assertThat(mensagemRegistrada).isInstanceOf(Mensagem.class);
    }

    @Test
    public void deveAtribuirUmIdAMensagemAoRegistrarMensagem() {
        var mensagem = MensagemHelper.gerarMensagem();
        when(mensagemRepository.save(any(Mensagem.class)))
                .thenAnswer(i -> i.getArgument(0));

        var mensagemRegistrada = mensagemService.registrarMensagem(mensagem);

        assertThat(mensagemRegistrada.getId()).isNotNull();
    }

    @Test
    public void deveManterOUsuarioEnviadoAoRegistrarMensagem() {
        var mensagem = MensagemHelper.gerarMensagem();
        when(mensagemRepository.save(any(Mensagem.class)))
                .thenAnswer(i -> i.getArgument(0));

        var mensagemRegistrada = mensagemService.registrarMensagem(mensagem);

        assertThat(mensagemRegistrada.getUsuario()).isEqualTo(mensagem.getUsuario());
    }

    @Test
    public void deveManterOConteudoEnviadoAoRegistrarMensagem() {
        var mensagem = MensagemHelper.gerarMensagem();
        when(mensagemRepository.save(any(Mensagem.class)))
                .thenAnswer(i -> i.getArgument(0));

        var mensagemRegistrada = mensagemService.registrarMensagem(mensagem);

        assertThat(mensagemRegistrada.getConteudo()).isEqualTo(mensagem.getConteudo());
    }

    @Test
    public void devePermitirObterMensagemPorId() throws MensagemNotFoundException {
        var id = UUID.randomUUID();
        var mensagem = MensagemHelper.gerarMensagem();
        mensagem.setId(id);
        when(mensagemRepository.findById(any(UUID.class)))
                .thenReturn(Optional.of(mensagem));

        mensagemService.obterMensagemPorId(id);

        verify(mensagemRepository, times(1)).findById(id);
    }

    @Test
    public void deveRetornarAMensagemAdequadaAoObterMensagemPorId() throws MensagemNotFoundException {
        var id = UUID.randomUUID();
        var mensagem = MensagemHelper.gerarMensagem();
        mensagem.setId(id);
        when(mensagemRepository.findById(any(UUID.class)))
                .thenReturn(Optional.of(mensagem));

        var mensagemObtida = mensagemService.obterMensagemPorId(id);

        assertThat(mensagemObtida).isEqualTo(mensagem);
    }

    @Test
    public void devePermitirObterMensagens() {
        var id = UUID.randomUUID();
        var mensagem = MensagemHelper.gerarMensagem();
        mensagem.setId(id);
        when(mensagemRepository.findAll()).thenReturn(List.of(mensagem));

        var mensagensObtidas = mensagemService.obterMensagens();

        verify(mensagemRepository, times(1))
                .findAll();
    }

    @Test
    public void deveRetornarAQuantidadeAdequadaQuandoObterAsMensagens() {
        var id = UUID.randomUUID();
        var mensagem = MensagemHelper.gerarMensagem();
        mensagem.setId(id);
        when(mensagemRepository.findAll()).thenReturn(List.of(mensagem));

        var mensagensObtidas = mensagemService.obterMensagens();

        assertThat(mensagensObtidas.size())
                .isEqualTo(1);
    }

    @Test
    public void deveRetornarAsMensagensAdequadasQuandoObterMensagens() {
        var id = UUID.randomUUID();
        var mensagem = MensagemHelper.gerarMensagem();
        mensagem.setId(id);
        when(mensagemRepository.findAll()).thenReturn(List.of(mensagem));

        var mensagensObtidas = mensagemService.obterMensagens();

        assertThat(mensagensObtidas)
                .isEqualTo(List.of(mensagem));
    }

    @Test
    public void devePermitirRemoverMensagem() {
        var id = UUID.randomUUID();
        doNothing().when(mensagemRepository).deleteById(any(UUID.class));

        mensagemService.removerMensagem(id);

        verify(mensagemRepository, times(1))
                .deleteById(any(UUID.class));
    }

    @Test
    public void devePermitirAtualizarMensagem() {
        var id = UUID.randomUUID();
        var mensagem = MensagemHelper.gerarMensagem();
        mensagem.setId(id);
        when(mensagemRepository.save(any(Mensagem.class)))
                .thenAnswer(i -> i.getArgument(0));

        mensagemService.atualizarMensagem(mensagem);

        verify(mensagemRepository, times(1))
                .save(any(Mensagem.class));
    }

    @Test
    public void deveRetornarAMensagemEnviadaAdequadamenteQuandoAtualizarMensagem() {
        var id = UUID.randomUUID();
        var mensagem = MensagemHelper.gerarMensagem();
        mensagem.setId(id);
        when(mensagemRepository.save(any(Mensagem.class)))
                .thenAnswer(i -> i.getArgument(0));

        var mensagemAtualizada = mensagemService.atualizarMensagem(mensagem);

        assertThat(mensagemAtualizada)
                .isEqualTo(mensagem);
    }

}
