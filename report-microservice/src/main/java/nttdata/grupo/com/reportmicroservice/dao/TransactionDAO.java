package nttdata.grupo.com.reportmicroservice.dao;

import nttdata.grupo.com.reportmicroservice.models.Movimiento;
import org.springframework.data.mongodb.repository.Query;
import reactor.core.publisher.Flux;

public interface TransactionDAO {
  Flux<Movimiento> findByCuentaBancariaNumero(String idProducto);
  Flux<Movimiento> findTop10ByOrderByCreatedDesc(String idProducto);
}
