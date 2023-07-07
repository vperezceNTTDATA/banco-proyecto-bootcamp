package nttdata.grupo.com.walletmobilservice.models.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
@Setter
public class AccountRequest {
  @NotBlank(message = "El campo 'mobileNumber' no puede estar en blanco")
  private String mobileNumber;
  @NotBlank(message = "El campo 'mobileIMEI' no puede estar en blanco")
  private String mobileIMEI;
  @NotBlank(message = "El campo 'email' no puede estar en blanco")
  private String email;

  private String numberDebitCard = "";
}
