package nttdata.grupo.com.reportmicroservice.dao;

import nttdata.grupo.com.reportmicroservice.models.BankAccount;
import nttdata.grupo.com.reportmicroservice.models.dto.AccountProductDTO;
import reactor.core.publisher.Mono;

public interface BankAccountDAO {
    Mono<BankAccount> findByNumero(String accountNumber);
    Mono<AccountProductDTO> findAnyProductByProdNumber(String cuentaNum);
}
