package Login.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties
@Data
public class EnderecoDTO {
    private Integer numero;
    private String logradouro;
    private String bairro;
    private String cidade;
    private String cep;
    private String estado;
    private Integer idEndereco;


    @Override
    public String toString(){
        return "{\"cidade\":\"" + cidade + "\",\"estado\":\"" + estado + "\",\"numero\":" + numero +
                ",\"logradouro\":\""+logradouro+"\",\"bairro\":\""+bairro+"\",\"cep\":\""+cep+"\"}";
    }
}