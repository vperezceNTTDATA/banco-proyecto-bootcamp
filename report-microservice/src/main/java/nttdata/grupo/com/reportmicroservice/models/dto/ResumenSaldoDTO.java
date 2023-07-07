package nttdata.grupo.com.reportmicroservice.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class ResumenSaldoDTO {
    private String productId;
    private String productNumber;
    private String description;
    private BigDecimal saldoPromedioDiario;
}