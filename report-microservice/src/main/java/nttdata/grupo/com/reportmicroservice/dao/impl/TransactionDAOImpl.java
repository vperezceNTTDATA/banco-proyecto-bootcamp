package nttdata.grupo.com.reportmicroservice.dao.impl;

import nttdata.grupo.com.reportmicroservice.dao.TransactionDAO;
import nttdata.grupo.com.reportmicroservice.models.BankAccount;
import nttdata.grupo.com.reportmicroservice.models.Movimiento;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;


public class TransactionDAOImpl implements TransactionDAO {
  private static final Logger logger = LoggerFactory.getLogger(TransactionDAOImpl.class);
  private final WebClient webClientTransaction;
  private static final String GET_TRANSACTION_ACCOUNT = "/api/transactions/by-account/{idProduct}";
  private static final String GET_10_TRANSACTION = "/api/transactions/10/product/{idProduct}";

  public TransactionDAOImpl(WebClient.Builder webClientBuilder) {
    this.webClientTransaction = webClientBuilder.baseUrl("http://localhost:8004").build();
  }

  @Override
  public Flux<Movimiento> findByCuentaBancariaNumero(String idProducto) {
    return this.webClientTransaction.get()
        .uri(GET_TRANSACTION_ACCOUNT, idProducto)
        .accept(MediaType.TEXT_EVENT_STREAM)
        .retrieve().bodyToFlux(Movimiento.class);
  }

  @Override
  public Flux<Movimiento> findTop10ByOrderByCreatedDesc(String idProducto) {
    return this.webClientTransaction.get()
        .uri(GET_10_TRANSACTION, idProducto)
        .accept(MediaType.TEXT_EVENT_STREAM)
        .retrieve().bodyToFlux(Movimiento.class);
  }
}
