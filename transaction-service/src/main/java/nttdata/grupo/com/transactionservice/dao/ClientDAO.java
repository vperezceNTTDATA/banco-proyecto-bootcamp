package nttdata.grupo.com.transactionservice.dao;

import nttdata.grupo.com.transactionservice.models.Cliente;
import reactor.core.publisher.Mono;

public interface ClientDAO {
  Mono<Cliente> findByNumDocumento(String docNum);
}
