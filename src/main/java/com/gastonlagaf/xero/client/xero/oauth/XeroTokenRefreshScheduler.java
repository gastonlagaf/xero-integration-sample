package com.gastonlagaf.xero.client.xero.oauth;

import com.gastonlagaf.xero.client.xero.XeroClient;
import com.gastonlagaf.xero.entity.XeroToken;
import com.gastonlagaf.xero.exception.XeroTokenNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class XeroTokenRefreshScheduler {

    private static final Double REFRESH_DELAY_MULTIPLIER = 0.5;
    private static final Integer DEFAULT_REFRESH_DELAY = 10;

    private final XeroClient xeroClient;
    private final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
    private final Runnable process = new Runnable() {
        @Override
        public void run() {
            refresh(this);
        }
    };

    private ScheduledFuture<?> future;

    public XeroTokenRefreshScheduler(XeroClient xeroClient) {
        this.xeroClient = xeroClient;
        initScheduler();
    }

    public void initScheduler() {
        process.run();
    }

    private void refresh(Runnable runnable) {
        XeroToken xeroToken = null;
        try {
            xeroToken = xeroClient.refreshToken();
            log.info("Xero Token refreshed");
        } catch (XeroTokenNotFoundException ex) {
            log.warn("Xero Token not located");
        } catch (Exception ex) {
            log.error("Unknown error occured on Xero Token refresh: " + ex.getMessage());
        } finally {
            if (null == future) {
                future = executorService.scheduleAtFixedRate(process, DEFAULT_REFRESH_DELAY, DEFAULT_REFRESH_DELAY, TimeUnit.SECONDS);
            }
        }
        if (null != xeroToken) {
            Optional.of(future).ifPresent(it -> it.cancel(false));
            long delay = Math.round(xeroToken.getExpiry() * REFRESH_DELAY_MULTIPLIER);
            future = executorService.scheduleAtFixedRate(runnable, delay, delay, TimeUnit.SECONDS);
        }
    }

}
