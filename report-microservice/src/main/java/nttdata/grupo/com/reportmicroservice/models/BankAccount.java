package nttdata.grupo.com.reportmicroservice.models;

import lombok.*;
import nttdata.grupo.com.reportmicroservice.enums.TipoCuenta;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


@Getter
@Builder
@NoArgsConstructor
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

    public BankAccount(String numero, String idCliente, String tipoCuenta, int movimientosMensuales, BigDecimal saldo) {
        this.numero = numero;
        this.idCliente = idCliente;

        if(tipoCuenta.equals(TipoCuenta.AHORRO.name())){
            this.tipoCuenta = TipoCuenta.AHORRO;
        }else if(tipoCuenta.equals(TipoCuenta.CUENTA_CORRIENTE.name())){
            this.tipoCuenta = TipoCuenta.CUENTA_CORRIENTE;
        }else{
            this.tipoCuenta = TipoCuenta.PLAZO_FIJO;
        }

        this.movimientosMensuales = movimientosMensuales;
        this.saldo = saldo;
        this.created = LocalDateTime.now();
    }

    public BankAccount(String numero, String idCliente, String tipoCuenta, BigDecimal saldo) {
        this.numero = numero;
        this.idCliente = idCliente;

        if(tipoCuenta.equals(TipoCuenta.AHORRO.name())){
            this.tipoCuenta = TipoCuenta.AHORRO;
        }else if(tipoCuenta.equals(TipoCuenta.CUENTA_CORRIENTE.name())){
            this.tipoCuenta = TipoCuenta.CUENTA_CORRIENTE;
        }else{
            this.tipoCuenta = TipoCuenta.PLAZO_FIJO;
        }

        this.movimientosMensuales = 20;
        this.movimientosActuales = 0;
        this.saldo = saldo;
        this.created = LocalDateTime.now();
    }
}
