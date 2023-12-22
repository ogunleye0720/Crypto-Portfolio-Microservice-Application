package pl.kargolek.walletservice.testutils.config;

import lombok.SneakyThrows;
import okhttp3.mockwebserver.MockWebServer;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.ContextClosedEvent;

import java.io.IOException;

/**
 * @author Karol Kuta-Orlowicz
 */
public class InitializerCryptoPriceMockWebServer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @SneakyThrows
    @Override
    public void initialize(@NotNull ConfigurableApplicationContext applicationContext) {
        MockWebServer mockWebServer = new MockWebServer();
        mockWebServer.start();

        applicationContext
                .getBeanFactory()
                .registerSingleton("mockWebServer", mockWebServer);

        applicationContext.addApplicationListener(listener -> {
            if (listener instanceof ContextClosedEvent) {
                try {
                    mockWebServer.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
