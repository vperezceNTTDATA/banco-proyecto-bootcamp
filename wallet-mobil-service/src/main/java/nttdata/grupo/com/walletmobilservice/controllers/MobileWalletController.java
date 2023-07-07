package nttdata.grupo.com.walletmobilservice.controllers;

import io.reactivex.Single;
import nttdata.grupo.com.walletmobilservice.models.MobilWallet;
import nttdata.grupo.com.walletmobilservice.models.dto.AccountRequest;
import nttdata.grupo.com.walletmobilservice.models.dto.ResponseDTO;
import nttdata.grupo.com.walletmobilservice.services.MobileWalletService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;

@RestController
@RequestMapping(MobileWalletController.WALLETS)
public class MobileWalletController {
  private static final Logger logger = LoggerFactory.getLogger(MobileWalletController.class);

  public static final String POST_MOBILE_WALLET = "/mobile-wallet";
  public static final String POST_WALLET_RETIRO = "/{mobileNumber}/mobileNumber/retiro";
  public static final String POST_WALLET_PAGO = "/{mobileNumber}/mobileNumber/pago";
  public static final String WALLETS = "/api/wallets";
  @Autowired
  private MobileWalletService mobileWalletService;

  @PostMapping(MobileWalletController.POST_MOBILE_WALLET)
  public Single<MobilWallet> createMobileWalletAccount(@RequestBody @Valid AccountRequest accountRequest) {
    logger.info("INI - createMobileWalletAccount");
    return mobileWalletService.createMobileWalletAccount(accountRequest);
  }
  @PostMapping(MobileWalletController.POST_WALLET_RETIRO)
  public Single<ResponseEntity<String>> doWalletRetiro(@PathVariable String mobileNumber, @RequestParam String monto) {
    logger.info("INI - doWalletPago");
    return mobileWalletService.makeWalletMobileRetiro(mobileNumber, new BigDecimal(monto))
        .filter(ResponseDTO::isValid)
        .map(response -> ResponseEntity.status(HttpStatus.CREATED).body("Retiro al monedero realizado exitosamente."))
        .switchIfEmpty(Single.just(ResponseEntity.status(HttpStatus.ACCEPTED).body("Retiro ya está pagado")));
  }
  @PostMapping(MobileWalletController.POST_WALLET_PAGO)
  public Single<ResponseEntity<String>> doWalletPago(@PathVariable String mobileNumber, @RequestParam String monto) {
    logger.info("INI - doWalletPago");
    return mobileWalletService.makeWalletMobilePaid(mobileNumber, new BigDecimal(monto))
        .filter(ResponseDTO::isValid)
        .map(response -> ResponseEntity.status(HttpStatus.CREATED).body("Pago al monedero realizado exitosamente."))
        .switchIfEmpty(Single.just(ResponseEntity.status(HttpStatus.ACCEPTED).body("Pago al monedero ya está pagado")));
  }
}
