package nttdata.grupo.com.walletmobilservice.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@Setter
public class ResponseDTO {
  boolean isValid;
  String description;
}
