package Candidato;

import Login.dto.InvalidDTO;
import Login.service.CandidatoService;
import Utils.Enums.InvalidacoesCandidato;
import Utils.JsonManipulation;
import org.apache.http.HttpStatus;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;

import static Login.AutenticacaoDeUsuarioTests.getAuthenticatedToken;
import static MassaDeDados.Paths.candidatoCriado;
import static MassaDeDados.Paths.documentoValido;
import static Utils.Util.converterJsonParaArrayDeBytes;

public class DOBTests {

    CandidatoService candidatoService = new CandidatoService();

    @Test
    public void criarCandidatoDOBInvalidoTamanhoSuperior(){

        /********************************************************************
         Crio um novo candidato válido. *
         ********************************************************************/
        JSONObject candidatoEnviadoParaRequisicao = JsonManipulation
                .criarJsonCandidato(InvalidacoesCandidato.DOB_APOS_DIA_ATUAL);

        /********************************************************************
         Executo de fato a operação, tentando cadastrar/criar um candidato com um DOB de tamanho
         superior ao tamanho permitido. Recupero o retorno da requisição para realizar as validações.    *
         *********************************************************************/
        InvalidDTO invalidDTO =
                candidatoService.cadastroCandidatoInvalido(converterJsonParaArrayDeBytes(candidatoCriado),
                        getAuthenticatedToken(), HttpStatus.SC_BAD_REQUEST, documentoValido);

        Assert.assertTrue(Arrays.stream(invalidDTO.getErrors())
                .allMatch(erro -> erro.contains("dataNascimento: data de nascimento deve estar no passado")));
    }

    @Test
    public void criarCandidatoDOBEmBranco(){

        /********************************************************************
         Crio um novo candidato válido. *
         ********************************************************************/
        JSONObject candidatoEnviadoParaRequisicao = JsonManipulation
                .criarJsonCandidato(InvalidacoesCandidato.DOB_EM_BRANCO);

        /********************************************************************
         Executo de fato a operação, tentando cadastrar/criar um candidato com um DOB em.
         branco. Recupero o retorno da requisição para realizar as validações.    *
         *********************************************************************/
        InvalidDTO invalidDTO =
                candidatoService.cadastroCandidatoInvalido(converterJsonParaArrayDeBytes(candidatoCriado),
                        getAuthenticatedToken(), HttpStatus.SC_BAD_REQUEST, documentoValido);

        Assert.assertTrue(Arrays.stream(invalidDTO.getErrors())
                .allMatch(erro -> erro.contains("dataNascimento: must not be null")));
    }


    @Test
    public void criarCandidatoDOBNulo(){

        /********************************************************************
         Crio um novo candidato válido. *
         ********************************************************************/
        JSONObject candidatoEnviadoParaRequisicao = JsonManipulation
                .criarJsonCandidato(InvalidacoesCandidato.DOB_NULO);

        /********************************************************************
         Executo de fato a operação, tentando cadastrar/criar um candidato com um DOB nulo.
         Recupero o retorno da requisição para realizar as validações.    *
         *********************************************************************/
        InvalidDTO invalidDTO =
                candidatoService.cadastroCandidatoInvalido(converterJsonParaArrayDeBytes(candidatoCriado),
                        getAuthenticatedToken(), HttpStatus.SC_BAD_REQUEST, documentoValido);

        Assert.assertTrue(Arrays.stream(invalidDTO.getErrors())
                .allMatch(erro -> erro.contains("dataNascimento: must not be null")));

    }

}
