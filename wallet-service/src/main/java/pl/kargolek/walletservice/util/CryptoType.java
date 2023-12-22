package pl.kargolek.walletservice.util;

/**
 * @author Karol Kuta-Orlowicz
 */
public enum CryptoType {

    ETHEREUM("Ethereum", "ETH"),
    POLYGON("Polygon", "MATIC"),
    AVALANCHE("Avalanche", "AVAX"),
    UNKNOWN("Unknown", "UNKNOWN");

    CryptoType(String name, String symbol) {
        this.name = name;
        this.symbol = symbol;
    }

    private final String name;
    private final String symbol;

    public String getName(){
        return this.name;
    }

    public String getSymbol(){
        return this.symbol;
    }

}
