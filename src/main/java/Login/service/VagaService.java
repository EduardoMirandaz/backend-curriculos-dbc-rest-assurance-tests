package Login.service;

import Login.Util.Geradores;
import Login.dto.InvalidDTO;
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
                .extract().as(PageDTO.class);
    }

    public void vincularCandidatoAVagaValido(TokenDTO token, Integer idVaga, Integer idCandidato) {
        String newUrl = baseUrl + "/vincular/vaga/" + idVaga +"/candidato/"+ idCandidato;
        given().
                header("content-type", "multipart/form-data").
                header("Authorization", token.getToken()).
                multiPart("idVaga", idVaga, "application/json").
                multiPart("idCandidato", idCandidato, "application/json").
                log().all()
                .when()
                .post(newUrl)
                .then()
                .log()
                .all()
                .statusCode(200);
    }

    public InvalidDTO vincularCandidatoAVagaInvalido(TokenDTO token, Integer idVaga, Integer idCandidato) {
        String newUrl = baseUrl + "/vincular/vaga/" + idVaga +"/candidato/"+ idCandidato;
        return given().
                header("content-type", "multipart/form-data").
                header("Authorization", token.getToken()).
                multiPart("idVaga", idVaga, "application/json").
                multiPart("idCandidato", idCandidato, "application/json").
                log().all()
                .when()
                .post(newUrl)
                .then()
                .log()
                .all()
                .statusCode(404)
                .extract().as(InvalidDTO.class);
    }

    public void desvincularCandidatoAVagaValido(TokenDTO token, Integer idVaga, Integer idCandidato) {
        String newUrl = baseUrl + "/desvincular/vaga/" + idVaga +"/candidato/"+ idCandidato;
        given().
                header("content-type", "multipart/form-data").
                header("Authorization", token.getToken()).
                multiPart("idVaga", idVaga, "application/json").
                multiPart("idCandidato", idCandidato, "application/json").
                log().all()
                .when()
                .post(newUrl)
                .then()
                .log()
                .all()
                .statusCode(200);
    }

    public InvalidDTO desvincularCandidatoAVagaInvalido(TokenDTO token, Integer idVaga, Integer idCandidato) {
        String newUrl = baseUrl + "/desvincular/vaga/" + idVaga +"/candidato/"+ idCandidato;
        return given().
                header("content-type", "multipart/form-data").
                header("Authorization", token.getToken()).
                multiPart("idVaga", idVaga, "application/json").
                multiPart("idCandidato", idCandidato, "application/json").
                log().all()
                .when()
                .post(newUrl)
                .then()
                .log()
                .all()
                .statusCode(404)
                .extract().as(InvalidDTO.class);
    }

    public void vincularCandidatoAVagaSemAutorizacao(Integer idVaga, Integer idCandidato) {
        String newUrl = baseUrl + "/vincular/vaga/" + idVaga +"/candidato/"+ idCandidato;
        given().
                header("content-type", "multipart/form-data").
                multiPart("idVaga", idVaga, "application/json").
                multiPart("idCandidato", idCandidato, "application/json").
                log().all()
                .when()
                .post(newUrl)
                .then()
                .log()
                .all()
                .statusCode(403);
    }

    public void desvincularCandidatoAVagaSemAutorizacao(Integer idVaga, Integer idCandidato) {
        String newUrl = baseUrl + "/desvincular/vaga/" + idVaga +"/candidato/"+ idCandidato;
        given().
                header("content-type", "multipart/form-data").
                multiPart("idVaga", idVaga, "application/json").
                multiPart("idCandidato", idCandidato, "application/json").
                log().all()
                .when()
                .post(newUrl)
                .then()
                .log()
                .all()
                .statusCode(403);
    }
}

