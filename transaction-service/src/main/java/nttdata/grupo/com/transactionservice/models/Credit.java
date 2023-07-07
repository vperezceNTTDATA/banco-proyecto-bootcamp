package nttdata.grupo.com.transactionservice.models;

import lombok.*;
import nttdata.grupo.com.transactionservice.enums.CreditType;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Credit {
  private String id;
  private String numero;
  private String idCliente;
  private CreditType tipoCredito;
  private BigDecimal monto;
  private BigDecimal montoPagado;
  private LocalDateTime created;
  private LocalDateTime updated;
}
