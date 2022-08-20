package Vaga;

import Login.dto.PageDTO;
import Login.service.CandidatoService;
import Login.service.VagaService;
import Utils.JsonManipulation;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import static Login.AutenticacaoDeUsuarioTests.getAuthenticatedToken;

public class RecuperarVagasTests {

    static VagaService vagaService = new VagaService();

    @Test
    public void vincularCandidatoAVagaValido(){
//
//        /********************************************************************
//         Crio um novo candidato válido. *
//         ********************************************************************/
//        JSONObject candidatoEnviadoParaRequisicao = JsonManipulation
//                .criarJsonCandidato();
//
//        /********************************************************************
//         Executo de fato a operação, tentando vincular um candidato válido
//         a uma vaga válida. *
//         *********************************************************************/
//        CandidatoValidoDTO candidatoValidoDTO =
//                candidatoService.cadastroCandidatoValido(converterJsonParaArrayDeBytes(candidatoCriado),
//                        getAuthenticatedToken(), documentoValido);
//
//
//        /**************************************************************************
//         Realizando a validação de que os campos criados conferem com os enviados.*
//         **************************************************************************/


    }

    @Test
    public void listarVagasValido(){

        /********************************************************************
         Crio um novo candidato válido. *
         ********************************************************************/
        JSONObject candidatoEnviadoParaRequisicao = JsonManipulation
                .criarJsonCandidato();

        /********************************************************************
         Executo de fato a operação, tentando vincular um candidato válido
         a uma vaga válida. *
         *********************************************************************/
        String nroPagina = "1";
        String tamanhoCadaPagina =  "100";

        PageDTO pageDTO =
                vagaService.listarVagasValido(getAuthenticatedToken(), nroPagina, tamanhoCadaPagina);


        /**************************************************************************
         Realizando a validação de que os campos criados conferem com os enviados.*
         **************************************************************************/

        Assert.assertNotNull(pageDTO);

    }

}
