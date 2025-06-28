package br.com.fiap.repository;

import br.com.fiap.model.Mensagem;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

public class MensagemRepositoryTest {

    @Mock
    private MensagemRepository mensagemRepository;

    private AutoCloseable mock;

    @BeforeEach
    void setup () {
        mock = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown () throws Exception {
        mock.close();
    }

    @Test
    void devePermitirCriarMensagem () {
        // Arrange
        Mensagem mensagem = gerarMensagem();
        when(mensagemRepository.save(any(Mensagem.class))).thenReturn(mensagem);

        // Act
        var mensagemArmazenada = mensagemRepository.save(mensagem);

        // Assert
        verify(mensagemRepository, times(1)).save(mensagem);
    }

    @Test
    void devePermitirConsultarMensagem () {
        var id = UUID.randomUUID();
        Mensagem mensagem = gerarMensagem();
        mensagem.setId(id);
        when(mensagemRepository.findById(any(UUID.class))).thenReturn(Optional.of(mensagem));

        var mensagemEncontrada = mensagemRepository.findById(id);

        assertThat(mensagemEncontrada)
                .isNotNull()
                .containsSame(mensagem);
    }

    @Test
    void devePermitirApagarMensagem () {
        var id = UUID.randomUUID();
        Mensagem mensagem = gerarMensagem();
        mensagem.setId(id);
        doNothing().when(mensagemRepository).deleteById(any(UUID.class));

        mensagemRepository.deleteById(id);

        verify(mensagemRepository, times(1)).deleteById(id);
    }

    private Mensagem gerarMensagem() {
        return Mensagem
                .builder()
                .usuario("Usuário")
                .conteudo("Conteúdo")
                .build();
    }

}
