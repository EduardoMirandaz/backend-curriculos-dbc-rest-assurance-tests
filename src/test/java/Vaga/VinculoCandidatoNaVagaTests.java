package Vaga;

import Login.dto.CandidatoValidoDTO;
import Login.dto.InvalidDTO;
import Login.service.VagaService;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;

import static Candidato.CriacaoDeCandidatoTests.cadastrarCandidato;
import static Login.AutenticacaoDeUsuarioTests.getAuthenticatedToken;

public class VinculoCandidatoNaVagaTests {

    static VagaService vagaService = new VagaService();

    @Test
    public void vincularCandidatoAVagaValido(){

        /********************************************************************
         Crio um novo candidato válido. *
         ********************************************************************/
        CandidatoValidoDTO candidatoCriado = cadastrarCandidato();
        Integer idCandidato = candidatoCriado.getIdCandidato();
        Integer idVaga = 25865;

        /********************************************************************
         Executo de fato a operação, tentando vincular um candidato válido
         a uma vaga válida. *
         *********************************************************************/
        vagaService.vincularCandidatoAVagaValido(
                        getAuthenticatedToken(), idVaga, idCandidato);

    }

    @Test
    public void vincularCandidatoInvalidoAVaga(){

        /********************************************************************
         Passando um id de candidato inválido. *
         ********************************************************************/
        Integer idCandidato = -1;
        Integer idVaga = 25865;

        /********************************************************************
         Executo de fato a operação, tentando vincular um candidato inválido
         a uma vaga válida. *
         *********************************************************************/
        InvalidDTO invalidDTO = vagaService.vincularCandidatoAVagaInvalido(
                getAuthenticatedToken(), idVaga, idCandidato);

        /********************************************************************
         Valido o erro de candidato nao encontrado.
         *********************************************************************/
        Assert.assertTrue(Arrays.stream(invalidDTO.getErrors())
                .allMatch(erro -> erro.contains("Candidato não encontrado")));

    }

    @Test
    public void desvincularCandidatoAVagaValido(){

        /********************************************************************
         Crio um novo candidato válido. *
         ********************************************************************/
        CandidatoValidoDTO candidatoCriado = cadastrarCandidato();
        Integer idCandidato = candidatoCriado.getIdCandidato();
        Integer idVaga = 25865;

        /********************************************************************
         Executo de fato a operação, tentando desvincular um candidato válido
         a uma vaga válida. *
         *********************************************************************/
        vagaService.desvincularCandidatoAVagaValido(
                getAuthenticatedToken(), idVaga, idCandidato);

    }

    @Test
    public void desvincularCandidatoInvalidoAVaga(){

        /********************************************************************
         Passando um id de candidato inválido. *
         ********************************************************************/
        Integer idCandidato = -1;
        Integer idVaga = 25865;

        /********************************************************************
         Executo de fato a operação, tentando desvincular um candidato inválido
         a uma vaga válida. *
         *********************************************************************/
        InvalidDTO invalidDTO = vagaService.desvincularCandidatoAVagaInvalido(
                getAuthenticatedToken(), idVaga, idCandidato);

        /********************************************************************
         Valido o erro de candidato nao encontrado.
         *********************************************************************/
        Assert.assertTrue(Arrays.stream(invalidDTO.getErrors())
                .allMatch(erro -> erro.contains("Candidato não encontrado")));

    }

    @Test
    public void vincularCandidatoAVagaSemAutorizacao(){

        /********************************************************************
         Crio um novo candidato válido. *
         ********************************************************************/
        CandidatoValidoDTO candidatoCriado = cadastrarCandidato();
        Integer idCandidato = candidatoCriado.getIdCandidato();
        Integer idVaga = 25865;

        /********************************************************************
         Executo de fato a operação, tentando vincular um candidato válido
         a uma vaga válida porém sem autorização. *
         *********************************************************************/
        vagaService.vincularCandidatoAVagaSemAutorizacao(idVaga, idCandidato);


    }


    @Test
    public void desvincularCandidatoAVagaSemAutorizacao(){

        /********************************************************************
         Crio um novo candidato válido. *
         ********************************************************************/
        CandidatoValidoDTO candidatoCriado = cadastrarCandidato();
        Integer idCandidato = candidatoCriado.getIdCandidato();
        Integer idVaga = 25865;

        /********************************************************************
         Executo de fato a operação, tentando desvincular um candidato válido
         a uma vaga válida porém sem autorização. *
         *********************************************************************/
        vagaService.desvincularCandidatoAVagaSemAutorizacao(idVaga, idCandidato);
        
    }
}
