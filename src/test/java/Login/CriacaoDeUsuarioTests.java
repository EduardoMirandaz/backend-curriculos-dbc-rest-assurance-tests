package Login;

import Login.dto.InvalidDTO;
import Login.dto.LoginValidDTO;
import Login.service.LoginService;
import Utils.JsonManipulation;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Map;

import static MassaDeDados.Paths.loginCriado;
import static Utils.Util.converterJsonParaArrayDeBytes;

public class CriacaoDeUsuarioTests {

    LoginService loginService = new LoginService();

    @Test
    public void criarUsuarioValido(){

        /********************************************************************
         Crio um novo usuário válido e de alta probabilidade de unicidade.  *
         ********************************************************************/
        Map<String, String> usuarioEnviadoParaRequisicao = JsonManipulation
                .criarJsonCadastro(loginService.criarLoginValido());

        /********************************************************************
           Executo de fato a operação, tentando cadastrar/criar um usuário. *
           Recupero o retorno da requisição para realizar as validações.    *
        *********************************************************************/
        LoginValidDTO loginValidDTO =
                loginService.cadastroLoginValido(converterJsonParaArrayDeBytes(loginCriado));

        /********************************************************************
         Realizando a validação de que o email criado e retornado pela requi-
         sicao é o mesmo que foi enviado.*
         *********************************************************************/
        Assert.assertEquals(usuarioEnviadoParaRequisicao.get("email"), loginValidDTO.getEmail());
        Assert.assertNotNull( loginValidDTO.getIdLogin());

    }

    @Test
    public void criarUsuarioEmailNulo(){

        /********************************************************************
         Crio um novo usuário com email nulo.  *
         ********************************************************************/
        JsonManipulation.criarJsonCadastro(loginService.criarLoginEmailNulo());

        /*******************************************************************************
         Executo de fato a operação, tentando cadastrar/criar um usuário com email nulo
         e senha valida.
         Recupero o retorno da requisição para realizar as validações.
         ******************************************************************************/
        InvalidDTO invalidDTO =
                loginService.cadastroLoginEmailNulo(converterJsonParaArrayDeBytes(loginCriado));

        /********************************************************************
         Realizando a validação de que nao foi possivel criar um usuario com
         email nulo e verificando os erros retornados.
         *********************************************************************/
        Assert.assertTrue(Arrays.stream(invalidDTO.getErrors()).anyMatch(erro -> erro.contains("must not be blank")));

    }

    @Test
    public void criarUsuarioSenhalNula(){

        /********************************************************************
         Crio um novo usuário com senha nula.  *
         ********************************************************************/
        JsonManipulation.criarJsonCadastro(loginService.criarLoginSenhaNula());

        /*******************************************************************************
         Executo de fato a operação, tentando cadastrar/criar um usuário com email valido
         e senha nula.
         Recupero o retorno da requisição para realizar as validações.
         ******************************************************************************/
        InvalidDTO invalidDTO =
                loginService.cadastroLoginSenhaNula(converterJsonParaArrayDeBytes(loginCriado));

        /********************************************************************
         Realizando a validação de que nao foi possivel criar um usuario com
         senha nula e verificando os erros retornados.
         *********************************************************************/
        Assert.assertTrue(Arrays.stream(invalidDTO.getErrors()).anyMatch(erro -> erro.contains("must not be blank")));

    }

    @Test
    public void criarUsuarioSenhaEmBranco(){

        /********************************************************************
         Crio um novo usuário com login e senha em branco.  *
         ********************************************************************/
        JsonManipulation.criarJsonCadastro(loginService.criarLoginSenhaEmBranco());

        /*******************************************************************************
         Executo de fato a operação, tentando cadastrar/criar um usuário com email valido
         e senha em branco.
         Recupero o retorno da requisição para realizar as validações.
         ******************************************************************************/
        InvalidDTO invalidDTO =
                loginService.cadastroLoginSenhaEmBranco(converterJsonParaArrayDeBytes(loginCriado));

        /********************************************************************
         Realizando a validação de que nao foi possivel criar um usuario com
         senha nula e verificando os erros retornados.
         *********************************************************************/
        Assert.assertTrue(Arrays.stream(invalidDTO.getErrors())
                .allMatch(erro -> erro.contains("must not be blank")
                        || erro.contains("size must be between 8 and 64")));

    }

