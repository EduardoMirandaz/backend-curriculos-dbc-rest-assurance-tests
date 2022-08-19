package Candidato;

import Login.dto.CandidatoValidoDTO;
import Login.service.CandidatoService;
import org.testng.Assert;
import org.testng.annotations.Test;

import static Login.AutenticacaoDeUsuarioTests.getAuthenticatedToken;

public class RecuperacaoDeCandidatoTests {


    CandidatoService candidatoService = new CandidatoService();

    @Test
    public void getCandidatosValido(){

        /********************************************************************
         Crio um novo candidato válido. *
         ********************************************************************/
//        cadastrarCandidato();

        CandidatoValidoDTO[] candidatosRecuperados =
                candidatoService.recuperarCandidatosAutenticado(getAuthenticatedToken());

        /**************************************************************************
         Realizando a validação de que os campos criados nao sao nulos.*
         **************************************************************************/
        // verificando que o ID gerado não é nulo.
        Assert.assertNotNull( candidatosRecuperados[0].getIdCandidato());

        Assert.assertNotNull(candidatosRecuperados[0].getNome());

        Assert.assertNotNull(candidatosRecuperados[0].getCargo());

        Assert.assertNotNull(candidatosRecuperados[0].getDataNascimento().toString());

        Assert.assertNotNull(candidatosRecuperados[0].getSenioridade().toString());

        Assert.assertNotNull(candidatosRecuperados[0].getCurriculoUrl());
    }

    @Test
    public void getCandidatosSemAutenticacao(){

        // Validando as responses
        candidatoService.recuperarCandidatosAutenticacaoInvalida(getAuthenticatedToken());

    }


}
