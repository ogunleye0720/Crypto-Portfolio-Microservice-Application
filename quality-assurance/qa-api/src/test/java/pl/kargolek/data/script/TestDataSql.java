package pl.kargolek.data.script;

import pl.kargolek.data.TestData;
import pl.kargolek.data.dto.CryptocurrencyTableDTO;
import pl.kargolek.data.jdbc.MySqlDbConnector;
import pl.kargolek.data.model.Cryptocurrency;
import pl.kargolek.data.model.Price;
import pl.kargolek.utils.TestProperty;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * @author Karol Kuta-Orlowicz
 */

public class TestDataSql {

    private final MySqlDbConnector mySqlDbConnector;
    private final TestData data;

    public TestDataSql(MySqlDbConnector mySqlDbConnector) {
        this.mySqlDbConnector = mySqlDbConnector;
        this.data = new TestData();
    }

    public TestDataSql(){
        this.mySqlDbConnector = MySqlDbConnector.builder()
                .setDataSourceURL(TestProperty.getInstance().getDataSourceURL())
                .setUsername(TestProperty.getInstance().getDbUsername())
                .setPassword(TestProperty.getInstance().getDbPassword())
                .build();
        this.data = new TestData();
    }

    public void deleteCryptocurrenciesData() {
        var deletePriceSql = "DELETE FROM price";
        var deleteCryptocurrencySql = "DELETE FROM cryptocurrency";

        mySqlDbConnector.getJdbcTemplate().update(deletePriceSql);
        mySqlDbConnector.getJdbcTemplate().update(deleteCryptocurrencySql);
    }

    public void insertPriceData(Price price) {
        var sql = "INSERT INTO price " +
                "(id, cryptocurrency_id, price_current, percent_change_1h, percent_change_24h, percent_change_7d, percent_change_30d, percent_change_60d, percent_change_90d, last_update) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        mySqlDbConnector.getJdbcTemplate().update(
                sql,
                price.getId(),
                price.getCryptocurrencyId(),
                price.getPriceCurrent(),
                price.getPercentChange1h(),
                price.getPercentChange24h(),
                price.getPercentChange7d(),
                price.getPercentChange30d(),
                price.getPercentChange60d(),
                price.getPercentChange90d(),
                price.getLastUpdate()
          );
    }

    public void insertCryptocurrencyData(Cryptocurrency cryptocurrency) {
        var sql = "INSERT INTO cryptocurrency (id, name, symbol, coin_market_id, platform, token_address, last_update) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        mySqlDbConnector.getJdbcTemplate().update(
                sql,
                cryptocurrency.getId(),
                cryptocurrency.getName(),
                cryptocurrency.getSymbol(),
                cryptocurrency.getCoinMarketId(),
                cryptocurrency.getPlatform(),
                cryptocurrency.getTokenAddress(),
                cryptocurrency.getLastUpdate()
        );
    }

    public void insertCryptocurrencyTableData(CryptocurrencyTableDTO cryptocurrencyTable) {
        var cryptocurrencySql = "INSERT INTO cryptocurrency (id, name, symbol, coin_market_id, platform, token_address, last_update) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        var priceSql = "INSERT INTO price " +
                "(id, cryptocurrency_id, price_current, percent_change_1h, percent_change_24h, percent_change_7d, percent_change_30d, percent_change_60d, percent_change_90d, last_update) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";


        mySqlDbConnector.getJdbcTemplate().update(
                cryptocurrencySql,
                cryptocurrencyTable.getId(),
                cryptocurrencyTable.getName(),
                cryptocurrencyTable.getSymbol(),
                cryptocurrencyTable.getCoinMarketId(),
                cryptocurrencyTable.getPlatform(),
                cryptocurrencyTable.getTokenAddress(),
                LocalDateTime.now(ZoneOffset.UTC)
        );

        mySqlDbConnector.getJdbcTemplate().update(
                priceSql,
                cryptocurrencyTable.getPriceId(),
                cryptocurrencyTable.getId(),
                cryptocurrencyTable.getPriceCurrent(),
                cryptocurrencyTable.getPercentChange1h(),
                cryptocurrencyTable.getPercentChange24h(),
                cryptocurrencyTable.getPercentChange7d(),
                cryptocurrencyTable.getPercentChange30d(),
                cryptocurrencyTable.getPercentChange60d(),
                cryptocurrencyTable.getPercentChange90d(),
                LocalDateTime.now(ZoneOffset.UTC)
        );
    }

    public void insertEthereumData() {
        this.insertCryptocurrencyData(this.data.getEthereum());
        this.insertPriceData(this.data.getEthereumPrice());
    }

    public void insertEthereumDataPricePastNegative() {
        this.insertCryptocurrencyData(this.data.getEthereum());
        this.insertPriceData(this.data.getEthereumPricePastNegative());
    }

    public void insertEthereumDataPricePastNegativeMinus10Each() {
        this.insertCryptocurrencyData(this.data.getEthereum());
        this.insertPriceData(this.data.getEthereumPricePastNegativeMinus10Each());
    }


    public void insertPolygonData() {
        this.insertCryptocurrencyData(this.data.getPolygon());
        this.insertPriceData(this.data.getPolygonPrice());
    }

    public void insertPolygonDataPricePastNegative() {
        this.insertCryptocurrencyData(this.data.getPolygon());
        this.insertPriceData(this.data.getPolygonPricePastNegative());
    }

    public void insertPolygonDataPricePastNegativeMinus10Each() {
        this.insertCryptocurrencyData(this.data.getPolygon());
        this.insertPriceData(this.data.getPolygonPricePastNegativeMinus10Each());
    }

    public void insertAvalancheData() {
        this.insertCryptocurrencyData(this.data.getAvalanche());
        this.insertPriceData(this.data.getAvalanchePrice());
    }

    public void insertAvalancheDataPricePastNegative() {
        this.insertCryptocurrencyData(this.data.getAvalanche());
        this.insertPriceData(this.data.getAvalanchePricePastNegative());
    }

    public void insertAvalancheDataPricePastNegativeMinus10Each() {
        this.insertCryptocurrencyData(this.data.getAvalanche());
        this.insertPriceData(this.data.getAvalanchePricePastNegativeMinus10Each());
    }

    public void insertTokenTableData(CryptocurrencyTableDTO cryptocurrencyTableDTO){
        this.insertCryptocurrencyTableData(cryptocurrencyTableDTO);
    }


}
