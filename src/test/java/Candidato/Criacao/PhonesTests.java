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

public class PhonesTests {

    CandidatoService candidatoService = new CandidatoService();

    @Test
    public void criarCandidatoTelefoneInvalidoTamanhoSuperior(){

        /********************************************************************
         Crio um novo candidato válido. *
         ********************************************************************/
        JSONObject candidatoEnviadoParaRequisicao = JsonManipulation
                .criarJsonCandidato(InvalidacoesCandidato.TELEFONE_ACIMA_TAMANHO_MAXIMO);

        /********************************************************************
         Executo de fato a operação, tentando cadastrar/criar um candidato com um TELEFONE de tamanho
         superior ao tamanho permitido. Recupero o retorno da requisição para realizar as validações.    *
         *********************************************************************/
        InvalidDTO invalidDTO =
                candidatoService.cadastroCandidatoInvalido(converterJsonParaArrayDeBytes(candidatoCriado),
                        getAuthenticatedToken(), HttpStatus.SC_BAD_REQUEST);

        Assert.assertTrue(Arrays.stream(invalidDTO.getErrors())
                .allMatch(erro -> erro.contains("telefone: O telefone deve ser composto pelo DDD seguido do número, sem simbolos. Ex. DDDDDDDDDDD.")
                        || erro.contains("telefone: O número deve conter 11 dígitos.")));


    }

    @Test
    public void criarCandidatoTelefoneInvalidoTamanhoInferior(){

        /********************************************************************
         Crio um novo candidato válido. *
         ********************************************************************/
        JSONObject candidatoEnviadoParaRequisicao = JsonManipulation
                .criarJsonCandidato(InvalidacoesCandidato.TELEFONE_ABAIXO_TAMANHO_MINIMO);

        /********************************************************************
         Executo de fato a operação, tentando cadastrar/criar um candidato com um TELEFONE de tamanho
         inferior ao tamanho permitido. Recupero o retorno da requisição para realizar as validações.    *
         *********************************************************************/
        InvalidDTO invalidDTO =
                candidatoService.cadastroCandidatoInvalido(converterJsonParaArrayDeBytes(candidatoCriado),
                        getAuthenticatedToken(), HttpStatus.SC_BAD_REQUEST);


        Assert.assertTrue(Arrays.stream(invalidDTO.getErrors())
                .allMatch(erro -> erro.contains("telefone: O telefone deve ser composto pelo DDD seguido do número, sem simbolos. Ex. DDDDDDDDDDD.")
                        || erro.contains("telefone: O número deve conter 11 dígitos.")));
    }

    @Test
    public void criarCandidatoTelefoneEmBranco(){

        /********************************************************************
         Crio um novo candidato válido. *
         ********************************************************************/
        JSONObject candidatoEnviadoParaRequisicao = JsonManipulation
                .criarJsonCandidato(InvalidacoesCandidato.TELEFONE_EM_BRANCO);

        /********************************************************************
         Executo de fato a operação, tentando cadastrar/criar um candidato com um TELEFONE em.
         branco. Recupero o retorno da requisição para realizar as validações.    *
         *********************************************************************/
        InvalidDTO invalidDTO =
                candidatoService.cadastroCandidatoInvalido(converterJsonParaArrayDeBytes(candidatoCriado),
                        getAuthenticatedToken(), HttpStatus.SC_BAD_REQUEST);

        Assert.assertTrue(Arrays.stream(invalidDTO.getErrors())
                .allMatch(erro -> erro.contains("telefone: O telefone deve ser composto pelo DDD seguido do número, sem simbolos. Ex. DDDDDDDDDDD.")
                        || erro.contains("telefone: must not be blank")
                        || erro.contains("telefone: O número deve conter 11 dígitos.")));
    }


    @Test
    public void criarCandidatoTelefoneNulo(){

        /********************************************************************
         Crio um novo candidato válido. *
         ********************************************************************/
        JSONObject candidatoEnviadoParaRequisicao = JsonManipulation
                .criarJsonCandidato(InvalidacoesCandidato.TELEFONE_NULO);

        /********************************************************************
         Executo de fato a operação, tentando cadastrar/criar um candidato com um TELEFONE nulo.
         Recupero o retorno da requisição para realizar as validações.    *
         *********************************************************************/
        InvalidDTO invalidDTO =
                candidatoService.cadastroCandidatoInvalido(converterJsonParaArrayDeBytes(candidatoCriado),
                        getAuthenticatedToken(), HttpStatus.SC_BAD_REQUEST);

        Assert.assertTrue(Arrays.stream(invalidDTO.getErrors())
                .allMatch(erro -> erro.contains("telefone: must not be blank")));

    }

}
