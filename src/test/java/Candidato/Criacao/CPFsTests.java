package Candidato.Criacao;

import Login.dto.InvalidDTO;
import Login.service.CandidatoService;
import Utils.InvalidacoesCandidato;
import Utils.JsonManipulation;
import org.apache.http.HttpStatus;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;

import static Login.AutenticacaoDeUsuarioTests.getAuthenticatedToken;
import static MassaDeDados.PostPaths.candidatoCriado;
import static Utils.Util.converterJsonParaArrayDeBytes;

public class CPFsTests {

    CandidatoService candidatoService = new CandidatoService();

    @Test
    public void criarCandidatoCPFInvalidoTamanhoSuperior(){

        /********************************************************************
         Crio um novo candidato válido. *
         ********************************************************************/
        JSONObject candidatoEnviadoParaRequisicao = JsonManipulation
                .criarJsonCandidato(InvalidacoesCandidato.CPF_ACIMA_TAMANHO_MAXIMO);

        /********************************************************************
         Executo de fato a operação, tentando cadastrar/criar um candidato com um CPF de tamanho
         superior ao tamanho permitido. Recupero o retorno da requisição para realizar as validações.    *
         *********************************************************************/
        InvalidDTO invalidDTO =
                candidatoService.cadastroCandidatoInvalido(converterJsonParaArrayDeBytes(candidatoCriado),
                        getAuthenticatedToken(), HttpStatus.SC_BAD_REQUEST);

        Assert.assertTrue(Arrays.stream(invalidDTO.getErrors())
                .allMatch(erro -> erro.contains("cpf: O CPF deve ser válido.")));

    }

    @Test
    public void criarCandidatoCPFInvalidoTamanhoInferior(){

        /********************************************************************
         Crio um novo candidato válido. *
         ********************************************************************/
        JSONObject candidatoEnviadoParaRequisicao = JsonManipulation
                .criarJsonCandidato(InvalidacoesCandidato.CPF_ABAIXO_TAMANHO_MINIMO);

        /********************************************************************
         Executo de fato a operação, tentando cadastrar/criar um candidato com um CPF de tamanho
         inferior ao tamanho permitido. Recupero o retorno da requisição para realizar as validações.    *
         *********************************************************************/
        InvalidDTO invalidDTO =
                candidatoService.cadastroCandidatoInvalido(converterJsonParaArrayDeBytes(candidatoCriado),
                        getAuthenticatedToken(), HttpStatus.SC_BAD_REQUEST);

        Assert.assertTrue(Arrays.stream(invalidDTO.getErrors())
                .allMatch(erro -> erro.contains("cpf: O CPF deve ser válido.")));

    }

    @Test
    public void criarCandidatoCPFEmBranco(){

        /********************************************************************
         Crio um novo candidato válido. *
         ********************************************************************/
        JSONObject candidatoEnviadoParaRequisicao = JsonManipulation
                .criarJsonCandidato(InvalidacoesCandidato.CPF_EM_BRANCO);

        /********************************************************************
         Executo de fato a operação, tentando cadastrar/criar um candidato com um CPF em.
         branco. Recupero o retorno da requisição para realizar as validações.    *
         *********************************************************************/
        InvalidDTO invalidDTO =
                candidatoService.cadastroCandidatoInvalido(converterJsonParaArrayDeBytes(candidatoCriado),
                        getAuthenticatedToken(), HttpStatus.SC_BAD_REQUEST);

        Assert.assertTrue(Arrays.stream(invalidDTO.getErrors())
                .allMatch(erro -> erro.contains("cpf: must not be blank")
                        || erro.contains("O CPF deve ser válido.")));

    }


    @Test
    public void criarCandidatoCPFNulo(){

        /********************************************************************
         Crio um novo candidato válido. *
         ********************************************************************/
        JSONObject candidatoEnviadoParaRequisicao = JsonManipulation
                .criarJsonCandidato(InvalidacoesCandidato.CPF_NULO);

        /********************************************************************
         Executo de fato a operação, tentando cadastrar/criar um candidato com um CPF nulo.
         Recupero o retorno da requisição para realizar as validações.    *
         *********************************************************************/
        InvalidDTO invalidDTO =
                candidatoService.cadastroCandidatoInvalido(converterJsonParaArrayDeBytes(candidatoCriado),
                        getAuthenticatedToken(), HttpStatus.SC_BAD_REQUEST);

        Assert.assertTrue(Arrays.stream(invalidDTO.getErrors())
                .allMatch(erro -> erro.contains("cpf: must not be blank")));

    }

}
