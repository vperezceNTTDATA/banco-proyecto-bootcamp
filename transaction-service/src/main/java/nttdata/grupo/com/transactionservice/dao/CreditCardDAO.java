package nttdata.grupo.com.transactionservice.dao;

import nttdata.grupo.com.transactionservice.models.Credit;
import nttdata.grupo.com.transactionservice.models.CreditCard;
import reactor.core.publisher.Mono;

public interface CreditCardDAO {
  Mono<CreditCard> findByNumeroAndIdCliente(String numero, String idCliente);
  Mono<CreditCard> updateCreditCard(CreditCard creditCard);
}
