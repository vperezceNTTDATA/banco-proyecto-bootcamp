package nttdata.grupo.com.transactionservice.models;

import lombok.*;
import nttdata.grupo.com.transactionservice.enums.TipoCliente;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Getter
@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {
  private String id;
  private String nombre;
  private String numDocumento;
  private TipoCliente tipoCliente;
  private LocalDateTime created;
  private LocalDateTime updated;
}
