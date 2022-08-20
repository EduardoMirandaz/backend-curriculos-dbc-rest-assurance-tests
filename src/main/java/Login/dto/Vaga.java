package Login.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;

@JsonIgnoreProperties
@Data
public class Vaga {
    private Integer id;
    private String titulo;
    private String status;
    private Date dataAbertura;
    private Categoria categoria;
    private String cidade;
    private String estado;
    private ArrayList<Tag> tags;
}
