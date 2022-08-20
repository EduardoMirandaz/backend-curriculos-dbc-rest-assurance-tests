package Login.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;

@JsonIgnoreProperties
@Data
public class Vaga{
    public int idVaga;
    public int id;
    @JsonProperty("Titulo")
    public String titulo;
    @JsonProperty("Status")
    public String status;
    @JsonProperty("DataAbertura")
    public Date dataAbertura;
    @JsonProperty("Categoria")
    public Categoria categoria;
    @JsonProperty("Cidade")
    public String cidade;
    @JsonProperty("Estado")
    public String estado;
    @JsonProperty("Tags")
    public ArrayList<Tag> tags;
}
