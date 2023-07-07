package nttdata.grupo.com.transactionservice.services;

import io.reactivex.Observable;
import nttdata.grupo.com.transactionservice.models.Movimiento;
import org.springframework.data.mongodb.repository.Query;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TransactionRESTService {
  Flux<Movimiento> findByCuentaBancariaNumero(String idProducto);
  Flux<Movimiento> findTop10ByOrderByCreatedDesc(String idProducto);
  Mono<Movimiento> insertTransactionWallet(Movimiento movimiento);
}
