package nttdata.grupo.com.walletmobilservice.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Movimiento {
  private String id;
  private String idProduct;
  private String idProductReceive;
  private String numberCard;
  private BigDecimal monto;
  private String action;
  private LocalDateTime fecha;
  private Boolean withCommission;
  private LocalDateTime created;
  private LocalDateTime updated;

  public Movimiento(String idProduct, String action, BigDecimal monto, String numberCard) {
    this.idProduct = idProduct;
    this.monto = monto;
    this.withCommission = false;
    this.action = action;
    this.numberCard = numberCard;

    this.fecha = LocalDateTime.now();
    this.created = LocalDateTime.now();
  }
}
