package pl.kargolek.util;

/**
 * @author Karol Kuta-Orlowicz
 */
public class PathResolver {
    public static final String TARGET_PATH = System.getProperty("user.dir") + "/target";
    public static final String TEST_RESOURCES_PATH = Thread.currentThread()
            .getContextClassLoader()
            .getResource("")
            .getPath();
}
