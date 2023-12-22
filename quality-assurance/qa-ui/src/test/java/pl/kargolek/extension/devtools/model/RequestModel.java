package pl.kargolek.extension.devtools.model;

import org.openqa.selenium.devtools.v114.network.model.MonotonicTime;
import org.openqa.selenium.devtools.v114.network.model.Request;

/**
 * @author Karol Kuta-Orlowicz
 */
public record RequestModel(MonotonicTime timestamp, Request request){}
