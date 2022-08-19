package Utils;

import Login.Enums.Senioridade;

import Login.Util.Geradores;
import com.github.javafaker.Faker;
import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.List;

public class CandidatoFaker {
    static JSONObject preencherCandidatoCompleto(Faker faker, HashMap<String, String> dadosPessoais, JSONObject enderecoJSON, List<JSONObject> escolaridades, List<JSONObject> experiencias) {
        JSONObject candidatoCompleto = new JSONObject();
        candidatoCompleto.put("nome", dadosPessoais.get("name"));
        candidatoCompleto.put("cpf", dadosPessoais.get("cpf"));
        candidatoCompleto.put("dataNascimento", dadosPessoais.get("dateTime"));
        candidatoCompleto.put("telefone", dadosPessoais.get("phone"));
        candidatoCompleto.put("senioridade", Senioridade.PLENO.toString());
        candidatoCompleto.put("cargo", faker.job().keySkills());
        candidatoCompleto.put("endereco", enderecoJSON);
        candidatoCompleto.put("escolaridades", escolaridades);
        candidatoCompleto.put("experiencias", experiencias);
        return candidatoCompleto;
    }

    static HashMap<String, String> criarDadosPessoais(Faker faker){
        HashMap<String, String> dadosPessoais = new HashMap<>();
        dadosPessoais.put("name", faker.name().fullName());
        dadosPessoais.put("cpf", Geradores.cpf(false));
        dadosPessoais.put("dateTime", Geradores.randomBirthday().toString());
        dadosPessoais.put("phone", Geradores.gerarNumeroDeTelefone());
        return dadosPessoais;
    }

    static HashMap<String, String> criarDadosPessoais(Faker faker, InvalidacoesCandidato tipoDeInvalidacao){
        HashMap<String, String> dadosPessoais = new HashMap<>();

        dadosPessoais.put("name", faker.name().fullName());

        popularCPF(dadosPessoais, tipoDeInvalidacao);


        dadosPessoais.put("dateTime", Geradores.randomBirthday().toString());

        popularTelefone(dadosPessoais, tipoDeInvalidacao);

        return dadosPessoais;
    }

    private static void popularTelefone(HashMap<String, String> dadosPessoais, InvalidacoesCandidato tipoDeInvalidacao) {
        if(tipoDeInvalidacao == InvalidacoesCandidato.TELEFONE_ACIMA_TAMANHO_MAXIMO){
            dadosPessoais.put("phone", Geradores.gerarNumeroDeTelefone()+"0");
        }
        else if(tipoDeInvalidacao == InvalidacoesCandidato.TELEFONE_ABAIXO_TAMANHO_MINIMO){
            dadosPessoais.put("phone", Geradores.gerarNumeroDeTelefone().substring(1,5));
        }
        else if(tipoDeInvalidacao == InvalidacoesCandidato.TELEFONE_EM_BRANCO){
            dadosPessoais.put("phone", "");
        }
        else if(tipoDeInvalidacao == InvalidacoesCandidato.TELEFONE_NULO){
            dadosPessoais.put("phone", null);
        }
        else{
            dadosPessoais.put("phone", Geradores.gerarNumeroDeTelefone());
        }
    }

    private static void popularCPF(HashMap<String, String> dadosPessoais, InvalidacoesCandidato tipoDeInvalidacao) {
        if(tipoDeInvalidacao == InvalidacoesCandidato.CPF_ACIMA_TAMANHO_MAXIMO){
            dadosPessoais.put("cpf", Geradores.cpf(false)+"0");
        }
        else if(tipoDeInvalidacao == InvalidacoesCandidato.CPF_ABAIXO_TAMANHO_MINIMO){
            dadosPessoais.put("cpf", Geradores.cpf(false).substring(1,11));
        }
        else if(tipoDeInvalidacao == InvalidacoesCandidato.CPF_EM_BRANCO){
            dadosPessoais.put("cpf", "");
        }
        else if(tipoDeInvalidacao == InvalidacoesCandidato.CPF_NULO){
            dadosPessoais.put("cpf", null);
        }
        else{
            dadosPessoais.put("cpf", Geradores.cpf(false));
        }

    }

    static JSONObject criarEndereco(Faker faker) {
        JSONObject enderecoJSON = new JSONObject();
        enderecoJSON.put("numero", Integer.valueOf(faker.address().buildingNumber()));
        enderecoJSON.put("logradouro", faker.address().streetAddress());
        enderecoJSON.put("bairro", faker.address().cityPrefix());
        enderecoJSON.put("cidade", faker.address().city());
        enderecoJSON.put("cep", Geradores.gerarCEP());
        enderecoJSON.put("estado", Geradores.gerarUF());
        return enderecoJSON;
    }

    static JSONObject criarEscolaridade(Faker faker) {
        JSONObject escolaridade = new JSONObject();
        escolaridade.put("instituicao", faker.company().name());
        escolaridade.put("descricao", faker.pokemon().name());
        escolaridade.put("nivel", faker.job().keySkills());
        escolaridade.put("dataInicio", Geradores.randomBirthday().toString());
        escolaridade.put("dataFim", Geradores.randomBirthday().toString());
        return escolaridade;
    }

    static JSONObject criarExperiencia(Faker faker) {
        JSONObject experiencia = new JSONObject();
        experiencia.put("instituicao", faker.company().name());
        experiencia.put("descricao", faker.pokemon().name());
        experiencia.put("cargo", faker.job().keySkills());
        experiencia.put("dataInicio", Geradores.randomBirthday().toString());
        experiencia.put("dataFim", Geradores.randomBirthday().toString());
        return experiencia;
    }
}
