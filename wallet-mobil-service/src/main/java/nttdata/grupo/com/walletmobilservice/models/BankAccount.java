package nttdata.grupo.com.walletmobilservice.models;

import lombok.*;
import nttdata.grupo.com.walletmobilservice.enums.TipoCuenta;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


@Getter
@Builder
@Setter
@AllArgsConstructor
public class BankAccount {
    private String id;
    private String numero;
    private String idCliente;
    private TipoCuenta tipoCuenta;
    private int movimientosMensuales;
    private int movimientosActuales;
    private BigDecimal saldo;
    private List<String> titulares;
    private List<String> firmantesAutorizados;
    private LocalDateTime created;
    private LocalDateTime updated;
}
