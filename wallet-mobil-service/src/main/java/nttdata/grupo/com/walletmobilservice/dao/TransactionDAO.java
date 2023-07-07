package nttdata.grupo.com.walletmobilservice.dao;

import nttdata.grupo.com.walletmobilservice.models.Movimiento;
import reactor.core.publisher.Mono;

public interface TransactionDAO {
  Mono<Movimiento> insertTransactionWallet(Movimiento movimiento);
}
