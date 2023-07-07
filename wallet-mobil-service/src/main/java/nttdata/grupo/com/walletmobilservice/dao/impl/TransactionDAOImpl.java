package nttdata.grupo.com.walletmobilservice.dao.impl;

import nttdata.grupo.com.walletmobilservice.dao.TransactionDAO;
import nttdata.grupo.com.walletmobilservice.models.Movimiento;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


@Service
public class TransactionDAOImpl implements TransactionDAO {
  private static final Logger logger = LoggerFactory.getLogger(TransactionDAOImpl.class);
  private final WebClient webClient;
  private static final String CREATE_TRANSACTION_WALLET = "/api/transactions/wallet";

  public TransactionDAOImpl(WebClient.Builder webClientBuilder) {
    this.webClient = webClientBuilder.baseUrl("http://localhost:8004").build();
  }

  @Override
  public Mono<Movimiento> insertTransactionWallet(Movimiento movimiento) {
    logger.info("INI - insertTransactionWallet - DAO");
    return webClient.post()
        .uri(CREATE_TRANSACTION_WALLET)
        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .bodyValue(movimiento)
        .retrieve()
        .bodyToMono(Movimiento.class);
  }
}
