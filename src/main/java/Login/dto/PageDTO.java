package Login.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties
public class PageDTO {
      private String total;
      private String paginas;
      private String pagina;
      private String quantidade;
      private Vaga[] vagaGeralList;
      private Integer id;

}
