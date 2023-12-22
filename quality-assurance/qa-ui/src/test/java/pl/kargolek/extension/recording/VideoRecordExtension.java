package pl.kargolek.extension.recording;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import pl.kargolek.util.*;
import pl.kargolek.util.constant.BrowserType;
import pl.kargolek.util.constant.HeadlessMode;
import pl.kargolek.util.constant.ParallelTest;

import java.io.File;
import java.util.Arrays;
import java.util.Objects;

/**
 * @author Karol Kuta-Orlowicz
 */
@Slf4j
class VideoRecordExtension implements BeforeEachCallback, AfterTestExecutionCallback {

    private final BrowserType browserType = TestProperty.getInstance().getBrowserType();
    private final VideoTestService videoTestService = new VideoTestService();
    private final ReportAttachment reportAttachment = new ReportAttachment();

    @Override
    public void beforeEach(ExtensionContext extensionContext) throws Exception {
        if (TestProperty.getInstance().getParallelism() == ParallelTest.ENABLE)
            return;
        videoTestService.startRecord(1200);
    }

    @Override
    public void afterTestExecution(ExtensionContext extensionContext) throws Exception {
        if (TestProperty.getInstance().getParallelism() == ParallelTest.ENABLE ||
                TestProperty.getInstance().getHeadlessMode() == HeadlessMode.ENABLE)
            return;
        videoTestService.stopRecord();
        var tempVideo = getTempVideo();
        if (validateVideoTempFile(tempVideo)) {
            var attachmentVideo = new File(PathResolver.TARGET_PATH, "video-" + RandomUtils.nextLong() + ".mp4");
            tempVideo.renameTo(attachmentVideo);
            if (isVideoOnFailedTest(extensionContext) && isTestFailed(extensionContext)) {
                reportAttachment.createAttachment(attachmentVideo, "Video attachment", ReportAttachment.AttachmentType.VIDEO_MP4);
            } else {
                reportAttachment.createAttachment(attachmentVideo, "Video attachment", ReportAttachment.AttachmentType.VIDEO_MP4);
            }
        }
    }

    private File getTempVideo() {
        return switch (browserType) {
            case CHROME, MOBILE_CHROME -> new File(PathResolver.TARGET_PATH, "temp_video_chrome.mp4");
            case FIREFOX -> new File(PathResolver.TARGET_PATH, "temp_video_firefox.mp4");
            case EDGE -> new File(PathResolver.TARGET_PATH, "temp_video_edge.mp4");
            default -> throw new RuntimeException("Unable to get temp video. Unknown browser type");
        };
    }

    private boolean validateVideoTempFile(File tempVideo) {
        if (!tempVideo.exists()) {
            log.error("Video temp file doesn't exist. Attach video file process skipped.");
            return false;
        } else
            return true;
    }

    private boolean isVideoOnFailedTest(ExtensionContext extensionContext) {
        var annotationMethod = extensionContext
                .getTestMethod()
                .orElseThrow()
                .getAnnotation(VideoRecording.class);

        var annotationClass = extensionContext
                .getTestClass()
                .orElseThrow()
                .getAnnotation(VideoRecording.class);
        if (annotationClass != null) {
            return annotationClass.onFailedTest();
        }

        if (annotationMethod != null) {
            return annotationMethod.onFailedTest();
        }

        var videoRecordAnnotations = Arrays.stream(extensionContext
                        .getTestClass()
                        .orElseThrow()
                        .getAnnotations())
                .map(annotation -> annotation.annotationType().getAnnotation(VideoRecording.class))
                .filter(Objects::nonNull)
                .toList();

        if (videoRecordAnnotations.size() > 0)
            return videoRecordAnnotations.get(0).onFailedTest();

        return false;
    }

    private boolean isTestFailed(ExtensionContext extensionContext) {
        return extensionContext
                .getExecutionException()
                .isPresent();
    }
}
