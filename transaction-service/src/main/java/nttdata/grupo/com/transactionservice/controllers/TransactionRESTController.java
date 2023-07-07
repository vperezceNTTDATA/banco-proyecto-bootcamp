package nttdata.grupo.com.transactionservice.controllers;

import io.reactivex.Observable;
import nttdata.grupo.com.transactionservice.models.Movimiento;
import nttdata.grupo.com.transactionservice.services.TransactionRESTService;
import nttdata.grupo.com.transactionservice.services.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(TransactionRESTController.TRANSACTION)
public class TransactionRESTController {
  private static final Logger logger = LoggerFactory.getLogger(TransactionController.class);
  public static final String GET_TRANSACTION_ACCOUNT = "/by-account/{idProducto}";
  public static final String GET_10_TRANSACTION = "/10/product/{idProducto}";
  public static final String POST_TRANSACTION_WALLET = "/wallet";
  public static final String TRANSACTION = "/api/transactions";

  @Autowired
  private TransactionRESTService transactionRESTService;

  @RequestMapping(value = TransactionRESTController.GET_TRANSACTION_ACCOUNT, produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  public Flux<Movimiento> findByCuentaBancariaNumero(@PathVariable String idProducto) {
    logger.info("INI - findByCuentaBancariaNumero");
    return transactionRESTService.findByCuentaBancariaNumero(idProducto);
  }
  @RequestMapping(value = TransactionRESTController.GET_10_TRANSACTION, produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  public Flux<Movimiento> findTop10ByOrderByCreatedDesc(@PathVariable String idProducto) {
    logger.info("INI - findTop10ByOrderByCreatedDesc");
    return transactionRESTService.findTop10ByOrderByCreatedDesc(idProducto);
  }
  @PostMapping(TransactionRESTController.POST_TRANSACTION_WALLET)
  public Mono<ResponseEntity<Void>> insertTransactionWallet(@RequestBody Movimiento movimiento) {
    logger.info("INI - insertTransactionWallet");
    return transactionRESTService.insertTransactionWallet(movimiento).map(savedData -> ResponseEntity.ok().build());
  }

}
