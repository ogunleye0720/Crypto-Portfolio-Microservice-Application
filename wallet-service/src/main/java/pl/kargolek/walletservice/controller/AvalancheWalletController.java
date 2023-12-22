package pl.kargolek.walletservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.kargolek.walletservice.dto.JsonApiErrorDTO;
import pl.kargolek.walletservice.dto.UserWallet;
import pl.kargolek.walletservice.service.balance.avalanche.AvalancheBalanceService;

/**
 * @author Karol Kuta-Orlowicz
 */
@RestController
@RequestMapping(path = "api/v1/wallet/avax")
public class AvalancheWalletController {

    @Autowired
    private AvalancheBalanceService avalancheBalanceService;

    @Operation(summary = "Get avalanche wallet balance")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserWallet.class))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = JsonApiErrorDTO.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = JsonApiErrorDTO.class)))
    })
    @GetMapping("/balance")
    public ResponseEntity<UserWallet> getAvaxWalletsBalance(@RequestParam(name = "wallets") String wallets){
        var userWallets = avalancheBalanceService.getMultiBalance(wallets);
        return new ResponseEntity<>(userWallets, HttpStatus.OK);
    }
}
