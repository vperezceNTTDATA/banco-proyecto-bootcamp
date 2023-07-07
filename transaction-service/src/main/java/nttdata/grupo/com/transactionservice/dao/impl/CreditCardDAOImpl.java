package nttdata.grupo.com.transactionservice.dao.impl;

import nttdata.grupo.com.transactionservice.dao.CreditCardDAO;
import nttdata.grupo.com.transactionservice.models.BankAccount;
import nttdata.grupo.com.transactionservice.models.Credit;
import nttdata.grupo.com.transactionservice.models.CreditCard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class CreditCardDAOImpl implements CreditCardDAO {

  private static final Logger logger = LoggerFactory.getLogger(BankAccountDAOImpl.class);
  private final WebClient webClient;
  private static final String BANK_GET_CREDIT_CARD_CLIENT = "/api/accounts/credit-card/{accountNumber}/client/{idClient}";
  private static final String BANK_GET_ACCOUNT = "/api/accounts/bank-account/{accountNumber}";
  private static final String BANK_UPDATE_CREDIT_CARD = "/api/accounts/update/credit-card";

  public CreditCardDAOImpl(WebClient.Builder webClientBuilder) {
    this.webClient = webClientBuilder.baseUrl("http://localhost:8002").build();
  }

  @Override
  public Mono<CreditCard> findByNumeroAndIdCliente(String accountNumber, String idClient) {
    logger.info("INI - findByNumeroAndIdCliente - DAO");
    return this.webClient.get()
        .uri(BANK_GET_CREDIT_CARD_CLIENT, accountNumber, idClient)
        .retrieve().bodyToMono(CreditCard.class);
  }

  @Override
  public Mono<CreditCard> updateCreditCard(CreditCard creditCard) {
    logger.info("INI - updateCreditCard - DAO");
    return webClient.post()
        .uri(BANK_UPDATE_CREDIT_CARD)
        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .bodyValue(creditCard)
        .retrieve()
        .bodyToMono(CreditCard.class);
  }
}
