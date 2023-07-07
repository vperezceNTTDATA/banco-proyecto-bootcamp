package nttdata.grupo.com.transactionservice.dao.impl;

import nttdata.grupo.com.transactionservice.dao.ClientDAO;
import nttdata.grupo.com.transactionservice.models.Cliente;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ClientDAOImpl implements ClientDAO {

  private static final Logger logger = LoggerFactory.getLogger(BankAccountDAOImpl.class);
  private static final String BANK_GET_CLIENT = "/api/clients/client/{docNumber}";
  private final WebClient webClient;

  public ClientDAOImpl(WebClient.Builder webClientBuilder) {
    this.webClient = webClientBuilder.baseUrl("http://localhost:8002").build();
  }

  @Override
  public Mono<Cliente> findByNumDocumento(String docNum) {
    return this.webClient.get()
        .uri(BANK_GET_CLIENT, docNum)
        .retrieve().bodyToMono(Cliente.class);
  }
}
