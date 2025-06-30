package br.com.fiap.helper;

import br.com.fiap.model.Mensagem;

public abstract class MensagemHelper {

    public static Mensagem gerarMensagem() {
        return Mensagem
                .builder()
                .usuario("Usuário")
                .conteudo("Conteúdo")
                .build();
    }

}
