package br.com.fiap.bdd;

import br.com.fiap.helper.MensagemHelper;
import br.com.fiap.model.Mensagem;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import static io.restassured.RestAssured.given;
import io.restassured.response.Response;

import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.hasKey;
import org.springframework.http.MediaType;

public class CucumberSteps {

    private Response response;
    private String HOST = "http://localhost:8080";
    private Mensagem mensagemRegistrada;

    @Quando("submeter uma nova mensagem")
    public Mensagem submeter_uma_nova_mensagem() {
        var novaMensagem = MensagemHelper.gerarMensagem();

        response = given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(novaMensagem)
                .when()
                    .post(HOST + "/mensagens");

        return response.then().extract().as(Mensagem.class);
    }

    @Então("deve retornar a mensagem criada")
    public void deve_retornar_a_mensagem_criada() {
        response
                .then()
                .body("$", hasKey("id"))
                .body("$", hasKey("usuario"))
                .body("$", hasKey("conteudo"))
                .body("$", hasKey("gostei"))
                .body("$", hasKey("dataCriacao"))
                .body("$", hasKey("dataAtualizacao"));
    }

    @Então("deve retornar status {int}")
    public void deve_retornar_status(Integer statusEsperado) {
        response
                .then()
                .statusCode(statusEsperado);
    }

    @Dado("que uma mensagem já foi registrada")
    public void que_uma_mensagem_já_foi_registrada() {
        mensagemRegistrada = submeter_uma_nova_mensagem();
    }

    @Quando("buscar a mensagem por id")
    public void buscar_a_mensagem_por_id() {
        response = when()
                .get(HOST + "/mensagens/{id}", mensagemRegistrada.getId());
    }

    @Então("a mensagem é retornada com sucesso")
    public void a_mensagem_é_retornada_com_sucesso() {
        response
                .then()
                .body("$", hasKey("id"))
                .body("$", hasKey("usuario"))
                .body("$", hasKey("conteudo"))
                .body("$", hasKey("gostei"))
                .body("$", hasKey("dataCriacao"))
                .body("$", hasKey("dataAtualizacao"));
    }

    @Quando("requisitar a remoção da mensagem")
    public void requisitar_a_remoção_da_mensagem() {
        response = when()
                .delete(HOST + "/mensagens/{id}", mensagemRegistrada.getId());
    }

}
