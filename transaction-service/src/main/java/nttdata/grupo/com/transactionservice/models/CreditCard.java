package nttdata.grupo.com.transactionservice.models;

import lombok.*;
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
public class CreditCard {
  private String id;
  private String cardNumber;
  private String idCliente;
  private BigDecimal availableBalance;
  private BigDecimal utilizedBalance;
  private BigDecimal interests;
  private Boolean isExpired;
  private LocalDateTime paymentDate;
  private LocalDateTime billingCycleEndDate;
  private LocalDateTime created;
  private LocalDateTime updated;
}
