package Login;

import Login.dto.InvalidDTO;
import Login.dto.TokenDTO;
import Login.service.LoginService;
import Utils.JsonManipulation;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Map;

import static MassaDeDados.Paths.loginCriado;
import static Utils.Util.converterJsonParaArrayDeBytes;
import static Utils.Util.recuperarBearerPeloJSON;

public class AutenticacaoDeUsuarioTests {

    LoginService loginService = new LoginService();

    @Test
    public void obterAutenticacaoValida(){
        TokenDTO tokenDTO = getAuthenticatedToken();

        /*********************************************************************
         Realizando a validação de que a requisicao retornou um bearer para um
         acesso válido.*
         *********************************************************************/
        Assert.assertNotNull(tokenDTO);

    }

    public static TokenDTO getAuthenticatedToken() {
        LoginService loginService = new LoginService();
        JsonManipulation.criarJsonCadastro(loginService.criarLoginValido());
        loginService.cadastroLoginValido(converterJsonParaArrayDeBytes(loginCriado));


        TokenDTO tokenDTO = recuperarBearerPeloJSON(loginCriado);
        return tokenDTO;
    }

    @Test
    public void obterAutenticacaoComUsuarioInexistente(){
        // Crio um login válido mas nao salvo no banco de dados.
        JsonManipulation.criarJsonCadastro(loginService.criarLoginValido());

        InvalidDTO invalidDTO = loginService
                .autenticarLoginValidoNaoSalvoNoBanco(converterJsonParaArrayDeBytes(loginCriado));

        /*************************************************************************
         Realizando a validação de que a requisicao não retornou um bearer pois o
         acesso foi invalidado.*
         *********************************************************************/
        Assert.assertTrue(Arrays.stream(invalidDTO.getErrors())
                .allMatch(erro -> erro.contains("Bad credentials")));
        Assert.assertEquals(invalidDTO.getStatus(), Integer.toString(HttpStatus.SC_FORBIDDEN));

    }

    @Test
    public void obterAutenticacaoComUsuarioExistenteESenhaIncorreta(){

        /********************************************************************
         Crio um novo usuário válido e de alta probabilidade de unicidade.  *
         ********************************************************************/
        JsonManipulation.criarJsonCadastro(loginService.criarLoginValido());

        /*******************************************************************************
         Executo de fato a operação, tentando recuperar um token com email valido e senha
         incorreta.
         Recupero o retorno da requisição para realizar as validações.
         ******************************************************************************/
        // Preciso alterar a senha para mandar a incorreta para a requisicao

        Map<String, String> login = JsonManipulation.recuperarCadastro();
        login.put("senha", login.get("senha")+"#");
        JsonManipulation.criarJsonCadastro(login);

        InvalidDTO invalidDTO = loginService
                .autenticarLoginValidoSenhaIncorreta(converterJsonParaArrayDeBytes(loginCriado));

        /*************************************************************************
         Realizando a validação de que a requisicao não retornou um bearer pois o
         acesso foi invalidado.*
         *********************************************************************/
        Assert.assertTrue(Arrays.stream(invalidDTO.getErrors())
                .allMatch(erro -> erro.contains("Bad credentials")));
        Assert.assertEquals(invalidDTO.getStatus(), Integer.toString(HttpStatus.SC_FORBIDDEN));

    }

    @Test
    public void obterAutenticacaoComEmailESenhaEmBranco(){

        /********************************************************************
         Crio um novo usuário válido e de alta probabilidade de unicidade.  *
         ********************************************************************/
        JsonManipulation.criarJsonCadastro(loginService.criarLoginEmBranco());

        /*******************************************************************************
         Executo de fato a operação, tentando recuperar um token com email valido e senha
         incorreta.
         Recupero o retorno da requisição para realizar as validações.
         ******************************************************************************/

        InvalidDTO invalidDTO = loginService
                .autenticarLoginESenhaEmBranco(converterJsonParaArrayDeBytes(loginCriado));

        /*************************************************************************
         Realizando a validação de que a requisicao não retornou um bearer pois o
         acesso foi invalidado.*
         *********************************************************************/
        Assert.assertEquals(invalidDTO.getStatus(), Integer.toString(HttpStatus.SC_BAD_REQUEST));
        Assert.assertTrue(Arrays.stream(invalidDTO.getErrors())
                .allMatch(erro -> erro.contains("email: must not be blank")
                        || erro.contains("senha: must not be blank")
                        || erro.contains("email: must be a well-formed email address")
                        || erro.contains("senha: size must be between 8 and 64")));
    }

    @Test
    public void obterAutenticacaoComEmailESenhaNulos(){

        /********************************************************************
         Crio um novo usuário válido e de alta probabilidade de unicidade.  *
         ********************************************************************/
        JsonManipulation.criarJsonCadastro(loginService.criarLoginNulo());

        /*******************************************************************************
         Executo de fato a operação, tentando recuperar um token com email valido e senha
         incorreta.
         Recupero o retorno da requisição para realizar as validações.
         ******************************************************************************/

        InvalidDTO invalidDTO = loginService
                .autenticarLoginESenhaNulos(converterJsonParaArrayDeBytes(loginCriado));

        /*************************************************************************
         Realizando a validação de que a requisicao não retornou um bearer pois o
         acesso foi invalidado.*
         *********************************************************************/
        Assert.assertEquals(invalidDTO.getStatus(), Integer.toString(HttpStatus.SC_BAD_REQUEST));
        Assert.assertTrue(Arrays.stream(invalidDTO.getErrors())
                .allMatch(erro -> erro.contains("email: must not be blank")
                        || erro.contains("senha: must not be blank")));
    }

    @Test
    public void obterAutenticacaoComEmailValidoInexistenteESenhaValida(){

        /********************************************************************
         Crio um novo usuário válido e de alta probabilidade de unicidade.  *
         ********************************************************************/
        JsonManipulation.criarJsonCadastro(loginService.criarLoginValido());

        /*******************************************************************************
         Executo de fato a operação, tentando recuperar um token com email valido e senha
         incorreta.
         Recupero o retorno da requisição para realizar as validações.
         ******************************************************************************/

        // Preciso alterar o email para mandar o incorreto para a requisicao

        Map<String, String> login = JsonManipulation.recuperarCadastro();
        String novoEmail = "#" + login.get("email");
        login.put("email", novoEmail);
        JsonManipulation.criarJsonCadastro(login);


        InvalidDTO invalidDTO = loginService
                .autenticarEmailValidoInexistenteESenhaValida(converterJsonParaArrayDeBytes(loginCriado));

        /*************************************************************************
         Realizando a validação de que a requisicao não retornou um bearer pois o
         acesso foi invalidado.*
         *********************************************************************/
        Assert.assertTrue(Arrays.stream(invalidDTO.getErrors())
                .allMatch(erro -> erro.contains("Bad credentials")));
        Assert.assertEquals(invalidDTO.getStatus(), Integer.toString(HttpStatus.SC_FORBIDDEN));
    }

}
