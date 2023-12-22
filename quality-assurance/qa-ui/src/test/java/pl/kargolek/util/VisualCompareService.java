package pl.kargolek.util;

import nu.pattern.OpenCV;
import org.apache.commons.io.FileUtils;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import pl.kargolek.util.constant.HeadlessMode;

import java.io.File;
import java.io.IOException;

/**
 * @author Karol Kuta-Orlowicz
 */
public class VisualCompareService {

    private static final String WEB_ELEMENT_IMAGE_TO_COMPARE = "visual/%s/expected/%s_base.png";
    private static final String WEB_ELEMENT_IMAGE_TO_COMPARE_HEADLESS = "visual/%s/expected/headless/%s_base.png";
    private static final String CURRENT_WEB_ELEMENT_IMAGE = "visual/%s.png";
    private final ReportAttachment attachment = new ReportAttachment();
    private final HeadlessMode headlessMode = TestProperty.getInstance().getHeadlessMode();
    private final String browser = TestProperty.getInstance().getBrowserType().toString().toLowerCase();

    public VisualCompareService(VisualCompareServiceBuilder visualCompareServiceBuilder) {
    }

    public static VisualCompareServiceBuilder builder() {
        return new VisualCompareServiceBuilder();
    }

    public double compareElement(WebElement element, String imgName) {
        validateTempFolder(imgName);
        takeScreenshotAndCopyTemp(element, imgName);

        var result = compereImagesProcess(imgName);

        attachImagesToReport(imgName);
        return result;
    }

    private void validateTempFolder(String imgName) {
        var baseFile = getCompareBaseFile(imgName);
        if (!baseFile.exists() && !baseFile.canRead())
            throw new RuntimeException("Unable to perform visual compare element. Base file doesn't exist.");
    }

    private void takeScreenshotAndCopyTemp(WebElement element, String imageName) {
        var imgElement = element.getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(imgElement, getCompareSystemFile(imageName));
        } catch (IOException e) {
            throw new RuntimeException("Unable to copy element image to visual folder");
        }
    }

    private double compereImagesProcess(String imageName) {
        Mat mat1 = Imgcodecs.imread(getCompareBaseFile(imageName).getPath(), Imgcodecs.IMREAD_GRAYSCALE);
        Mat mat2 = Imgcodecs.imread(getCompareSystemFile(imageName).getPath(), Imgcodecs.IMREAD_GRAYSCALE);

        if (!mat1.size().equals(mat2.size())) {
            Imgproc.resize(mat1, mat1, mat2.size());
        }

        Mat diff = new Mat(mat1.size(), CvType.CV_8UC1);
        Core.absdiff(mat1, mat2, diff);

        Mat threshold = new Mat(mat1.size(), CvType.CV_8UC1);
        Imgproc.threshold(diff, threshold, 0, 255, Imgproc.THRESH_BINARY | Imgproc.THRESH_OTSU);

        return (Core.countNonZero(threshold) * 100.0) / (threshold.size().width * threshold.size().height);
    }

    private void attachImagesToReport(String imgName) {
        attachment.createAttachment(getCompareBaseFile(imgName), "Web element base to compare",
                ReportAttachment.AttachmentType.IMAGE_PNG);
        attachment.createAttachment(getCompareSystemFile(imgName), "Web element on the system",
                ReportAttachment.AttachmentType.IMAGE_PNG);
    }

    private File getCompareSystemFile(String imgName){
        return new File(String.format(CURRENT_WEB_ELEMENT_IMAGE, imgName));
    }
    private File getCompareBaseFile(String imgName){
        return new File(String.format(getWebElementImageToCompare(), browser, imgName));
    }

    private String getWebElementImageToCompare() {
        if (headlessMode == HeadlessMode.DISABLE) {
            return WEB_ELEMENT_IMAGE_TO_COMPARE;
        } else
            return WEB_ELEMENT_IMAGE_TO_COMPARE_HEADLESS;
    }

    public static class VisualCompareServiceBuilder {
        public VisualCompareService build() {
            OpenCV.loadLocally();
            return new VisualCompareService(this);
        }
    }
}
