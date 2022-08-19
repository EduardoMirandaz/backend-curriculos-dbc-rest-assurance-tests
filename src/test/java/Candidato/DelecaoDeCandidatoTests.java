package Candidato;

import Login.dto.CandidatoValidoDTO;
import Login.dto.InvalidDTO;
import Login.service.CandidatoService;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;

import static Candidato.CriacaoDeCandidatoTests.cadastrarCandidato;
import static Login.AutenticacaoDeUsuarioTests.getAuthenticatedToken;

public class DelecaoDeCandidatoTests {


    CandidatoService candidatoService = new CandidatoService();

    @Test
    public void deletarCandidatoValido(){

        /********************************************************************
         Crio um novo candidato válido. *
         ********************************************************************/
        CandidatoValidoDTO candidatoValidoDTO = cadastrarCandidato();

        /********************************************************************
         Recupera o id do candidato criado e o remove. *
         ********************************************************************/
        Integer idCandidato = candidatoValidoDTO.getIdCandidato();
        candidatoService.deletarCandidatoValido(getAuthenticatedToken(), idCandidato);

        /**************************************************************************************************************
         Validacoes: recupero os candidatos cadastrados e verifico que nao é possível encontrar o que eu exclui. *
         *************************************************************************************************************/

        CandidatoValidoDTO[] candidatosRecuperados =
                candidatoService.recuperarCandidatosAutenticado(getAuthenticatedToken());

        Assert.assertFalse(Arrays.stream(candidatosRecuperados).
                anyMatch(
                        candidato -> candidato.getIdCandidato().equals(idCandidato)))
                ;

    }

    @Test
    public void deletarCandidatoInexistente(){


        /********************************************************************
        Por questões de validacao, recuperarei quantos candidatos tenho cadastrado antes da deleção invalida. *
         ********************************************************************/
        Integer quantidadeDeCanditadosInicial = candidatoService.recuperarCandidatosAutenticado(getAuthenticatedToken()).length;


        /********************************************************************
         Indicando um id inválido para remoção. *
         ********************************************************************/
        Integer idCandidato = -1; // Passo "-1" para a requisicao de delecao que
                                  // é um ID invalido para os candidatos da aplicacao
        InvalidDTO invalidDTO = candidatoService.deletarCandidatoInexistente(getAuthenticatedToken(), idCandidato);

        /**************************************************************************************************************
         Validacoes: verifico que foi de fato impossível excluir um usuário inexistente, através da
         validação da resposta da requisição e da equivalencia do tamanho da lista de candidatos. *
         *************************************************************************************************************/

        Assert.assertTrue(Arrays.stream(invalidDTO.getErrors())
                .allMatch(erro -> erro.contains("Candidato não encontrado")));

        Integer quantidadeDeCanditadosFinal = candidatoService.recuperarCandidatosAutenticado(getAuthenticatedToken()).length;

        Assert.assertEquals(quantidadeDeCanditadosInicial, quantidadeDeCanditadosFinal);

    }


    @Test
    public void deletarCandidatoSemPermissao(){

        /********************************************************************
         Crio um novo candidato válido. *
         ********************************************************************/
        CandidatoValidoDTO candidatoValidoDTO = cadastrarCandidato();


        /********************************************************************
         Por questões de validacao, recuperarei quantos candidatos tenho cadastrado antes da deleção invalida. *
         ********************************************************************/
        Integer quantidadeDeCanditadosInicial = candidatoService.recuperarCandidatosAutenticado(getAuthenticatedToken()).length;


        /********************************************************************
         Indicando um id inválido para remoção. *
         ********************************************************************/
        Integer idCandidato = candidatoValidoDTO.getIdCandidato();
        candidatoService.deletarCandidatoSemAutenticacao(null, idCandidato);

        /**************************************************************************************************************
         Validacoes: verifico que foi de fato impossível excluir um usuário sem autenticacao, através da
         validação da resposta da requisição (forbidden) e da equivalencia do tamanho da lista de candidatos. *
         *************************************************************************************************************/

        Integer quantidadeDeCanditadosFinal = candidatoService.recuperarCandidatosAutenticado(getAuthenticatedToken()).length;

        Assert.assertEquals(quantidadeDeCanditadosInicial, quantidadeDeCanditadosFinal);


        // por fim, apago de maneira válida o usuário criado para encerrar o ciclo do teste.
        candidatoService.deletarCandidatoValido(getAuthenticatedToken(), idCandidato);

    }

}
