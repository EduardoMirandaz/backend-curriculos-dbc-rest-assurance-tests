package Candidato.Criacao;

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

public class CEPsTests {

    CandidatoService candidatoService = new CandidatoService();

    @Test
    public void criarCandidatoCEPInvalidoTamanhoSuperior(){

        /********************************************************************
         Crio um novo candidato válido. *
         ********************************************************************/
        JSONObject candidatoEnviadoParaRequisicao = JsonManipulation
                .criarJsonCandidato(InvalidacoesCandidato.CEP_ACIMA_TAMANHO_MAXIMO);

        /********************************************************************
         Executo de fato a operação, tentando cadastrar/criar um candidato com um CEP de tamanho
         superior ao tamanho permitido. Recupero o retorno da requisição para realizar as validações.    *
         *********************************************************************/
        InvalidDTO invalidDTO =
                candidatoService.cadastroCandidatoInvalido(converterJsonParaArrayDeBytes(candidatoCriado),
                        getAuthenticatedToken(), HttpStatus.SC_BAD_REQUEST, documentoValido);

        Assert.assertTrue(Arrays.stream(invalidDTO.getErrors())
                .allMatch(erro -> erro.contains("endereco.cep: O cep deve conter 8 dígitos")
                        || erro.contains("endereco.cep: O cep deve estar no seguinte formato: 00000000")));

    }

    @Test
    public void criarCandidatoCEPInvalidoTamanhoInferior(){

        /********************************************************************
         Crio um novo candidato válido. *
         ********************************************************************/
        JSONObject candidatoEnviadoParaRequisicao = JsonManipulation
                .criarJsonCandidato(InvalidacoesCandidato.CEP_ABAIXO_TAMANHO_MINIMO);

        /********************************************************************
         Executo de fato a operação, tentando cadastrar/criar um candidato com um CEP de tamanho
         inferior ao tamanho permitido. Recupero o retorno da requisição para realizar as validações.    *
         *********************************************************************/
        InvalidDTO invalidDTO =
                candidatoService.cadastroCandidatoInvalido(converterJsonParaArrayDeBytes(candidatoCriado),
                        getAuthenticatedToken(), HttpStatus.SC_BAD_REQUEST, documentoValido);

        Assert.assertTrue(Arrays.stream(invalidDTO.getErrors())
                .allMatch(erro -> erro.contains("endereco.cep: O cep deve conter 8 dígitos")
                        || erro.contains("endereco.cep: O cep deve estar no seguinte formato: 00000000")));

    }

    @Test
    public void criarCandidatoCEPEmBranco(){

        /********************************************************************
         Crio um novo candidato válido. *
         ********************************************************************/
        JSONObject candidatoEnviadoParaRequisicao = JsonManipulation
                .criarJsonCandidato(InvalidacoesCandidato.CEP_EM_BRANCO);

        /********************************************************************
         Executo de fato a operação, tentando cadastrar/criar um candidato com um CEP em.
         branco. Recupero o retorno da requisição para realizar as validações.    *
         *********************************************************************/
        InvalidDTO invalidDTO =
                candidatoService.cadastroCandidatoInvalido(converterJsonParaArrayDeBytes(candidatoCriado),
                        getAuthenticatedToken(), HttpStatus.SC_BAD_REQUEST, documentoValido);

        Assert.assertTrue(Arrays.stream(invalidDTO.getErrors())
                .allMatch(erro -> erro.contains("cep: must not be blank")
                        || erro.contains("endereco.cep: O cep deve conter 8 dígitos")
                        || erro.contains("endereco.cep: O cep deve estar no seguinte formato: 00000000")));

    }


    @Test
    public void criarCandidatoCEPNulo(){

        /********************************************************************
         Crio um novo candidato válido. *
         ********************************************************************/
        JSONObject candidatoEnviadoParaRequisicao = JsonManipulation
                .criarJsonCandidato(InvalidacoesCandidato.CEP_NULO);

        /********************************************************************
         Executo de fato a operação, tentando cadastrar/criar um candidato com um CEP nulo.
         Recupero o retorno da requisição para realizar as validações.    *
         *********************************************************************/
        InvalidDTO invalidDTO =
                candidatoService.cadastroCandidatoInvalido(converterJsonParaArrayDeBytes(candidatoCriado),
                        getAuthenticatedToken(), HttpStatus.SC_BAD_REQUEST, documentoValido);

        Assert.assertTrue(Arrays.stream(invalidDTO.getErrors())
                .allMatch(erro -> erro.contains("cep: must not be blank")));

    }

}
