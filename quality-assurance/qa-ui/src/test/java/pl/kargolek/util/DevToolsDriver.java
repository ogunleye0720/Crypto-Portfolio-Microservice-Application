package pl.kargolek.util;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.HasDevTools;
import org.openqa.selenium.devtools.v114.network.Network;
import org.openqa.selenium.remote.Augmenter;
import pl.kargolek.extension.devtools.model.RequestModel;
import pl.kargolek.extension.devtools.model.ResponseModel;

import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author Karol Kuta-Orlowicz
 */
@Slf4j
public class DevToolsDriver {
    private final DevTools devTools;
    private final BlockingQueue<RequestModel> requests = new LinkedBlockingQueue<>();
    private final BlockingQueue<ResponseModel> responses = new LinkedBlockingQueue<>();

    public DevToolsDriver(DevToolsUtilsBuilder devToolsUtilsBuilder) {
        this.devTools = devToolsUtilsBuilder.devTools;
    }

    public DevToolsDriver setNetworkRequestListener() {
        devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));
        devTools.addListener(Network.requestWillBeSent(),
                request -> {
                    try {
                        requests.put(new RequestModel(request.getTimestamp(), request.getRequest()));
                    } catch (InterruptedException e) {
                        log.error("Error in parse request data");
                    }
                });
        return this;
    }

    public DevToolsDriver setNetworkResponsesListener() {
        devTools.send(Network.enable(Optional.of(100000), Optional.empty(), Optional.empty()));
        devTools.addListener(Network.responseReceived(),
                responseReceived -> {
                    try {
                        var ids = responseReceived.getRequestId();
                        var responseBody = devTools.send(Network.getResponseBody(ids)).getBody();
                        int endIndex = Math.min(responseBody.length(), 100);
                        responses.put(
                                new ResponseModel(
                                        responseReceived.getTimestamp(),
                                        responseReceived.getResponse(),
                                        responseBody.substring(0, endIndex)
                                )
                        );
                    } catch (InterruptedException | WebDriverException e) {
                        log.info("Error in parse dev tools request data");
                    }
                });
        return this;
    }

    public DevToolsDriver closeConnection() {
        this.devTools.close();
        return this;
    }

    public ArrayList<RequestModel> getRequests() {
        var arrayRequest = new ArrayList<RequestModel>();
        requests.drainTo(arrayRequest);
        requests.clear();
        return arrayRequest;
    }

    public ArrayList<ResponseModel> getResponses() {
        var arrayRequest = new ArrayList<ResponseModel>();
        responses.drainTo(arrayRequest);
        responses.clear();
        return arrayRequest;
    }

    public static DevToolsUtilsBuilder builder(WebDriver webDriver) {
        return new DevToolsUtilsBuilder(webDriver);
    }

    public static class DevToolsUtilsBuilder {
        private final WebDriver webDriver;
        private DevTools devTools;

        public DevToolsUtilsBuilder(WebDriver webDriver) {
            this.webDriver = new Augmenter().augment(webDriver);
        }

        public DevToolsUtilsBuilder createConnection() {
            this.devTools = ((HasDevTools) webDriver).getDevTools();
            this.devTools.createSession();
            devTools.send(Network.clearBrowserCache());
            return this;
        }

        public DevToolsDriver build() {
            return new DevToolsDriver(this);
        }
    }
}
