package Login.service;

import Login.Util.Geradores;
import Login.dto.PageDTO;
import Login.dto.TokenDTO;

import static io.restassured.RestAssured.given;


public class VagaService {

    Geradores geradores = new Geradores();

    // Chamadas

    private static final String baseUrl = "https://gerenciador-curriculo-teste.herokuapp.com/vaga";


    public PageDTO listarVagasValido(TokenDTO token, String nroPagina, String tamanhoDeCadaPagina) {

        String newUrl = baseUrl + "/pagina=" + nroPagina +"/quantidade="+tamanhoDeCadaPagina;
        return given().
                header("Authorization", token.getToken()).
                log().all()
                .when()
                .get(newUrl)
                .then()
                .log()
                .all()
                .statusCode(200)
                .extract().as(PageDTO.class); // recuperar como PessoaValidPostDTO
    }

}

