package nttdata.grupo.com.walletmobilservice.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Setter
@Document(collection = "mobilWallets")
public class MobilWallet {
  @Id
  private String id;
  @Field
  @Indexed(unique = true)
  private String mobileNumber;
  @Field
  @Indexed(unique = true)
  private String mobileIMEI;
  @Field
  @Indexed(unique = true)
  private String email;
  private BigDecimal saldo;
  private String numberDebitCard;
  private boolean withDebitCard;

  private LocalDateTime created;
  private LocalDateTime updated;

  public MobilWallet(String mobileNumber, String mobileIMEI, String email, String numberDebitCard) {
    this.mobileNumber = mobileNumber;
    this.mobileIMEI = mobileIMEI;
    this.email = email;
    this.saldo = BigDecimal.ZERO;
    this.created = LocalDateTime.now();
    this.numberDebitCard = numberDebitCard;
    this.withDebitCard = !numberDebitCard.trim().isEmpty();
  }
}
