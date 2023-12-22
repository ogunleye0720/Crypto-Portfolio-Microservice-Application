package pl.kargolek.walletservice.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import pl.kargolek.walletservice.util.CryptoType;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Karol Kuta-Orlowicz
 */
@Tag("UnitTest")
class WalletExplorerServiceTest {

    private WalletExplorerService underTest;
    private final String etherscanExplorerBaseUrl = "https://etherscan.io/address/";
    private final String polygonExplorerBaseUrl = "https://etherscan.io/address/";
    private final String avalanchescanExplorerBaseUrl = "https://snowtrance.io/address/";

    @BeforeEach
    void setUp() {
        underTest = new WalletExplorerService(etherscanExplorerBaseUrl, polygonExplorerBaseUrl, avalanchescanExplorerBaseUrl);
    }

    @Test
    void whenWalletAddress_thenReturnWalletEtherscanExplorerHref() {
        var walletAddress = "0x0";

        var expected = underTest.getWalletExplorerAddress(walletAddress, CryptoType.ETHEREUM);

        assertThat(expected).isEqualTo(etherscanExplorerBaseUrl + walletAddress);
    }

    @Test
    void whenWalletAddress_thenReturnWalletPolygonscanExplorerHref() {
        var walletAddress = "0x0";

        var expected = underTest.getWalletExplorerAddress(walletAddress, CryptoType.POLYGON);

        assertThat(expected).isEqualTo(polygonExplorerBaseUrl + walletAddress);
    }

    @Test
    void whenWalletAddress_thenReturnWalletAvalanchescanExplorerHref() {
        var walletAddress = "0x0";

        var expected = underTest.getWalletExplorerAddress(walletAddress, CryptoType.AVALANCHE);

        assertThat(expected).isEqualTo(avalanchescanExplorerBaseUrl + walletAddress);
    }


    @Test
    void whenCryptoTypeUnknown_thenReturnUnknown() {
        var walletAddress = "0x0";

        var expected = underTest.getWalletExplorerAddress(walletAddress, CryptoType.UNKNOWN);

        assertThat(expected).isEqualTo("unknown");
    }
}