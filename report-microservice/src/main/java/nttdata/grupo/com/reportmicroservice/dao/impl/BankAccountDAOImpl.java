package nttdata.grupo.com.reportmicroservice.dao.impl;

import nttdata.grupo.com.reportmicroservice.dao.BankAccountDAO;
import nttdata.grupo.com.reportmicroservice.models.BankAccount;
import nttdata.grupo.com.reportmicroservice.models.dto.AccountProductDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class BankAccountDAOImpl implements BankAccountDAO {
    private static final Logger logger = LoggerFactory.getLogger(BankAccountDAOImpl.class);
    private final WebClient webClient;
    private static final String BANK_GET_ACCOUNT = "/api/accounts/bank-account/{accountNumber}";
    private static final String BANK_GET_ANY_PRODUCT = "/api/accounts/any-product/{accountNumber}";

    public BankAccountDAOImpl(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8002").build();
    }
    @Override
    public Mono<BankAccount> findByNumero(String accountNumber) {
        return this.webClient.get()
            .uri(BANK_GET_ACCOUNT, accountNumber)
            .retrieve().bodyToMono(BankAccount.class);
    }
    @Override
    public Mono<AccountProductDTO> findAnyProductByProdNumber(String cuentaNum) {
        return this.webClient.get()
            .uri(BANK_GET_ANY_PRODUCT, cuentaNum)
            .retrieve().bodyToMono(AccountProductDTO.class);
    }
}
