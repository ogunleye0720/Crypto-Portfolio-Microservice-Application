package pl.kargolek.extension.devtools.model;

import org.openqa.selenium.devtools.v114.network.model.MonotonicTime;
import org.openqa.selenium.devtools.v114.network.model.Response;

/**
 * @author Karol Kuta-Orlowicz
 */
public record ResponseModel(MonotonicTime timestamp, Response response, String body) {}
