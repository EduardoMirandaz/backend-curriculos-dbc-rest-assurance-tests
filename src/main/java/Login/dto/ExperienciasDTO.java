package Login.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.time.LocalDate;

@JsonIgnoreProperties
@Data
public class ExperienciasDTO {
    private String instituicao;
    private String descricao;
    private String cargo;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private Integer idExperiencia;

    @Override
    public String toString(){
        return "{\"dataFim\":\"" + dataFim + "\",\"instituicao\":\"" + instituicao + "\",\"dataInicio\":\"" + dataInicio +
                "\",\"cargo\":\""+cargo+"\",\"descricao\":\""+descricao+"\"}";
    }

}