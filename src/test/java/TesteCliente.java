import io.restassured.http.ContentType;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

public class TesteCliente {

    String enderecoApiCliente = "http://localhost:8080/";
    String endpointCliente = "cliente/";

    @Test
    @DisplayName("Quando Pegar todos os clientes sem cadastrar clientes, então a lista deve estar vazia")
    public void pegaTodosClientes(){

        deletaTodosClientes();

        String respostaEsperada = "{}";

        given().
                contentType(ContentType.JSON).
        when().
                get(enderecoApiCliente).
        then().
                statusCode(200).
                assertThat().body(new IsEqual<>(respostaEsperada));

    }

    @Test
    @DisplayName("Quando cadastrar cliente, então deve estar disponivel no resultado")
    public void cadastraCliente(){
        String clienteParaCadastrar = "{\n" +
                "  \"id\": 2,\n" +
                "  \"idade\": 20,\n" +
                "  \"nome\": \"joao\",\n" +
                "  \"risco\": 0\n" +
                "}";

        String respostaEsperada = "{\"2\":{\"nome\":\"joao\",\"idade\":20,\"id\":2,\"risco\":0}}";

        given().
                contentType(ContentType.JSON).
                body(clienteParaCadastrar).
        when().
                post(enderecoApiCliente+endpointCliente).
        then().
                log().all().
                statusCode(201).
                assertThat().body(containsString(respostaEsperada));
    }

    @Test
    @DisplayName("Quando atualizar cliente, então deve estar disponivel no resultado")
    public void atualizarCliente(){
        String clienteParaCadastrar = "{\n" +
                "  \"id\": 3,\n" +
                "  \"idade\": 33,\n" +
                "  \"nome\": \"Larissa\",\n" +
                "  \"risco\": 0\n" +
                "}";

        String clienteParaAtualizar = "{\n" +
                "  \"id\": 3,\n" +
                "  \"idade\": 33,\n" +
                "  \"nome\": \"Larissa Manoela\",\n" +
                "  \"risco\": 0\n" +
                "}";
        String respostaEsperada = "{\"3\":{\"nome\":\"Larissa Manoela\",\"idade\":33,\"id\":3,\"risco\":0}}";

        given().
                contentType(ContentType.JSON).
                body(clienteParaCadastrar).
        when().
                post(enderecoApiCliente+endpointCliente).
        then().
                statusCode(201);


        given().
                contentType(ContentType.JSON).
                body(clienteParaAtualizar).
        when().
                put(enderecoApiCliente+endpointCliente).
        then().
                statusCode(200).
                assertThat().body(new IsEqual<>(respostaEsperada));

    }

    @Test
    @DisplayName("Quando deletar um cliente, então deve ser removido")
    public void deletarCliente(){

        String idCliente = "2";
        String respostaEsperada = "CLIENTE REMOVIDO: { NOME: joao, IDADE: 20, ID: 2 }";
        String clienteParaDeletar = "{\n" +
                "  \"id\": 2,\n" +
                "  \"idade\": 20,\n" +
                "  \"nome\": \"joao\",\n" +
                "  \"risco\": 0\n" +
                "}";
        given().
                contentType(ContentType.JSON).
                body(clienteParaDeletar).
        when().
                post(enderecoApiCliente+endpointCliente).
        then().
                statusCode(201);

        given().
                contentType(ContentType.JSON).
        when().
                delete(enderecoApiCliente+endpointCliente +idCliente).
        then().
                statusCode(200).
                assertThat().body(new IsEqual<>(respostaEsperada));
    }

    @Test
    @DisplayName("Quando deletar todos os clientes, etao todos clientes sao deletados")
    public void deletaTodosClientes(){

        String respostaEsperada = "{}";

        given().
                contentType(ContentType.JSON).
        when().
                delete(enderecoApiCliente+endpointCliente + "apagaTodos").
        then().
                statusCode(200).
                assertThat().body(new IsEqual<>(respostaEsperada));

    }

}
