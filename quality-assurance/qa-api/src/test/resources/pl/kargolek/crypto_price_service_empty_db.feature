@crypto_price_service_api_test_empty_db
@crypto_price_service_api_test
Feature: CryptoPriceService API Test - empty db

  Background: The system has empty db
    Given crypto price service db data deleted

  @severity=normal
  Scenario: As user when db empty and GET resource /cryptocurrency
    Given valid API endpoint
    When send GET request to path "/cryptocurrency"
    Then receive status code 200
    And receive data is json format
    And receive empty body

  @severity=normal
  Scenario: As user when db empty and GET and query resource by name /cryptocurrency?name={token_name}
    Given valid API endpoint
    When send GET request to path "/cryptocurrency?name=Ethereum"
    Then receive status code 200
    And receive data is json format
    And receive empty body

  @severity=normal
  Scenario: As user when db empty and GET resource /cryptocurrency/contract-address/{address}
    Given valid API endpoint
    When send GET request to path "/cryptocurrency/contract-address/1234567890"
    Then receive status code 404
    And receive data is json format
    And receive valid json data
      | status  | NOT_FOUND                                                           |
      | message | Provided contract address is not exist, contractAddress: 1234567890 |

  @severity=normal
  Scenario: As user when db empty and DELETE resource /cryptocurrency/{id}
    Given valid API endpoint
    When send DELETE request to path "/cryptocurrency/1"
    Then receive status code 404
    And receive data is json format
    And receive valid json data
      | status  | NOT_FOUND                                                                             |
      | message | No class pl.kargolek.cryptopriceservice.model.Cryptocurrency entity with id 1 exists! |

  @severity=normal
  Scenario: As user when db empty and PUT json data to the resource /cryptocurrency/{id}
    Given valid API endpoint
    When send PUT json data to path "/cryptocurrency/1"
      | name         | Ethereum            |
      | symbol       | ETH                 |
      | platform     | platformExample     |
      | tokenAddress | tokenAddressExample |
    Then receive status code 404
    And receive valid json data
      | status  | NOT_FOUND                                |
      | message | Unable to find cryptocurrency with id: 1 |

  @severity=normal
  Scenario: As user when db empty and POST json data to the resource /cryptocurrency/{id}
    Given valid API endpoint
    When send POST json data to path "/cryptocurrency"
      | name   | Ethereum |
      | symbol | ETH      |
    Then receive status code 201
    And receive valid json data for crypto-price "Ethereum-default"