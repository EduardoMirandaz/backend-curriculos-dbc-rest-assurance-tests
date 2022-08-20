package Vaga;

import Login.dto.CandidatoValidoDTO;
import Login.dto.InvalidDTO;
import Login.service.CandidatoService;
import Login.service.VagaService;
import Utils.JsonManipulation;
import org.apache.http.HttpStatus;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;

import static Login.AutenticacaoDeUsuarioTests.getAuthenticatedToken;
import static MassaDeDados.Paths.*;
import static Utils.Util.converterJsonParaArrayDeBytes;

public class AdicionarCandidatoNaVagaTests {

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



}
