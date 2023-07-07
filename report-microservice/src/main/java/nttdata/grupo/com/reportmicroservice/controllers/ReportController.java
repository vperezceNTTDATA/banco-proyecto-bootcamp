package nttdata.grupo.com.reportmicroservice.controllers;


import io.reactivex.Observable;
import nttdata.grupo.com.reportmicroservice.models.Movimiento;
import nttdata.grupo.com.reportmicroservice.models.dto.ResumenSaldoDTO;
import nttdata.grupo.com.reportmicroservice.services.ReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ReportController.REPORT)
public class ReportController {

    private static final Logger logger = LoggerFactory.getLogger(ReportController.class);
    public static final String GET_TRANSACTIONSByACCOUNT = "/{idProduct}/product";
    public static final String GET_COMMISSIONS = "/{idProduct}/commission";
    public static final String GET_RESUMEN_DAILY = "/{idProduct}/resumen";
    public static final String GET_RESUMEN_10_DEBIT_CREDIT = "/10/{idProduct}/debit-credit";
    public static final String REPORT = "/api/reports";

    @Autowired
    private ReportService reportService;

    @RequestMapping(value = ReportController.GET_COMMISSIONS, produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Observable<Movimiento> findCommisionByProduct(@PathVariable String idProduct) {
        logger.info("INI - findCommisionByProduct");
        return reportService.getCommissionsByProduct(idProduct);
    }
    @RequestMapping(value = ReportController.GET_RESUMEN_DAILY, produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Observable<ResumenSaldoDTO> findResumenSaldoDaily(@PathVariable String idProduct) {
        logger.info("INI - findResumenSaldoDaily");
        return reportService.getResumenDailyByProduct(idProduct);
    }
    @RequestMapping(value = ReportController.GET_RESUMEN_10_DEBIT_CREDIT, produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Observable<Movimiento> findTenResumenByDebitAndCreditCard(@PathVariable String idProduct) {
        logger.info("INI - findTenResumenByDebitAndCreditCard");
        return reportService.findTenResumenByDebitAndCreditCard(idProduct);
    }
    @RequestMapping(value = ReportController.GET_TRANSACTIONSByACCOUNT, produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Observable<Movimiento> findTransactionsByProduct(@PathVariable String idProduct) {
        logger.info("INI - findTransactionsByProduct");
        return reportService.findTransactionsByProduct(idProduct);
    }

}
