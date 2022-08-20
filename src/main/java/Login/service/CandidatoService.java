package Login.service;

import Login.dto.CandidatoCompletoDTO;
import Login.dto.CandidatoValidoDTO;
import Login.dto.InvalidDTO;
import Login.dto.TokenDTO;
import io.restassured.http.ContentType;

import java.io.File;

import static io.restassured.RestAssured.given;

public class CandidatoService {

    private static final String baseUrl = "https://gerenciador-curriculo-teste.herokuapp.com/candidato";

    public CandidatoValidoDTO cadastroCandidatoValido(String jsonBody, TokenDTO token, String documentoValidoPath) {

        return given().
                header("content-type", "multipart/form-data").
                header("Authorization", token.getToken()).
                multiPart("documento", new File(documentoValidoPath), "multipart/form-data").
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
                multiPart("documento", new File(docPath), "multipart/form-data").
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

    public CandidatoCompletoDTO edicaoCandidatoValido(String jsonBody, TokenDTO token, Integer httpStatus) {
        return given().
                contentType(ContentType.JSON).
                header("Authorization", token.getToken()).
                log().all().
                body(jsonBody).
                log().all()
                .when()
                .put(baseUrl + "/update-candidato")
                .then()
                .log()
                .all()
                .statusCode(200)
                .extract().as(CandidatoCompletoDTO.class);
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


    public String edicaoCurriculoValido(Integer idCandidato, TokenDTO token, String documentoValidoPath) {
        return given().
                header("content-type", "multipart/form-data").
                header("Authorization", token.getToken()).
                multiPart("idCandidato", idCandidato, "application/json").
                multiPart("documento", new File(documentoValidoPath), "multipart/form-data").
                log().all()
                .when()
                .put(baseUrl + "/update-documento/" + idCandidato)
                .then()
                .log()
                .all()
                .statusCode(200)
                .extract().asString();
    }

    public InvalidDTO edicaoCurriculoCandidatoInvalido(Integer idCandidato, TokenDTO token, Integer httpStatusExpected,
                                                       String documentoValidoPath) {
        return given().
                header("content-type", "multipart/form-data").
                header("Authorization", token.getToken()).
                multiPart("idCandidato", idCandidato, "application/json").
                multiPart("documento", new File(documentoValidoPath), "multipart/form-data").
                log().all()
                .when()
                .put(baseUrl + "/update-documento/" + idCandidato)
                .then()
                .log()
                .all()
                .statusCode(httpStatusExpected)
                .extract().as(InvalidDTO.class);
    }

}
