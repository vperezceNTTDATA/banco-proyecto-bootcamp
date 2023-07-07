package nttdata.grupo.com.transactionservice.models;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DebitCard {
  private String id;
  private String cardNumber;
  private int securityNumber;
  private List<String> numberBankAccounts;
  private LocalDateTime created;
  private LocalDateTime updated;
}
