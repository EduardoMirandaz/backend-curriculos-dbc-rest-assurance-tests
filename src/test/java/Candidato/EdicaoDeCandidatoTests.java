package Candidato;

import Login.dto.CandidatoCompletoDTO;
import Login.dto.CandidatoValidoDTO;
import Login.dto.InvalidDTO;
import Login.service.CandidatoService;
import MassaDeDados.Paths;
import Utils.Enums.AtributoASerEditado;
import Utils.JsonManipulation;
import org.apache.http.HttpStatus;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

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

    @Test
    public void editarDOBCandidatoInvalido(){

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

    @Test
    public void editarEnderecoCandidatoValido(){

        /********************************************************************
         Crio um novo candidato válido. *
         ********************************************************************/
        CandidatoValidoDTO candidatoCriado = cadastrarCandidato();
        JSONObject candidatoCriadoCompleto = JsonManipulation.recuperarJSONObject(Paths.candidatoCriado);

        /**
         *
         * Realizar edição no Json
         */
        Integer idCandidatoASerEditado = candidatoCriado.getIdCandidato();


        JSONObject candidatoEnviadoParaRequisicao = JsonManipulation
                .criarJsonCandidatoEditado(AtributoASerEditado.ENDERECO, idCandidatoASerEditado);


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
        Assert.assertEquals( candidatoCriado.getDataNascimento().toString(),
                candidatoEditadoRetornado.getDataNascimento().toString());

        Assert.assertEquals( candidatoCriado.getSenioridade().toString(),
                candidatoEditadoRetornado.getSenioridade().toString());

        // Verificando os enderecos
        Assert.assertEquals(candidatoEnviadoParaRequisicao.get("endereco").toString(),
                candidatoEditadoRetornado.getEndereco().toString());

        // Verificando os enderecos
        Assert.assertNotEquals(candidatoCriadoCompleto.get("endereco").toString(),
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
    public void editarExperienciasCandidatoValido(){

        /********************************************************************
         Crio um novo candidato válido. *
         ********************************************************************/
        CandidatoValidoDTO candidatoCriado = cadastrarCandidato();
        JSONObject candidatoCriadoCompleto = JsonManipulation.recuperarJSONObject(Paths.candidatoCriado);
        /**
         *
         * Realizar edição no Json
         */
        Integer idCandidatoASerEditado = candidatoCriado.getIdCandidato();

        // Recuperando quantas experiencias tinham para ver se o valor será de fato alterado.
        List<JSONObject> experienciasIniciais = (List<JSONObject>) candidatoCriadoCompleto.get("experiencias");


        JSONObject candidatoEnviadoParaRequisicao = JsonManipulation
                .criarJsonCandidatoEditado(AtributoASerEditado.EXPERIENCIAS, idCandidatoASerEditado);


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
        Assert.assertEquals( candidatoCriado.getDataNascimento().toString(),
                candidatoEditadoRetornado.getDataNascimento().toString());

        Assert.assertEquals( candidatoCriado.getSenioridade().toString(),
                candidatoEditadoRetornado.getSenioridade().toString());

        // Verificando os enderecos
        Assert.assertEquals(candidatoEnviadoParaRequisicao.get("endereco").toString(),
                candidatoEditadoRetornado.getEndereco().toString());

        // Verificando os enderecos
        Assert.assertEquals(candidatoCriadoCompleto.get("endereco").toString(),
                candidatoEditadoRetornado.getEndereco().toString());

        // Verificando as escolaridades
        String escolaridades = candidatoEnviadoParaRequisicao.get("escolaridades").toString();
        String expected = Arrays.toString(candidatoEditadoRetornado.getEscolaridade());
        Assert.assertEquals(escolaridades, expected);

        // Verificando as experiencias
        String experiencias = candidatoEnviadoParaRequisicao.get("experiencias").toString();
        String expectedXp = Arrays.toString(candidatoEditadoRetornado.getExperiencia());
        Assert.assertNotEquals(experiencias, expectedXp);

        // Verificando as experiencias para ver se de fato o tamanho aumentou
        List<JSONObject> experienciasAtuais = (List<JSONObject>) candidatoEnviadoParaRequisicao.get("experiencias");
        Assert.assertTrue(experienciasIniciais.size() < experienciasAtuais.size());

        // Recuperando a lista de candidatos e vendo se o mesmo se encontra nela
        CandidatoValidoDTO[] candidatosRecuperados =
                candidatoService.recuperarCandidatosAutenticado(getAuthenticatedToken());

        Assert.assertTrue(Arrays.stream(candidatosRecuperados).
                anyMatch(
                        candidato -> candidato.getIdCandidato().equals(candidatoEditadoRetornado.getIdCandidato())));

    }

    @Test
    public void editarCurriculoCandidatoValido(){

        /********************************************************************
         Crio um novo candidato válido. *
         ********************************************************************/
        CandidatoValidoDTO candidatoCriado = cadastrarCandidato();
        /**
         *
         * Realizar edição no Json
         */
        Integer idCandidatoASerEditado = candidatoCriado.getIdCandidato();

        /********************************************************************
         Executo de fato a operação, tentando editar o candidato que criei. *
         Recupero o retorno da requisição para realizar as validações.    *
         *********************************************************************/
        String urlCurriculoAlterado =
                candidatoService.edicaoCurriculoValido(idCandidatoASerEditado,
                        getAuthenticatedToken(), documentoValido2);

        /**************************************************************************
         Realizando a validação de que a imagem foi editada.
         **************************************************************************/
        // verificando que o url do documento retornado não é nulo.
        Assert.assertNotNull( urlCurriculoAlterado );

    }

    @Test
    public void editarCurriculoCandidatoIdInvalido(){

        /********************************************************************
         Executo de fato a operação, tentando editar o candidato que criei. *
         Recupero o retorno da requisição para realizar as validações.    *
         *********************************************************************/
        Integer idCandidatoASerEditado = -1 ;

        InvalidDTO invalidDTO = candidatoService.edicaoCurriculoCandidatoInvalido(idCandidatoASerEditado,
                getAuthenticatedToken(), HttpStatus.SC_NOT_FOUND, documentoValido2);

        /**************************************************************************
         Realizando a validação de que a imagem nao foi editada.
         **************************************************************************/
        Assert.assertTrue(Arrays.stream(invalidDTO.getErrors())
                .allMatch(erro -> erro.contains("Candidato não encontrado")));

    }

//    @Test
//    public void editarCurriculoCandidatoArquivoInvalido(){
//
//        /********************************************************************
//         Crio um novo candidato válido. *
//         ********************************************************************/
//        CandidatoValidoDTO candidatoCriado = cadastrarCandidato();
//        Integer idCandidatoASerEditado = candidatoCriado.getIdCandidato();
//
//        /********************************************************************
//         Executo de fato a operação, tentando editar o candidato que criei. *
//         Recupero o retorno da requisição para realizar as validações.    *
//         *********************************************************************/
//
//        InvalidDTO invalidDTO = candidatoService.edicaoCurriculoCandidatoInvalido(idCandidatoASerEditado,
//                getAuthenticatedToken(), HttpStatus.SC_NOT_FOUND, documentoInvalido);
//
//        /**************************************************************************
//         Realizando a validação de que a imagem nao foi editada.
//         **************************************************************************/
//        Assert.assertTrue(Arrays.stream(invalidDTO.getErrors())
//                .allMatch(erro -> erro.contains("Candidato não encontrado")));
//
//    }


}
