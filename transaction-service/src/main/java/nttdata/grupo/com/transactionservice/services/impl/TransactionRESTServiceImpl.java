package nttdata.grupo.com.transactionservice.services.impl;

import nttdata.grupo.com.transactionservice.models.Movimiento;
import nttdata.grupo.com.transactionservice.repositories.MovimientosRepository;
import nttdata.grupo.com.transactionservice.services.TransactionRESTService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TransactionRESTServiceImpl implements TransactionRESTService {
  private static final Logger logger = LoggerFactory.getLogger(TransactionRESTServiceImpl.class);
  @Autowired
  private MovimientosRepository movimientosRepository;

  @Override
  public Flux<Movimiento> findByCuentaBancariaNumero(String idProducto) {
    return movimientosRepository.findByCuentaBancariaNumero(idProducto);
  }

  @Override
  public Flux<Movimiento> findTop10ByOrderByCreatedDesc(String idProducto) {
    return movimientosRepository.findTop10ByOrderByCreatedDesc(idProducto);
  }
  @Override
  public Mono<Movimiento> insertTransactionWallet(Movimiento movimiento) {
    logger.info("INI - insertTransactionWallet - ServiceIMPL");
    return movimientosRepository.save(movimiento);
  }
}
