package nttdata.grupo.com.transactionservice.dao;


import nttdata.grupo.com.transactionservice.models.BankAccount;
import reactor.core.publisher.Mono;

public interface BankAccountDAO {
    Mono<BankAccount> findByNumero(String accountNumber);
    Mono<BankAccount> findByNumeroAndIdCliente(String accountNumber, String idClient);
    Mono<BankAccount> updateBankAccount(BankAccount bankAccount);
    Mono<BankAccount> findBankAccountByDebitCard(String numDebitCard);
}
