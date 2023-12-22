package pl.kargolek.util;

import pl.kargolek.util.constant.BrowserType;
import pl.kargolek.util.constant.HeadlessMode;

import java.io.IOException;

/**
 * @author Karol Kuta-Orlowicz
 */
public class VideoTestService {

    private final BrowserType browserType = TestProperty.getInstance().getBrowserType();
    private final ContainerProcessHandler handler = new ContainerProcessHandler();

    public void startRecord(int sleepAfterStartContainer) throws IOException, InterruptedException {
            handler.startDockerContainer(getRecordContainerName());
            Thread.sleep(sleepAfterStartContainer);
    }

    public void stopRecord() throws IOException, InterruptedException {
        handler.stopDockerContainer(getRecordContainerName());
    }

    private String getRecordContainerName(){
        if (browserType == BrowserType.CHROME || browserType == BrowserType.MOBILE_CHROME) {
            return ContainerNameResolver.CHROME_VIDEO_CONTAINER_NAME;
        } else if (browserType == BrowserType.FIREFOX) {
            return ContainerNameResolver.FIREFOX_VIDEO_CONTAINER_NAME;
        } else if (browserType == BrowserType.EDGE) {
            return ContainerNameResolver.EDGE_VIDEO_CONTAINER_NAME;
        }
        return ContainerNameResolver.CHROME_VIDEO_CONTAINER_NAME;
    }
}
