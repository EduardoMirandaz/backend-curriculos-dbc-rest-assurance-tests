package Utils;

import Login.Enums.Senioridade;
import Login.Util.Geradores;
import Utils.Enums.InvalidacoesCandidato;
import com.github.javafaker.Faker;
import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.List;

public class CandidatoFaker {

    /***************************************************************************
     * CRIA OS DADOS PESSOAIS VALIDOS, POIS NAO HÁ PARAMETRO PARA INVALIDACAO
     */
    static HashMap<String, String> criarDadosPessoais(Faker faker){
        HashMap<String, String> dadosPessoais = new HashMap<>();
        dadosPessoais.put("name", faker.name().fullName());
        dadosPessoais.put("cpf", Geradores.cpf(false));
        dadosPessoais.put("dateTime", Geradores.randomBirthday().toString());
        dadosPessoais.put("phone", Geradores.gerarNumeroDeTelefone());
        return dadosPessoais;
    }
    /***************************************************************************
     * CRIA OS DADOS PESSOAIS INVALIDOS, DEPENDENDO DA INVALIADÇÃO
     */
    static HashMap<String, String> criarDadosPessoais(Faker faker, InvalidacoesCandidato tipoDeInvalidacao){
        HashMap<String, String> dadosPessoais = new HashMap<>();

        dadosPessoais.put("name", faker.name().fullName());

        popularCPF(dadosPessoais, tipoDeInvalidacao);

        popularDOB(dadosPessoais, tipoDeInvalidacao);

        popularTelefone(dadosPessoais, tipoDeInvalidacao);

        return dadosPessoais;
    }


    private static void popularDOB(HashMap<String, String> dadosPessoais, InvalidacoesCandidato tipoDeInvalidacao) {
        if(tipoDeInvalidacao == InvalidacoesCandidato.DOB_APOS_DIA_ATUAL){
            dadosPessoais.put("dateTime", Geradores.randomInvalidBirthday().toString());
        }
        else if(tipoDeInvalidacao == InvalidacoesCandidato.DOB_EM_BRANCO){
            dadosPessoais.put("dateTime", "");
        }
        else if(tipoDeInvalidacao == InvalidacoesCandidato.DOB_NULO){
            dadosPessoais.put("dateTime", null);
        }
        else{
            dadosPessoais.put("dateTime", Geradores.randomBirthday().toString());
        }
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

    /***************************************************************************
     * CRIA OS DADOS PESSOAIS VALIDOS, POIS NAO HÁ PARAMETRO PARA INVALIDACAO
     */
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
    /***************************************************************************
     * CRIA OS ENDERECOS PESSOAIS INVALIDOS, DEPENDENDO DA INVALIADÇÃO
     */
    static JSONObject criarEndereco(Faker faker, InvalidacoesCandidato tipoInvalidacao) {
        JSONObject enderecoJSON = new JSONObject();
        enderecoJSON.put("numero", Integer.valueOf(faker.address().buildingNumber()));
        enderecoJSON.put("logradouro", faker.address().streetAddress());
        enderecoJSON.put("bairro", faker.address().cityPrefix());
        enderecoJSON.put("cidade", faker.address().city());
        popularCEP(enderecoJSON, tipoInvalidacao);
        enderecoJSON.put("estado", Geradores.gerarUF());
        return enderecoJSON;
    }

    private static void popularCEP(HashMap<String, String> enderecoJSON, InvalidacoesCandidato tipoDeInvalidacao) {
        if(tipoDeInvalidacao == InvalidacoesCandidato.CEP_ACIMA_TAMANHO_MAXIMO){
            enderecoJSON.put("cep", Geradores.gerarCEP()+"0");
        }
        else if(tipoDeInvalidacao == InvalidacoesCandidato.CEP_ABAIXO_TAMANHO_MINIMO){
            enderecoJSON.put("cep", Geradores.gerarCEP().substring(1,8));
        }
        else if(tipoDeInvalidacao == InvalidacoesCandidato.CEP_EM_BRANCO){
            enderecoJSON.put("cep", "");
        }
        else if(tipoDeInvalidacao == InvalidacoesCandidato.CEP_NULO){
            enderecoJSON.put("cep", null);
        }
        else{
            enderecoJSON.put("cep", Geradores.gerarCEP());
        }

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

    static JSONObject preencherCandidatoCompleto(Faker faker, HashMap<String, String> dadosPessoais, JSONObject enderecoJSON, List<JSONObject> escolaridades, List<JSONObject> experiencias) {
        JSONObject candidatoCompleto = new JSONObject();
        candidatoCompleto.put("nome", dadosPessoais.get("name"));
        candidatoCompleto.put("cpf", dadosPessoais.get("cpf"));
        candidatoCompleto.put("dataNascimento", dadosPessoais.get("dateTime"));
        candidatoCompleto.put("telefone", dadosPessoais.get("phone"));
        candidatoCompleto.put("senioridade", Senioridade.ESPECIALISTA.toString());
        candidatoCompleto.put("cargo", faker.job().keySkills());
        candidatoCompleto.put("endereco", enderecoJSON);
        candidatoCompleto.put("escolaridades", escolaridades);
        candidatoCompleto.put("experiencias", experiencias);
        return candidatoCompleto;
    }

}
