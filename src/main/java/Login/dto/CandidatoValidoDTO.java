package Login.dto;


import Login.Enums.Senioridade;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;

import java.time.LocalDate;

@JsonIgnoreProperties
@Getter
public class CandidatoValidoDTO {
    private Integer idCandidato;
    private String nome;
    private String cargo;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate dataNascimento;

    private Senioridade senioridade;
    private String curriculoUrl;
    private Vaga[] vagas;

}
