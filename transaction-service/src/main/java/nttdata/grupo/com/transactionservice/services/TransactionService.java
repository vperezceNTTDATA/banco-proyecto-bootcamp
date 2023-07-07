package nttdata.grupo.com.transactionservice.services;

import io.reactivex.Single;
import nttdata.grupo.com.transactionservice.models.dto.ResponseDTO;

import java.math.BigDecimal;

public interface TransactionService {

    Single<ResponseDTO> makeDeposit(String docClient, String numCuenta, BigDecimal monto);
    Single<ResponseDTO> makeRetiro(String docClient, String numCuenta, BigDecimal monto);
    Single<ResponseDTO> makeCreditPaid(String docClient, String numCuenta, BigDecimal monto);
    Single<ResponseDTO> makeCreditCardConsume(String docClient, String numCuenta, BigDecimal monto);
    Single<ResponseDTO> makeTransfer(String numSend, String numRec, BigDecimal monto);

}
