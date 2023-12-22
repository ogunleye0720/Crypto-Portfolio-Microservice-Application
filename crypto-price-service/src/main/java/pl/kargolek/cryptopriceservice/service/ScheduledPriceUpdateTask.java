package pl.kargolek.cryptopriceservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@Slf4j
@RequiredArgsConstructor
public class ScheduledPriceUpdateTask {

    @Autowired
    private PriceUpdateService priceUpdateService;

    @Scheduled(
            fixedDelayString = "${price.update.schedule.fixed.delay.seconds}",
            initialDelayString = "${price.update.schedule.initial.delay.seconds}",
            timeUnit = TimeUnit.SECONDS
    )
    public void scheduledUpdatePriceJob() {
        log.info("Running scheduled update price job");
        priceUpdateService.updateCryptocurrencyPrices();
    }
}
