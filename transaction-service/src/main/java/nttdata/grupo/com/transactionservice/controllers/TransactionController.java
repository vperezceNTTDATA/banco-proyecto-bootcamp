package nttdata.grupo.com.transactionservice.controllers;

import io.reactivex.Observable;
import io.reactivex.Single;
import nttdata.grupo.com.transactionservice.models.dto.ResponseDTO;
import nttdata.grupo.com.transactionservice.services.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping(TransactionController.TRANSACTION)
public class TransactionController {
    private static final Logger logger = LoggerFactory.getLogger(TransactionController.class);

    public static final String POST_MAKE_DEPOSITO = "/{numCliente}/account/{numProd}/deposit";
    public static final String POST_MAKE_RETIRO = "/{numCliente}/account/{numProd}/retiro";
    public static final String POST_PAY_CREDIT = "/{numCliente}/credit/{numProd}/payment";
    public static final String POST_PAY_CREDIT_CARD = "/{numCliente}/creditCard/{numProd}/consume";
    public static final String POST_TRANSFER = "/{numBankAccountSend}/send/{numBankAccountRec}/receive";
    public static final String TRANSACTION = "/api/transactions";
    @Autowired
    private TransactionService transactionService;

    @PostMapping(TransactionController.POST_MAKE_DEPOSITO)
    public Single<ResponseEntity<String>> doDeposit(@PathVariable String numCliente, @PathVariable String numProd, @RequestParam String monto) {
        logger.info("INI - doDeposit");
        return transactionService.makeDeposit(numCliente, numProd, new BigDecimal(monto))
            .map(response -> ResponseEntity.status(HttpStatus.CREATED).body("Depósito realizado exitosamente."));
    }
    @PostMapping(TransactionController.POST_MAKE_RETIRO)
    public Single<ResponseEntity<String>> doRetiro(@PathVariable String numCliente, @PathVariable String numProd, @RequestParam String monto) {
        logger.info("INI - doRetiro");
        return transactionService.makeRetiro(numCliente, numProd, new BigDecimal(monto))
            .map(response -> ResponseEntity.status(HttpStatus.CREATED).body("Retiro realizado exitosamente."));
    }
    @PostMapping(TransactionController.POST_PAY_CREDIT)
    public Single<ResponseEntity<String>> doCreditPaid(@PathVariable String numCliente, @PathVariable String numProd, @RequestParam String monto) {
        logger.info("INI - doCreditPaid");
        return transactionService.makeCreditPaid(numCliente, numProd, new BigDecimal(monto))
            .filter(ResponseDTO::isValid)
            .map(response -> ResponseEntity.status(HttpStatus.CREATED).body("Pago de crédito realizado exitosamente."))
            .switchIfEmpty(Single.just(ResponseEntity.status(HttpStatus.ACCEPTED).body("Credit ya está pagado")));
    }
    @PostMapping(TransactionController.POST_PAY_CREDIT_CARD)
    public Single<ResponseEntity<String>> doCreditCardConsume(@PathVariable String numCliente, @PathVariable String numProd, @RequestParam String monto) {
        logger.info("INI - doCreditCardConsume");
        return transactionService.makeCreditCardConsume(numCliente, numProd, new BigDecimal(monto))
            .map(response -> ResponseEntity.status(HttpStatus.CREATED).body("Consumo de la tarjeta de crédito realizado exitosamente."));
    }
    @PostMapping(TransactionController.POST_TRANSFER)
    public Single<ResponseEntity<String>> doTransfer(@PathVariable String numBankAccountSend, @PathVariable String numBankAccountRec, @RequestParam String monto) {
        logger.info("INI - doTransfer");
        return transactionService.makeTransfer(numBankAccountSend, numBankAccountRec, new BigDecimal(monto))
            .map(response -> ResponseEntity.status(HttpStatus.CREATED).body("Transferencia realizado exitosamente."));
    }

}
