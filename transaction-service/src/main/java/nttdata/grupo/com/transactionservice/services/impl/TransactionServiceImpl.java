package nttdata.grupo.com.transactionservice.services.impl;

import io.reactivex.Single;
import nttdata.grupo.com.transactionservice.dao.*;
import nttdata.grupo.com.transactionservice.models.BankAccount;
import nttdata.grupo.com.transactionservice.models.Movimiento;
import nttdata.grupo.com.transactionservice.models.dto.ResponseDTO;
import nttdata.grupo.com.transactionservice.models.exepcion.ConflictException;
import nttdata.grupo.com.transactionservice.repositories.MovimientosRepository;
import nttdata.grupo.com.transactionservice.services.TransactionService;
import nttdata.grupo.com.transactionservice.util.Constantes;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@Service
public class TransactionServiceImpl implements TransactionService {
    private static final Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);
    @Autowired
    private ClientDAO clientDAO;
    @Autowired
    private MovimientosRepository movimientosRepository;
    @Autowired
    private BankAccountDAO bankAccountDAO;
    @Autowired
    private CreditDAO creditDAO;
    @Autowired
    private CreditCardDAO creditCardDAO;
    @Autowired
    private DebitCardDAO debitCardDAO;

    @Override
    public Single<ResponseDTO> makeDeposit(String docNumClient, String numCuenta, BigDecimal monto) {
      logger.info("INI - makeDeposit - ServiceIMPL");
        return Single.fromPublisher(clientDAO.findByNumDocumento(docNumClient)
            .flatMap(client -> bankAccountDAO.findByNumeroAndIdCliente(numCuenta, client.getId())
                .switchIfEmpty(this.findBankAccountByDebitCard(numCuenta))
                .flatMap(cuentaBancaria -> {
                    BigDecimal newMonto = monto;
                    cuentaBancaria.setMovimientosActuales(cuentaBancaria.getMovimientosActuales() + 1);
                    if(cuentaBancaria.getMovimientosActuales() >= cuentaBancaria.getMovimientosMensuales())newMonto = monto.subtract(Constantes.COMMISSION_AMOUNT);
                    cuentaBancaria.setSaldo(cuentaBancaria.getSaldo().add(newMonto));
                    bankAccountDAO.updateBankAccount(cuentaBancaria).subscribe();
                    movimientosRepository.save(new Movimiento(cuentaBancaria.getId(), newMonto, Constantes.DEPOSIT, monto, numCuenta)).subscribe();
                    return Mono.just(new ResponseDTO(true, ""));
                }).switchIfEmpty(Mono.error(new ConflictException("Cuenta bancaria: " + numCuenta + " no existe."))))
            .switchIfEmpty(Mono.error(new ConflictException("Cliente: " + docNumClient + " no existe esta cuenta"))));
    }
    @Override
    public Single<ResponseDTO> makeRetiro(String docNumClient, String numCuenta, BigDecimal monto)  {
        logger.info("INI - makeRetiro - ServiceIMPL");
        return Single.fromPublisher(clientDAO.findByNumDocumento(docNumClient)
            .flatMap(client ->bankAccountDAO.findByNumeroAndIdCliente(numCuenta, client.getId())
                .switchIfEmpty(this.findBankAccountByDebitCard(numCuenta))
                .flatMap(cuentaBancaria -> {
                    BigDecimal newMonto = monto;
                    cuentaBancaria.setMovimientosActuales(cuentaBancaria.getMovimientosActuales() + 1);
                    if(cuentaBancaria.getMovimientosActuales() >= cuentaBancaria.getMovimientosMensuales())newMonto = monto.add(Constantes.COMMISSION_AMOUNT);
                    if(cuentaBancaria.getSaldo().compareTo(newMonto) < 0)return Mono.error(new ConflictException("No tienes suficiente saldo."));
                    cuentaBancaria.setSaldo(cuentaBancaria.getSaldo().subtract(newMonto));
                    bankAccountDAO.updateBankAccount(cuentaBancaria).subscribe();
                    movimientosRepository.save(new Movimiento(cuentaBancaria.getId(), newMonto, Constantes.RETIRO, monto, numCuenta)).subscribe();
                    return Mono.just(new ResponseDTO(true, ""));
                }).switchIfEmpty(Mono.error(new ConflictException("Cuenta bancaria: " + numCuenta + " no existe."))))
            .switchIfEmpty(Mono.error(new ConflictException("Cliente: " + docNumClient + " no existe esta cuenta"))));
    }
    @Override
    public Single<ResponseDTO> makeCreditPaid(String docNumClient, String numCuenta, BigDecimal monto) {
        logger.info("INI - makeCreditPaid - ServiceIMPL");
          return Single.fromPublisher(clientDAO.findByNumDocumento(docNumClient)
            .flatMap(client -> creditDAO.findByNumeroAndIdCliente(numCuenta, client.getId())
                .flatMap(credit -> {
                    if(credit.getMonto().compareTo(credit.getMontoPagado()) == 0)return Mono.just(new ResponseDTO(false, ""));
                    if((credit.getMonto().subtract(credit.getMontoPagado())).compareTo(monto) < 0)credit.setMontoPagado(credit.getMonto());
                    else credit.setMontoPagado(credit.getMontoPagado().add(monto));
                    creditDAO.updateCredit(credit).subscribe();
                    movimientosRepository.save(new Movimiento(credit.getId(), monto, Constantes.PAGO_CREDIT)).subscribe();
                    return Mono.just(new ResponseDTO(true, ""));
                }).switchIfEmpty(Mono.error(new ConflictException("Credit: " + numCuenta + " no existe."))))
            .switchIfEmpty(Mono.error(new ConflictException("Cliente: " + docNumClient + " no existe esta cuenta"))));
    }
    @Override
    public Single<ResponseDTO> makeCreditCardConsume(String docNumClient, String numCuenta, BigDecimal monto) {
        logger.info("INI - makeCreditCardConsume - ServiceIMPL");
        return Single.fromPublisher(clientDAO.findByNumDocumento(docNumClient)
            .flatMap(client -> creditCardDAO.findByNumeroAndIdCliente(numCuenta, client.getId())
                .flatMap(creditCard -> {
                    if((creditCard.getAvailableBalance().subtract(creditCard.getUtilizedBalance())).compareTo(monto) < 0)return Mono.error(new ConflictException("El monto del consumo excede al limite de su tarjeta."));
                    creditCard.setUtilizedBalance(creditCard.getUtilizedBalance().add(monto));
                    creditCardDAO.updateCreditCard(creditCard).subscribe();
                    movimientosRepository.save(new Movimiento(creditCard.getId(), monto, Constantes.CONSUMO_CREDIT_CARD)).subscribe();
                    return Mono.just(new ResponseDTO(true, ""));
                }).switchIfEmpty(Mono.error(new ConflictException("Tarjeta de crédito: " + numCuenta + " no existe."))))
            .switchIfEmpty(Mono.error(new ConflictException("Cliente: " + docNumClient + " no existe esta cuenta"))));
    }
    @Override
    public Single<ResponseDTO> makeTransfer(String numSend, String numRec, BigDecimal monto) {
        logger.info("INI - makeTransfer - ServiceIMPL");

        return Single.fromPublisher(bankAccountDAO.findByNumero(numSend)
            .flatMap(bankAccountSend -> bankAccountDAO.findByNumero(numRec)
                .flatMap(bankAccountRec -> {
                    if(bankAccountSend.getSaldo().compareTo(monto) < 0)return Mono.error(new ConflictException("No tienes suficiente saldo."));
                    bankAccountSend.setSaldo(bankAccountSend.getSaldo().subtract(monto));
                    bankAccountDAO.updateBankAccount(bankAccountSend).subscribe();
                    bankAccountRec.setSaldo(bankAccountRec.getSaldo().add(monto));
                    bankAccountDAO.updateBankAccount(bankAccountRec).subscribe();
                    if(bankAccountRec.getIdCliente().equals(bankAccountSend.getIdCliente()))movimientosRepository.save(new Movimiento(bankAccountSend.getId().toString(), monto, Constantes.TRANS_PROPIO, bankAccountRec.getId().toString())).subscribe();
                    else movimientosRepository.save(new Movimiento(bankAccountSend.getId(), monto, Constantes.TRANS_TERCERO, bankAccountRec.getId().toString())).subscribe();
                    return Mono.just(new ResponseDTO(true, ""));
                })
                .switchIfEmpty(Mono.error(new ConflictException("Número de cuenta: " + numRec + " no existe."))))
            .switchIfEmpty(Mono.error(new ConflictException("Número de cuenta: " + numSend + " no existe."))));

    }

    public Mono<BankAccount> findBankAccountByDebitCard(String numDebitCard){
        return debitCardDAO.findByCardNumber(numDebitCard)
            .flatMap(numberProduct -> Flux.fromIterable(numberProduct.getNumberBankAccounts())
                .flatMap(numberAccount -> bankAccountDAO.findByNumero(numberAccount))
                .take(1).single())
            .switchIfEmpty(Mono.error(new ConflictException("Número de tarjeta: " + numDebitCard + " no existe esta cuenta")));
    }

}
