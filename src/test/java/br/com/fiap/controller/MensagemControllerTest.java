package br.com.fiap.controller;

import br.com.fiap.exception.MensagemNotFoundException;
import br.com.fiap.handler.GlobalExceptionHandler;
import br.com.fiap.helper.MensagemHelper;
import br.com.fiap.helper.TestHelper;
import br.com.fiap.model.Mensagem;
import br.com.fiap.service.MensagemService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.springframework.test.util.AssertionErrors.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class MensagemControllerTest {

    @Mock
    private MensagemService mensagemService;
    private MensagemController mensagemController;

    private MockMvc mockMvc;

    private AutoCloseable mock;

    @BeforeEach
    void setup () {
        mock = MockitoAnnotations.openMocks(this);
        mensagemController = new MensagemController(mensagemService);
        mockMvc = MockMvcBuilders
                .standaloneSetup(mensagemController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .addFilter((request, response, chain) -> {
                    response.setCharacterEncoding("UTF-8");
                    chain.doFilter(request, response);
                }, "/*")
                .build();
    }

    @AfterEach
    void tearDown() throws Exception {
        mock.close();
    }

    @Test
    void devePermitirRegistrarMensagem() throws Exception {
        // Arrange
        var mensagemRequest = MensagemHelper.gerarMensagem();
        when(mensagemService.registrarMensagem(any(Mensagem.class)))
                .thenAnswer(i -> i.getArgument(0));
        // Act + Assert
        mockMvc.perform(
                post("/mensagens")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestHelper.asJsonString(mensagemRequest))
        ).andExpect(status().isCreated());
        verify(mensagemService, times(1)).registrarMensagem(any(Mensagem.class));
    }

    @Test
    void devePermitirObterMensagemPorId() throws Exception {
        var id = "32d2672f-3bde-40e6-9648-81b6fad0dc03";
        var mensagem = MensagemHelper.gerarMensagem();
        mensagem.setId(UUID.fromString(id));
        mensagem.setDataCriacao(LocalDateTime.now());
        mensagem.setDataAtualizacao(LocalDateTime.now());
        when(mensagemService.obterMensagemPorId(any(UUID.class)))
                .thenReturn(mensagem);

        mockMvc.perform(
                get("/mensagens/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
        verify(mensagemService, times(1)).obterMensagemPorId(any(UUID.class));
    }

    @Test
    void deveGerarExcecaoAoObterMensagemComIdInexistente() throws Exception {
        var id = "32d2672f-3bde-40e6-9648-81b6fad0dc03";
        when(mensagemService.obterMensagemPorId(any(UUID.class)))
                .thenThrow(new MensagemNotFoundException("Mensagem não encontrada"));

        mockMvc.perform(
                get("/mensagens/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound());
        verify(mensagemService, times(1)).obterMensagemPorId(any(UUID.class));
    }

    @Test
    void deveGerarExcecaoAoObterMensagemComIdInvalido() throws Exception {
        var id = "123";

        mockMvc.perform(
                get("/mensagens/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

    @Test
    void devePermitirRemoverMensagem() {
        fail("Não implementado");
    }

    @Test
    void devePermitirAtualizarMensagem() {
        fail("Não implementado");
    }

    @Test
    void devePermitirBuscarMensagens() {
        fail("Não implementado");
    }

}
