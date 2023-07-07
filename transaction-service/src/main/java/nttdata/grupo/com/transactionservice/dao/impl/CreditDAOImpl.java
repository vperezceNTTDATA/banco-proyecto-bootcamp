package nttdata.grupo.com.transactionservice.dao.impl;

import nttdata.grupo.com.transactionservice.dao.CreditDAO;
import nttdata.grupo.com.transactionservice.models.Credit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class CreditDAOImpl implements CreditDAO {

  private static final Logger logger = LoggerFactory.getLogger(BankAccountDAOImpl.class);
  private final WebClient webClient;
  private static final String BANK_GET_CREDIT_CLIENT = "/api/accounts/credit/{accountNumber}/client/{idClient}";
  private static final String BANK_GET_ACCOUNT = "/api/accounts/bank-account/{accountNumber}";
  private static final String BANK_UPDATE_CREDIT_CARD = "/api/accounts/update/credit-card";

  public CreditDAOImpl(WebClient.Builder webClientBuilder) {
    this.webClient = webClientBuilder.baseUrl("http://localhost:8002").build();
  }

  @Override
  public Mono<Credit> findByNumeroAndIdCliente(String accountNumber, String idClient) {
    logger.info("INI - findByNumeroAndIdCliente - DAO");
    return this.webClient.get()
        .uri(BANK_GET_CREDIT_CLIENT, accountNumber, idClient)
        .retrieve().bodyToMono(Credit.class);
  }

  @Override
  public Mono<Credit> updateCredit(Credit credit) {
    logger.info("INI - updateCredit - DAO");
    return webClient.post()
        .uri(BANK_UPDATE_CREDIT_CARD)
        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .bodyValue(credit)
        .retrieve()
        .bodyToMono(Credit.class);
  }
}
