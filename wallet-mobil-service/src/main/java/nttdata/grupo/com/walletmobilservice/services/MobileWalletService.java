package nttdata.grupo.com.walletmobilservice.services;


import io.reactivex.Single;
import nttdata.grupo.com.walletmobilservice.models.MobilWallet;
import nttdata.grupo.com.walletmobilservice.models.dto.AccountRequest;
import nttdata.grupo.com.walletmobilservice.models.dto.ResponseDTO;

import java.math.BigDecimal;

public interface MobileWalletService {
  Single<MobilWallet> createMobileWalletAccount(AccountRequest accountRequest);

  Single<ResponseDTO> makeWalletMobileRetiro(String mobileNumber, BigDecimal monto);
  Single<ResponseDTO> makeWalletMobilePaid(String mobileNumber, BigDecimal monto);
}
