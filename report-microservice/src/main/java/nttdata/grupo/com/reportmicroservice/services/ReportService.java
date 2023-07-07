package nttdata.grupo.com.reportmicroservice.services;

import io.reactivex.Observable;
import io.reactivex.Single;
import nttdata.grupo.com.reportmicroservice.models.Movimiento;
import nttdata.grupo.com.reportmicroservice.models.dto.ResumenSaldoDTO;

import java.io.IOException;

public interface ReportService {
    Observable<Movimiento> getCommissionsByProduct(String cuentaNum) ;
    Observable<ResumenSaldoDTO> getResumenDailyByProduct(String cuentaNum);
    Observable<Movimiento> findTenResumenByDebitAndCreditCard(String cuentaNum);
    Observable<Movimiento> findTransactionsByProduct(String cuentaNum);
}
