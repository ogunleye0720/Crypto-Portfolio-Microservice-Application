package pl.kargolek.util;

import io.qameta.allure.Allure;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author Karol Kuta-Orlowicz
 */
@Slf4j
public class ReportAttachment {

    public enum AttachmentType {

        VIDEO_MP4("video/mp4", "mp4"),
        IMAGE_PNG("image/png", "png"),
        TEXT_LOG("text/plain", ".log");

        private final String format;
        private final String fileExtension;

        AttachmentType(String format, String fileExtension) {
            this.format = format;
            this.fileExtension = fileExtension;
        }

        public String getFormat() {
            return format;
        }

        public String getFileExtension() {
            return fileExtension;
        }
    }

    public void createAttachment(byte[] byteArray, String attachmentName, AttachmentType attachmentType) {
        Allure.addAttachment(
                attachmentName,
                attachmentType.getFormat(),
                new ByteArrayInputStream(byteArray),
                attachmentType.getFileExtension()
        );
    }

    public void createAttachment(File attachFile, String attachmentName, AttachmentType attachmentType) {
        try {
            byte[] byteArray = IOUtils.toByteArray(new FileInputStream(attachFile));
            Allure.addAttachment(
                    attachmentName,
                    attachmentType.getFormat(),
                    new ByteArrayInputStream(byteArray),
                    attachmentType.getFileExtension()
            );
        } catch (IOException e) {
            log.error("Unable to attach file to test report: {}", attachFile.getAbsolutePath());
        }
    }

}
