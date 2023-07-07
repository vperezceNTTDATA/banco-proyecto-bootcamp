package nttdata.grupo.com.reportmicroservice.services.impl;

import io.reactivex.Observable;
import io.reactivex.Single;
import nttdata.grupo.com.reportmicroservice.dao.BankAccountDAO;
import nttdata.grupo.com.reportmicroservice.dao.TransactionDAO;
import nttdata.grupo.com.reportmicroservice.models.Movimiento;
import nttdata.grupo.com.reportmicroservice.models.dto.ResumenSaldoDTO;
import nttdata.grupo.com.reportmicroservice.models.exepcion.NotFoundException;
import nttdata.grupo.com.reportmicroservice.services.ReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ReportServiceImpl implements ReportService {
    private static final Logger logger = LoggerFactory.getLogger(ReportServiceImpl.class);
    @Autowired
    private TransactionDAO transactionDAO;
    @Autowired
    private BankAccountDAO bankAccountDAO;
    @Override
    public Observable<Movimiento> getCommissionsByProduct(String accountNumber){
        logger.info("INI - getCommissionsByProduct - ServiceIMPL");
        return Observable.fromPublisher(bankAccountDAO.findByNumero(accountNumber)
                .flatMapMany(bankAccount -> transactionDAO.findByCuentaBancariaNumero(bankAccount.getId())
                        .filter(Movimiento::getWithCommission)
                        .switchIfEmpty(Mono.error(new NotFoundException("No tiene movimientos con comisiones."))))
                .switchIfEmpty(Mono.error(new NotFoundException("Número de cuenta: " + accountNumber + " no existe."))));
    }
    @Override
    public Observable<ResumenSaldoDTO> getResumenDailyByProduct(String cuentaNum) {
        return null;
    }

    @Override
    public Observable<Movimiento> findTenResumenByDebitAndCreditCard(String cuentaNum) {
        return Observable.fromPublisher(transactionDAO.findTop10ByOrderByCreatedDesc(cuentaNum));
    }
    @Override
    public Observable<Movimiento> findTransactionsByProduct(String cuentaNum) {
        logger.info("INI - consultarMovimientosCuentaBancaria - ServiceIMPL");
        return Single.fromPublisher(bankAccountDAO.findAnyProductByProdNumber(cuentaNum))
            .flatMapObservable(product -> Observable.fromPublisher(transactionDAO.findByCuentaBancariaNumero(product.getIdProduct()))
                .switchIfEmpty(Observable.empty()));
    }
}
