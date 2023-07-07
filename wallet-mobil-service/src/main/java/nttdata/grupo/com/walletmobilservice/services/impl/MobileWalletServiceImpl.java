package nttdata.grupo.com.walletmobilservice.services.impl;

import io.reactivex.Single;
import nttdata.grupo.com.walletmobilservice.dao.BankAccountDAO;
import nttdata.grupo.com.walletmobilservice.dao.TransactionDAO;
import nttdata.grupo.com.walletmobilservice.models.MobilWallet;
import nttdata.grupo.com.walletmobilservice.models.Movimiento;
import nttdata.grupo.com.walletmobilservice.models.dto.AccountRequest;
import nttdata.grupo.com.walletmobilservice.models.dto.ResponseDTO;
import nttdata.grupo.com.walletmobilservice.models.exepcion.ConflictException;
import nttdata.grupo.com.walletmobilservice.models.exepcion.NotFoundException;
import nttdata.grupo.com.walletmobilservice.repositories.MobileWalletRepository;
import nttdata.grupo.com.walletmobilservice.services.MobileWalletService;
import nttdata.grupo.com.walletmobilservice.util.Constantes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@Service
public class MobileWalletServiceImpl implements MobileWalletService {
  private static final Logger logger = LoggerFactory.getLogger(MobileWalletServiceImpl.class);
  @Autowired
  private MobileWalletRepository mobileWalletRepository;
  @Autowired
  private TransactionDAO transactionDAO;
  @Autowired
  private BankAccountDAO bankAccountDAO;
  @Override
  public Single<MobilWallet> createMobileWalletAccount(AccountRequest accountRequest) {
    logger.info("INI - createMobileWalletAccount - ServiceIMPL");
    return Single.fromPublisher(mobileWalletRepository.findByMobileNumber(accountRequest.getMobileNumber())
            .map(wallet -> Mono.error(new NotFoundException("Usuario: " + accountRequest.getMobileNumber() + " tiene un monedero asignado.")))
        .then(mobileWalletRepository.save(new MobilWallet(accountRequest.getMobileNumber(), accountRequest.getMobileIMEI(), accountRequest.getEmail(), accountRequest.getNumberDebitCard()))));
  }

  @Override
  public Single<ResponseDTO> makeWalletMobileRetiro(String mobileNumber, BigDecimal monto) {
    return Single.fromPublisher(mobileWalletRepository.findByMobileNumberAndWithDebitCard(mobileNumber, true)
            .flatMap(mobilWallet -> bankAccountDAO.findBankAccountByDebitCard(mobilWallet.getNumberDebitCard())
                .flatMap(bankAccount -> {
                  if(bankAccount.getSaldo().compareTo(monto) < 0)return Mono.error(new ConflictException("No tienes suficiente saldo."));
                  bankAccount.setSaldo(bankAccount.getSaldo().subtract(monto));
                  bankAccountDAO.updateBankAccount(bankAccount).subscribe();
                  transactionDAO.insertTransactionWallet(new Movimiento(bankAccount.getId(), Constantes.WALLET_RETIRO, monto, mobileNumber)).subscribe();
                  return Mono.just(new ResponseDTO(true, ""));
                })
                .switchIfEmpty(Mono.error(new NotFoundException("Ha ocurrido un error con su número de tarjeta de debito vinculada a: " + mobileNumber + "."))))
            .defaultIfEmpty(new ResponseDTO(false,""))
            .flatMap(responseDTO -> {
                  if(!responseDTO.isValid()) {
                    return mobileWalletRepository.findByMobileNumberAndWithDebitCard(mobileNumber, false)
                        .flatMap(mobileWallet -> {
                          if(mobileWallet.getSaldo().compareTo(monto) < 0)return Mono.error(new ConflictException("No tienes suficiente saldo."));
                          mobileWallet.setSaldo(mobileWallet.getSaldo().subtract(monto));
                          mobileWalletRepository.save(mobileWallet).subscribe();
                          transactionDAO.insertTransactionWallet(new Movimiento(mobileWallet.getId(), Constantes.WALLET_RETIRO, monto, mobileNumber)).subscribe();
                          return Mono.just(new ResponseDTO(true, ""));
                        })
                        .switchIfEmpty(Mono.error(new NotFoundException("Número de celular: " + mobileNumber + " no tiene monedero creado.")));
                  }
                  return Mono.just(new ResponseDTO(true, ""));
                })
            .switchIfEmpty(Mono.error(new NotFoundException("Número de celular: " + mobileNumber + " no tiene monedero creado."))));
  }

  @Override
  public Single<ResponseDTO> makeWalletMobilePaid(String mobileNumber, BigDecimal monto) {
    return Single.fromPublisher(mobileWalletRepository.findByMobileNumberAndWithDebitCard(mobileNumber, true)
        .flatMap(mobilWallet -> bankAccountDAO.findBankAccountByDebitCard(mobilWallet.getNumberDebitCard())
            .flatMap(bankAccount -> {
              bankAccount.setSaldo(bankAccount.getSaldo().add(monto));
              bankAccountDAO.updateBankAccount(bankAccount).subscribe();
              transactionDAO.insertTransactionWallet(new Movimiento(bankAccount.getId(), Constantes.WALLET_PAGO, monto, mobileNumber)).subscribe();
              return Mono.just(new ResponseDTO(true, ""));
            })
            .switchIfEmpty(Mono.error(new NotFoundException("Ha ocurrido un error con su número de tarjeta de debito vinculada a: " + mobileNumber + "."))))
        .defaultIfEmpty(new ResponseDTO(false,""))
        .flatMap(responseDTO -> {
          if(!responseDTO.isValid()) {
            return mobileWalletRepository.findByMobileNumberAndWithDebitCard(mobileNumber, false)
                .flatMap(mobileWallet -> {
                  mobileWallet.setSaldo(mobileWallet.getSaldo().add(monto));
                  mobileWalletRepository.save(mobileWallet).subscribe();
                  transactionDAO.insertTransactionWallet(new Movimiento(mobileWallet.getId(), Constantes.WALLET_PAGO, monto, mobileNumber)).subscribe();
                  return Mono.just(new ResponseDTO(true, ""));
                })
                .switchIfEmpty(Mono.error(new NotFoundException("Número de celular: " + mobileNumber + " no tiene monedero creado.")));
          }
          return Mono.just(new ResponseDTO(true, ""));
        })
        .switchIfEmpty(Mono.error(new NotFoundException("Número de celular: " + mobileNumber + " no tiene monedero creado."))));
  }
}
