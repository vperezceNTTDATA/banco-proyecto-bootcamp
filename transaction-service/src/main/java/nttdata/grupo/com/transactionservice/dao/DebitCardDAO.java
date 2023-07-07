package nttdata.grupo.com.transactionservice.dao;

import nttdata.grupo.com.transactionservice.models.DebitCard;
import reactor.core.publisher.Mono;

public interface DebitCardDAO {
  Mono<DebitCard> findByCardNumber(String cardNumber);
}
