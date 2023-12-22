package pl.kargolek.extension;

import pl.kargolek.extension.devtools.DevTools;
import pl.kargolek.extension.driver.SeleniumWebDriver;
import pl.kargolek.extension.logs.BrowserLogs;
import pl.kargolek.extension.pages.InitPageObject;
import pl.kargolek.extension.properties.TestProperties;
import pl.kargolek.extension.recording.VideoRecording;
import pl.kargolek.extension.screenshot.TakeScreenshot;
import pl.kargolek.extension.sql.MySqlScript;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;



/**
 * @author Karol Kuta-Orlowicz
 */

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@SeleniumWebDriver(isBeforeAll = false)
@MySqlScript
@TestProperties
@VideoRecording
@TakeScreenshot
@BrowserLogs
@DevTools
@InitPageObject
public @interface BaseTestConfig {}
