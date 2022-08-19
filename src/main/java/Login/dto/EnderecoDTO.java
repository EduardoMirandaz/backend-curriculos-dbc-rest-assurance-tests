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
}