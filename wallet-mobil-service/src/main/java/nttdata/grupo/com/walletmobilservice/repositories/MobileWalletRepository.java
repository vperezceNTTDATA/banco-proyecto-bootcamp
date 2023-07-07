package nttdata.grupo.com.walletmobilservice.repositories;

import nttdata.grupo.com.walletmobilservice.models.MobilWallet;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface MobileWalletRepository extends ReactiveMongoRepository<MobilWallet, ObjectId> {
  Mono<MobilWallet> findByMobileNumber(String mobileNumber);

  Mono<MobilWallet> findByMobileNumberAndWithDebitCard(String mobileNumber, boolean withDebitCard);
}
