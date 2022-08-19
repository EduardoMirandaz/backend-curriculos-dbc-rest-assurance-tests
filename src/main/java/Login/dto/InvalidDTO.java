package Login.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties
@Data
public class InvalidDTO {
    private String timestamp;
    private String status;
    private String[] errors;
    private String error;
    private String message;
    private String path;
}
