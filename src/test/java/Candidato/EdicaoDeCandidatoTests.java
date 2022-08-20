package Candidato;

import Login.dto.CandidatoCompletoDTO;
import Login.dto.CandidatoValidoDTO;
import Login.dto.InvalidDTO;
import Login.service.CandidatoService;
import Utils.Enums.AtributoASerEditado;
import Utils.JsonManipulation;
import org.apache.http.HttpStatus;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;

import static Candidato.CriacaoDeCandidatoTests.cadastrarCandidato;
import static Login.AutenticacaoDeUsuarioTests.getAuthenticatedToken;
import static MassaDeDados.Paths.*;
import static Utils.Util.converterJsonParaArrayDeBytes;

public class EdicaoDeCandidatoTests {

    static CandidatoService candidatoService = new CandidatoService();

    @Test
    public void editarNomeCandidatoValido(){

        /********************************************************************
         Crio um novo candidato válido. *
         ********************************************************************/
        CandidatoValidoDTO candidatoCriado = cadastrarCandidato();

        /**
         *
         * Realizar edição no Json
         */
        Integer idCandidatoASerEditado = candidatoCriado.getIdCandidato();

        JSONObject candidatoEnviadoParaRequisicao = JsonManipulation
                .criarJsonCandidatoEditado(AtributoASerEditado.NOME, idCandidatoASerEditado);


        /********************************************************************
         Executo de fato a operação, tentando editar o candidato que criei. *
         Recupero o retorno da requisição para realizar as validações.    *
         *********************************************************************/
        CandidatoCompletoDTO candidatoEditadoRetornado =
                candidatoService.edicaoCandidatoValido(converterJsonParaArrayDeBytes(candidatoEditado),
                        getAuthenticatedToken(), HttpStatus.SC_OK);

        /**************************************************************************
         Realizando a validação de que os campos criados conferem com os enviados
         e que os editados estão de fato diferentes.
         **************************************************************************/
        // verificando que o ID retornado não é nulo.
        Assert.assertNotNull( candidatoEditadoRetornado.getIdCandidato());

        // Verificando que o nome de fato foi alterado
        Assert.assertNotEquals( candidatoCriado.getNome(),
                candidatoEditadoRetornado.getNome());

        Assert.assertEquals( candidatoCriado.getCargo(),
                candidatoEditadoRetornado.getCargo());

        Assert.assertEquals( candidatoCriado.getDataNascimento().toString(),
                candidatoEditadoRetornado.getDataNascimento().toString());

        Assert.assertEquals( candidatoCriado.getSenioridade().toString(),
                candidatoEditadoRetornado.getSenioridade().toString());

        // Verificando os enderecos
        Assert.assertEquals(candidatoEnviadoParaRequisicao.get("endereco").toString(),
                candidatoEditadoRetornado.getEndereco().toString());

        // Verificando as escolaridades
        String escolaridades = candidatoEnviadoParaRequisicao.get("escolaridades").toString();
        String expected = Arrays.toString(candidatoEditadoRetornado.getEscolaridade());
        Assert.assertEquals(escolaridades, expected);


        // Verificando as experiencias
        String experiencias = candidatoEnviadoParaRequisicao.get("experiencias").toString();
        String expectedXp = Arrays.toString(candidatoEditadoRetornado.getExperiencia());
        Assert.assertEquals(experiencias, expectedXp);

        // Recuperando a lista de candidatos e vendo se o mesmo se encontra nela
        CandidatoValidoDTO[] candidatosRecuperados =
                candidatoService.recuperarCandidatosAutenticado(getAuthenticatedToken());

        Assert.assertTrue(Arrays.stream(candidatosRecuperados).
                anyMatch(
                        candidato -> candidato.getIdCandidato().equals(candidatoEditadoRetornado.getIdCandidato())));

    }

