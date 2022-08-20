package Login.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.time.LocalDate;


@JsonIgnoreProperties
@Data
public class EscolaridadesDTO {
    private String instituicao;
    private String descricao;
    private String nivel;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private Integer idEscolaridade;

    @Override
    public String toString(){
        return "{\"dataFim\":\"" + dataFim + "\",\"instituicao\":\"" + instituicao + "\",\"dataInicio\":\"" + dataInicio +
                "\",\"nivel\":\""+nivel+"\",\"descricao\":\""+descricao+"\"}";
    }
}