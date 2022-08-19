package Utils;

import Login.Enums.TipoDeInvalidacao;
import MassaDeDados.PostPaths;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static Login.Enums.TipoDeInvalidacao.*;
import static Utils.InvalidacoesCandidato.*;

public class JsonManipulation{

    public static Map<String, String> criarJsonCadastro(Map<String, String> login){
        // caminho da massa de dados

        String pathJson = PostPaths.loginCriado;

        JSONObject file = new JSONObject();
        file.put("email", login.get("email"));
        file.put("senha", login.get("senha"));

        try (PrintWriter out = new PrintWriter(new FileWriter(pathJson))) {
            out.write(file.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return login;
    }

    /**************************************************************************
     * Ao chamar esse método sem passar invalidacoes como parametro, retorno um
     * candidato populado corretamente, ou seja, um candidato válido.
     *
     */
    public static JSONObject criarJsonCandidato(){
        // caminho da massa de dados
        String pathJson = PostPaths.candidatoCriado;

        // Aqui instancio uma das bibliotecas que gera os dados fakes da aplicação;
        Faker faker = new Faker();

        // Aqui populo os dados pessoais como nome, cpf, telefone e data e nascimento.
        HashMap<String, String> dadosPessoais = CandidatoFaker.criarDadosPessoais(faker);

        // Aqui populo os elementos do endereco como cep, logradouro, numero, etc.
        JSONObject enderecoJSON = CandidatoFaker.criarEndereco(faker);

        // Aqui populo ou nao as escolaridades da pessoa, com elementos como instituição de ensino e tempo de permanencia.
        List<JSONObject> escolaridades = new ArrayList<>(List.of(CandidatoFaker.criarEscolaridade(faker)));

        // Aqui populo ou nao as experiencias da pessoa, com elementos como empresas antigas e cargo de ocupação.
        List<JSONObject> experiencias = new ArrayList<>(List.of(CandidatoFaker.criarExperiencia(faker)));

        // Por fim monto o JSON final com o candidato completo, para retornar para o método anterior que chamará a re-
        // quisicao e para salvar o objeto em um arquivo JSON nas resources da aplicação;
        JSONObject candidatoCompleto = CandidatoFaker.preencherCandidatoCompleto(faker, dadosPessoais, enderecoJSON, escolaridades, experiencias);

        try (PrintWriter out = new PrintWriter(new FileWriter(pathJson))) {
            out.write(candidatoCompleto.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return candidatoCompleto;
    }


    /**************************************************************************
     * Aqui adiciono as invalidações ao JSON que sera enviado para teste;
     *
     */
    public static JSONObject criarJsonCandidato(InvalidacoesCandidato invalidacoesCandidato){
        // caminho da massa de dados
        String pathJson = PostPaths.candidatoCriado;

        // Aqui instancio uma das bibliotecas que gera os dados fakes da aplicação;
        Faker faker = new Faker();

        // Aqui populo os dados pessoais como nome, cpf, telefone e data e nascimento.
        HashMap<String, String> dadosPessoais = CandidatoFaker.criarDadosPessoais(faker, invalidacoesCandidato);

        // Aqui populo os elementos do endereco como cep, logradouro, numero, etc.
        JSONObject enderecoJSON = CandidatoFaker.criarEndereco(faker);

        // Aqui populo ou nao as escolaridades da pessoa, com elementos como instituição de ensino e tempo de permanencia.
        List<JSONObject> escolaridades = new ArrayList<>(List.of(CandidatoFaker.criarEscolaridade(faker)));

        // Aqui populo ou nao as experiencias da pessoa, com elementos como empresas antigas e cargo de ocupação.
        List<JSONObject> experiencias = new ArrayList<>(List.of(CandidatoFaker.criarExperiencia(faker)));

        // Por fim monto o JSON final com o candidato completo, para retornar para o método anterior que chamará a re-
        // quisicao e para salvar o objeto em um arquivo JSON nas resources da aplicação;
        JSONObject candidatoCompleto = CandidatoFaker.preencherCandidatoCompleto(faker, dadosPessoais, enderecoJSON, escolaridades, experiencias);

        try (PrintWriter out = new PrintWriter(new FileWriter(pathJson))) {
            out.write(candidatoCompleto.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return candidatoCompleto;
    }


    public static Map<String, String> recuperarCadastro(){

        // caminho da massa de dados
        String pathJson = "src/test/resources/data/login/loginCriado.json";

        // Recuperando o usuario completo em formato de json
        JSONObject jsonObject = recuperarJSONObject(pathJson);
        String email = (String) jsonObject.get("email");
        String senha = (String) jsonObject.get("senha");

        Map<String, String> login = new HashMap<>();
        login.put("email", email);
        login.put("senha", senha);

        return login;

    }

    private static JSONObject recuperarJSONObject(String jsonPath){
        Object parse = null;
        try {
            parse = JSONValue.parse(new FileReader(
                    jsonPath));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        // Converte o JSONParser para JSONObject
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.convertValue(parse, JSONObject.class);
    }

}
