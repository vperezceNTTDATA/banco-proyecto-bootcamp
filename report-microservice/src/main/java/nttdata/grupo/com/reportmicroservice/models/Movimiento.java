package nttdata.grupo.com.reportmicroservice.models;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Movimiento {
    private String id;
    private String idProduct;
    private String idProductReceive;
    private String numberCreditCard;
    private BigDecimal monto;
    private String action;
    private LocalDateTime fecha;
    private Boolean withCommission;
    private LocalDateTime created;
    private LocalDateTime updated;
}