    @Test
    public void editarDOBCandidatoValido(){

        /********************************************************************
         Crio um novo candidato válido. *
         ********************************************************************/
        CandidatoValidoDTO candidatoCriado = cadastrarCandidato();

        /**
         *
         * Realizar edição no Json
         */
        Integer idCandidatoASerEditado = candidatoCriado.getIdCandidato();

        JSONObject candidatoEnviadoParaRequisicao = JsonManipulation
                .criarJsonCandidatoEditado(AtributoASerEditado.DOB, idCandidatoASerEditado);


        /********************************************************************
         Executo de fato a operação, tentando editar o candidato que criei. *
         Recupero o retorno da requisição para realizar as validações.    *
         *********************************************************************/
        CandidatoCompletoDTO candidatoEditadoRetornado =
                candidatoService.edicaoCandidatoValido(converterJsonParaArrayDeBytes(candidatoEditado),
                        getAuthenticatedToken(), HttpStatus.SC_OK);

        /**************************************************************************
         Realizando a validação de que os campos criados conferem com os enviados
         e que os editados estão de fato diferentes.
         **************************************************************************/
        // verificando que o ID retornado não é nulo.
        Assert.assertNotNull( candidatoEditadoRetornado.getIdCandidato());

        Assert.assertEquals( candidatoCriado.getNome(),
                candidatoEditadoRetornado.getNome());

        Assert.assertEquals( candidatoCriado.getCargo(),
                candidatoEditadoRetornado.getCargo());

        // Verificando que a data foi de fato alterada
        Assert.assertNotEquals( candidatoCriado.getDataNascimento().toString(),
                candidatoEditadoRetornado.getDataNascimento().toString());

        Assert.assertEquals( candidatoCriado.getSenioridade().toString(),
                candidatoEditadoRetornado.getSenioridade().toString());

        // Verificando os enderecos
        Assert.assertEquals(candidatoEnviadoParaRequisicao.get("endereco").toString(),
                candidatoEditadoRetornado.getEndereco().toString());

        // Verificando as escolaridades
        String escolaridades = candidatoEnviadoParaRequisicao.get("escolaridades").toString();
        String expected = Arrays.toString(candidatoEditadoRetornado.getEscolaridade());
        Assert.assertEquals(escolaridades, expected);


        // Verificando as experiencias
        String experiencias = candidatoEnviadoParaRequisicao.get("experiencias").toString();
        String expectedXp = Arrays.toString(candidatoEditadoRetornado.getExperiencia());
        Assert.assertEquals(experiencias, expectedXp);

        // Recuperando a lista de candidatos e vendo se o mesmo se encontra nela
        CandidatoValidoDTO[] candidatosRecuperados =
                candidatoService.recuperarCandidatosAutenticado(getAuthenticatedToken());

        Assert.assertTrue(Arrays.stream(candidatosRecuperados).
                anyMatch(
                        candidato -> candidato.getIdCandidato().equals(candidatoEditadoRetornado.getIdCandidato())));

    }

//    @Test
//    public void editarCandidatoRepetido(){
//
//        /********************************************************************
//         Crio um novo candidato válido. *
//         ********************************************************************/
//        JsonManipulation.criarJsonCandidato();
//        candidatoService.cadastroCandidatoValido(converterJsonParaArrayDeBytes(candidatoCriado),getAuthenticatedToken());
//
//        /********************************************************************
//         Executo de fato a operação, tentando cadastrar/criar um candidato com um CPF já existente no banco
//         inferior ao tamanho permitido. Recupero o retorno da requisição para realizar as validações.    *
//         *********************************************************************/
//
//        InvalidDTO invalidDTO = candidatoService.cadastroCandidatoInvalido(converterJsonParaArrayDeBytes(candidatoCriado),
//                getAuthenticatedToken(), HttpStatus.SC_BAD_REQUEST, documentoValido);
//
//        Assert.assertTrue(Arrays.stream(invalidDTO.getErrors())
//                .allMatch(erro -> erro.contains("CPF já cadastrado e número já cadastrado.")));
//
//    }
//
}
