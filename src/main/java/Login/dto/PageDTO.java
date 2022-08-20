package Login.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.ArrayList;

@Data
@JsonIgnoreProperties
public class PageDTO<T> {
      public ArrayList<T> vagaGeralList;
      public int total;
      public int paginas;
      public int pagina;
      public int quantidade;
      public String id;

}
