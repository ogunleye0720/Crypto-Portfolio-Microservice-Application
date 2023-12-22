package pl.kargolek.util;

import java.io.IOException;

/**
 * @author Karol Kuta-Orlowicz
 */
public class ContainerProcessHandler {

    public void startDockerContainer(String containerName) throws IOException, InterruptedException {
        ProcessBuilder pb = new ProcessBuilder("docker", "start", containerName);
        Process process = pb.start();
        process.waitFor();
    }

    public void stopDockerContainer(String containerName) throws IOException, InterruptedException {
        ProcessBuilder pb = new ProcessBuilder("docker", "stop", containerName);
        Process process = pb.start();
        process.waitFor();
    }
}
