package pl.kargolek.cryptopriceservice.dto.controller;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * @author Karol Kuta-Orlowicz
 */
@Data
@Accessors(chain = true)
public class CryptocurrencyPostDTO {

    @NotEmpty(message = "Name cannot be an empty")
    @Size(min = 2, max = 100, message = "Name length exceeds range [2,100]")
    private String name;

    @NotEmpty(message = "Symbol cannot be an empty")
    @Size(min = 2, max = 20, message = "Symbol length exceeds range [2,20]")
    private String symbol;

    @Size(min = 2, max = 100, message = "Platform length exceeds range [2,100]")
    private String platform;

    @Size(min = 2, message = "Token address length exceeds range [2,255]")
    private String tokenAddress;
}
