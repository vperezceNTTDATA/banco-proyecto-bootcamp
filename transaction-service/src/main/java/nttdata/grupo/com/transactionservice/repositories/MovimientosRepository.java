package nttdata.grupo.com.transactionservice.repositories;

import nttdata.grupo.com.transactionservice.models.Movimiento;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface MovimientosRepository extends ReactiveMongoRepository<Movimiento, ObjectId> {
    @Query(value = "{'idProduct': ?0}")
    Flux<Movimiento> findByCuentaBancariaNumero(String idProducto);
    @Query(value = "{'idProduct': ?0}")
    Flux<Movimiento> findTop10ByOrderByCreatedDesc(String idProducto);
}
