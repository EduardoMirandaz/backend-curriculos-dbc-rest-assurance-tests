package Vaga;

import Login.dto.PageDTO;
import Login.dto.Vaga;
import Login.service.VagaService;
import Utils.JsonManipulation;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;

import static Login.AutenticacaoDeUsuarioTests.getAuthenticatedToken;

public class RecuperarVagasTests {

    static VagaService vagaService = new VagaService();

    @Test
    public void listarVagasValido(){

        /********************************************************************
         Crio um novo candidato válido. *
         ********************************************************************/
        JSONObject candidatoEnviadoParaRequisicao = JsonManipulation
                .criarJsonCandidato();

        /********************************************************************
         Executo de fato a operação, tentando listar as vagas. *
         *********************************************************************/
        String nroPagina = "1";
        String tamanhoCadaPagina =  "100";

        PageDTO pageDTO =
                vagaService.listarVagasValido(getAuthenticatedToken(), nroPagina, tamanhoCadaPagina);

        ObjectMapper objectMapper = new ObjectMapper();
        ArrayList<Vaga> vagas = objectMapper.convertValue(pageDTO.vagaGeralList, ArrayList.class);

        /**************************************************************************
         Realizando a validação de que a lista de vagas retornada nao é nula.*
         **************************************************************************/

        Assert.assertNotNull(vagas);

    }

    @Test
    public void listarVagasVazias(){

        /********************************************************************
         Crio um novo candidato válido. *
         ********************************************************************/
        JSONObject candidatoEnviadoParaRequisicao = JsonManipulation
                .criarJsonCandidato();

        /********************************************************************
         Executo de fato a operação, tentando listar vagas vazias. *
         *********************************************************************/
        String nroPagina = "0";
        String tamanhoCadaPagina =  "0";

        PageDTO pageDTO =
                vagaService.listarVagasValido(getAuthenticatedToken(), nroPagina, tamanhoCadaPagina);

        ObjectMapper objectMapper = new ObjectMapper();
        ArrayList<Vaga> vagas = objectMapper.convertValue(pageDTO.vagaGeralList, ArrayList.class);

        /**************************************************************************
         Realizando a validação de que a lista de vagas está vazia.*
         **************************************************************************/

        Assert.assertEquals(vagas.size(), 0);

    }

}
