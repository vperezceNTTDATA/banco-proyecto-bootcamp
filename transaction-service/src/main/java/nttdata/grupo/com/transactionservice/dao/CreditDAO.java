package nttdata.grupo.com.transactionservice.dao;

import nttdata.grupo.com.transactionservice.models.Credit;
import org.springframework.data.mongodb.repository.Query;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CreditDAO {
  Mono<Credit> findByNumeroAndIdCliente(String numero, String idClient);
  Mono<Credit> updateCredit(Credit credit);
}
