package pl.kargolek.steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import pl.kargolek.data.script.TestDataSql;
import pl.kargolek.mapper.DataTableMapper;

import java.util.List;
import java.util.Map;

/**
 * @author Karol Kuta-Orlowicz
 */
public class CommonSteps {

    private final TestDataSql dataSql = new TestDataSql();

    @Given("crypto price service db data deleted")
    public void priceServiceDbDoesNotHaveData() {
        dataSql.deleteCryptocurrenciesData();
    }

    @And("adding ethereum token default data to db")
    public void ethereumTokenDataToDb() {
        dataSql.insertEthereumData();
    }

    @And("adding polygon token default data to db")
    public void polygonTokenDataToDb() {
        dataSql.insertPolygonData();
    }

    @And("adding avalanche token default data to db")
    public void avalancheTokenDataToDb() {
        dataSql.insertAvalancheData();
    }

    @And("adding tokens default data to db")
    public void tokenDataToDb() {
        dataSql.insertEthereumData();
        dataSql.insertAvalancheData();
        dataSql.insertPolygonData();
    }

    @And("adding token data to db")
    public void addingTokenDataToDb(List<Map<String, String>> data) {
        var mappedEntries = DataTableMapper.mapCryptocurrencyTableDto(data);
        mappedEntries.forEach(dataSql::insertCryptocurrencyTableData);
    }
}
