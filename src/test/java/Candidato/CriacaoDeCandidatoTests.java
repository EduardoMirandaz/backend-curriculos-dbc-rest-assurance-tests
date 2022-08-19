package Candidato;

import Login.dto.CandidatoValidoDTO;
import Login.service.CandidatoService;
import Utils.JsonManipulation;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;

import static Login.AutenticacaoDeUsuarioTests.getAuthenticatedToken;
import static MassaDeDados.PostPaths.candidatoCriado;
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
                        getAuthenticatedToken());



        /**************************************************************************
         Realizando a validação de que os campos criados conferem com os enviados.*
         **************************************************************************/
        // verificando que o ID gerado não é nulo.
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

    }

    static CandidatoValidoDTO cadastrarCandidato() {
        JsonManipulation.criarJsonCandidato();
        return candidatoService.cadastroCandidatoValido(converterJsonParaArrayDeBytes(candidatoCriado),
                getAuthenticatedToken());
    }

}
