package nttdata.grupo.com.transactionservice.dao.impl;

import nttdata.grupo.com.transactionservice.dao.BankAccountDAO;
import nttdata.grupo.com.transactionservice.models.BankAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class BankAccountDAOImpl implements BankAccountDAO {
    private static final Logger logger = LoggerFactory.getLogger(BankAccountDAOImpl.class);
    private final WebClient webClient;
    private static final String BANK_GET_ACCOUNT_CLIENT = "/api/accounts/bank-account/{accountNumber}/client/{idClient}";
    private static final String BANK_GET_ACCOUNT = "/api/accounts/bank-account/{accountNumber}";
    private static final String BANK_UPDATE_ACCOUNT = "/api/accounts/update/bankAccount";
    private static final String BANK_GET_ACCOUNT_BY_DEBIT = "/api/accounts/debit/bank-account/{accountNumber}";

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
    public Mono<BankAccount> findByNumeroAndIdCliente(String accountNumber, String idClient) {
        return this.webClient.get()
            .uri(BANK_GET_ACCOUNT_CLIENT, accountNumber, idClient)
            .retrieve().bodyToMono(BankAccount.class);
    }

    @Override
    public Mono<BankAccount> updateBankAccount(BankAccount bankAccount) {
        logger.info("INI - updateBankAccount - DAO");
        return webClient.post()
            .uri(BANK_UPDATE_ACCOUNT)
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .bodyValue(bankAccount)
            .retrieve()
            .bodyToMono(BankAccount.class);
    }

    @Override
    public Mono<BankAccount> findBankAccountByDebitCard(String numDebitCard) {
        return this.webClient.get()
            .uri(BANK_GET_ACCOUNT_BY_DEBIT, numDebitCard)
            .retrieve().bodyToMono(BankAccount.class);
    }
}
