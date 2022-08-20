package Login.dto;

import Login.Enums.Senioridade;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import lombok.Data;

import java.time.LocalDate;

@JsonIgnoreProperties
@Data
public class CandidatoCompletoDTO {
    private Integer idCandidato;
    private String nome;
    private String cpf;
    private String curriculoUrl;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate dataNascimento;

    private String telefone;
    private Senioridade senioridade;
    private String cargo;
    private EnderecoDTO endereco;
    private EscolaridadesDTO[] escolaridade;
    private ExperienciasDTO[] experiencia;
}
