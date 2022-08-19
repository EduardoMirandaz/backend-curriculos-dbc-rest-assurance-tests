package Utils;

import Login.dto.TokenDTO;
import Login.service.LoginService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

public class Util {

    public static String dataNascimentoInvalida = "dataNascimento: must not be null";
    public static String nomeInvalido = "nome: must not be blank";
    public static String idPessoaNaoEncontrado = "ID da pessoa nao encontrada";

    static LoginService loginService = new LoginService();

    public static String gerarLoginRandomico(){
        UUID uuid = UUID.randomUUID();
        String myRandom = uuid.toString();
        return myRandom.substring(0,25);
    }

    public static String login(String path){
        TokenDTO bearer = recuperarBearerPeloJSON(path);
        return bearer.getToken().replace("Bearer ", "");
    }

//    public static String loginAdmin(){
//        String bearer = recuperarBearerPeloJSON(adminLoginPostPath);
//        return bearer.replace("Bearer ", "");
//    }

//    private Map<String, String> mapPessoaPostDTO(PessoaValidPostDTO pessoaValidPostDTO) {
//        Map<String, String> pessoaMapeada = new HashMap<>();
//        pessoaMapeada.put("nome", pessoaValidPostDTO.getNome());
//        pessoaMapeada.put("dataNascimento", pessoaValidPostDTO.getDataNascimento().toString());
//        pessoaMapeada.put("cpf", pessoaValidPostDTO.getCpf());
//        pessoaMapeada.put("email", pessoaValidPostDTO.getEmail());
//        pessoaMapeada.put("idPessoa", pessoaValidPostDTO.getIdPessoa().toString());
//
//        return  pessoaMapeada;
//    }

    public static String converterJsonParaArrayDeBytes(String pathJson){
        // caminho da massa de dados
        try {
            return new String(Files.readAllBytes(Paths.get(pathJson)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static TokenDTO recuperarBearerPeloJSON(String jsonPath){
        // Le a massa de dados
        String jsonBody = null;
        jsonBody = converterJsonParaArrayDeBytes(jsonPath);

        // Post- Chamada para o servico
        return loginService.recuperarBearer(jsonBody);
    }
}
