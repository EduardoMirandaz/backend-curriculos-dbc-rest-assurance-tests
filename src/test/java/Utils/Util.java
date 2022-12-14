package Utils;

import Login.dto.TokenDTO;
import Login.service.LoginService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

public class Util {

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
