package Login.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.javafaker.DateAndTime;
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

}