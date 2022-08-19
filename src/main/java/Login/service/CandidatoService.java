package Login.service;

import Login.dto.CandidatoValidoDTO;
import Login.dto.InvalidDTO;
import Login.dto.LoginValidDTO;
import Login.dto.TokenDTO;
import io.restassured.RestAssured;
import io.restassured.config.EncoderConfig;
import io.restassured.http.ContentType;
import io.restassured.http.Header;

import java.io.File;

import static io.restassured.RestAssured.given;

public class CandidatoService {

    private static final String baseUrl = "https://gerenciador-curriculo-teste.herokuapp.com/candidato";

    public CandidatoValidoDTO cadastroCandidatoValido(String jsonBody, TokenDTO token) {

        return given().
                header("content-type", "multipart/form-data").
                header("Authorization", token.getToken()).
                multiPart("documentoValido", new File("src/test/resources/data/curriculo/curriculo.pdf"), "multipart/form-data").
                multiPart("candidato", jsonBody, "application/json").
                log().all()
                .when()
                .post(baseUrl)
                .then()
                .log()
                .all()
                .statusCode(200)
                .extract().as(CandidatoValidoDTO.class);
    }

    public InvalidDTO cadastroCandidatoInvalido(String jsonBody, TokenDTO token, Integer httpStatus, String docPath) {

        return given().
                header("content-type", "multipart/form-data").
                header("Authorization", token.getToken()).
                multiPart("documentoValido", new File(docPath), "multipart/form-data").
                multiPart("candidato", jsonBody, "application/json").
                log().all()
                .when()
                .post(baseUrl)
                .then()
                .log()
                .all()
                .statusCode(httpStatus)
                .extract().as(InvalidDTO.class);
    }


    public CandidatoValidoDTO[] recuperarCandidatosAutenticado(TokenDTO token) {
        return given().
                header("Authorization", token.getToken()).
                log().all()
                .when()
                .get(baseUrl + "/list-candidato")
                .then()
                .log()
                .all()
                .statusCode(200)
                .extract().as(CandidatoValidoDTO[].class);
    }

    public void recuperarCandidatosAutenticacaoInvalida(TokenDTO token) {
        given().
                log().all()
                .when()
                .get(baseUrl + "/list-candidato")
                .then()
                .log()
                .all()
                .statusCode(403);
    }

    public void deletarCandidatoValido(TokenDTO token, Integer id) {
        given().
                header("Authorization", token.getToken()).
                log().all()
                .when()
                .delete(baseUrl + "/" + id)
                .then()
                .log()
                .all()
                .statusCode(200);
    }

    public InvalidDTO deletarCandidatoInexistente(TokenDTO token, Integer id) {
        return  given().
                header("Authorization", token.getToken()).
                log().all()
                .when()
                .delete(baseUrl + "/" + id)
                .then()
                .log()
                .all()
                .statusCode(404)
                .extract().as(InvalidDTO.class);
    }

    public void deletarCandidatoSemAutenticacao(TokenDTO token, Integer id) {
          given().
                log().all()
                .when()
                .delete(baseUrl + "/" + id)
                .then()
                .log()
                .all()
                .statusCode(403);
    }

    public void cadastroCandidatoSemCurriculo(String jsonBody, TokenDTO token, Integer httpStatus, String docPath) {
        given().
                header("content-type", "multipart/form-data").
                header("Authorization", token.getToken()).
                multiPart("candidato", jsonBody, "application/json").
                log().all()
                .when()
                .post(baseUrl)
                .then()
                .log()
                .all()
                .statusCode(httpStatus);
    }
}
