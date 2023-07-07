package nttdata.grupo.com.walletmobilservice.dao;

import nttdata.grupo.com.walletmobilservice.models.BankAccount;
import reactor.core.publisher.Mono;

public interface BankAccountDAO {
  Mono<BankAccount> findBankAccountByDebitCard(String numDebitCard);
  Mono<BankAccount> updateBankAccount(BankAccount bankAccount);
}
