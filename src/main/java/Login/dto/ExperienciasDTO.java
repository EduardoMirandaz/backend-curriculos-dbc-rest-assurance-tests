package Login.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.javafaker.DateAndTime;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@JsonIgnoreProperties
@Data
public class ExperienciasDTO {
    private String instituicao;
    private String descricao;
    private String cargo;
    private LocalDate dataInicio;
    private LocalDate dataFim;

}