package Login.service;

import Login.Enums.TipoDeInvalidacao;
import Login.Util.Geradores;
import Login.dto.InvalidDTO;
import Login.dto.LoginValidDTO;
import Login.dto.TokenDTO;
import io.restassured.http.ContentType;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;


public class LoginService {

    Geradores geradores = new Geradores();

    // Chamadas

    private static final String baseUrl = "https://gerenciador-curriculo-teste.herokuapp.com/login";



/*                  TESTES -------->>>>>> /criar-usuario                                            */
    /***********************************************************************************
     Criação e requisição de usuario com credenciais válidas  | POST - /criar-usuario |
     **********************************************************************************/
    public Map<String, String> criarLoginValido() {

        Geradores geradores1 = new Geradores();

        String email = geradores1.gerarLoginRandomico();
        String password = geradores1.gerarSenhaRandomica();

        return comporLogin(email, password);
    }

    public LoginValidDTO cadastroLoginValido(String jsonBody) {

        return given().
                contentType(ContentType.JSON).
                log().all()
                .body(jsonBody)
                .when()
                .post(baseUrl+"/criar-usuario")
                .then()
                .log()
                .all()
                .statusCode(200)
                .extract().as(LoginValidDTO.class);
    }




    /**************************************************************************
     Criação e requisição de usuário com email nulo  | POST - /criar-usuario |
     *************************************************************************/

    public InvalidDTO cadastroLoginRepetido(String jsonBody) {
        return cadastroDeUsuarioInvalidoResponse400(jsonBody, "/criar-usuario");
    }

    /**************************************************************************
     Criação e requisição de usuário com email nulo  | POST - /criar-usuario |
     *************************************************************************/
    public Map<String, String> criarLoginEmailNulo() {
        String email = geradores.gerarLoginRandomico(TipoDeInvalidacao.NULO);
        String password = geradores.gerarSenhaRandomica();

        return comporLogin(email, password);
    }

    public InvalidDTO cadastroLoginEmailNulo(String jsonBody) {
        return cadastroDeUsuarioInvalidoResponse400(jsonBody, "/criar-usuario");
    }

    /**************************************************************************
     Criação e requisição de usuário com senha nula  | POST - /criar-usuario |
     *************************************************************************/
    public InvalidDTO cadastroLoginSenhaNula(String jsonBody) {
        return cadastroDeUsuarioInvalidoResponse400(jsonBody, "/criar-usuario");
    }
    public Map<String, String> criarLoginSenhaNula() {

        String email = geradores.gerarLoginRandomico();
        String password = geradores.gerarSenhaRandomica(TipoDeInvalidacao.NULO);

        return comporLogin(email, password);
    }


    /*******************************************************************************
     Criação e requisição de usuário com senha em branco  | POST - /criar-usuario |
     ******************************************************************************/
    public InvalidDTO cadastroLoginSenhaEmBranco(String jsonBody) {
        return cadastroDeUsuarioInvalidoResponse400(jsonBody, "/criar-usuario");
    }
    public Map<String, String> criarLoginSenhaEmBranco() {

        String email = geradores.gerarLoginRandomico();
        String password = geradores.gerarSenhaRandomica(TipoDeInvalidacao.EMBRANCO);

        return comporLogin(email, password);
    }

    /*******************************************************************************
     Criação e requisição de usuário com senha curta  | POST - /criar-usuario |
     ******************************************************************************/
    public InvalidDTO cadastroLoginSenhaCurta(String jsonBody) {
        return cadastroDeUsuarioInvalidoResponse400(jsonBody, "/criar-usuario");
    }
    public Map<String, String> criarLoginSenhaCurta() {

        String email = geradores.gerarLoginRandomico();
        String password = geradores.gerarSenhaRandomica(TipoDeInvalidacao.ABAIXO_TAMANHO_MINIMO);

        return comporLogin(email, password);
    }


    /*******************************************************************************
     Criação e requisição de usuário com senha longa  | POST - /criar-usuario |
     ******************************************************************************/
    public InvalidDTO cadastroLoginSenhaLonga(String jsonBody) {
        return cadastroDeUsuarioInvalidoResponse400(jsonBody, "/criar-usuario");
    }
    public Map<String, String> criarLoginSenhaLonga() {

        String email = geradores.gerarLoginRandomico();
        String password = geradores.gerarSenhaRandomica(TipoDeInvalidacao.ACIMA_TAMANHO_MAXIMO);

        return comporLogin(email, password);
    }

