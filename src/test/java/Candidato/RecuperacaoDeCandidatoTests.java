package Candidato;

import Login.dto.CandidatoValidoDTO;
import Login.service.CandidatoService;
import org.testng.Assert;
import org.testng.annotations.Test;

import static Candidato.CriacaoDeCandidatoTests.cadastrarCandidato;
import static Candidato.DelecaoDeCandidatoTests.deletarCandidato;
import static Login.AutenticacaoDeUsuarioTests.getAuthenticatedToken;

public class RecuperacaoDeCandidatoTests {
    CandidatoService candidatoService = new CandidatoService();

    @Test
    public void getCandidatosValido(){

        /********************************************************************
         Crio um novo candidato válido, pois caso o banco esteja vazio, pelo
         menos um candidato terá para que eu possa realizar as validacoes. *
         ********************************************************************/
        CandidatoValidoDTO candidatoCriado = cadastrarCandidato();

        CandidatoValidoDTO[] candidatosRecuperados =
                candidatoService.recuperarCandidatosAutenticado(getAuthenticatedToken());

        /**************************************************************************
         Realizando a validação de que os campos criados nao sao nulos.*
         **************************************************************************/
        // verificando que o ID retornado não é nulo.
        Assert.assertNotNull( candidatosRecuperados[0].getIdCandidato());

        Assert.assertNotNull(candidatosRecuperados[0].getNome());

        Assert.assertNotNull(candidatosRecuperados[0].getCargo());

        Assert.assertNotNull(candidatosRecuperados[0].getDataNascimento().toString());

        Assert.assertNotNull(candidatosRecuperados[0].getSenioridade().toString());

        Assert.assertNotNull(candidatosRecuperados[0].getCurriculoUrl());

        /**************************
         * Deletando o candidato após o teste, para nao poluir o banco.
         */
        deletarCandidato(candidatoCriado.getIdCandidato());
    }

    @Test
    public void getCandidatosSemAutenticacao(){

        // Validando as responses (Espero um forbidden: 403)
        candidatoService.recuperarCandidatosAutenticacaoInvalida(getAuthenticatedToken());

    }


}
