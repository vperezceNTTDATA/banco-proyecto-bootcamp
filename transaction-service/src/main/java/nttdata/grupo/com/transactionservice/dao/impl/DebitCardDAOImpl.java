package nttdata.grupo.com.transactionservice.dao.impl;

import nttdata.grupo.com.transactionservice.dao.DebitCardDAO;
import nttdata.grupo.com.transactionservice.models.DebitCard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


@Service
public class DebitCardDAOImpl implements DebitCardDAO {
  private static final Logger logger = LoggerFactory.getLogger(BankAccountDAOImpl.class);
  private final WebClient webClient;
  private static final String BANK_GET_DEBIT = "/api/accounts/debit/{accountNumber}";
  private static final String BANK_GET_ACCOUNT = "/api/accounts/bank-account/{accountNumber}";

  public DebitCardDAOImpl(WebClient.Builder webClientBuilder) {
    this.webClient = webClientBuilder.baseUrl("http://localhost:8002").build();
  }
  @Override
  public Mono<DebitCard> findByCardNumber(String cardNumber) {
    logger.info("INI - findByCardNumber - DAO");
    return this.webClient.get()
        .uri(BANK_GET_DEBIT, cardNumber)
        .retrieve().bodyToMono(DebitCard.class);
  }
}