    /*******************************************************************************
     Criação e requisição de usuário com email sem domínio  | POST - /criar-usuario |
     ******************************************************************************/
    public InvalidDTO cadastroLoginSemDominio(String jsonBody) {
        return cadastroDeUsuarioInvalidoResponse400(jsonBody, "/criar-usuario");
    }
    public Map<String, String> criarLoginSemDominio() {

        String email = geradores.gerarLoginRandomico(TipoDeInvalidacao.EMAILSEMDOMINIO);
        String password = geradores.gerarSenhaRandomica();

        return comporLogin(email, password);
    }

    /*******************************************************************************
     Criação e requisição de usuário com email em branco  | POST - /criar-usuario |
     ******************************************************************************/
    public InvalidDTO cadastroLoginEmailEmBranco(String jsonBody) {
        return cadastroDeUsuarioInvalidoResponse400(jsonBody, "/criar-usuario");
    }
    public Map<String, String> criarLoginEmailEmBranco() {

        String email = geradores.gerarLoginRandomico(TipoDeInvalidacao.EMBRANCO);
        String password = geradores.gerarSenhaRandomica();

        return comporLogin(email, password);
    }


    //              TESTES -------->>>>>> get-token/login


public TokenDTO recuperarBearer(String jsonBody) {
    // REST-ASSURED
    //dado isso
    return given().
            contentType(ContentType.JSON).
            log().all()
            .body(jsonBody)
            .when()
            .post(baseUrl+"/get-token/login")
            .then()
            .log()
            .all()
            .extract().as(TokenDTO.class);// recuperar como TokenDTO
}

    /*******************************************************************************
     Requisição de autenticação com login e senha em branco. | POST - /get-token/login|
     ******************************************************************************/
    public InvalidDTO autenticarLoginESenhaEmBranco(String jsonBody) {
        return cadastroDeUsuarioInvalidoResponse400(jsonBody, "/get-token/login");
    }

    public Map<String, String> criarLoginEmBranco() {
        String email = geradores.gerarLoginRandomico(TipoDeInvalidacao.EMBRANCO);
        String password = geradores.gerarSenhaRandomica(TipoDeInvalidacao.EMBRANCO);

        return comporLogin(email, password);
    }

    /*******************************************************************************
     Requisição de autenticação com login e senha nulos. | POST - /get-token/login|
     ******************************************************************************/
    public InvalidDTO autenticarLoginESenhaNulos(String jsonBody) {
        return cadastroDeUsuarioInvalidoResponse400(jsonBody, "/get-token/login");
    }

    public Map<String, String> criarLoginNulo() {
        String email = geradores.gerarLoginRandomico(TipoDeInvalidacao.NULO);
        String password = geradores.gerarSenhaRandomica(TipoDeInvalidacao.NULO);

        return comporLogin(email, password);
    }

    /**************************************************************************************************
     Requisição de autenticação com usuario válido porém não salvo no banco.  | POST - /get-token/login|
     *************************************************************************************************/
    public InvalidDTO autenticarLoginValidoNaoSalvoNoBanco(String jsonBody) {
        return cadastroDeUsuarioInvalidoResponse403(jsonBody, "/get-token/login");
    }

    /*******************************************************************************
     Requisição de autenticação com usuario válido porém com senha invalida. | POST - /get-token/login|
     ******************************************************************************/
    public InvalidDTO autenticarLoginValidoSenhaIncorreta(String jsonBody) {
        return cadastroDeUsuarioInvalidoResponse403(jsonBody, "/get-token/login");
    }

    /*******************************************************************************
     Requisição de autenticação com usuario válido inexistente com senha valida. | POST - /get-token/login|
     ******************************************************************************/
    public InvalidDTO autenticarEmailValidoInexistenteESenhaValida(String jsonBody) {
        return cadastroDeUsuarioInvalidoResponse403(jsonBody, "/get-token/login");
    }





    private Map<String, String> comporLogin(String email, String password) {
        Map<String, String> login = new HashMap<>();
        login.put("email", email);
        login.put("senha", password);
        return login;
    }








    /****** Requisições *****/


    private InvalidDTO cadastroDeUsuarioInvalidoResponse400(String jsonBody, String url) {
        return given().
                contentType(ContentType.JSON).
                log().all()
                .body(jsonBody)
                .when()
                .post(baseUrl + url)
                .then()
                .log()
                .all()
                .statusCode(400)
                .extract().as(InvalidDTO.class);
    }

    private InvalidDTO cadastroDeUsuarioInvalidoResponse403(String jsonBody, String url) {
        return given().
                contentType(ContentType.JSON).
                log().all()
                .body(jsonBody)
                .when()
                .post(baseUrl + url)
                .then()
                .log()
                .all()
                .statusCode(403)
                .extract().as(InvalidDTO.class);
    }
}

