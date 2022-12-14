package Candidato;

import Login.dto.CandidatoValidoDTO;
import Login.dto.InvalidDTO;
import Login.service.CandidatoService;
import Utils.JsonManipulation;
import org.apache.http.HttpStatus;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;

import static Candidato.DelecaoDeCandidatoTests.deletarCandidato;
import static Login.AutenticacaoDeUsuarioTests.getAuthenticatedToken;
import static MassaDeDados.Paths.*;
import static Utils.Util.converterJsonParaArrayDeBytes;

public class CriacaoDeCandidatoTests {

    static CandidatoService candidatoService = new CandidatoService();

    @Test
    public void criarCandidatoValido(){

        /********************************************************************
         Crio um novo candidato válido. *
         ********************************************************************/
        JSONObject candidatoEnviadoParaRequisicao = JsonManipulation
                .criarJsonCandidato();

        /********************************************************************
         Executo de fato a operação, tentando cadastrar/criar um candidato. *
         Recupero o retorno da requisição para realizar as validações.    *
         *********************************************************************/
        CandidatoValidoDTO candidatoValidoDTO =
                candidatoService.cadastroCandidatoValido(converterJsonParaArrayDeBytes(candidatoCriado),
                        getAuthenticatedToken(), documentoValido);


        /**************************************************************************
         Realizando a validação de que os campos criados conferem com os enviados.*
         **************************************************************************/
        // verificando que o ID retornado não é nulo.
        Assert.assertNotNull( candidatoValidoDTO.getIdCandidato());

        Assert.assertEquals( candidatoEnviadoParaRequisicao.get("nome"),
                candidatoValidoDTO.getNome());

        Assert.assertEquals( candidatoEnviadoParaRequisicao.get("cargo"),
                candidatoValidoDTO.getCargo());

        Assert.assertEquals( candidatoEnviadoParaRequisicao.get("dataNascimento"),
                candidatoValidoDTO.getDataNascimento().toString());

        Assert.assertEquals( candidatoEnviadoParaRequisicao.get("senioridade"),
                candidatoValidoDTO.getSenioridade().toString());

        Assert.assertEquals(candidatoValidoDTO.getVagas().length, 0);

        // Recuperando a lista de candidatos e vendo se o mesmo se encontra nela
        CandidatoValidoDTO[] candidatosRecuperados =
                candidatoService.recuperarCandidatosAutenticado(getAuthenticatedToken());

        Assert.assertTrue(Arrays.stream(candidatosRecuperados).
                anyMatch(
                        candidato -> candidato.getIdCandidato().equals(candidatoValidoDTO.getIdCandidato())));

        /**************************
         * Deletando o candidato após o teste, para nao poluir o banco.
         */
        deletarCandidato(candidatoValidoDTO.getIdCandidato());
    }

    @Test
    public void criarCandidatoRepetido(){

        /********************************************************************
         Crio um novo candidato válido. *
         ********************************************************************/
        JsonManipulation.criarJsonCandidato();
        CandidatoValidoDTO candidatoValidoDTO = candidatoService.cadastroCandidatoValido(converterJsonParaArrayDeBytes(candidatoCriado)
                , getAuthenticatedToken(), documentoValido);

        /********************************************************************
         Executo de fato a operação, tentando cadastrar/criar um candidato com um CPF já existente no banco
         inferior ao tamanho permitido. Recupero o retorno da requisição para realizar as validações.    *
         *********************************************************************/

        InvalidDTO invalidDTO = candidatoService.cadastroCandidatoInvalido(converterJsonParaArrayDeBytes(candidatoCriado),
                getAuthenticatedToken(), HttpStatus.SC_BAD_REQUEST, documentoValido);

        Assert.assertTrue(Arrays.stream(invalidDTO.getErrors())
                .allMatch(erro -> erro.contains("CPF já cadastrado e número já cadastrado.")));

        /**************************
         * Deletando o candidato após o teste, para nao poluir o banco.
         */
        deletarCandidato(candidatoValidoDTO.getIdCandidato());
    }

    @Test
    public void criarCandidatoSemCurriculo(){

        /********************************************************************
         Crio um novo candidato válido. *
         ********************************************************************/
        JsonManipulation.criarJsonCandidato();

        /********************************************************************
         Executo de fato a operação, tentando cadastrar/criar um candidato sem documentoValido anexado.
         ecupero o retorno da requisição para realizar as validações.    *
         *********************************************************************/

        candidatoService.cadastroCandidatoSemCurriculo(converterJsonParaArrayDeBytes(candidatoCriado),
                getAuthenticatedToken(), HttpStatus.SC_BAD_REQUEST, documentoInvalido);


    }

    @Test
    public void criarCandidatoCurriculoNulo(){

        /********************************************************************
         Crio um novo candidato válido. *
         ********************************************************************/
        JsonManipulation.criarJsonCandidato();

        /********************************************************************
         Executo de fato a operação, tentando cadastrar/criar um candidato sem documentoValido anexado.
         ecupero o retorno da requisição para realizar as validações.    *
         *********************************************************************/

        candidatoService.cadastroCandidatoSemCurriculo(converterJsonParaArrayDeBytes(candidatoCriado),
                getAuthenticatedToken(), HttpStatus.SC_BAD_REQUEST, documentoVazio);

    }

    public static CandidatoValidoDTO cadastrarCandidato() {
        JsonManipulation.criarJsonCandidato();
        return candidatoService.cadastroCandidatoValido(converterJsonParaArrayDeBytes(candidatoCriado),
                getAuthenticatedToken(), documentoValido);
    }

}