    @Test
    public void criarUsuarioSenhaTamanhoInvalidoInferior(){

        /********************************************************************
         Crio um novo usuário com senha curta.  *
         ********************************************************************/
        JsonManipulation.criarJsonCadastro(loginService.criarLoginSenhaCurta());

        /*******************************************************************************
         Executo de fato a operação, tentando cadastrar/criar um usuário com email valido
         e senha inferior a 8 digitos.
         Recupero o retorno da requisição para realizar as validações.
         ******************************************************************************/
        InvalidDTO invalidDTO =
                loginService.cadastroLoginSenhaCurta(converterJsonParaArrayDeBytes(loginCriado));

        /********************************************************************
         Realizando a validação de que nao foi possivel criar um usuario com
         senha nula e verificando os erros retornados.
         *********************************************************************/
        Assert.assertTrue(Arrays.stream(invalidDTO.getErrors())
                .anyMatch(erro -> erro.contains("size must be between 8 and 64")));

    }

    @Test
    public void criarUsuarioSenhaTamanhoInvalidoSuperior(){

        /********************************************************************
         Crio um novo usuário com senha longa.  *
         ********************************************************************/
        JsonManipulation.criarJsonCadastro(loginService.criarLoginSenhaLonga());

        /*******************************************************************************
         Executo de fato a operação, tentando cadastrar/criar um usuário com email nulo.
         Recupero o retorno da requisição para realizar as validações.
         ******************************************************************************/
        InvalidDTO invalidDTO =
                loginService.cadastroLoginSenhaLonga(converterJsonParaArrayDeBytes(loginCriado));

        /********************************************************************
         Realizando a validação de que nao foi possivel criar um usuario com
         senha nula e verificando os erros retornados.
         *********************************************************************/
        Assert.assertTrue(Arrays.stream(invalidDTO.getErrors())
                .anyMatch(erro -> erro.contains("size must be between 8 and 64")));

    }

    @Test
    public void criarUsuarioEmailSemDominio(){

        /********************************************************************
         Crio um novo usuário com login sem dominio.  *
         ********************************************************************/
        JsonManipulation.criarJsonCadastro(loginService.criarLoginSemDominio());

        /*******************************************************************************
         Executo de fato a operação, tentando cadastrar/criar um usuário com email nulo.
         Recupero o retorno da requisição para realizar as validações.
         ******************************************************************************/
        InvalidDTO invalidDTO =
                loginService.cadastroLoginSemDominio(converterJsonParaArrayDeBytes(loginCriado));

        /********************************************************************
         Realizando a validação de que nao foi possivel criar um usuario com
         senha nula e verificando os erros retornados.
         *********************************************************************/
        Assert.assertTrue(Arrays.stream(invalidDTO.getErrors())
                .anyMatch(erro -> erro.contains("email: must be a well-formed email address")));

    }

    @Test
    public void criarUsuarioEmailEmBranco(){

        /********************************************************************
         Crio um novo usuário com login em branco.  *
         ********************************************************************/
        JsonManipulation.criarJsonCadastro(loginService.criarLoginEmailEmBranco());

        /*******************************************************************************
         Executo de fato a operação, tentando cadastrar/criar um usuário com email nulo.
         Recupero o retorno da requisição para realizar as validações.
         ******************************************************************************/
        InvalidDTO invalidDTO =
                loginService.cadastroLoginEmailEmBranco(converterJsonParaArrayDeBytes(loginCriado));

        /********************************************************************
         Realizando a validação de que nao foi possivel criar um usuario com
         senha nula e verificando os erros retornados.
         *********************************************************************/
        Assert.assertTrue(Arrays.stream(invalidDTO.getErrors())
                .allMatch(erro -> erro.contains("email: must not be blank")
                        || erro.contains("email: must be a well-formed email address")));
    }



    @Test
    public void criarUsuarioEmailJaExiste(){

        /********************************************************************
         Crio um novo usuário válido e de alta probabilidade de unicidade.  *
         ********************************************************************/
        Map<String, String> usuarioEnviadoParaRequisicao = JsonManipulation
                .criarJsonCadastro(loginService.criarLoginValido());


        /*******************************************************************************
         Executo de fato a operação, tentando cadastrar/criar um usuário com email valido
         para depois tentar cadastra-lo novamente.
         Recupero o retorno da requisição para realizar as validações.
         ******************************************************************************/
        loginService.cadastroLoginValido(converterJsonParaArrayDeBytes(loginCriado));

        InvalidDTO invalidDTO = loginService.cadastroLoginRepetido(converterJsonParaArrayDeBytes(loginCriado));

        /********************************************************************
         Realizando a validação de que nao foi possivel criar um usuario com
         senha nula e verificando os erros retornados.
         *********************************************************************/
        Assert.assertTrue(Arrays.stream(invalidDTO.getErrors())
                .allMatch(erro -> erro.contains("Email já cadastrado.")));
    }


}
