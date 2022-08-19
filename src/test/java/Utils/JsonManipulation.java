package Utils;

import Login.Enums.Senioridade;
import Login.Util.Geradores;
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

public class JsonManipulation{

    public static Map<String, String> criarJsonCadastro(Map<String, String> login){
        // caminho da massa de dados

        String pathJson = "src/test/resources/data/login/loginCriado.json";

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

    public static JSONObject criarJsonCandidato(){
        // caminho da massa de dados
        String pathJson = "src/test/resources/data/login/candidatoCriado.json";

        Faker faker = new Faker();

        String name = faker.name().fullName();
        String cpf = Geradores.cpf(false);
        String dateTime = Geradores.randomBirthday().toString();
        String phone = Geradores.gerarNumeroDeTelefone();

        JSONObject enderecoJSON = new JSONObject();
        enderecoJSON.put("numero", Integer.valueOf(faker.address().buildingNumber()));
        enderecoJSON.put("logradouro", faker.address().streetAddress());
        enderecoJSON.put("bairro", faker.address().cityPrefix());
        enderecoJSON.put("cidade", faker.address().city());
        enderecoJSON.put("cep", Geradores.gerarCEP());
        enderecoJSON.put("estado", Geradores.gerarUF());


        JSONObject escolaridade1 = new JSONObject();
        escolaridade1.put("instituicao", faker.company().name());
        escolaridade1.put("descricao", faker.pokemon().name());
        escolaridade1.put("nivel", faker.job().keySkills());
        escolaridade1.put("dataInicio", Geradores.randomBirthday().toString());
        escolaridade1.put("dataFim", Geradores.randomBirthday().toString());

        List<JSONObject> escolaridades = new ArrayList<>();
        escolaridades.add(escolaridade1);

        JSONObject experiencia1 = new JSONObject();
        experiencia1.put("instituicao", faker.company().name());
        experiencia1.put("descricao", faker.pokemon().name());
        experiencia1.put("cargo", faker.job().keySkills());
        experiencia1.put("dataInicio", Geradores.randomBirthday().toString());
        experiencia1.put("dataFim", Geradores.randomBirthday().toString());

        List<JSONObject> experiencias = new ArrayList<>();
        experiencias.add(experiencia1);

        JSONObject candidatoCompleto = new JSONObject();
        candidatoCompleto.put("nome", name);
        candidatoCompleto.put("cpf", cpf);
        candidatoCompleto.put("dataNascimento", dateTime);
        candidatoCompleto.put("telefone", phone);
        candidatoCompleto.put("senioridade", Senioridade.PLENO.toString());
        candidatoCompleto.put("cargo", faker.job().keySkills());
        candidatoCompleto.put("endereco", enderecoJSON);
        candidatoCompleto.put("escolaridades", escolaridades);
        candidatoCompleto.put("experiencias", experiencias);

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
