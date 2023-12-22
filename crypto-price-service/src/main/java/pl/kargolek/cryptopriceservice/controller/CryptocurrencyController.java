package pl.kargolek.cryptopriceservice.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.kargolek.cryptopriceservice.dto.controller.CryptocurrencyPostDTO;
import pl.kargolek.cryptopriceservice.dto.model.CryptocurrencyDTO;
import pl.kargolek.cryptopriceservice.exception.JsonApiError;
import pl.kargolek.cryptopriceservice.mapper.CryptocurrencyMapper;
import pl.kargolek.cryptopriceservice.service.CryptocurrencyService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * @author Karol Kuta-Orlowicz
 */

@RequestMapping(path = "api/v1/cryptocurrency")
@RestController
@Slf4j
public class CryptocurrencyController {

    private static final CryptocurrencyMapper mapper = CryptocurrencyMapper.INSTANCE;
    @Autowired
    private CryptocurrencyService cryptocurrencyService;

    @GetMapping("/{id}")
    @Operation(summary = "Get cryptocurrency by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CryptocurrencyDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = JsonApiError.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = JsonApiError.class)))
    })
    public ResponseEntity<CryptocurrencyDTO> getCryptocurrencyById(@PathVariable("id") Long id) {
        var cryptocurrency = cryptocurrencyService.getById(id);
        var cryptocurrencyDto = mapper.convertEntityDto(cryptocurrency);
        return ResponseEntity.status(HttpStatus.OK).body(cryptocurrencyDto);
    }

    @GetMapping("/contract-address/{contractAddress}")
    @Operation(summary = "Get cryptocurrency by smart contract address")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CryptocurrencyDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = JsonApiError.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = JsonApiError.class)))
    })
    public ResponseEntity<CryptocurrencyDTO> getCryptocurrencyContractAddress(@PathVariable("contractAddress") String contractAddress) {
        var cryptocurrency = cryptocurrencyService.getBySmartContractAddress(contractAddress);
        var cryptocurrencyDto = mapper.convertEntityDto(cryptocurrency);
        return ResponseEntity.status(HttpStatus.OK).body(cryptocurrencyDto);
    }

    @Operation(summary = "Get all cryptocurrencies")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CryptocurrencyDTO.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = JsonApiError.class)))
    })
    @GetMapping("")
    public List<CryptocurrencyDTO> getCryptocurrencies(@RequestParam(name = "name", required = false) List<String> names) {
        return Optional.ofNullable(names).isPresent() ?
                cryptocurrencyService.getByName(names)
                        .stream()
                        .map(mapper::convertEntityDto)
                        .toList() :
                cryptocurrencyService.getCryptocurrencies()
                        .stream()
                        .map(mapper::convertEntityDto)
                        .toList();
    }

    @Operation(summary = "Register a cryptocurrency")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Created",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CryptocurrencyDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = JsonApiError.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = JsonApiError.class)))
    })
    @PostMapping("")
    public ResponseEntity<CryptocurrencyDTO> registerCryptocurrency(
            @Valid @RequestBody CryptocurrencyPostDTO cryptocurrencyPostDTO) {
        var registerCryptocurrency = mapper.convertPostEntity(cryptocurrencyPostDTO);
        var cryptocurrency = cryptocurrencyService.addCryptocurrency(registerCryptocurrency);
        var cryptocurrencyDTO = mapper.convertEntityDto(cryptocurrency);
        return ResponseEntity.status(HttpStatus.CREATED).body(cryptocurrencyDTO);
    }

    @Operation(summary = "Delete a cryptocurrency by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = JsonApiError.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = JsonApiError.class)))
    })
    @DeleteMapping("/{id}")
    public void deleteCryptocurrencyById(@PathVariable("id") Long id) {
        cryptocurrencyService.deleteCryptocurrency(id);
    }

    @Operation(summary = "Update a cryptocurrency by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CryptocurrencyDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = JsonApiError.class))),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = JsonApiError.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = JsonApiError.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<CryptocurrencyDTO> updateCryptocurrency(@PathVariable("id") Long id,
                                                                  @Valid @RequestBody CryptocurrencyPostDTO cryptocurrencyPostDTO) {
        var cryptocurrencyUpdate = mapper.convertPostEntity(cryptocurrencyPostDTO);
        var cryptocurrency = cryptocurrencyService.updateCryptocurrency(id, cryptocurrencyUpdate);
        var cryptocurrencyDto = mapper.convertEntityDto(cryptocurrency);
        return ResponseEntity.status(HttpStatus.OK).body(cryptocurrencyDto);
    }

}
